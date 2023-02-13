package ProjectDoge.StudentSoup.service.restaurantmenu;

import ProjectDoge.StudentSoup.commonmodule.ConstField;
import ProjectDoge.StudentSoup.dto.restaurantmenu.RestaurantMenuDto;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenu;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenuLike;
import ProjectDoge.StudentSoup.repository.restaurantmenu.RestaurantMenuRepository;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantMenuCallService {

    private final RestaurantMenuRepository restaurantMenuRepository;

    @Transactional
    public Page<RestaurantMenuDto> restaurantMenuCall(Long restaurantId, Long memberId, Pageable pageable){
        log.info("음식점 메뉴 호출이 실행되었습니다. [{}]", restaurantId);
        JPAQuery<Long> count = restaurantMenuRepository.countByRestaurantId(restaurantId);
        Page<RestaurantMenuDto> restaurantMenuDtoList = setRestaurantMenuInfo(restaurantId, memberId, pageable, count);
        log.info("음식점 메뉴 호출이 완료되었습니다.");
        return restaurantMenuDtoList;
    }

    private Page<RestaurantMenuDto> setRestaurantMenuInfo(Long restaurantId, Long memberId, Pageable pageable, JPAQuery<Long> count) {
        if(isLoginMember(memberId)){
            return setLoginStatusRestaurantMenu(restaurantId, memberId, pageable, count);
        }
        return setNotLoginStatusRestaurantMenu(restaurantId, pageable, count);
    }

    private boolean isLoginMember(Long memberId) {
        return memberId != null;
    }

    private Page<RestaurantMenuDto> setLoginStatusRestaurantMenu(Long restaurantId,
                                                                 Long memberId,
                                                                 Pageable pageable,
                                                                 JPAQuery<Long> count){
        log.info("회원이 로그인 된 상태에서의 음식점 메뉴 호출이 실행되었습니다.");
        List<RestaurantMenuDto> restaurantMenuDtoList = new ArrayList<>();
        List<RestaurantMenu> restaurantMenuList = restaurantMenuRepository.findByRestaurantId(restaurantId, pageable);
        for(RestaurantMenu restaurantMenu : restaurantMenuList){
            restaurantMenuDtoList.add(getLoginStatusRestaurantMenuDto(memberId, restaurantMenu));
        }

        return PageableExecutionUtils.getPage(restaurantMenuDtoList, pageable, count::fetchOne);
    }

    private Page<RestaurantMenuDto> setNotLoginStatusRestaurantMenu(Long restaurantId,
                                                                    Pageable pageable,
                                                                    JPAQuery<Long> count){
        log.info("회원이 로그인 되지 않은 상태에서의 음식점 메뉴 호출이 실행되었습니다.");
        List<RestaurantMenuDto> restaurantMenuDtoList = new ArrayList<>();
        List<RestaurantMenu> restaurantMenuList = restaurantMenuRepository.findByRestaurantId(restaurantId, pageable);
        for(RestaurantMenu restaurantMenu : restaurantMenuList){
            restaurantMenuDtoList.add(getNotLoginStatusRestaurantMenuDto(restaurantMenu));
        }
        return PageableExecutionUtils.getPage(restaurantMenuDtoList, pageable, count::fetchOne);
    }

    private RestaurantMenuDto getLoginStatusRestaurantMenuDto(Long memberId, RestaurantMenu restaurantMenu){
        for (RestaurantMenuLike restaurantMenuLike : restaurantMenu.getRestaurantMenuLikes()){
            if (restaurantMenuLike.getMember().getMemberId().equals(memberId))
                return getLikeRestaurantMenuDto(restaurantMenu);
        }
        return getNotLikeRestaurantMenuDto(restaurantMenu);
    }

    private RestaurantMenuDto getNotLoginStatusRestaurantMenuDto(RestaurantMenu restaurantMenu){
        return getNotLikeRestaurantMenuDto(restaurantMenu);
    }

    private RestaurantMenuDto getNotLikeRestaurantMenuDto(RestaurantMenu restaurantMenu){
        return new RestaurantMenuDto().createRestaurantMenu(restaurantMenu, ConstField.NOT_LIKED);
    }

    private RestaurantMenuDto getLikeRestaurantMenuDto(RestaurantMenu restaurantMenu){
        return new RestaurantMenuDto().createRestaurantMenu(restaurantMenu, ConstField.LIKED);
    }
}
