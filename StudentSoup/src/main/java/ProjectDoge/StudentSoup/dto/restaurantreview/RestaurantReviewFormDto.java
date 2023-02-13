package ProjectDoge.StudentSoup.dto.restaurantreview;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RestaurantReviewFormDto {
    @NotEmpty(message = "레스토랑 이름 입력은 필수 입니다")
    private String restaurant;
    @NotEmpty(message = "컨텐트 입력은 필수 입나다")
    private String content;
    @NotEmpty(message = "메뉴 입력은 필수 입니다")
    private String menu_name;

    private String image_name;

}
