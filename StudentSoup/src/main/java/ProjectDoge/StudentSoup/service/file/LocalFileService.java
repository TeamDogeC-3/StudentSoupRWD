package ProjectDoge.StudentSoup.service.file;

import ProjectDoge.StudentSoup.dto.file.UploadFileDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.exception.file.FileExtNotMatchException;
import ProjectDoge.StudentSoup.repository.file.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class LocalFileService implements FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    @Transactional
    public Long join(MultipartFile multipartFile) {
        UploadFileDto uploadFileDto = storeFile(multipartFile);
        if (uploadFileDto == null) {
            return null;
        }
        ImageFile file = new ImageFile().createFile(uploadFileDto);
        fileRepository.save(file);
        log.info("데이터베이스에 파일이 저장되었습니다.");
        return file.getId();
    }

    public List<UploadFileDto> createUploadFileDtoList(List<MultipartFile> multipartFileList) {
        log.info("다중 파일 등록 서비스 메소드가 실행되었습니다.");

        List<UploadFileDto> uploadFiles = new ArrayList<>();
        if (multipartFileList != null) {
            for (MultipartFile multipartFile : multipartFileList) {
                if (!multipartFile.isEmpty()) {
                    uploadFiles.add(storeFile(multipartFile));
                }
            }
        }
        log.info("다중 파일 등록 서비스가 완료되었습니다.");
        return uploadFiles;
    }

    public UploadFileDto storeFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            log.info("전송된 이미지 파일이 존재하지 않아 파일 저장 메소드가 실행되지 않습니다.");
            return null;
        }
        log.info("로컬 파일 저장 메소드가 실행되었습니다.");
        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);
        String filePath = getFullPath(storeFileName);
        try {
            multipartFile.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("지정된 경로에 파일이 저장되었습니다. [{}]", fileDir);
        return new UploadFileDto(originalFileName, storeFileName, filePath);
    }

    public String createStoreFileName(String originalFileName) {
        log.info("데이터베이스 저장용 파일 이름 생성 메소드가 실행되었습니다.");
        String ext = extractExt(originalFileName);
        log.info("추출된 확장자 : {} ", ext);
        if (isNotImageFile(ext.toLowerCase())) {
            throw new FileExtNotMatchException("잘못된 이미지 파일 확장자 입니다.");
        }
        String uuid = UUID.randomUUID().toString();
        log.info("생성된 데이터베이스 저장용 파일 이름 : [{}]", uuid + "." + ext);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName) {
        log.info("확장자 추출이 시작되었습니다.");
        int pos = originalFileName.lastIndexOf('.');
        return originalFileName.substring(pos + 1);
    }

    private static boolean isNotImageFile(String ext) {
        return !ext.equals("jpeg") && !ext.equals("jpg") && !ext.equals("bmp") && !ext.equals("gif") && !ext.equals("png") && !ext.equals("svg") && !ext.equals("jfif") && !ext.equals("webp");
    }

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    @Override
    public void deleteFile(ImageFile image) {
        Path filePath = Paths.get(getFullPath(image.getFileName()));
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("입력 에러 발생이 됐습니다.", e);
        }
    }
}




