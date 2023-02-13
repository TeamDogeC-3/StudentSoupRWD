package ProjectDoge.StudentSoup.service.restaurantreview;

import ProjectDoge.StudentSoup.dto.file.UploadFileDto;
import ProjectDoge.StudentSoup.dto.restaurant.RestaurantUpdateDto;
import ProjectDoge.StudentSoup.dto.restaurantreview.RestaurantReviewRequestDto;
import ProjectDoge.StudentSoup.dto.restaurantreview.RestaurantReviewUpdateDto;
import ProjectDoge.StudentSoup.dto.restaurantreview.RestaurantReviewUpdateReqDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.member.MemberClassification;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReview;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantReviewContentLessThanFiveException;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantReviewNotOwnException;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantStarLikedMoreThanFiveException;
import ProjectDoge.StudentSoup.repository.file.FileRepository;
import ProjectDoge.StudentSoup.repository.restaurantreview.RestaurantReviewRepository;
import ProjectDoge.StudentSoup.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantReviewUpdateService {

    private final RestaurantReviewRepository restaurantReviewRepository;
    private final RestaurantReviewFindService restaurantReviewFindService;
    private final FileRepository fileRepository;
    private final FileService fileService;

    public RestaurantReviewUpdateDto findUpdateRestaurantReviewForm(Long restaurantReviewId, Long memberId){
        RestaurantReview review = restaurantReviewFindService.findOne(restaurantReviewId);
        checkReviewAuthorization(memberId, review);
        return new RestaurantReviewUpdateDto(review);
    }

    private void checkReviewAuthorization(Long memberId, RestaurantReview review) {
        if(!review.getMember().getMemberId().equals(memberId) && !review.getMember().getMemberClassification().equals(MemberClassification.ADMIN))
            throw new RestaurantReviewNotOwnException("해당 리뷰의 작성자만 수정이 가능합니다.");
    }

    @Transactional(rollbackFor = Exception.class)
    public Long updateRestaurantReview(Long restaurantReviewId, RestaurantReviewUpdateReqDto dto){
        log.info("음식점 리뷰 업데이트 메소드가 실행 되었습니다.");
        RestaurantReview restaurantReview = restaurantReviewFindService.findOne(restaurantReviewId);
        checkReviewDto(dto);
        restaurantReview.updateRestaurantReview(dto);
        List<UploadFileDto> uploadFileDtoList = fileService.createUploadFileDtoList(dto.getMultipartFileList());
        uploadRestaurantReviewImage(restaurantReview, uploadFileDtoList);
        return restaurantReviewId;
    }

    private void checkReviewDto(RestaurantReviewUpdateReqDto dto){
        if(dto.getStarLiked() > 5){
            throw new RestaurantStarLikedMoreThanFiveException("별점은 5점을 초과할 수 없습니다.");
        } else if(dto.getContent().length() < 5){
            throw new RestaurantReviewContentLessThanFiveException("리뷰 작성 글은 5글자 이상이여야 합니다.");
        }
    }

    private void uploadRestaurantReviewImage(RestaurantReview restaurantReview, List<UploadFileDto> uploadFileDtoList) {
        if(!uploadFileDtoList.isEmpty()){
            log.info("음식점 리뷰 이미지 업데이트를 시작하였습니다.");
            ifPresentImageFileDelete(restaurantReview);
            for(UploadFileDto fileDto : uploadFileDtoList){
                log.info("생성되는 이미지 파일 이름 : [{}]", fileDto.getOriginalFileName());
                ImageFile imageFile = new ImageFile().createFile(fileDto);
                restaurantReview.addImageFile(fileRepository.save(imageFile));
            }
        }
        log.info("음식점 리뷰 이미지 업데이트가 완료되었습니다.");
    }

    private void ifPresentImageFileDelete(RestaurantReview restaurantReview) {
        if(!restaurantReview.getImageFileList().isEmpty()){
            log.info("이미지 파일이 존재하여 이미지 파일을 삭제하였습니다.");
            for(ImageFile image : restaurantReview.getImageFileList()){
                fileService.deleteFile(image);
            }
            fileRepository.deleteAllInBatch(restaurantReview.getImageFileList());
        }
        restaurantReviewRepository.save(restaurantReview);
    }

}
