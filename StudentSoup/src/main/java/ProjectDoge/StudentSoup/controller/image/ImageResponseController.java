package ProjectDoge.StudentSoup.controller.image;

import ProjectDoge.StudentSoup.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@Slf4j
@RestController
@RequiredArgsConstructor

public class ImageResponseController {

    private final FileService fileService;

    @GetMapping("/image/{fileName}")
    public Object responseImage(@PathVariable String fileName) throws MalformedURLException {
        log.info("이미지 호출이 시작되었습니다. 해당 이미지의 이름 : [{}], 저장 위치 : [{}]", fileName, fileService.getFullPath(fileName));
        String filePath = fileService.getFullPath(fileName);
        if(filePath.startsWith("https://"))
            return (String)filePath;
        return new UrlResource("file:" + fileService.getFullPath(fileName));
    }
}
