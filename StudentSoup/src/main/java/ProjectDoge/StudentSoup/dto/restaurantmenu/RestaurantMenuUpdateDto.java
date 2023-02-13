package ProjectDoge.StudentSoup.dto.restaurantmenu;

import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenu;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenuCategory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RestaurantMenuUpdateDto {
    private Long restaurantId;

    @NotEmpty(message = "음식 이름은 필수입니다.")
    private String name;
    private RestaurantMenuCategory restaurantMenuCategory;

    @NotNull(message = "음식 가격은 필수입니다.")
    private int cost;

    public void createRestaurantMenuUpdateDto(String name, RestaurantMenuCategory category, int cost) {
        this.name = name;
        this.restaurantMenuCategory = category;
        this.cost = cost;
    }
    public void setRestaurantMenu(RestaurantMenu restaurantMenu){
        this.setRestaurantId(restaurantMenu.getRestaurant().getId());
        this.setName(restaurantMenu.getName());
        this.setRestaurantMenuCategory(restaurantMenu.getRestaurantMenuCategory());
        this.setCost(restaurantMenu.getCost());
    }
}
