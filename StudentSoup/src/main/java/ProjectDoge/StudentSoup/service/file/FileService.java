package ProjectDoge.StudentSoup.service.file;

import ProjectDoge.StudentSoup.dto.file.UploadFileDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    Long join(MultipartFile multipartFile);

    List<UploadFileDto> createUploadFileDtoList(List<MultipartFile> multipartFileList);

    UploadFileDto storeFile(MultipartFile multipartFile) throws IOException;

    String createStoreFileName(String originalFileName);

    String getFullPath(String fileName);

    void deleteFile(ImageFile image);
}
