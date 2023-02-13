package ProjectDoge.StudentSoup.dto.restaurantreview;

import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReview;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class RestaurantReviewDto {
    private Long restaurantReviewId;
    private Long restaurantId;
    private String restaurantName;
    private Long memberId;
    private String memberProfileImageName;
    private String nickName;
    private String content;
    private String writeDate;
    private int starLiked;
    private int likedCount;
    private boolean like;
    private List<String> imageFileNameList;


    public RestaurantReviewDto(RestaurantReview restaurantReview, boolean isLike){
        this.restaurantReviewId = restaurantReview.getId();
        this.restaurantId = restaurantReview.getRestaurant().getId();
        this.restaurantName = restaurantReview.getRestaurant().getName();
        this.memberId = restaurantReview.getMember().getMemberId();
        this.memberProfileImageName = setProfileImageFileName(restaurantReview.getMember());
        this.nickName = restaurantReview.getMember().getNickname();
        this.content = restaurantReview.getContent();
        this.writeDate = restaurantReview.getWriteDate().toLocalDate().toString();
        this.starLiked = restaurantReview.getStarLiked();
        this.likedCount = restaurantReview.getLikedCount();
        this.imageFileNameList = setImageFileList(restaurantReview);
        this.like = isLike;
    }

    // 비즈니스 로직
    private String setProfileImageFileName(Member member){
        if(member.getImageFile() != null){
            return member.getImageFile().getFileName();
        }
        return null;
    }


    private List<String> setImageFileList(RestaurantReview restaurantReview){
        List<String> imageFileNameList = new ArrayList<>();
        if(!restaurantReview.getImageFileList().isEmpty()){
            for(ImageFile imageFile : restaurantReview.getImageFileList()){
                imageFileNameList.add(imageFile.getFileName());
            }
        }
        return imageFileNameList;
    }
}
