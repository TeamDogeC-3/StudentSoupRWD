package ProjectDoge.StudentSoup.service.restaurant;

import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantNotFoundException;
import ProjectDoge.StudentSoup.repository.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantFindService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant findByRestaurantNameAndSchoolName(String restaurantName, String schoolName){
        Restaurant restaurant = restaurantRepository.findByRestaurantNameAndSchool_SchoolName(restaurantName, schoolName)
                .orElseThrow(() -> {
                    log.info("학교에 속한 음식점을 찾지 못했습니다.");
                    throw new RestaurantNotFoundException("음식점이 존재하지 않습니다.");
                });
        return restaurant;
    }
    public Restaurant findOne(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> {
            return new RestaurantNotFoundException("등록되지 않은 음식점 입니다.");
        });
        return restaurant;
    }
}
