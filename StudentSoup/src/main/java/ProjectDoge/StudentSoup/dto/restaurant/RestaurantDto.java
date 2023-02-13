package ProjectDoge.StudentSoup.dto.restaurant;

import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import lombok.Data;

import java.time.LocalTime;

@Data
public class RestaurantDto {
    private Long restaurantId;
    private String restaurantCategory;
    private String name;
    private String address;
    private String startTime;
    private String endTime;
    private String distance;
    private String fileName;
    private double starLiked;
    private int likedCount;
    private int viewCount;
    private boolean like;
    private String tag;
    private String detail;

    // 생성 메서드
    public RestaurantDto createRestaurantDto(Restaurant restaurant, boolean like){
        this.restaurantId = restaurant.getId();
        this.restaurantCategory = restaurant.getRestaurantCategory().getRestaurantCategory();
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.startTime = restaurant.getStartTime().toString();
        this.endTime = restaurant.getEndTime().toString();
        this.fileName = setImageFile(restaurant);
        this.starLiked = restaurant.getStarLiked();
        this.likedCount = restaurant.getLikedCount();
        this.viewCount = restaurant.getViewCount();
        this.distance = restaurant.getDistance() + "M";
        this.like = like;
        this.detail = restaurant.getDetail();
        this.tag = restaurant.getTag();
        return this;
    }

    // 비즈니스 로직
    private String setImageFile(Restaurant restaurant){
        if(!restaurant.getImageFileList().isEmpty()){
            return restaurant.getImageFileList().get(0).getFileName();
        }

        return null;
    }
}
