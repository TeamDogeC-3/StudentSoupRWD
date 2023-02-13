package ProjectDoge.StudentSoup.controller.admin;


import ProjectDoge.StudentSoup.dto.restaurant.RestaurantFormDto;
import ProjectDoge.StudentSoup.dto.restaurant.RestaurantSearch;
import ProjectDoge.StudentSoup.dto.restaurant.RestaurantUpdateDto;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantCategory;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.repository.restaurant.RestaurantRepository;
import ProjectDoge.StudentSoup.repository.school.SchoolRepository;
import ProjectDoge.StudentSoup.service.admin.AdminRestaurantService;
import ProjectDoge.StudentSoup.service.restaurant.RestaurantRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminRestaurantController {

    private final RestaurantRegisterService restaurantRegisterService;
    private final AdminRestaurantService adminRestaurantService;
    private final SchoolRepository schoolRepository;

    private final RestaurantRepository restaurantRepository;


    @GetMapping("admin/restaurant")
    public String createRestaurant(Model model) {
        List<School> schools = schoolRepository.findAll();
        model.addAttribute("restaurantForm", new RestaurantFormDto());
        model.addAttribute("schools", schools);
        model.addAttribute("restaurantCategory", RestaurantCategory.values());
        return "/admin/restaurant/createRestaurant";
    }

    @PostMapping("admin/restaurant")
    public String createRestaurant(@ModelAttribute RestaurantFormDto restaurantFormDto) {
        restaurantRegisterService.join(restaurantFormDto);
        return "redirect:/admin/restaurants";
    }

    @GetMapping("admin/restaurants")
    public String restaurantList(@ModelAttribute RestaurantSearch restaurantSearch, Model model) {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        model.addAttribute("restaurants", restaurants);

        List<Restaurant> findRestaurants = adminRestaurantService.adminSearchRestaurants(restaurantSearch.getColumn(), restaurantSearch.getFind_value());
        model.addAttribute("findRestaurants", findRestaurants);

        return "admin/restaurant/restaurantList";
    }

    @GetMapping("admin/restaurant/edit/{restaurantId}")
    public String editRestaurant(@PathVariable Long restaurantId, Model model) {
        RestaurantUpdateDto restaurantFormDto = adminRestaurantService.adminFindUpdateRestaurant(restaurantId);
        List<School> schools = schoolRepository.findAll();
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("restaurantForm", restaurantFormDto);
        model.addAttribute("schools", schools);
        model.addAttribute("restaurantCategory", RestaurantCategory.values());

        return "/admin/restaurant/updateRestaurant";
    }

    @PostMapping("admin/restaurant/edit/{restaurantId}")
    public String editRestaurant(@PathVariable Long restaurantId,
                                 RestaurantUpdateDto restaurantUpdateDto) {
        adminRestaurantService.adminUpdateRestaurant(restaurantId, restaurantUpdateDto);
        return "redirect:/admin/restaurants";
    }
    @GetMapping("admin/restaurant/{restaurantId}")
    public String deleteRestaurant(@PathVariable Long restaurantId){
        adminRestaurantService.adminDeleteRestaurant(restaurantId);
        return "redirect:/admin/restaurants";
    }
}
