package ProjectDoge.StudentSoup.service.restaurantmenu;

import ProjectDoge.StudentSoup.dto.restaurantmenu.RestaurantMenuFormDto;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantMenuValidationException;
import ProjectDoge.StudentSoup.repository.restaurantmenu.RestaurantMenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantMenuValidationService {
    private final RestaurantMenuRepository restaurantMenuRepository;
    public void validationDuplicateRestaurantMenu(RestaurantMenuFormDto restaurantMenuFormDto) {
        log.info("음식점 메뉴 생성 검증 로직이 실행되었습니다.");
        restaurantMenuRepository.findByRestaurantMenuNameAndRestaurant_RestaurantId(
                        restaurantMenuFormDto.getName(),
                        restaurantMenuFormDto.getRestaurantId())
                .ifPresent(value -> {
                    log.info("메뉴가 존재하는 예외가 발생했습니다.");
                    throw new RestaurantMenuValidationException("이미 존재하는 메뉴 입니다.");
                });
        log.info("메뉴 검증이 완료 되었습니다.");
    }
}
