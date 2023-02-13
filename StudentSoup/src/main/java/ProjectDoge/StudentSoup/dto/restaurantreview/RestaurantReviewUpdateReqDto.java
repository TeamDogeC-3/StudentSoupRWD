package ProjectDoge.StudentSoup.dto.restaurantreview;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class RestaurantReviewUpdateReqDto {

    private String content;
    private int starLiked;
    private List<MultipartFile> multipartFileList;
}
