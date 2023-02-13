package ProjectDoge.StudentSoup.controller.restaurant;

import ProjectDoge.StudentSoup.dto.restaurant.RestaurantLikeDto;
import ProjectDoge.StudentSoup.service.restaurant.RestaurantLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantLikeController {

    private final RestaurantLikeService restaurantLikeService;

    @PostMapping("/{restaurantId}/like")
    public ResponseEntity<ConcurrentHashMap<String, Object>> restaurantLike(@PathVariable Long restaurantId, @RequestBody RestaurantLikeDto dto){
        ConcurrentHashMap<String, Object> resultMap = restaurantLikeService.restaurantLike(dto.getRestaurantId(), dto.getMemberId());
        return ResponseEntity.ok(resultMap);
    }
}
