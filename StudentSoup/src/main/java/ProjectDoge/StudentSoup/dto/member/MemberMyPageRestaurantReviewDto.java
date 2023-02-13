package ProjectDoge.StudentSoup.dto.member;

import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReview;
import lombok.Data;

import java.util.List;

@Data
public class MemberMyPageRestaurantReviewDto {

    private Long restaurantReviewId;
    private Long restaurantId;
    private String imageName;
    private int starLiked;
    private String content;
    private String writeDate;
    private int likedCount;

    public MemberMyPageRestaurantReviewDto(){
    }

    public MemberMyPageRestaurantReviewDto(RestaurantReview restaurantReview){
        this.restaurantReviewId = restaurantReview.getId();
        this.restaurantId = restaurantReview.getRestaurant().getId();
        this.imageName = setImageFileName(restaurantReview.getImageFileList());
        this.starLiked = restaurantReview.getStarLiked();
        this.content = restaurantReview.getContent();
        this.writeDate = restaurantReview.getWriteDate().toLocalDate().toString().replaceAll("-", ".");
        this.likedCount = restaurantReview.getLikedCount();
    }

    private String setImageFileName(List<ImageFile> imageFileList){
        if(imageFileList.size() != 0){
            return imageFileList.get(0).getFileName();
        }
        return null;
    }
}
