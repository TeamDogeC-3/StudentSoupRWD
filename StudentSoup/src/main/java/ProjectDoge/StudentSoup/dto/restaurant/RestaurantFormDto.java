package ProjectDoge.StudentSoup.dto.restaurant;

import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class RestaurantFormDto {
    @NotEmpty(message = "레스토랑 입력은 필수입니다.")
    private String name;

    private String address;

    private RestaurantCategory restaurantCategory;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "시작시간 입력은 필수입니다.")
    private LocalTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "종료시간 입력은 필수입니다.")
    private LocalTime endTime;

    private Long schoolId;

    private String coordinate;

    private List<MultipartFile> multipartFileList;

    @NotEmpty(message = "가게번호 입력은 필수 입니다.")
    private String tel;

    private String tag;

    private String detail;

    private String isDelivery;

    //== 생성 메서드 ==//
    public void setRestaurant(Restaurant restaurant){
        this.setName(restaurant.getName());
        this.setAddress(restaurant.getAddress());
        this.setRestaurantCategory(restaurant.getRestaurantCategory());
        this.setStartTime(restaurant.getStartTime());
        this.setEndTime(restaurant.getEndTime());
        this.setCoordinate(restaurant.getCoordinate());
        this.setTel(restaurant.getTel());
        this.setTag(restaurant.getTag());
        this.setDetail(restaurant.getDetail());
        this.setIsDelivery(restaurant.getIsDelivery());
    }

    public RestaurantFormDto createRestaurantFormDto(
            String name, String address, RestaurantCategory category, LocalTime startTime, LocalTime endTime,
            Long schoolId, String coordinate, ImageFile imageFile, String tel, String tag, String detail, String isDelivery) {
        this.name = name;
        this.address = address;
        this.restaurantCategory = category;
        this.startTime = startTime;
        this.endTime = endTime;
        this.schoolId = schoolId;
        this.coordinate = coordinate;
        this.tel = tel;
        this.tag = tag;
        this.detail = detail;
        this.isDelivery = isDelivery;

        return this;
    }
}
