package ProjectDoge.StudentSoup.entity.restaurant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RestaurantCategory {

    KOREAN("한식"),
    WESTERN("양식"),
    FASTFOOD("패스트푸드"),
    ASIAN("아시아음식"),
    JAPAN("일식"),
    CHINESE("중식"),
    SNACK("분식"),
    CAFE("카페"),
    BUFFET("뷔페"),
    OTHERS("기타")
    ;

    private final String restaurantCategory;

}
