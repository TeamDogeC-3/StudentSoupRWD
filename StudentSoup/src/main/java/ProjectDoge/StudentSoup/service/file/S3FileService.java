package ProjectDoge.StudentSoup.service.file;

import ProjectDoge.StudentSoup.dto.file.UploadFileDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.exception.file.FileExtNotMatchException;
import ProjectDoge.StudentSoup.exception.file.ImageConvertException;
import ProjectDoge.StudentSoup.repository.file.FileRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor

public class S3FileService implements FileService {
    private final FileRepository fileRepository;
    private final AmazonS3Client amazonS3Client;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long join(MultipartFile multipartFile) {
        UploadFileDto uploadFileDto = storeFile(multipartFile);
        if (uploadFileDto == null)
            return null;

        ImageFile file = new ImageFile().createFile(uploadFileDto);
        fileRepository.save(file);
        log.info("S3에 파일 저장이 완료되었습니다.");
        return file.getId();
    }

    @Override
    public List<UploadFileDto> createUploadFileDtoList(List<MultipartFile> multipartFileList) {

        log.info("S3에 다중 파일 등록 서비스 메소드를 실행하였습니다.");
        List<UploadFileDto> uploadFileDtoList = new ArrayList<>();
        if (multipartFileList != null) {
            for (MultipartFile multipartFile : multipartFileList) {
                if (!multipartFileList.isEmpty()) {
                    uploadFileDtoList.add(storeFile(multipartFile));
                }
            }
        }
        log.info("S3에 다중 파일 등록 서비스 메소드를 완료되었습니다.");
        return uploadFileDtoList;
    }

    @Override
    public UploadFileDto storeFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            log.info("전송된 이미지 파일이 존재하지 않아 파일 저장 메소드가 실행되지 않습니다.");
            return null;
        }
        log.info("S3 파일 저장 메소드가 실행되었습니다.");
        File uploadFile = convertFile(multipartFile)
                .orElseThrow(() -> {
                    log.info("파일을 변환하는 동안 오류가 발생했습니다.");
                    throw new ImageConvertException("이미지 변환 오류가 발생했습니다.");
                });

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);
        String fullPath = getFullPath(storeFileName);

        log.info("uploadFileName : [{}], storeFileName : [{}]", uploadFile, storeFileName);

        amazonS3Client.putObject(
                new PutObjectRequest(bucket, storeFileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        removeFile(uploadFile);
        log.info("지정된 경로에 파일이 저장되었습니다. [{}]", bucket);

        return new UploadFileDto(originalFileName, storeFileName, fullPath);
    }

    private Optional<File> convertFile(MultipartFile file) {
        log.info("multipartFile 을 File 로 변환하는 메소드가 실행되었습니다.");
        File convertFile = new File(file.getOriginalFilename());
        FileOutputStream fos = null;
        try {
            if (convertFile.createNewFile()) {
                fos = new FileOutputStream(convertFile);
                fos.write(file.getBytes());
                return Optional.of(convertFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
        return Optional.empty();
    }

    @Override
    public String createStoreFileName(String originalFileName) {
        log.info("파일 저장용 파일 이름 생성 메소드가 실행되었습니다.");
        String ext = extractExt(originalFileName);
        if (isNotImageFile(ext.toLowerCase()))
            throw new FileExtNotMatchException("잘못된 이미지 파일 확장자입니다.");

        String uuid = UUID.randomUUID().toString();
        log.info("파일의 실제 이름과 S3에 저장될 파일 이름 : [{}] , [{}]", originalFileName, uuid + "." + ext);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName) {
        log.info("확장자 추출이 시작되었습니다.");
        int pos = originalFileName.lastIndexOf('.');
        return originalFileName.substring(pos + 1);
    }

    @Override
    public void deleteFile(ImageFile image) {
        if (!amazonS3.doesObjectExist(bucket, image.getFileName()))
            throw new AmazonS3Exception("Object " + image.getFileName() + " Not Exist!");
        amazonS3.deleteObject(bucket, image.getFileName());

    }

    private boolean isNotImageFile(String ext) {
        log.info("올바른 이미지 파일인지 확인하는 확장자 체크 로직이 실행되었습니다. [{}]", ext);
        return !ext.equals("jpeg") && !ext.equals("jpg") && !ext.equals("bmp") && !ext.equals("gif") && !ext.equals("png") && !ext.equals("svg") && !ext.equals("jfif") && !ext.equals("webp");
    }

    public String getFullPath(String fileName) {
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeFile(File createFile) {
        if (createFile.delete()) {
            log.info("메모리에 생성된 파일이 삭제되었습니다.");
        } else {
            log.info("파일 삭제를 실패하였습니다.");
        }
    }

}
