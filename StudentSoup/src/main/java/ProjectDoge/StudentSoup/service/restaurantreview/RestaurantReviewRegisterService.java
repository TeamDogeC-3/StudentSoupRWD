package ProjectDoge.StudentSoup.service.restaurantreview;

import ProjectDoge.StudentSoup.dto.file.UploadFileDto;
import ProjectDoge.StudentSoup.dto.restaurantreview.RestaurantReviewRegRespDto;
import ProjectDoge.StudentSoup.dto.restaurantreview.RestaurantReviewRequestDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReview;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantNotMatchException;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantReviewContentLessThanFiveException;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantStarLikedMoreThanFiveException;
import ProjectDoge.StudentSoup.repository.file.FileRepository;
import ProjectDoge.StudentSoup.repository.restaurantreview.RestaurantReviewRepository;
import ProjectDoge.StudentSoup.service.file.FileService;
import ProjectDoge.StudentSoup.service.member.MemberFindService;
import ProjectDoge.StudentSoup.service.restaurant.RestaurantFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantReviewRegisterService {

    private final MemberFindService memberFindService;
    private final RestaurantFindService restaurantFindService;
    private final RestaurantReviewRepository restaurantReviewRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;

    @Transactional(rollbackFor = Exception.class)
    public Long join(Long restaurantId, RestaurantReviewRequestDto dto){
        log.info("음식점 리뷰 등록 서비스가 실행되었습니다.");
        RestaurantReview restaurantReview = createRestaurantReview(restaurantId, dto);
        List<UploadFileDto> uploadFileDtoList = fileService.createUploadFileDtoList(dto.getMultipartFileList());
        uploadRestaurantReviewImage(restaurantReview, uploadFileDtoList);
        RestaurantReview createdRestaurantReview = restaurantReviewRepository.save(restaurantReview);
        log.info("음식점 리뷰 등록이 완료되었습니다.");
        return createdRestaurantReview.getId();
    }

    private RestaurantReview createRestaurantReview(Long restaurantId, RestaurantReviewRequestDto dto) {
        log.info("음식점 리뷰 엔티티 생성 메소드가 실행 되었습니다.");
        Restaurant restaurant = restaurantFindService.findOne(restaurantId);
        checkReviewDto(dto, restaurant);

        Member member = memberFindService.findOne(dto.getMemberId());
        RestaurantReview restaurantReview = new RestaurantReview().createRestaurantReview(dto, restaurant, member);
        log.info("음식점 리뷰 엔티티 생성 메소드가 완료 되었습니다. 닉네임 : [{}], 음식점 이름 : [{}]", dto.getNickName(), dto.getRestaurantName());
        return restaurantReview;
    }

    private void checkReviewDto(RestaurantReviewRequestDto dto, Restaurant restaurant) {

        if(dto.getStarLiked() > 5){
            throw new RestaurantStarLikedMoreThanFiveException("별점은 5점을 초과할 수 없습니다.");
        } else if(dto.getContent().length() < 5){
            throw new RestaurantReviewContentLessThanFiveException("리뷰 작성 글은 5글자 이상이여야 합니다.");
        } else if(!dto.getRestaurantName().equals(restaurant.getName())){
            throw new RestaurantNotMatchException("잘못 된 음식점 호출입니다.");
        }
    }

    private void uploadRestaurantReviewImage(RestaurantReview restaurantReview, List<UploadFileDto> uploadFileDtoList) {
        log.info("음식점 리뷰 이미지 업로드 메소드가 실행 되었습니다.");
        if(!uploadFileDtoList.isEmpty()){
            for(UploadFileDto fileDto : uploadFileDtoList){
                log.info("생성되는 이미지 파일 이름 : [{}]", fileDto.getOriginalFileName());
                ImageFile imageFile = new ImageFile().createFile(fileDto);
                restaurantReview.addImageFile(fileRepository.save(imageFile));
            }
        }
        log.info("음식점 리뷰 이미지 업로드가 완료되었습니다.");
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
        log.info("리뷰 등록 응답 객체 생성을 시작하였습니다.");
        RestaurantReviewRegRespDto dto = new RestaurantReviewRegRespDto();
        dto.setRestaurantId(restaurantId);
        dto.setStarLiked(star);
        Long count = restaurantReviewRepository.countByRestaurantId(restaurantId);
        dto.setReviewCount(count);
        log.info("음식점 : [{}], 별점 : [{}], 리뷰 개수 : [{}]", restaurantId, star, count);
        return dto;
    }
}
