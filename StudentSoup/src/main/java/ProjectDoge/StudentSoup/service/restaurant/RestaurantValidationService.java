package ProjectDoge.StudentSoup.service.restaurant;

import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantValidationException;
import ProjectDoge.StudentSoup.repository.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantValidationService {

    private final RestaurantRepository restaurantRepository;

    public void validateDuplicateRestaurant(Restaurant restaurant) {
        log.info("음식점 생성 검증 메소드가 실행되었습니다.");
        restaurantRepository.findByRestaurantNameAndSchool_SchoolName(restaurant.getName(),restaurant.getSchool().getSchoolName())
                .ifPresent( value -> {
                    log.info("음식점이 존재하는 예외가 발생했습니다");
                    throw new RestaurantValidationException("이미 존재하는 음식점 입니다");
                });
        log.info("음식점 검증이 완료되었습니다.");
    }
}
