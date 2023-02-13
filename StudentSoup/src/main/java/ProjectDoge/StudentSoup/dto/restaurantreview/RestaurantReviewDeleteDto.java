package ProjectDoge.StudentSoup.dto.restaurantreview;

import lombok.Data;

@Data
public class RestaurantReviewDeleteDto {

    private Long restaurantReviewId;
    private Long restaurantId;
    private Long memberId;
}
