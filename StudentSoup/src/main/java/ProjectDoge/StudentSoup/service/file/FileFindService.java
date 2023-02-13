package ProjectDoge.StudentSoup.service.file;

import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.exception.file.ImageFileNotFoundException;
import ProjectDoge.StudentSoup.repository.file.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileFindService {

    private final FileRepository fileRepository;

    public ImageFile findOne(Long fileId){
        if(fileId == null){
            return null;
        }
        return fileRepository.findById(fileId)
                .orElseThrow(()-> {
                    log.info("이미지 파일 조회 메소드 findOne 에서 예외가 발생했습니다.");
                    throw new ImageFileNotFoundException("파일이 존재하지 않습니다.");
                });
    }
}
