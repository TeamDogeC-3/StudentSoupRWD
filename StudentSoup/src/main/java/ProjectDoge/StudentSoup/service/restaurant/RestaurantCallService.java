package ProjectDoge.StudentSoup.service.restaurant;


import ProjectDoge.StudentSoup.commonmodule.ConstField;
import ProjectDoge.StudentSoup.dto.restaurant.RestaurantDto;
import ProjectDoge.StudentSoup.dto.school.SchoolResponseDto;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantLike;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.repository.restaurant.RestaurantRepository;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class RestaurantCallService {

    private final RestaurantRepository restaurantRepository;
    private final SchoolFindService schoolFindService;

    public ConcurrentHashMap<String, Object> restaurantSortedCall(Long schoolId,
                                                                  String schoolName,
                                                                  Long memberId,
                                                                  String category,
                                                                  int sorted,
                                                                  Pageable pageable) {
        log.info("======= 정렬된 페이지 처리 음식점 조회가 시작되었습니다. ========");

        ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();

        schoolId = firstCall(schoolId, schoolName, pageable, resultMap);

        List<Restaurant> sortedRestaurants = restaurantRepository.
                findBySchoolIdAndCategoryAndSorted(schoolId, category, sorted, pageable);
        JPAQuery<Long> queryCount = restaurantRepository.countBySchoolId(schoolId,category);



        if (isLoginMember(memberId)) {
            return getLoginRestaurantList(memberId, sortedRestaurants, pageable, queryCount, resultMap);
        }

        return getNotLoginRestaurantList(sortedRestaurants, pageable, queryCount, resultMap);
    }

    private Long firstCall(Long schoolId, String schoolName, Pageable pageable, ConcurrentHashMap<String, Object> resultMap) {
        if (pageable.getPageNumber() == 0 || schoolId == null) {
            School school = schoolFindService.findOne(schoolName);
            schoolId = school.getId();
            resultMap.put("school", new SchoolResponseDto(school));
        }
        return schoolId;
    }


    /**
     * @param memberId 유저가 로그인이 되어있는가
     * @return
     */
    private boolean isLoginMember(Long memberId) {
        return memberId != null;
    }

    private ConcurrentHashMap<String, Object> getLoginRestaurantList(Long memberId,
                                                                     List<Restaurant> restaurants,
                                                                     Pageable pageable,
                                                                     JPAQuery<Long> count,
                                                                     ConcurrentHashMap<String, Object> resultMap) {
        List<RestaurantDto> restaurantDtoList = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            restaurantDtoList.add(getLoginRestaurantDto(memberId, restaurant));
        }
        Slice <RestaurantDto> pagingLoginRestaurantDto = PageableExecutionUtils.getPage(restaurantDtoList, pageable, count::fetchOne);
        resultMap.put("restaurant", pagingLoginRestaurantDto);
        return resultMap;
    }

    private RestaurantDto getLoginRestaurantDto(Long memberId, Restaurant restaurant) {
        for (RestaurantLike restaurantLike : restaurant.getRestaurantLikes()) {
            if (restaurantLike.getMember().getMemberId().equals(memberId))
                return getLikeRestaurantDto(restaurant);
        }
        return getNotLikeRestaurantDto(restaurant);
    }

    private RestaurantDto getLikeRestaurantDto(Restaurant restaurant) {
        return new RestaurantDto().createRestaurantDto(restaurant, ConstField.LIKED);
    }

    private RestaurantDto getNotLikeRestaurantDto(Restaurant restaurant) {
        return new RestaurantDto().createRestaurantDto(restaurant, ConstField.NOT_LIKED);
    }

    private ConcurrentHashMap<String, Object> getNotLoginRestaurantList(List<Restaurant> restaurants,
                                                                        Pageable pageable,
                                                                        JPAQuery<Long> count,
                                                                        ConcurrentHashMap<String, Object> resultMap) {
        List<RestaurantDto> restaurantDtoList = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            restaurantDtoList.add(getNotLikeRestaurantDto(restaurant));
        }
        Slice <RestaurantDto> pagingNotLoginRestaurantDto = PageableExecutionUtils.getPage(restaurantDtoList, pageable, count::fetchOne);
        resultMap.put("restaurant", pagingNotLoginRestaurantDto);
        return resultMap;
    }
}
