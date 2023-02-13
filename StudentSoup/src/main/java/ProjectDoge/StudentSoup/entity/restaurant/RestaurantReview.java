package ProjectDoge.StudentSoup.entity.restaurant;

import ProjectDoge.StudentSoup.dto.restaurantreview.RestaurantReviewRequestDto;
import ProjectDoge.StudentSoup.dto.restaurantreview.RestaurantReviewUpdateReqDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RESTAURANT_REVIEW")
@Getter
@Setter
public class RestaurantReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "WRITER_NICKNAME")
    public String nickname;  // Member

    @Lob
    private String content;

    @OneToMany(mappedBy = "restaurantReview", cascade = CascadeType.ALL)
    private List<ImageFile> imageFileList = new ArrayList<>();

    // 리뷰가 좋아요 눌린 수
    private int likedCount;

    // 리뷰에서 작성한 음식점의 별점
    private int starLiked;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime writeDate;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "restaurantReview", cascade = CascadeType.REMOVE)
    private List<RestaurantReviewLike> restaurantReviewLikes = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
        restaurant.getRestaurantReviews().add(this);
    }

    public void setMember(Member member){
        this.member = member;
        member.getRestaurantReviews().add(this);
    }

    //== 생성 메서드 ==//
    public RestaurantReview createRestaurantReview(RestaurantReviewRequestDto dto,
                                                   Restaurant restaurant,
                                                   Member member){
        this.restaurant = restaurant;
        this.member = member;
        this.nickname = dto.getNickName();
        this.content = dto.getContent();
        this.likedCount = 0;
        this.starLiked = dto.getStarLiked();
        this.writeDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        return this;
    }

    public void updateRestaurantReview(RestaurantReviewUpdateReqDto dto){
        this.content = dto.getContent();
        this.starLiked = dto.getStarLiked();
    }

    //== 비즈니스 로직 ==//
    public void addImageFile(ImageFile imageFile){
        if(imageFile.getRestaurantReview() != this)
            imageFile.setRestaurantReview(this);

        this.getImageFileList().add(imageFile);
    }

    public void addLikedCount(){
        this.likedCount += 1;
    }
    public void minusLikedCount(){
        if(this.likedCount != 0) {
            this.likedCount -= 1;
        }
    }

}
