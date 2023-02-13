package ProjectDoge.StudentSoup.dto.restaurantmenu;

import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenu;
import lombok.Data;

@Data
public class RestaurantMenuDto {
    private Long restaurantMenuId;
    private String restaurantMenuName;
    private String restaurantMenuCategory;
    private int cost;
    private String fileName;
    private int likedCount;
    private boolean like;

    public RestaurantMenuDto createRestaurantMenu(RestaurantMenu restaurantMenu, boolean like){
        this.restaurantMenuId = restaurantMenu.getId();
        this.restaurantMenuName = restaurantMenu.getName();
        this.restaurantMenuCategory = restaurantMenu.getRestaurantMenuCategory().getRestaurantMenuCategory();
        this.cost = restaurantMenu.getCost();
        this.fileName = setImageFile(restaurantMenu);
        this.likedCount = restaurantMenu.getLikedCount();
        this.like = like;
        return this;
    }

    // 비즈니스 로직
    private String setImageFile(RestaurantMenu restaurantMenu){
        ImageFile imageFile = restaurantMenu.getImageFile();
        if(imageFile == null){
            return null;
        }
        return imageFile.getFileName();
    }

}
