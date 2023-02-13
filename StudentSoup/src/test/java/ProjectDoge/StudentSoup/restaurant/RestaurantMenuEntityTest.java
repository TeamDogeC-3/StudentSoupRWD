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
    void 학교등록_음식점등록() throws Exception {
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
    void 메뉴등록_테스트() throws Exception {
        //given
        RestaurantMenuFormDto restaurantMenuFormDto = createRestaurantMenuDto(restaurantId, "메뉴1", RestaurantMenuCategory.Main, 10000);
        //when
        Long restaurantMenuId = restaurantMenuRegisterService.join(restaurantMenuFormDto, multipartFile);
        Restaurant restaurant = restaurantFindService.findOne(restaurantId);
        RestaurantMenu restaurantMenu = restaurantMenuRepository.findById(restaurantMenuId).get();
        //then
        assertThat(restaurantMenuId).isEqualTo(restaurantMenu.getId());
    }

    @Test
    void 메뉴등록시_음식점없음() {
        //given
        Long errorRestaurantId = 0L;
        RestaurantMenuFormDto restaurantMenuFormDto = createRestaurantMenuDto(errorRestaurantId, "메뉴1", RestaurantMenuCategory.Main, 10000);
        //then
        assertThatThrownBy(() -> restaurantMenuRegisterService.join(restaurantMenuFormDto, multipartFile))
                .isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    void 메뉴중복테스트() {
        //given
        RestaurantMenuFormDto restaurantMenuFormDto = createRestaurantMenuDto(restaurantId, "메뉴1", RestaurantMenuCategory.Main, 10000);
        restaurantMenuRegisterService.join(restaurantMenuFormDto, multipartFile);
        RestaurantMenuFormDto duplicateRestaurantMenuFormDto = createRestaurantMenuDto(restaurantId, "메뉴1", RestaurantMenuCategory.Main, 10000);
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
        dto.setSchoolName("테스트 학교");
        dto.setSchoolCoordinate("테스트 학교 좌표");
        return dto;
    }


    private RestaurantFormDto createRestaurant() {
        RestaurantFormDto dto = createRestaurantDto("음식점1", "주소", RestaurantCategory.ASIAN, LocalTime.now(), LocalTime.now(), schoolId, "좌표값", new ImageFile(), "전화번호", "태그", "디테일", "Y");
        return dto;
    }

    private RestaurantFormDto createRestaurantDto(String name, String address, RestaurantCategory category, LocalTime startTime, LocalTime endTime, Long schoolId, String coordinate, ImageFile file, String tel, String tag, String detail, String isDelivery) {
        RestaurantFormDto dto = new RestaurantFormDto();
        dto.createRestaurantFormDto(name, address, category, startTime, endTime, schoolId, coordinate, file, tel, tag, detail, isDelivery);
        return dto;
    }
}
