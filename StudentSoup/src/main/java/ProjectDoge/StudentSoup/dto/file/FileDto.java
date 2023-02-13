package ProjectDoge.StudentSoup.dto.file;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDto {
    // TODO 이미지가 필요한 데이터 전달 객체들마다 MultipartFile 필드 추가
    private MultipartFile imageFile;
}
