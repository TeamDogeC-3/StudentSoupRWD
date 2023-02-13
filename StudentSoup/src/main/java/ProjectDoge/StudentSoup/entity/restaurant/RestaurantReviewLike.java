package ProjectDoge.StudentSoup.entity.restaurant;

import ProjectDoge.StudentSoup.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "RESTAURANT_REVIEW_LIKE")
@Getter
@Setter
public class RestaurantReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_REVIEW_ID")
    private RestaurantReview restaurantReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_REVIEW_LIKED_ID")
    private Member member;

    //== 연관관계 메서드 ==//
    public void setMember(Member member){
        member.getRestaurantReviewLikes().add(this);
    }

    public void setRestaurant(RestaurantReview restaurantReview){
        restaurantReview.getRestaurantReviewLikes().add(this);
    }

    //== 생성 메서드 ==//
    public RestaurantReviewLike createRestaurantReviewLike(RestaurantReview review, Member member){
        this.restaurantReview = review;
        this.member = member;
        return this;
    }
}
