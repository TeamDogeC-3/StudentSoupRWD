package ProjectDoge.StudentSoup.service.restaurantreview;

import ProjectDoge.StudentSoup.exception.restaurant.RestaurantIdNotSentException;
import ProjectDoge.StudentSoup.repository.file.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantReviewImageCallService {

    private final FileRepository fileRepository;

    public Page<String> callRestaurantReviewImages(Long restaurantId, Pageable pageable){
        checkNullRestaurantId(restaurantId);

        return fileRepository.callImageFileByRestaurantReviewId(restaurantId, pageable);
    }

    private static void checkNullRestaurantId(Long restaurantId) {
        if(restaurantId == null){
            throw new RestaurantIdNotSentException("음식점의 기본키가 전달되지 않았습니다.");
        }
    }
}
