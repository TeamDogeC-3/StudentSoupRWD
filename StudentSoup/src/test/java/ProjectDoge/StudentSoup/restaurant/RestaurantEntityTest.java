package ProjectDoge.StudentSoup.restaurant;

import ProjectDoge.StudentSoup.dto.restaurant.RestaurantFormDto;
import ProjectDoge.StudentSoup.dto.school.SchoolFormDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantCategory;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantValidationException;
import ProjectDoge.StudentSoup.exception.school.SchoolNotFoundException;
import ProjectDoge.StudentSoup.service.restaurant.RestaurantFindService;
import ProjectDoge.StudentSoup.service.restaurant.RestaurantRegisterService;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import ProjectDoge.StudentSoup.service.school.SchoolRegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class RestaurantEntityTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    SchoolRegisterService schoolRegisterService;

    @Autowired
    SchoolFindService schoolFindService;

    @Autowired
    RestaurantFindService restaurantFindService;

    @Autowired
    RestaurantRegisterService restaurantRegisterService;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMultipartFile multipartFile = new MockMultipartFile("mockFile",
            "",
            "",
            "".getBytes());



    Long schoolId = 0L;

    @BeforeEach
    void ????????????() throws Exception {
        SchoolFormDto dto = createSchool();
        schoolId = schoolRegisterService.join(dto);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/api/uri/send")
                .file(multipartFile));
    }

    @Test
    void ????????????????????????() throws Exception{
        //given
        RestaurantFormDto dto = createRestaurantDto("?????????1","??????", RestaurantCategory.ASIAN,LocalTime.now(),LocalTime.now(), schoolId,"?????????",new ImageFile(),"????????????","??????","?????????",  "Y");
        //when
        Long restaurantId = restaurantRegisterService.join(dto);
        School school = schoolFindService.findOne(schoolId);
        //then
        assertThat(restaurantId).isEqualTo(restaurantFindService.findByRestaurantNameAndSchoolName(dto.getName(),school.getSchoolName()).getId());
    }
    @Test
    void ??????????????????_????????????() throws Exception{
        //given
        Long errorSchoolId = 0L;
        RestaurantFormDto dto = createRestaurantDto("?????????1","??????", RestaurantCategory.ASIAN,LocalTime.now(),LocalTime.now(),errorSchoolId,"?????????",new ImageFile(),"????????????","??????","?????????", "Y");
        //then
        assertThatThrownBy(() -> restaurantRegisterService.join(dto))
                .isInstanceOf(SchoolNotFoundException.class);
    }
    @Test
    void ???????????????() throws Exception{
        //given
        RestaurantFormDto dto = createRestaurantDto("?????????1","??????", RestaurantCategory.ASIAN,LocalTime.now(),LocalTime.now(),schoolId,"?????????",new ImageFile(),"????????????","??????","?????????", "Y");
        restaurantRegisterService.join(dto);
        RestaurantFormDto dto1 = createRestaurantDto("?????????1","??????", RestaurantCategory.ASIAN,LocalTime.now(),LocalTime.now(),schoolId,"?????????",new ImageFile(),"????????????","??????","?????????", "Y");
        //then
        assertThatThrownBy(() -> restaurantRegisterService.join(dto1))
                .isInstanceOf(RestaurantValidationException.class);
    }

    private RestaurantFormDto createRestaurantDto(
            String name, String address, RestaurantCategory category,
            LocalTime startTime, LocalTime endTime, Long schoolId,
            String coordinate, ImageFile imageFile, String tel, String tag, String detail, String isDelivery) {
        RestaurantFormDto dto = new RestaurantFormDto();
        dto.createRestaurantFormDto(name,address,category,startTime,endTime,schoolId,coordinate, imageFile,tel,tag,detail,isDelivery);
        return dto;
    }

    private SchoolFormDto createSchool(){
        SchoolFormDto dto = new SchoolFormDto();
        dto.setSchoolName("????????? ??????");
        dto.setSchoolCoordinate("????????? ?????? ??????");
        return dto;
    }
}
