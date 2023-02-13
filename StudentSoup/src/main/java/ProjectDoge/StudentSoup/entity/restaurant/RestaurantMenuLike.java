package ProjectDoge.StudentSoup.entity.restaurant;

import ProjectDoge.StudentSoup.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "RESTAURANT_MENU_LIKE")
@Getter
@Setter
public class RestaurantMenuLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_MENU_ID")
    private RestaurantMenu restaurantMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_MENU_LIKED_ID")
    private Member member;

    //== 생성 메서드 ==//
    public RestaurantMenuLike createRestaurantMenuLike(Member member, RestaurantMenu restaurantMenu){
        this.restaurantMenu = restaurantMenu;
        this.member = member;
        return this;
    }
}
