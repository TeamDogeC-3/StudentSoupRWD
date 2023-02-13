package ProjectDoge.StudentSoup.repository.restaurantmenu;

import ProjectDoge.StudentSoup.entity.board.BoardCategory;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenu;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenuCategory;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static ProjectDoge.StudentSoup.entity.board.QBoard.board;
import static ProjectDoge.StudentSoup.entity.restaurant.QRestaurant.restaurant;
import static ProjectDoge.StudentSoup.entity.restaurant.QRestaurantMenu.restaurantMenu;

@RequiredArgsConstructor
public class RestaurantMenuRepositoryImpl implements RestaurantMenuRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<RestaurantMenu> findByRestaurantMenuNameAndRestaurant_RestaurantId(String MenuName, Long restaurantId){
        RestaurantMenu query = queryFactory
                .selectFrom(restaurantMenu)
                .leftJoin(restaurantMenu.restaurant,restaurant)
                .fetchJoin()
                .where(restaurantMenu.name.eq(MenuName),restaurantMenu.restaurant.id.eq(restaurantId))
                .fetchOne();
        return Optional.ofNullable(query);

    }

    @Override
    public List<RestaurantMenu> findByRestaurantId(Long restaurantId, Pageable pageable){
        List<RestaurantMenu> query = queryFactory
                .selectFrom(restaurantMenu)
                .leftJoin(restaurantMenu.restaurant,restaurant)
                .fetchJoin()
                .where(restaurantMenu.restaurant.id.eq(restaurantId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(priorMenuCategory())
                .fetch();
        return query;
    }

    @Override
    public List<RestaurantMenu> findByRestaurantId(Long restaurantId) {
        List<RestaurantMenu> query = queryFactory
                .selectFrom(restaurantMenu)
                .leftJoin(restaurantMenu.restaurant,restaurant)
                .fetchJoin()
                .where(restaurantMenu.restaurant.id.eq(restaurantId))
                .fetch();
        return query;
    }

    private OrderSpecifier<?> priorMenuCategory() {
        NumberExpression<Integer> cases = new CaseBuilder()
                .when(restaurantMenu.restaurantMenuCategory.eq(RestaurantMenuCategory.Main))
                .then(1)
                .otherwise(2);

        return new OrderSpecifier<>(Order.ASC, cases);
    }

    @Override
    public JPAQuery<Long> countByRestaurantId(Long restaurantId) {
        return queryFactory
                .select(restaurantMenu.count())
                .from(restaurantMenu)
                .where(restaurantMenu.restaurant.id.eq(restaurantId));
    }

}

