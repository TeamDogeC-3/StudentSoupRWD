package ProjectDoge.StudentSoup.service.restaurantreview;

import ProjectDoge.StudentSoup.dto.restaurantreview.RestaurantReviewRegRespDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.member.MemberClassification;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReview;
import ProjectDoge.StudentSoup.exception.member.MemberNotFoundException;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantReviewNotOwnException;
import ProjectDoge.StudentSoup.repository.restaurantreview.RestaurantReviewRepository;
import ProjectDoge.StudentSoup.service.file.FileService;
import ProjectDoge.StudentSoup.service.restaurant.RestaurantFindService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class RestaurantReviewDeleteService {

    @Value("${spring.profiles.active}")
    private String profiles;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;
    private final RestaurantFindService restaurantFindService;
    private final RestaurantReviewFindService restaurantReviewFindService;
    private final FileService fileService;
    private final RestaurantReviewRepository restaurantReviewRepository;

    @Transactional
    public ConcurrentHashMap<String, Object> deleteRestaurantReview(Long restaurantReviewId, Long memberId){
        checkLoginStatus(memberId);
        ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();
        RestaurantReview findRestaurantReview = restaurantReviewFindService.findOne(restaurantReviewId);
        if(!findRestaurantReview.getMember().getMemberId().equals(memberId) && findRestaurantReview.getMember().getMemberClassification() != MemberClassification.ADMIN) {
            throw new RestaurantReviewNotOwnException("해당 리뷰는 해당 회원이 작성한 리뷰가 아닙니다.");
        }
        ifPresentImageDelete(findRestaurantReview);
        restaurantReviewRepository.delete(findRestaurantReview);
        resultMap.put("result", "ok");
        return resultMap;
    }

    private void ifPresentImageDelete(RestaurantReview findRestaurantReview) {
        for(ImageFile image : findRestaurantReview.getImageFileList()){
            fileService.deleteFile(image);
        }
    }

    private void checkLoginStatus(Long memberId){
        log.info("회원의 로그인 상태를 확인합니다. [{}]", memberId);
        if(memberId == null){
            throw new MemberNotFoundException("기본키가 전달되지 않았거나, 로그인 되지 않은 상태에서 리뷰 삭제는 불가능합니다.");
        }
        log.info("회원의 로그인 상태 확인이 완료되었습니다.");
    }

    @Transactional(rollbackFor = Exception.class)
    public RestaurantReviewRegRespDto starUpdate(Long restaurantId){
        Restaurant restaurant = restaurantFindService.findOne(restaurantId);
        log.info("레스토랑의 업데이트 전 별점 : [{}]", restaurant.getStarLiked());

        Double starAvg = restaurantReviewRepository.avgByRestaurantId(restaurantId).orElse(0.0);

        double star = Math.round(starAvg * 10) / 10.0;

        restaurant.updateStarLiked(star);
        log.info("레스토랑의 업데이트 된 별점 : [{}] , 쿼리 결과 별점 : [{}]", restaurant.getStarLiked(), star);

        return restaurantRespDto(restaurantId, star);
    }

    private RestaurantReviewRegRespDto restaurantRespDto(Long restaurantId, double star) {
        log.info("리뷰 삭제 응답 객체 생성을 시작하였습니다.");
        RestaurantReviewRegRespDto dto = new RestaurantReviewRegRespDto();
        dto.setRestaurantId(restaurantId);
        dto.setStarLiked(star);
        Long count = restaurantReviewRepository.countByRestaurantId(restaurantId);
        dto.setReviewCount(count);
        log.info("음식점 : [{}], 별점 : [{}], 리뷰 개수 : [{}]", restaurantId, star, count);
        return dto;
    }

}
