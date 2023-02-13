package ProjectDoge.StudentSoup.entity.restaurant;

import ProjectDoge.StudentSoup.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "RESTAURANT_LIKE")
@Getter
@Setter
public class RestaurantLike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_LIKED_ID")
    private Member member;

    //== 연관관계 메서드 ==//
    public void setMember(Member member){
        member.getRestaurantLikes().add(this);
    }

    public void setRestaurant(Restaurant restaurant){
        restaurant.getRestaurantLikes().add(this);
    }

    //== 생성 메서드 ==//
    public RestaurantLike createRestaurantLike(Member member, Restaurant restaurant){
        this.restaurant = restaurant;
        this.member = member;
        return this;
    }
}
