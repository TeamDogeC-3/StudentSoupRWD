package ProjectDoge.StudentSoup.service.restaurantreview;

import ProjectDoge.StudentSoup.commonmodule.ConstField;
import ProjectDoge.StudentSoup.dto.restaurantreview.RestaurantReviewDto;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReview;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReviewLike;
import ProjectDoge.StudentSoup.exception.member.MemberNotFoundException;
import ProjectDoge.StudentSoup.repository.restaurantreview.RestaurantReviewLikeRepository;
import ProjectDoge.StudentSoup.service.member.MemberFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantReviewLikeService {

    private final RestaurantReviewLikeRepository restaurantReviewLikeRepository;
    private final RestaurantReviewFindService restaurantReviewFindService;
    private final MemberFindService memberFindService;

    @Transactional
    public ConcurrentHashMap<String, Object> restaurantReviewLike(Long restaurantReviewId, Long memberId){
        ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();
        Long restaurantReviewLikeId = isAlreadyRestaurantReviewLiked(restaurantReviewId, memberId);
        RestaurantReview restaurantReview = restaurantReviewFindService.findOne(restaurantReviewId);
        if(restaurantReviewLikeId != null){
            unlikeRestaurantReview(restaurantReviewLikeId, restaurantReview, resultMap);
        } else {
            Member member = memberFindService.findOne(memberId);
            likeRestaurantReview(member, restaurantReview, resultMap);
        }
        return resultMap;
    }

    private Long isAlreadyRestaurantReviewLiked(Long restaurantReviewId, Long memberId){
        log.info("회원이 이미 좋아요를 눌렀는지 체크하는 로직이 실행되었습니다. 음식점 리뷰 ID : [{}] , 회원 ID : [{}]", restaurantReviewId, memberId);
        isLoginMember(memberId);
        RestaurantReviewLike restaurantReviewLike = restaurantReviewLikeRepository.findRestaurantReviewLikeByReviewIdAndMemberId(restaurantReviewId, memberId)
                .orElse(null);
        if(restaurantReviewLike == null) {
            log.info("[{}]님은 해당 음식점 리뷰[{}]의 좋아요를 누른 상태가 아닙니다.", memberId, restaurantReviewId);
            return null;
        }
        log.info("[{}]님은 해당 음식점 리뷰[{}]의 좋아요를 누른 상태입니다..", memberId, restaurantReviewId);
        return restaurantReviewLike.getId();
    }

    private void isLoginMember(Long memberId){
        log.info("회원이 로그인이 되었는지 확인하는 로직이 실행되었습니다.");
        if(memberId == null){
            log.info("회원의 기본키가 전달이 되지 않았거나 로그인이 되어있지 않은 상태입니다.");
            throw new MemberNotFoundException("회원이 로그인이 되어있지 않은 상태이거나, 기본키가 전달 되지 않았습니다.");
        }
        log.info("회원이 로그인이 되어있는 상태입니다.");
    }

    private void unlikeRestaurantReview(Long restaurantReviewLikeId, RestaurantReview restaurantReview, ConcurrentHashMap<String, Object> resultMap) {
        log.info("음식점 리뷰 좋아요 취소 서비스 로직이 실행되었습니다.");
        restaurantReviewLikeRepository.deleteById(restaurantReviewLikeId);
        restaurantReview.minusLikedCount();

        RestaurantReviewDto dto = new RestaurantReviewDto(restaurantReview, ConstField.NOT_LIKED);
        resultMap.put("data", dto);
        resultMap.put("result", "cancel");

        log.info("음식점 리뷰 좋아요가 취소 되었습니다. 좋아요 수 : [{}]", restaurantReview.getLikedCount());

    }

    private void likeRestaurantReview(Member member, RestaurantReview restaurantReview, ConcurrentHashMap<String, Object> resultMap){
        log.info("음식점 리뷰 좋아요 서비스 로직이 실행되었습니다.");
        RestaurantReviewLike restaurantReviewLike = new RestaurantReviewLike().createRestaurantReviewLike(restaurantReview, member);
        restaurantReviewLikeRepository.save(restaurantReviewLike);
        restaurantReview.addLikedCount();

        RestaurantReviewDto dto = new RestaurantReviewDto(restaurantReview, ConstField.LIKED);
        resultMap.put("data", dto);
        resultMap.put("result", "like");

        log.info("음식점 리뷰 좋아요가 완료 되었습니다. 좋아요 수 : [{}]", restaurantReview.getLikedCount());
    }
}
