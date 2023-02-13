package ProjectDoge.StudentSoup.service.restaurant;

import ProjectDoge.StudentSoup.commonmodule.ConstField;
import ProjectDoge.StudentSoup.dto.restaurant.RestaurantDto;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantLike;
import ProjectDoge.StudentSoup.exception.member.MemberNotFoundException;
import ProjectDoge.StudentSoup.repository.restaurant.RestaurantLikeRepository;
import ProjectDoge.StudentSoup.service.member.MemberFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantLikeService {

    private final RestaurantLikeRepository restaurantLikeRepository;
    private final RestaurantFindService restaurantFindService;
    private final MemberFindService memberFindService;

    @Transactional
    public ConcurrentHashMap<String, Object> restaurantLike(Long restaurantId, Long memberId) {
        log.info("좋아요 / 좋아요 취소 서비스 로직이 실행되었습니다.");
        ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();
        isLoginMember(memberId);
        Restaurant restaurant = restaurantFindService.findOne(restaurantId);
        Long restaurantLikeId = isAlreadyRestaurantLiked(restaurantId, memberId, restaurant);
        if (restaurantLikeId != null) {
            unlikeRestaurant(restaurantLikeId, restaurant, resultMap);
        } else {
            Member member = memberFindService.findOne(memberId);
            likeRestaurant(member, restaurant, resultMap);
        }
        return resultMap;
    }

    private Long isAlreadyRestaurantLiked(Long restaurantId, Long memberId, Restaurant restaurant) {
        log.info("회원이 이미 좋아요를 눌렀는지 체크하는 로직이 실행되었습니다. 음식점 ID : [{}] , 회원 ID : [{}]", restaurantId, memberId);
        for (RestaurantLike restaurantLike : restaurant.getRestaurantLikes()) {
            if (restaurantLike.getMember().getMemberId().equals(memberId)) {
                log.info("[{}]님은 해당 음식점[{}]의 좋아요를 누른 상태입니다..", memberId, restaurantId);
                return restaurantLike.getId();
            }
        }
        log.info("[{}]님은 해당 음식점[{}]의 좋아요를 누른 상태가 아닙니다.", memberId, restaurantId);
        return null;
    }

    private void isLoginMember(Long memberId) {
        log.info("회원이 로그인이 되었는지 확인하는 로직이 실행되었습니다.");
        if (memberId == null) {
            log.info("회원의 기본키가 전달이 되지 않았거나 로그인이 되어있지 않은 상태입니다.");
            throw new MemberNotFoundException("회원이 로그인이 되어있지 않은 상태이거나, 기본키가 전달 되지 않았습니다.");
        }
        log.info("회원이 로그인이 되어있는 상태입니다.");
    }

    private void unlikeRestaurant(Long restaurantLikeId, Restaurant restaurant, ConcurrentHashMap<String, Object> resultMap) {
        log.info("음식점 좋아요 취소 서비스 로직이 실행되었습니다.");
        restaurantLikeRepository.deleteById(restaurantLikeId);
        restaurant.minusLikedCount();

        RestaurantDto dto = new RestaurantDto().createRestaurantDto(restaurant, ConstField.NOT_LIKED);
        resultMap.put("data", dto);
        resultMap.put("result", "cancel");

        log.info("음식점 좋아요가 취소 되었습니다. 좋아요 수 : [{}]", restaurant.getLikedCount());
    }

    private void likeRestaurant(Member member, Restaurant restaurant, ConcurrentHashMap<String, Object> resultMap) {
        log.info("음식점 좋아요 서비스 로직이 실행되었습니다.");
        RestaurantLike restaurantLike = new RestaurantLike().createRestaurantLike(member, restaurant);
        restaurantLikeRepository.save(restaurantLike);
        restaurant.addLikedCount();

        RestaurantDto dto = new RestaurantDto().createRestaurantDto(restaurant, ConstField.LIKED);
        resultMap.put("data", dto);
        resultMap.put("result", "like");

        log.info("음식점 좋아요가 완료 되었습니다. 좋아요 수 : [{}]", restaurant.getLikedCount());
    }

}
