package ProjectDoge.StudentSoup.service.member;

import ProjectDoge.StudentSoup.dto.member.*;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReview;
import ProjectDoge.StudentSoup.exception.member.MemberIdNotSentException;
import ProjectDoge.StudentSoup.repository.board.BoardRepository;
import ProjectDoge.StudentSoup.repository.boardreply.BoardReplyRepository;
import ProjectDoge.StudentSoup.repository.member.MemberRepository;
import ProjectDoge.StudentSoup.repository.restaurantreview.RestaurantReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberMyPageCallService {
    private final BoardRepository boardRepository;
    private final BoardReplyRepository boardReplyRepository;
    private final RestaurantReviewRepository restaurantReviewRepository;
    private final MemberRepository memberRepository;
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MemberMyPageDto callMyPageMain(Long memberId){
        isNotNullMemberId(memberId);
        Member member = memberRepository.fullFindById(memberId);
        return new MemberMyPageDto(member);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MemberMyPageDetailDto callMyPageDetail(Long memberId){
        isNotNullMemberId(memberId);
        Long boardCount = boardRepository.countByMemberId(memberId);
        Long boardReplyCount = boardReplyRepository.countByMemberId(memberId);

        return new MemberMyPageDetailDto(boardCount, boardReplyCount);
    }


    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Page<MemberMyPageBoardReplyDto> callMyPageBoardReview(Long memberId, Pageable pageable){
        isNotNullMemberId(memberId);
        return boardReplyRepository.findByMemberIdForMyPage(memberId, pageable);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Page<MemberMyPageBoardDto> callMyPageBoard(Long memberId, Pageable pageable){
        isNotNullMemberId(memberId);
        return boardRepository.findByMemberIdForMyPage(memberId, pageable);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Page<MemberMyPageRestaurantReviewDto> callMyRestaurantReview(Long memberId, String cond, Pageable pageable){
        isNotNullMemberId(memberId);
        Page<RestaurantReview> pagingRestaurantReview = restaurantReviewRepository.findByMemberIdForMyPage(memberId, cond, pageable);
        return pagingRestaurantReview.map(MemberMyPageRestaurantReviewDto::new);
    }

    private void isNotNullMemberId(Long memberId) {
        if(memberId == null){
            throw new MemberIdNotSentException("회원의 기본키가 전달되지 않았습니다.");
        }
    }
}
