package ProjectDoge.StudentSoup.repository.file;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static ProjectDoge.StudentSoup.entity.file.QImageFile.imageFile;
import static ProjectDoge.StudentSoup.entity.restaurant.QRestaurantReview.restaurantReview;

@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<String> callImageFileByRestaurantReviewId(Long restaurantId, Pageable pageable) {

        List<String> content = queryFactory
                .select(imageFile.fileName)
                .from(imageFile)
                .where(restaurantReview.restaurant.id.eq(restaurantId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        JPAQuery<Long> count = queryFactory
                .select(imageFile.fileName.count())
                .from(imageFile)
                .where(restaurantReview.restaurant.id.eq(restaurantId));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }
}
