package ProjectDoge.StudentSoup.service.restaurantreview;

import ProjectDoge.StudentSoup.commonmodule.ConstField;
import ProjectDoge.StudentSoup.dto.restaurantreview.RestaurantReviewDto;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReview;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReviewLike;
import ProjectDoge.StudentSoup.repository.restaurantreview.RestaurantReviewRepository;
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
@RequiredArgsConstructor
@Service
public class RestaurantReviewCallService {

    private final RestaurantReviewRepository restaurantReviewRepository;

    @Transactional
    public Page<RestaurantReviewDto> getRestaurantReviewCall(Long restaurantId,
                                                             Long memberId,
                                                             String sorted,
                                                             Pageable pageable){
        List<RestaurantReview> sortedRestaurantReviews = restaurantReviewRepository.
                findByRestaurantIdSorted(restaurantId, sorted, pageable);
        JPAQuery<Long> queryCount = restaurantReviewRepository.pagingCountByRestaurantId(restaurantId);

        List<RestaurantReviewDto> restaurantReviewDtoList = new ArrayList<>();

        if(isLoginMember(memberId)){
            return getLoginRestaurantReviewList(memberId, sortedRestaurantReviews, restaurantReviewDtoList, pageable, queryCount);
        }

        return getNotLoginRestaurantReviewList(sortedRestaurantReviews, restaurantReviewDtoList, pageable, queryCount);
    }

    private boolean isLoginMember(Long memberId) {
        return memberId != null;
    }

    private Page<RestaurantReviewDto> getLoginRestaurantReviewList(Long memberId,
                                                                   List<RestaurantReview> sortedRestaurantReviews,
                                                                   List<RestaurantReviewDto> restaurantReviewDtoList,
                                                                   Pageable pageable,
                                                                   JPAQuery<Long> queryCount){
        for(RestaurantReview restaurantReview : sortedRestaurantReviews){
            restaurantReviewDtoList.add(getLoginRestaurantReviewDto(memberId, restaurantReview));
        }

        return PageableExecutionUtils.getPage(restaurantReviewDtoList, pageable, queryCount::fetchOne);
    }

    private RestaurantReviewDto getLoginRestaurantReviewDto(Long memberId, RestaurantReview restaurantReview){
        for(RestaurantReviewLike restaurantReviewLike : restaurantReview.getRestaurantReviewLikes()){
            if(restaurantReviewLike.getMember().getMemberId().equals(memberId))
                return getLikeRestaurantReviewDto(restaurantReview);
        }
        return getNotLikeRestaurantReviewDto(restaurantReview);
    }

    private RestaurantReviewDto getLikeRestaurantReviewDto(RestaurantReview restaurantReview){
        return new RestaurantReviewDto(restaurantReview, ConstField.LIKED);
    }

    private RestaurantReviewDto getNotLikeRestaurantReviewDto(RestaurantReview restaurantReview){
        return new RestaurantReviewDto(restaurantReview, ConstField.NOT_LIKED);
    }

    private Page<RestaurantReviewDto> getNotLoginRestaurantReviewList(List<RestaurantReview> sortedRestaurantReviews,
                                                                      List<RestaurantReviewDto> restaurantReviewDtoList,
                                                                      Pageable pageable,
                                                                      JPAQuery<Long> queryCount){
        for(RestaurantReview restaurantReview : sortedRestaurantReviews){
            restaurantReviewDtoList.add(getNotLikeRestaurantReviewDto(restaurantReview));
        }
        return PageableExecutionUtils.getPage(restaurantReviewDtoList, pageable, queryCount::fetchOne);
    }
}
