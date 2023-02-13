package ProjectDoge.StudentSoup.service.restaurant;

import ProjectDoge.StudentSoup.dto.file.UploadFileDto;
import ProjectDoge.StudentSoup.dto.restaurant.RestaurantFormDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.repository.file.FileRepository;
import ProjectDoge.StudentSoup.repository.restaurant.RestaurantRepository;
import ProjectDoge.StudentSoup.service.file.FileService;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantRegisterService {

    private final SchoolFindService schoolFindService;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final RestaurantValidationService restaurantValidationService;

    private final RestaurantRepository restaurantRepository;

    @Transactional(rollbackFor = Exception.class)
    public Long join(RestaurantFormDto dto) {
        log.info("음식점 생성 메서드가 실행되었습니다.");
        School school = schoolFindService.findOne(dto.getSchoolId());
        Restaurant restaurant = new Restaurant().createRestaurant(dto,school);
        restaurant.setDistance(restaurant.calcDistance(school));
        restaurantValidationService.validateDuplicateRestaurant(restaurant);
        List<UploadFileDto> uploadFileDtoList = fileService.createUploadFileDtoList(dto.getMultipartFileList());
        uploadRestaurantImage(restaurant, uploadFileDtoList);
        restaurantRepository.save(restaurant);
        log.info("음식점이 생성되었습니다.[{}][{}]",restaurant.getId(), restaurant.getName(), restaurant.getDistance());
        return restaurant.getId();
    }

    private void uploadRestaurantImage(Restaurant restaurant, List<UploadFileDto> uploadFileDtoList) {
        log.info("음식점 이미지 업로드 메소드가 실행 되었습니다.");
        if(!uploadFileDtoList.isEmpty()){
            for(UploadFileDto fileDto : uploadFileDtoList){
                log.info("생성되는 이미지 파일 이름 : [{}]", fileDto.getOriginalFileName());
                ImageFile imageFile = new ImageFile().createFile(fileDto);
                restaurant.addImageFile(fileRepository.save(imageFile));
            }
        }
        log.info("음식점 이미지 업로드가 완료되었습니다.");
    }

    @Transactional
    public Long testJoin(RestaurantFormDto dto) {
        log.info("음식점 테스트 생성 메서드가 실행되었습니다.");
        School school = schoolFindService.findOne(dto.getSchoolId());
        Restaurant restaurant = new Restaurant().createRestaurant(dto, school);
        restaurantValidationService.validateDuplicateRestaurant(restaurant);
        restaurant.setDistance(restaurant.calcDistance(school));
        restaurantRepository.save(restaurant);
        log.info("테스트 음식점이 생성되었습니다.[{}][{}]",restaurant.getId(),restaurant.getName());
        return restaurant.getId();
    }

}
