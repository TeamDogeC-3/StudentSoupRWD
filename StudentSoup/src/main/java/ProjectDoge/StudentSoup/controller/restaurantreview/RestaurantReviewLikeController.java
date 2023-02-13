package ProjectDoge.StudentSoup.controller.restaurantreview;

import ProjectDoge.StudentSoup.dto.restaurantreview.RestaurantReviewLikeReqDto;
import ProjectDoge.StudentSoup.service.restaurantreview.RestaurantReviewLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantReview/{restaurantReviewId}")
public class RestaurantReviewLikeController {

    private final RestaurantReviewLikeService restaurantReviewLikeService;

    @PostMapping("/like")
    public ResponseEntity<ConcurrentHashMap<String, Object>> restaurantReviewLike(
            @PathVariable Long restaurantReviewId,
            @RequestBody RestaurantReviewLikeReqDto dto){

        ConcurrentHashMap<String, Object> resultMap = restaurantReviewLikeService.restaurantReviewLike(dto.getRestaurantReviewId(), dto.getMemberId());
        return ResponseEntity.ok(resultMap);
    }
}
