package ProjectDoge.StudentSoup.service.restaurant;

import ProjectDoge.StudentSoup.commonmodule.ConstField;
import ProjectDoge.StudentSoup.dto.restaurant.RestaurantDetailDto;
import ProjectDoge.StudentSoup.dto.restaurantmenu.RestaurantMenuDto;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantLike;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenu;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenuLike;
import ProjectDoge.StudentSoup.repository.restaurant.RestaurantLikeRepository;
import ProjectDoge.StudentSoup.repository.restaurantmenu.RestaurantMenuLikeRepository;
import ProjectDoge.StudentSoup.repository.restaurantmenu.RestaurantMenuRepository;
import ProjectDoge.StudentSoup.service.restaurant.RestaurantFindService;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantDetailCallService {
    private final RestaurantFindService restaurantFindService;
    private final RestaurantLikeRepository restaurantLikeRepository;

    @Transactional
    public ConcurrentHashMap<String, Object> restaurantDetailCall(Long restaurantId, Long memberId){

        log.info("음식점 세부사항 호출 로직이 실행되었습니다. [{}]", restaurantId);

        ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();
        Restaurant restaurant = restaurantFindService.findOne(restaurantId);
        setRestaurantDetailInfo(restaurantId, memberId, resultMap, restaurant);
        log.info("음식점 세부사항 호출 로직이 완료되었습니다.");
        return resultMap;
    }

    private void setRestaurantDetailInfo(Long restaurantId,
                                         Long memberId,
                                         ConcurrentHashMap<String, Object> resultMap,
                                         Restaurant restaurant) {
        restaurant.addViewCount();
        if(isLoginMember(memberId)) {
            log.info("회원이 로그인 된 상태의 음식점 세부사항을 호출합니다. [{}]", memberId);
            setLoginStatusRestaurantDetailDto(restaurantId, memberId, resultMap, restaurant);
        } else {
            log.info("로그인 되지 않은 상태의 음식점 세부사항을 호출합니다.");
            setNotLoginStatusRestaurantDetailDto(resultMap, restaurant);
        }
    }
    private void setLoginStatusRestaurantDetailDto(Long restaurantId,
                                                   Long memberId,
                                                   ConcurrentHashMap<String, Object> resultMap,
                                                   Restaurant restaurant) {
        log.info("로그인이 된 상태의 음식점 세부사항과 메뉴를 호출합니다.");
        setLoginStatusRestaurantDetail(restaurantId, memberId, resultMap, restaurant);
    }

    private void setNotLoginStatusRestaurantDetailDto(ConcurrentHashMap<String, Object> resultMap,
                                                      Restaurant restaurant) {
        log.info("로그인이 되어있지 않은 상태의 음식점 세부사항과 메뉴를 호출합니다.");
        setNotLoginStatusRestaurantDetail(resultMap, restaurant);
    }


    private boolean isLoginMember(Long memberId) {
        return memberId != null;
    }

    private void setLoginStatusRestaurantDetail(Long restaurantId,
                                                Long memberId,
                                                ConcurrentHashMap<String, Object> resultMap,
                                                Restaurant restaurant) {
        log.info("로그인 된 회원의 음식점 세부사항을 호출합니다.");
        RestaurantLike restaurantLike = restaurantLikeRepository.findRestaurantLikeByRestaurantIdAndMemberId(restaurantId, memberId)
                .orElse(null);

        setMemberRestaurantLikeDetail(resultMap, restaurant, restaurantLike);
        log.info("로그인 된 회원의 음식점 세부사항 호출이 완료되었습니다.");
    }

    private void setMemberRestaurantLikeDetail(ConcurrentHashMap<String, Object> resultMap, Restaurant restaurant, RestaurantLike restaurantLike) {
        if(restaurantLike == null){
            getNotLikeRestaurantDto(resultMap, restaurant);
        } else {
            getLikeRestaurantDto(resultMap, restaurant);
        }
    }

    private void setNotLoginStatusRestaurantDetail(ConcurrentHashMap<String, Object> resultMap, Restaurant restaurant){
        log.info("로그인이 되어있지 않은 상태의 음식점 세부사항을 호출합니다.");
        getNotLikeRestaurantDto(resultMap, restaurant);
    }

    private void getLikeRestaurantDto(ConcurrentHashMap<String, Object> resultMap, Restaurant restaurant) {
        RestaurantDetailDto dto = new RestaurantDetailDto().createRestaurantDetailDto(restaurant, ConstField.LIKED);
        resultMap.put("restaurant", dto);
    }

    private void getNotLikeRestaurantDto(ConcurrentHashMap<String, Object> resultMap, Restaurant restaurant) {
        RestaurantDetailDto dto = new RestaurantDetailDto().createRestaurantDetailDto(restaurant, ConstField.NOT_LIKED);
        resultMap.put("restaurant", dto);
    }
}
