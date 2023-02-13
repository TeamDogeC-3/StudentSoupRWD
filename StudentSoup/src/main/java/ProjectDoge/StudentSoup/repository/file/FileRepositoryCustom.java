package ProjectDoge.StudentSoup.repository.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FileRepositoryCustom {

    Page<String> callImageFileByRestaurantReviewId(Long restaurantId, Pageable pageable);
}
