package ProjectDoge.StudentSoup.controller.restaurant;

import ProjectDoge.StudentSoup.dto.restaurantmenu.RestaurantMenuLikeRequestDto;
import ProjectDoge.StudentSoup.service.restaurantmenu.RestaurantMenuLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant/{restaurantId}")
public class RestaurantMenuLikeController {

    private final RestaurantMenuLikeService restaurantMenuLikeService;

    @PostMapping("/menu/like")
    public ResponseEntity<ConcurrentHashMap<String, Object>> restaurantMenuLike(@PathVariable Long restaurantId, @RequestBody RestaurantMenuLikeRequestDto dto){
        return ResponseEntity.ok(restaurantMenuLikeService.restaurantMenuLike(dto.getRestaurantMenuId(), dto.getMemberId()));
    }
}
