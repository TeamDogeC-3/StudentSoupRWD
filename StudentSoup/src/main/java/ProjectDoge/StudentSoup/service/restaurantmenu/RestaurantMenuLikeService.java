package ProjectDoge.StudentSoup.service.restaurantmenu;

import ProjectDoge.StudentSoup.commonmodule.ConstField;
import ProjectDoge.StudentSoup.dto.restaurantmenu.RestaurantMenuDto;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenu;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenuLike;
import ProjectDoge.StudentSoup.exception.member.MemberNotFoundException;
import ProjectDoge.StudentSoup.repository.restaurantmenu.RestaurantMenuLikeRepository;
import ProjectDoge.StudentSoup.service.member.MemberFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor

public class RestaurantMenuLikeService {
    private final RestaurantMenuLikeRepository restaurantMenuLikeRepository;
    private final RestaurantMenuFindService restaurantMenuFindService;
    private final MemberFindService memberFindService;

    @Transactional
    public ConcurrentHashMap<String, Object> restaurantMenuLike(Long restaurantMenuId, Long memberId){
        ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();
        Long restaurantMenuLikeId = isAlreadyRestaurantMenuLiked(restaurantMenuId, memberId);
        RestaurantMenu restaurantMenu = restaurantMenuFindService.findOne(restaurantMenuId);
        if(restaurantMenuLikeId != null){
            unlikeRestaurantMenu(restaurantMenuLikeId, restaurantMenu, resultMap);
        } else {
            Member member = memberFindService.findOne(memberId);
            likeRestaurantMenu(member, restaurantMenu, resultMap);
        }
        return resultMap;
    }

    private Long isAlreadyRestaurantMenuLiked(Long restaurantMenuId, Long memberId){
        log.info("회원이 이미 좋아요를 눌렀는지 체크하는 로직이 실행되었습니다. 음식점 메뉴 ID : [{}] , 회원 ID : [{}]", restaurantMenuId, memberId);
        isLoginMember(memberId);
        RestaurantMenuLike restaurantMenuLike = restaurantMenuLikeRepository.findRestaurantMenuLikeByRestaurantMenuIdAndMemberId(restaurantMenuId, memberId)
                .orElse(null);
        if(restaurantMenuLike == null) {
            log.info("[{}]님은 해당 음식점메뉴[{}]의 좋아요를 누른 상태가 아닙니다.", memberId, restaurantMenuId);
            return null;
        }
        log.info("[{}]님은 해당 음식점메뉴[{}]의 좋아요를 누른 상태입니다..", memberId, restaurantMenuId);
        return restaurantMenuLike.getId();
    }

    private void isLoginMember(Long memberId){
        log.info("회원이 로그인이 되었는지 확인하는 로직이 실행되었습니다.");
        if(memberId == null){
            log.info("회원의 기본키가 전달이 되지 않았거나 로그인이 되어있지 않은 상태입니다.");
            throw new MemberNotFoundException("회원이 로그인이 되어있지 않은 상태이거나, 기본키가 전달 되지 않았습니다.");
        }
        log.info("회원이 로그인이 되어있는 상태입니다.");
    }

    private void unlikeRestaurantMenu(Long restaurantLikeId, RestaurantMenu restaurantMenu, ConcurrentHashMap<String, Object> resultMap) {
        log.info("음식점 메뉴 좋아요 취소 서비스 로직이 실행되었습니다.");
        restaurantMenuLikeRepository.deleteById(restaurantLikeId);
        restaurantMenu.minusLikedCount();

        RestaurantMenuDto dto = new RestaurantMenuDto().createRestaurantMenu(restaurantMenu, ConstField.NOT_LIKED);
        resultMap.put("data", dto);
        resultMap.put("result", "cancel");

        log.info("음식점 메뉴 좋아요가 취소 되었습니다. 좋아요 수 : [{}]", restaurantMenu.getLikedCount());

    }

    private void likeRestaurantMenu(Member member, RestaurantMenu restaurantMenu, ConcurrentHashMap<String, Object> resultMap){
        log.info("음식점 메뉴 좋아요 서비스 로직이 실행되었습니다.");
        RestaurantMenuLike restaurantMenuLike = new RestaurantMenuLike().createRestaurantMenuLike(member, restaurantMenu);
        restaurantMenuLikeRepository.save(restaurantMenuLike);
        restaurantMenu.addLikedCount();

        RestaurantMenuDto dto = new RestaurantMenuDto().createRestaurantMenu(restaurantMenu, ConstField.LIKED);
        resultMap.put("data", dto);
        resultMap.put("result", "like");

        log.info("음식점 메뉴 좋아요가 완료 되었습니다. 좋아요 수 : [{}]", restaurantMenu.getLikedCount());
    }
}
