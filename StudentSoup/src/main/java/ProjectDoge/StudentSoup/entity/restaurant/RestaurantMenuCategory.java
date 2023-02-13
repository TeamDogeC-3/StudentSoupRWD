package ProjectDoge.StudentSoup.entity.restaurant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RestaurantMenuCategory {
    Main("주메뉴"),
    Side("사이드메뉴"),
    Drink("음료 및 주류")
    ;

    private final String restaurantMenuCategory;
}
