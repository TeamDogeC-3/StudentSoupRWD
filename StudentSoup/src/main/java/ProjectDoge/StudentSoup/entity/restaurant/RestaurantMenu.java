package ProjectDoge.StudentSoup.entity.restaurant;

import ProjectDoge.StudentSoup.dto.restaurantmenu.RestaurantMenuFormDto;
import ProjectDoge.StudentSoup.dto.restaurantmenu.RestaurantMenuUpdateDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RESTAURANT_MENU")
@Getter
@Setter
public class RestaurantMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    private String name;

    @Enumerated
    private RestaurantMenuCategory restaurantMenuCategory;

    private int cost;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IMAGE_FILE_ID")
    private ImageFile imageFile;

    private int likedCount;

    @OneToMany(mappedBy = "restaurantMenu", cascade = CascadeType.REMOVE)
    private List<RestaurantMenuLike> restaurantMenuLikes = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
        restaurant.getRestaurantMenus().add(this);
    }

    //== 생성 메서드 ==//
    public RestaurantMenu createRestaurantMenu(RestaurantMenuFormDto form, Restaurant restaurant, ImageFile imageFile){
        this.setName(form.getName());
        this.setRestaurantMenuCategory(form.getRestaurantMenuCategory());
        this.setRestaurant(restaurant);
        this.setCost(form.getCost());
        this.setImageFile(imageFile);
        return this;
    }
    public  void updateRestaurantMenu(RestaurantMenuUpdateDto restaurantMenuUpdateDto,ImageFile imageFile){
        this.setName(restaurantMenuUpdateDto.getName());
        this.setRestaurantMenuCategory(restaurantMenuUpdateDto.getRestaurantMenuCategory());
        this.setCost(restaurantMenuUpdateDto.getCost());
        this.setImageFile(imageFile);
    }

    //== 비즈니스 로직 ==//
    public void addLikedCount(){
        this.likedCount += 1;
    }
    public void minusLikedCount(){
        if(this.likedCount != 0) {
            this.likedCount -= 1;
        }
    }
}
