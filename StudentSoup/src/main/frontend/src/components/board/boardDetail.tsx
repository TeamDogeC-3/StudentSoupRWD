import React, { useEffect, useState } from 'react';
import MypageNavbar from '../common/mypageNavbar';
import BoardReviewList from './boardReviewList';
import BoardBestReplyHeart from './boardBestReply';
import Footer from '../common/footer';
import axios from 'axios';
import { ReactComponent as BoardWriteIcon } from '../../img/BoardWriteIcon.svg';
import { ReactComponent as BoardWriteIconHeart } from '../../img/boardWriteIconHeart.svg';
import { ReactComponent as BoardOrangeIconHeart } from '../../img/boardOrangeIconHeart.svg';
import { ReactComponent as BoardWriteWhiteHeart } from '../../img/BoardWriteWhiteHeart.svg';
import { ReactComponent as BoardWriteActiveHeart } from '../../img/BoardWriteActiveHeart.svg';
import { ReactComponent as BoardScrollUp } from '../../img/boardScrollUpIcon.svg';
import { ReactComponent as BoardScrollDown } from '../../img/boardScroolDownIcon.svg';
import { Link, useHistory, useLocation } from 'react-router-dom';

const boardDetail = () => {
  const history = useHistory();
  const state = useLocation<any>();
  const getBoardId = state.state;
  const [boardTitle, setBoardTitle] = useState<string>('');
  const [boardImg, setBoardImg] = useState<any>([]);
  const [boardReviewCount, setBoardReviewCount] = useState<number>();
  const [boardContent, setBoardContent] = useState<string>('');
  const [boardNickName, setBoardNickName] = useState<string>('');
  const [boardDate, setBoardDate] = useState<any>();
  const [boardView, setBoardView] = useState<number>();
  const [boardLikedCount, setBoardLikedCount] = useState<number>();
  const [boardLiked, isBoardLiked] = useState<boolean>();
  const [boardReviewList, setBoardReviewList] = useState<any>([]);
  const [boardBestReviewList, setBoardBestReviewList] = useState<any>([]);
  const [reply, setReply] = useState<number>(0);
  const [categoryList, setCategoryList] = useState<string>('');
  const [category, setCategory] = useState<string>('');
  const saveMemberId = sessionStorage.getItem('memberId');
  const saveMemberName = sessionStorage.getItem('nickname');
  const [replyTextValue, setReplyTextValue] = useState<string>('');
  const [like, isLike] = useState<boolean>(false);
  const [clickLike, isClickLike] = useState<boolean>();
  const [likeCount, setLikeCount] = useState<number>();
  // const url = `/board/${boardId}/${saveMemberId}`; 최종 데이터
  useEffect(() => {
    axios
      .post(`/board/${getBoardId}/${saveMemberId}`)
      .then(res => {
        setBoardReviewCount(res.data.reviewCount);
        setBoardTitle(res.data.title);
        setBoardContent(res.data.content);
        setBoardNickName(res.data.nickname);
        setBoardDate(res.data.writeDate);
        setBoardView(res.data.view);
        setBoardLikedCount(res.data.likedCount);
        isBoardLiked(res.data.like);
        setCategoryList(res.data.boardCategory);
        setBoardImg(res.data.fileNames);
      })
      .catch(err => {
        console.error(err);
      });
  }, []);
  useEffect(() => {
    axios
      .get(`/boardReplies/${getBoardId}/${saveMemberId}`)
      .then(res => {
        setBoardReviewList(res.data.boardReplyList);
        setBoardBestReviewList(res.data.bestReplyList);
      })
      .catch(err => {
        console.error(err);
      });
  }, []);

  useEffect(() => {
    if (categoryList === 'FREE') {
      setCategory('자유게시판');
    } else if (categoryList === 'CONSULTING') {
      setCategory('취업/상담게시판');
    } else if (categoryList === 'TIP') {
      setCategory('팁게시판');
    } else if (categoryList === 'ANNOUNCEMENT') {
      setCategory('공지사항');
    } else if (categoryList === 'EMPLOYMENT') {
      setCategory('취업/상담게시판');
    }
  });

  const handleBackCategory = () => {
    if (categoryList === 'FREE') {
      history.push('/board/free');
    } else if (categoryList === 'CONSULTING' || categoryList === 'EMPLOYMENT') {
      history.push('/board/consulting');
    } else if (categoryList === 'TIP') {
      history.push('/board/tip');
    } else if (categoryList === 'ANNOUNCEMENT') {
      history.push('/board/notice');
    }
  };

  const handleReply = (e: any) => {
    if (!replyTextValue) {
      alert('댓글을 입력해주세요.');
      return;
    }
    if (replyTextValue.length < 2 || replyTextValue.length > 500) {
      alert('댓글은 2자이상 500자 이하입니다.');
      return;
    }
    axios
      .put('/boardReply', {
        boardId: getBoardId,
        memberId: saveMemberId,
        content: replyTextValue,
        level: 0,
        seq: reply,
      })
      .then(res => {
        alert('성공적으로 댓글을 작성하였습니다.');
        location.reload();
      })
      .catch(err => {
        console.error(err);
      });
  };

  const handleReplySetContentValue = (e: any) => {
    setReplyTextValue(e.target.value);
  };

  const handleScrollDown = () => {
    window.scrollTo(0, document.body.scrollHeight);
  };
  const handleScrollUp = () => {
    window.scroll({
      top: 0,
      left: 0,
    });
  };

  const handleBoardLikeCount = () => {
    // /board/{boardId}/{saveMemberId}/like 가 최종 데이터
    axios
      .post(`/board/${getBoardId}/${saveMemberId}/like`)
      .then(res => {
        isClickLike(res.data.data.like);
        setLikeCount(res.data.data.likedCount);
      })
      .catch(err => {
        console.error(err);
      });
    isLike(!like);
    isBoardLiked(!boardLiked);
  };

  const handleBoardDelete = () => {
    // /board/{boardId}/{memberId} 가 최종 데이터
    if (confirm('정말로 게시글을 삭제하시겟습니까?')) {
      axios
        .delete(`/board/${getBoardId}/${saveMemberId}`)
        .then(res => {
          alert('게시글이 삭제되었습니다.');
          history.goBack();
        })
        .catch(err => {
          console.error(err);
        });
    }
  };
  const handleBoardEdit = () => {
    // /board/{boardId}/{memberId} 가 최종 데이터
    if (confirm('정말로 게시글을 수정하시겠습니까?')) {
      axios
        .get(`/board/${getBoardId}/${saveMemberId}`)
        .then(res => {
          history.push('/board/edit', [
            boardTitle,
            boardContent,
            categoryList,
            res.data.departmentId,
            state.state,
          ]);
        })
        .catch(err => {
          console.error(err);
        });
    }
  };
  return (
    <>
      <MypageNavbar />
      <div className='min-h-screen'>
      <div className="w-full h-[103px] scroll-smooth"></div>
      <div className="flex flex-row justify-center">
        <div className="mt-[15px] w-[296px] h-[60px] font-bold leading-[29px] text-[24px] items-center text-[#262626]">
          {category}
        </div>
        <div
          onClick={handleBackCategory}
          className="ml-[398px] mt-[7px] w-[77px] h-[44px] border-[0.8px] border-[#929292] rounded-[22px] bg-[#FFFFFF] cursor-pointer"
        >
          <div className="ml-[22.06px] mt-[8.62px] font-normal text-[16px] flex items-center text-[#929292]">
            목록
          </div>
        </div>
        <Link to="/board/write">
          <div className="ml-[14px] mt-[7px] w-[123px] h-[44px] border-[0.8px] border-[#FF611D] rounded-[22px] bg-[#FFFFFF] flex flex-row">
            <BoardWriteIcon className="ml-[22.06px] mt-[13.28px]" />
            <div className="ml-[9.2px] font-normal text-[16px] flex items-center text-[#FF611D]">
              글쓰기
            </div>
          </div>
        </Link>
      </div>
      <div className="flex justify-center mb-[57px]">
        <div className="w-[938px] h-auto border-[1px] border-[#BCBCBC] bg-[#FFFFFF] shadow-[0px_2px_10px_rgba(0,0,0,0.1)] rounded-[5px] content-center">
          <div className="flex flex-row">
            <div className="ml-[41px] mt-[35px] w-[850px] h-auto text-[20px] font-medium flex items-center text-[#252525]">
              {boardTitle}
            </div>
          </div>
          <div className="flex flex-row w-[400px] h-[17px] ml-[41px] mt-[21px]">
            <div className="font-normal text-[14px] text-[#A8A8A8]">
              {boardNickName} | {boardDate} | 조회 {boardView} |
            </div>
            {boardLiked ? (
              <BoardOrangeIconHeart className="ml-[6px] mt-[6.4px]" />
            ) : (
              <BoardWriteIconHeart className="ml-[6px] mt-[6.4px]" />
            )}

            <div className="ml-[5.11px] font-normal text-[14px] text-[#A8A8A8]">
              {like ? likeCount : boardLikedCount}
            </div>
          </div>
          <div className="ml-[28px] mt-[22px] w-[884px] border-[1px] border-[#BCBCBC] bg-[#BCBCBC] "></div>
          <div className="w-[856px] ml-[41px] mt-[34px] font-normal text-[16px] text-left leading-[26px] text-[#636363] mb-[20px]">
            {boardContent}
          </div>
          <div className="flex flex-row ml-[8px]">
            {boardImg.map((school: any) => (
              <>
                <img
                  key={school}
                  src={`/image/${school}`}
                  className="ml-[4px] w-[180px] h-[180px] border-[1px] rounded-[5px] bg-[#A5A5A5]"
                />
              </>
            ))}
          </div>
          <button
            onClick={handleBoardLikeCount}
            className="ml-[398px] mt-[34px] w-[139px] h-[56px] border-[1px] rounded-[20px] bg-[#FF611D]"
          >
            <div className="flex flex-row">
              {boardLiked ? (
                <BoardWriteActiveHeart className="ml-[21px] mt-[7px]" />
              ) : (
                <BoardWriteWhiteHeart className="ml-[21px] mt-[7px]" />
              )}

              <div className="ml-[6px] mb-[10px] w-auto h-[20px] font-normal text-[20px] text-[#FFFFFF]">
                추천 <span>{like ? likeCount : boardLikedCount}</span>
              </div>
            </div>
          </button>
          <div className="flex flex-row ml-[30px]">
            <div className="w-[423px] h-[23px] leading-[22px] mt-[72px] font-medium text-[16px] text-[#404040]">
              {boardReviewCount}개의 댓글
            </div>
            {saveMemberName === boardNickName ? (
              <>
                <div
                  onClick={handleBoardEdit}
                  className="ml-[397px] mt-[72px] h-[23px] font-semibold text-[16px] leading-[23px] text-[#989898] cursor-pointer"
                >
                  수정
                </div>
                <div
                  onClick={handleBoardDelete}
                  className="ml-[7px] mt-[72px] font-semibold text-[16px] leading-[23px] text-[#989898] cursor-pointer"
                >
                  삭제
                </div>
              </>
            ) : (
              ''
            )}
          </div>
          <div className="flex flex-row">
            <textarea
              maxLength={500}
              onChange={e => {
                handleReplySetContentValue(e);
              }}
              placeholder="댓글을 입력해주세요."
              className="ml-[28px] mt-[16px] w-[834px] h-[50px] resize-y border-[1px] rounded-[5px] border-[#C4C4C4]"
            ></textarea>
            <button
              onClick={handleReply}
              className="mt-[16px] ml-[10px] w-[50px] h-[50px] bg-[#FF611D] rounded-[5px] text-[16px] font-normal text-[#FFFFFF]"
            >
              등록
            </button>
          </div>
          {boardBestReviewList.map((data: any) => (
            <>
              <BoardBestReplyHeart {...data} data={data} />
            </>
          ))}

          {boardReviewList.map((data: any) => (
            <>
              <BoardReviewList {...data} data={data} />
            </>
          ))}
          <div className="mb-[40px]"></div>
        </div>
        {boardReviewList.length < 3 ||
          (boardBestReviewList < 3 && (
            <div className="relative left-[30px] flex flex-col">
              <BoardScrollUp
                onClick={handleScrollUp}
                className="sticky top-[88%] left-[75%] cursor-pointer"
              />
              <BoardScrollDown
                onClick={handleScrollDown}
                className="sticky top-[95%] left-[75%] cursor-pointer"
              />
            </div>
          ))}
      </div>
      </div>
      <Footer />
    </>
  );
};

export default boardDetail;
