package ProjectDoge.StudentSoup.controller.restaurantreview;

import ProjectDoge.StudentSoup.dto.restaurantreview.RestaurantReviewUpdateDto;
import ProjectDoge.StudentSoup.dto.restaurantreview.RestaurantReviewUpdateReqDto;
import ProjectDoge.StudentSoup.service.restaurantreview.RestaurantReviewUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantReview/{memberId}/{restaurantReviewId}")
public class RestaurantReviewUpdateController {

    private final RestaurantReviewUpdateService restaurantReviewUpdateService;

    @GetMapping
    public RestaurantReviewUpdateDto restaurantReviewUpdateForm(@PathVariable Long memberId, @PathVariable Long restaurantReviewId){
        return restaurantReviewUpdateService.findUpdateRestaurantReviewForm(restaurantReviewId, memberId);
    }

    @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ConcurrentHashMap<String, Object>> restaurantReviewUpdate(
            @PathVariable Long memberId,
            @PathVariable Long restaurantReviewId,
            RestaurantReviewUpdateReqDto dto)
    {
        restaurantReviewUpdateService.updateRestaurantReview(restaurantReviewId, dto);
        ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();

        resultMap.put("result", "update");
        resultMap.put("restaurantReviewId", restaurantReviewId);

        return ResponseEntity.ok(resultMap);
    }
}
