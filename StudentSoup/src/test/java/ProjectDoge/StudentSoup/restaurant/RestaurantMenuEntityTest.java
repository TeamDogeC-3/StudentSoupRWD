package ProjectDoge.StudentSoup.restaurant;

import ProjectDoge.StudentSoup.dto.restaurant.RestaurantFormDto;
import ProjectDoge.StudentSoup.dto.restaurantmenu.RestaurantMenuFormDto;
import ProjectDoge.StudentSoup.dto.school.SchoolFormDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantCategory;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenu;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenuCategory;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantMenuValidationException;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantNotFoundException;
import ProjectDoge.StudentSoup.repository.restaurantmenu.RestaurantMenuRepository;
import ProjectDoge.StudentSoup.service.restaurant.RestaurantFindService;
import ProjectDoge.StudentSoup.service.restaurant.RestaurantRegisterService;
import ProjectDoge.StudentSoup.service.restaurantmenu.RestaurantMenuRegisterService;
import ProjectDoge.StudentSoup.service.school.SchoolRegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class RestaurantMenuEntityTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    RestaurantRegisterService restaurantRegisterService;

    @Autowired
    RestaurantFindService restaurantFindService;

    @Autowired
    RestaurantMenuRepository restaurantMenuRepository;
    @Autowired
    RestaurantMenuRegisterService restaurantMenuRegisterService;
    @Autowired
    SchoolRegisterService schoolRegisterService;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMultipartFile multipartFile = new MockMultipartFile(
            "name",
            "",
            "",
            "".getBytes());

    Long restaurantId = 0L;

    Long schoolId = 0L;


    @BeforeEach
    void ????????????_???????????????() throws Exception {
        SchoolFormDto school = createSchool();
        schoolId = schoolRegisterService.join(school);
        RestaurantFormDto restaurantDto = createRestaurant();
        restaurantId = restaurantRegisterService.join(restaurantDto);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/api/test/uri")
                .file(multipartFile));
    }

    @Test
    void ????????????_?????????() throws Exception {
        //given
        RestaurantMenuFormDto restaurantMenuFormDto = createRestaurantMenuDto(restaurantId, "??????1", RestaurantMenuCategory.Main, 10000);
        //when
        Long restaurantMenuId = restaurantMenuRegisterService.join(restaurantMenuFormDto, multipartFile);
        Restaurant restaurant = restaurantFindService.findOne(restaurantId);
        RestaurantMenu restaurantMenu = restaurantMenuRepository.findById(restaurantMenuId).get();
        //then
        assertThat(restaurantMenuId).isEqualTo(restaurantMenu.getId());
    }

    @Test
    void ???????????????_???????????????() {
        //given
        Long errorRestaurantId = 0L;
        RestaurantMenuFormDto restaurantMenuFormDto = createRestaurantMenuDto(errorRestaurantId, "??????1", RestaurantMenuCategory.Main, 10000);
        //then
        assertThatThrownBy(() -> restaurantMenuRegisterService.join(restaurantMenuFormDto, multipartFile))
                .isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    void ?????????????????????() {
        //given
        RestaurantMenuFormDto restaurantMenuFormDto = createRestaurantMenuDto(restaurantId, "??????1", RestaurantMenuCategory.Main, 10000);
        restaurantMenuRegisterService.join(restaurantMenuFormDto, multipartFile);
        RestaurantMenuFormDto duplicateRestaurantMenuFormDto = createRestaurantMenuDto(restaurantId, "??????1", RestaurantMenuCategory.Main, 10000);
        //then
        assertThatThrownBy(() -> restaurantMenuRegisterService.join(duplicateRestaurantMenuFormDto, multipartFile))
                .isInstanceOf(RestaurantMenuValidationException.class);
    }

    private RestaurantMenuFormDto createRestaurantMenuDto(Long restaurantId, String name, RestaurantMenuCategory category, int cost) {
        RestaurantMenuFormDto restaurantMenuFormDto = new RestaurantMenuFormDto().createRestaurantMenuDto(restaurantId, name, category, cost);
        return restaurantMenuFormDto;
    }

    private SchoolFormDto createSchool() {
        SchoolFormDto dto = new SchoolFormDto();
        dto.setSchoolName("????????? ??????");
        dto.setSchoolCoordinate("????????? ?????? ??????");
        return dto;
    }


    private RestaurantFormDto createRestaurant() {
        RestaurantFormDto dto = createRestaurantDto("?????????1", "??????", RestaurantCategory.ASIAN, LocalTime.now(), LocalTime.now(), schoolId, "?????????", new ImageFile(), "????????????", "??????", "?????????", "Y");
        return dto;
    }

    private RestaurantFormDto createRestaurantDto(String name, String address, RestaurantCategory category, LocalTime startTime, LocalTime endTime, Long schoolId, String coordinate, ImageFile file, String tel, String tag, String detail, String isDelivery) {
        RestaurantFormDto dto = new RestaurantFormDto();
        dto.createRestaurantFormDto(name, address, category, startTime, endTime, schoolId, coordinate, file, tel, tag, detail, isDelivery);
        return dto;
    }
}
