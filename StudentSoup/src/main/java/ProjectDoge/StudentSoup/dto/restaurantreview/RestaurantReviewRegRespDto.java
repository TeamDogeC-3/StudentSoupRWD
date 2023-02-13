package ProjectDoge.StudentSoup.dto.restaurantreview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RestaurantReviewRegRespDto {

    private Long restaurantId;
    private double starLiked;
    private Long reviewCount;
}
