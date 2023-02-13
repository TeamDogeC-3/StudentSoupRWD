package ProjectDoge.StudentSoup.service.restaurantmenu;

import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenu;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantMenuNotFoundException;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantMenuIdNotSentException;
import ProjectDoge.StudentSoup.repository.restaurantmenu.RestaurantMenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantMenuFindService {
    private final RestaurantMenuRepository restaurantMenuRepository;

    public RestaurantMenu findOne(Long restaurantMenuId){
        checkRestaurantMenuIdSent(restaurantMenuId);
        RestaurantMenu restaurantMenu = restaurantMenuRepository.findById(restaurantMenuId).orElseThrow(() -> {
            return new RestaurantMenuNotFoundException("등록되지 않은 메뉴 입니다.");
        });
        return restaurantMenu;
    }

    private void checkRestaurantMenuIdSent(Long restaurantMenuId) {
        if (restaurantMenuId == null){
            log.info("RestaurantMenuId가 전송되지 않았습니다.");
            throw new RestaurantMenuIdNotSentException("restaurantMenuId가 전송되지 않았습니다.");
        }
    }
}
