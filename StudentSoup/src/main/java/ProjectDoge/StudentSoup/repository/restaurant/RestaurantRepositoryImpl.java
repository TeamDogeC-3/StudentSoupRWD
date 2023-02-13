package ProjectDoge.StudentSoup.repository.restaurant;

import ProjectDoge.StudentSoup.dto.restaurant.RestaurantSortedCase;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantCategory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static ProjectDoge.StudentSoup.entity.restaurant.QRestaurant.restaurant;
import static ProjectDoge.StudentSoup.entity.school.QSchool.school;

@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Restaurant> findByRestaurantNameAndSchool_SchoolName(String restaurantName, String schoolName) {
        Restaurant query = queryFactory
                .select(restaurant)
                .from(restaurant)
                .leftJoin(restaurant.school, school)
                .fetchJoin()
                .where(restaurant.name.eq(restaurantName), restaurant.school.schoolName.eq(schoolName))
                .fetchOne();
        return Optional.ofNullable(query);
    }

    @Override
    public List<Restaurant> findRestaurantDynamicSearch(String column, String find_value) {
        List<Restaurant> query = queryFactory
                .selectFrom(restaurant)
                .where(checkRestaurantCategoryEq(column, find_value),
                        (checkRestaurantNameEq(column, find_value)),
                        (checkRestaurantSchoolNameEq(column, find_value)),
                        (checkRestaurantTagContains(column, find_value)))
                .fetch();

        return query;
    }

    private BooleanExpression checkRestaurantCategoryEq(String column, String category) {
        if (column.equals("category")) {
            for (RestaurantCategory restaurantCategory : RestaurantCategory.values()) {
                if (restaurantCategory.getRestaurantCategory().equals(category))
                    return restaurant.restaurantCategory.eq(RestaurantCategory.valueOf(restaurantCategory.name()));
            }
        }
        return null;
    }

    private BooleanExpression checkRestaurantNameEq(String column, String restaurantName) {
        if (column.equals("name") && restaurantName != null) {
            return restaurant.name.contains(restaurantName);
        }
        return null;
    }

    private BooleanExpression checkRestaurantSchoolNameEq(String column, String schoolName) {
        if (column.equals("schoolName") && schoolName != null) {
            return restaurant.school.schoolName.contains(schoolName);
        }
        return null;
    }

    private BooleanExpression checkRestaurantTagContains(String column, String tag) {
        if (column.equals("tag") && tag != null) {
            return restaurant.tag.contains(tag);
        }
        return null;
    }

    @Override
    public List<Restaurant> findBySchoolId(Long schoolId) {
        List<Restaurant> query = queryFactory
                .select(restaurant)
                .from(restaurant)
                .leftJoin(restaurant.school, school)
                .fetchJoin()
                .where(restaurant.school.id.eq(schoolId))
                .fetch();
        return query;
    }

    @Override
    public List<Restaurant> findBySchoolIdAndCategoryAndSorted(Long schoolId, String category, int sorted, Pageable pageable) {
        List<Restaurant> query = queryFactory
                .select(restaurant)
                .from(restaurant)
                .leftJoin(restaurant.school, school)
                .fetchJoin()
                .where(restaurant.school.id.eq(schoolId), checkSortedRestaurantCategory(category))
                .orderBy(checkSortedRestaurantCondition(sorted))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return query;
    }

    @Override
    public List<Restaurant> findBySchoolId(Long schoolId, Pageable pageable) {
        List<Restaurant> content = queryFactory
                .select(restaurant)
                .from(restaurant)
                .leftJoin(restaurant.school, school)
                .fetchJoin()
                .where(restaurant.school.id.eq(schoolId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return content;
    }
    @Override
    public JPAQuery<Long> countBySchoolId(Long schoolId,String category) {
        return queryFactory
                .select(restaurant.count())
                .from(restaurant)
                .where(restaurant.school.id.eq(schoolId), checkSortedRestaurantCategory(category));
    }

    private BooleanExpression checkSortedRestaurantCategory(String category) {
            if(category.equals("ALL")){
                return null;
            }
            return restaurant.restaurantCategory.eq(RestaurantCategory.valueOf(category));
    }

    private OrderSpecifier<?> checkSortedRestaurantCondition(int sorted){

        if(RestaurantSortedCase.STAR.getValue() == sorted)
            return restaurant.starLiked.desc();
        else if(RestaurantSortedCase.LIKED.getValue() == sorted)
            return restaurant.likedCount.desc();
        else if(RestaurantSortedCase.REVIEW.getValue() == sorted)
            return restaurant.restaurantReviews.size().desc();
        else if(RestaurantSortedCase.DISTANCE.getValue() == sorted){
            return restaurant.distance.asc();
        } else {
            return restaurant.id.asc();
        }
    }

}
