package ProjectDoge.StudentSoup.dto.restaurant;

import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class RestaurantDetailDto {

    private Long restaurantId;
    private String name;
    private String address;
    private String startTime;
    private String endTime;
    private String distance;
    private List<String> fileName;
    private String schoolName;
    private String longitude;
    private String latitude;
    private String tel;
    private String isDelivery;
    private int reviewCount;
    private double starLiked;
    private int likedCount;
    private int viewCount;
    private boolean like;
    private String tag;
    private String detail;

    // 생성 메서드
    public RestaurantDetailDto createRestaurantDetailDto(Restaurant restaurant, boolean like){
        this.restaurantId = restaurant.getId();
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.startTime = restaurant.getStartTime().toString();
        this.endTime = restaurant.getEndTime().toString();
        this.fileName = setImageFile(restaurant);
        this.schoolName = restaurant.getSchool().getSchoolName();
        this.longitude = restaurant.getCoordinate().split(",")[0];
        this.latitude = restaurant.getCoordinate().split(",")[1];
        this.tel = restaurant.getTel();
        this.reviewCount = restaurant.getRestaurantReviews().size();
        this.starLiked = restaurant.getStarLiked();
        this.likedCount = restaurant.getLikedCount();
        this.viewCount = restaurant.getViewCount();
        this.distance = restaurant.getDistance() + "M";
        this.like = like;
        this.tag = restaurant.getTag();
        this.detail = restaurant.getDetail();
        this.isDelivery = restaurant.getIsDelivery();
        return this;
    }

    // 비즈니스 로직
    private List<String> setImageFile(Restaurant restaurant){

        List<String> imageNameList = new ArrayList<>();
        if(!restaurant.getImageFileList().isEmpty()) {
            for (ImageFile imagefile : restaurant.getImageFileList())
                imageNameList.add(imagefile.getFileName());

            return imageNameList;
        }
        return Collections.emptyList();
    }
}
