package ProjectDoge.StudentSoup.service.admin;

import ProjectDoge.StudentSoup.dto.restaurantmenu.RestaurantMenuUpdateDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenu;
import ProjectDoge.StudentSoup.repository.file.FileRepository;
import ProjectDoge.StudentSoup.repository.restaurantmenu.RestaurantMenuRepository;
import ProjectDoge.StudentSoup.service.file.FileFindService;
import ProjectDoge.StudentSoup.service.file.FileService;
import ProjectDoge.StudentSoup.service.restaurantmenu.RestaurantMenuFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AdminRestaurantMenuService {

    private final RestaurantMenuRepository restaurantMenuRepository;
    private final RestaurantMenuFindService restaurantMenuFindService;
    private final FileService fileService;
    private final FileFindService fileFindService;

    private final FileRepository fileRepository;
    public RestaurantMenuUpdateDto adminFindUpdateRestaurantMenu(Long restaurantMenuId){
        RestaurantMenu restaurantMenu = restaurantMenuFindService.findOne(restaurantMenuId);
        RestaurantMenuUpdateDto restaurantMenuUpdateDto = new RestaurantMenuUpdateDto();
        restaurantMenuUpdateDto.setRestaurantMenu(restaurantMenu);

        return restaurantMenuUpdateDto;
    }
    @Transactional
    public void adminUpdateRestaurantMenu(Long restaurantMenuId, MultipartFile multipartFile, RestaurantMenuUpdateDto restaurantMenuUpdateDto) {
        RestaurantMenu restaurantMenu = restaurantMenuFindService.findOne(restaurantMenuId);
        if (restaurantMenu.getImageFile() != null) {
            fileService.deleteFile(restaurantMenu.getImageFile());
            fileRepository.delete(restaurantMenu.getImageFile());
        }
        Long fileId = fileService.join(multipartFile);
        ImageFile file = fileFindService.findOne(fileId);
        restaurantMenu.updateRestaurantMenu(restaurantMenuUpdateDto,file);
    }

    @Transactional
    public void adminDeleteRestaurantMenu(Long restaurantMenuId){
        RestaurantMenu restaurantMenu = restaurantMenuFindService.findOne(restaurantMenuId);
        fileService.deleteFile(restaurantMenu.getImageFile());
        restaurantMenuRepository.delete(restaurantMenu);
    }
}
