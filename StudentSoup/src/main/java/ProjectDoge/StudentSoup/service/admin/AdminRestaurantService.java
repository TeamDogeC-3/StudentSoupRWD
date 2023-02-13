package ProjectDoge.StudentSoup.service.admin;

import ProjectDoge.StudentSoup.dto.file.UploadFileDto;
import ProjectDoge.StudentSoup.dto.restaurant.RestaurantUpdateDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.repository.file.FileRepository;
import ProjectDoge.StudentSoup.repository.restaurant.RestaurantRepository;
import ProjectDoge.StudentSoup.service.file.FileService;
import ProjectDoge.StudentSoup.service.restaurant.RestaurantFindService;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminRestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantFindService restaurantFindService;
    private final SchoolFindService schoolFindService;
    private final FileService fileService;

    private final FileRepository fileRepository;


    public List<Restaurant> adminSearchRestaurants(String column, String find_value) {
        if(column == null || find_value == null) return Collections.emptyList();
        List<Restaurant> findRestaurants = restaurantRepository.findRestaurantDynamicSearch(column,find_value);

        return findRestaurants;
    }

    public RestaurantUpdateDto adminFindUpdateRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantFindService.findOne(restaurantId);
        RestaurantUpdateDto restaurantUpdateDto = new RestaurantUpdateDto();
        restaurantUpdateDto.setRestaurant(restaurant);

        return restaurantUpdateDto;
    }
    @Transactional
    public void adminUpdateRestaurant(Long restaurantId, RestaurantUpdateDto dto){
        Restaurant restaurant = restaurantFindService.findOne(restaurantId);
        List<UploadFileDto> uploadFileDtoList = fileService.createUploadFileDtoList(dto.getMultipartFileList());
        uploadRestaurantImage(restaurant, uploadFileDtoList);
        School school = schoolFindService.findOne(dto.getSchool().getId());
        restaurant.updateRestaurant(dto,school);
    }

    private void uploadRestaurantImage(Restaurant restaurant, List<UploadFileDto> uploadFileDtoList) {
        log.info("음식점 이미지 업로드 메소드가 실행 되었습니다.");
        if(!uploadFileDtoList.isEmpty()){
            ifPresentImageFileDelete(restaurant);
            for(UploadFileDto fileDto : uploadFileDtoList){
                log.info("생성되는 이미지 파일 이름 : [{}]", fileDto.getOriginalFileName());
                ImageFile imageFile = new ImageFile().createFile(fileDto);
                restaurant.addImageFile(fileRepository.save(imageFile));
            }
        }
        log.info("음식점 리뷰 이미지 업로드가 완료되었습니다.");
    }

    private void ifPresentImageFileDelete(Restaurant restaurant) {
        if(!restaurant.getImageFileList().isEmpty()){
            log.info("이미지 파일이 존재하여 이미지 파일을 삭제하였습니다.");
            for(ImageFile image : restaurant.getImageFileList()){
                fileService.deleteFile(image);
            }
            fileRepository.deleteAllInBatch(restaurant.getImageFileList());
        }
        restaurantRepository.save(restaurant);
    }

    @Transactional
    public void adminDeleteRestaurant(Long restaurantId){
        Restaurant restaurant = restaurantFindService.findOne(restaurantId);
        for(ImageFile image : restaurant.getImageFileList())
            fileService.deleteFile(image);
        restaurantRepository.delete(restaurant);
    }
}
