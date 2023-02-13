package ProjectDoge.StudentSoup.dto.restaurantmenu;

import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenuCategory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RestaurantMenuFormDto {

    private Long restaurantId;

    @NotEmpty(message = "음식 이름은 필수입니다.")
    private String name;
    private RestaurantMenuCategory restaurantMenuCategory;

    @NotNull(message = "음식 가격은 필수입니다.")
    private int cost;


    public RestaurantMenuFormDto createRestaurantMenuDto(Long restaurantId, String name, RestaurantMenuCategory category, int cost) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.restaurantMenuCategory=category;
        this.cost =cost;
        return this;
    }
}
