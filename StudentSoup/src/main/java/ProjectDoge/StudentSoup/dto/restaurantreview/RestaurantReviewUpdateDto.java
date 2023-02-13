package ProjectDoge.StudentSoup.dto.restaurantreview;

import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReview;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class RestaurantReviewUpdateDto {

    private Long restaurantReviewId;
    private Long restaurantId;
    private Long memberId;
    private String nickName;
    private String content;
    private int starLiked;
    private List<String> imageFileList;


    //== 생성 메서드 ==//
    public RestaurantReviewUpdateDto(RestaurantReview restaurantReview){
        this.restaurantReviewId = restaurantReview.getId();
        this.restaurantId = restaurantReview.getRestaurant().getId();
        this.memberId = restaurantReview.getMember().getMemberId();
        this.nickName = restaurantReview.getNickname();
        this.content = restaurantReview.getContent();
        this.starLiked =restaurantReview.getStarLiked();
        this.imageFileList = setImageFileList(restaurantReview.getImageFileList());
    }

    private List<String> setImageFileList(List<ImageFile> imageFileList){
        if(!imageFileList.isEmpty()){
            List<String> imageFileNameList = new ArrayList<>();
            for(ImageFile img : imageFileList){
                imageFileNameList.add(img.getFileName());
            }
            return imageFileNameList;
        }
        return Collections.emptyList();
    }
}
