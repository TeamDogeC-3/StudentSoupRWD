package ProjectDoge.StudentSoup.repository.restaurant;

import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepositoryCustom {
    Optional<Restaurant> findByRestaurantNameAndSchool_SchoolName(String name, String schoolName);

    List<Restaurant> findRestaurantDynamicSearch(String column, String find_value);

    List<Restaurant> findBySchoolId(Long schoolId);

    List<Restaurant> findBySchoolIdAndCategoryAndSorted(Long schoolId, String category, int sorted, Pageable pageable);

    List<Restaurant> findBySchoolId(Long schoolId, Pageable pageable);

    JPAQuery<Long> countBySchoolId(Long schoolId,String category);
}
