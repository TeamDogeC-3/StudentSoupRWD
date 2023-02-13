package ProjectDoge.StudentSoup.dto.file;

import lombok.Data;

@Data
public class UploadFileDto {

    private String originalFileName;
    private String storeFileName;
    private String fileUrl;

    public UploadFileDto(String originalFileName, String storeFileName, String fileUrl) {
        this.originalFileName = originalFileName;
        this.storeFileName = storeFileName;
        this.fileUrl = fileUrl;
    }
}
