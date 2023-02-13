import axios from 'axios';
import cn from 'clsx';
import { useCallback, useEffect, useState } from 'react';
import ReviewStarView from '../restaurant/reviewStarView';
import MypageBoard from './mypageBoard';
import MypageBoardReply from './mypageBoardReply';
import MypageReviewModal from './mypageReviewModal';

const MypageBoardReview = () => {
  const reviewUrl = '/mypage/restaurantReview';
  const detailUrl = '/mypage/detail';
  const memberId = sessionStorage.getItem('memberId');
  const [click, setClick] = useState<number>(0);
  const [restaurantReivew, setRestaurantReivew] = useState<any[]>();
  const [size, setSize] = useState<number>(6);
  const [last, isLast] = useState<boolean>(false);
  const [deleteCheck, setDeleteCheck] = useState<boolean>(false);
  const [sorted, setSorted] = useState<string>('');
  const [clickBoardOrReply, setClickBoardOrReply] = useState<string>('게시판');
  const [boardCount, setBoardCount] = useState<number>();
  const [replyCount, setReplyCount] = useState<number>();
  const [isModal, setModal] = useState<Boolean>(false);
  const [reviewId, setReviewId] = useState<number>();

  useEffect(() => {
    axios
      .post(
        reviewUrl,
        {
          memberId,
        },
        {
          params: {
            size,
            filter: sorted,
          },
        },
      )
      .then(function (response) {
        console.log(response.data);
        console.log(sorted);
        setRestaurantReivew(response.data.content);
        isLast(response.data.last);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [click, deleteCheck, sorted]);

  useEffect(() => {
    axios
      .post(detailUrl, { memberId })
      .then(function (res) {
        setBoardCount(res.data.boardWriteCount);
        setReplyCount(res.data.boardReplyWriteCount);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, []);

  const onClickToggleModal = useCallback(() => {
    setModal(!isModal);
  }, [isModal]);

  const handleClickButton = (_e: any) => {
    setClick(click + 1);
    setSize(size + 6);
  };

  const handleDelete = (e: any) => {
    const restaurantReviewId = e.target.id;
    const restaurantId = e.target.value;
    if (confirm('해당 리뷰를 삭제하시겠습니까?')) {
      axios
        .delete(`/restaurantReview/${restaurantReviewId}`, {
          data: { restaurantReviewId, restaurantId, memberId },
        })
        .then(function (response) {
          console.log(response);
          setDeleteCheck(true);
          alert('삭제되었습니다.');
        })
        .catch(function (error) {
          console.log(error);
        });
    } else {
      /* empty */
    }
    setDeleteCheck(false);
  };

  const handleSorted = (e: any) => {
    const id: string = e.target.value;
    setSorted(id);
  };

  const handleClickBoard = (e: any) => {
    const id = e.target.id;
    setClickBoardOrReply(id);
  };

  const handleClickReply = (e: any) => {
    const id = e.target.id;
    setClickBoardOrReply(id);
  };

  const handleModify = (e: any) => {
    const id = e.target.id;
    setReviewId(id);
    setModal(true);
  };

  return (
    <div className="flex flex-[9] w-full h-[150vh] z-[1] bg-zinc-100">
      <div className="w-full h-[820px] ml-[60px]">
        <div className="mt-[45px]">
          <span className="text-[24px] leading-[33px] text-[#262626] font-bold">게시글</span>
        </div>
        <div className="flex flex-col w-[962px] h-auto mt-[30px]">
          <div className="flex flex-row h-[50px]">
            <div
              onClick={handleClickBoard}
              id="게시판"
              className={cn('w-[50%] rounded-l-[5px] bg-white items-center justify-center flex', {
                ['border-[#FF611D] border-[2px]']: clickBoardOrReply === '게시판',
                ['border-[#BCBCBC] border-[1px]']: clickBoardOrReply === '댓글',
              })}
            >
              게시글({boardCount})
            </div>
            <div
              onClick={handleClickReply}
              id="댓글"
              className={cn('w-[50%] rounded-r-[5px] bg-white items-center justify-center flex', {
                ['border-[#FF611D] border-[2px]']: clickBoardOrReply === '댓글',
                ['border-[#BCBCBC] border-[1px]']: clickBoardOrReply === '게시판',
              })}
            >
              댓글({replyCount})
            </div>
          </div>
          {clickBoardOrReply === '게시판' && <MypageBoard />}
          {clickBoardOrReply === '댓글' && <MypageBoardReply />}
        </div>
        <div className="flex flex-col w-[962px] mt-[220px]">
          <div className="text-[24px] leading-[33px] font-bold text-[#262626]">리뷰</div>
          <div className="flex flex-row items-center justify-end">
            <div>
              <select
                onChange={handleSorted}
                className="w-[191px] h-[35px] text-[#939393] text-[16px] fw-400 leading-[21px] pl-[3px] border-[1px] border-[#BCBCBC] rounded-[3px]"
              >
                <option value="">전체</option>
                <option value="today">오늘</option>
                <option value="month">한달</option>
                <option value="halfYear">6개월</option>
                <option value="year">1년</option>
              </select>
            </div>
          </div>
          <div className="mt-[13px]">
            <div className="w-full h-[3px] border-[1px] border-[#BCBCBC] bg-[#BCBCBC]"></div>
            <div className="flex flex-col max-h-[450px] overflow-auto">
              {restaurantReivew?.map(restaurantReivew => (
                <>
                  {restaurantReivew.imageName !== null ? (
                    <div
                      key={restaurantReivew.restaurantReviewId}
                      className="flex flex-row mt-[19px] pb-[19px] border-b-[2px] border-[#BCBCBC]"
                    >
                      <div className="w-[20%]">
                        <img
                          src={`/image/${restaurantReivew.imageName}`}
                          className="w-[189px] h-[141px] border border-[#DDDDDD] rounded-[10px]"
                        />
                      </div>
                      <div className="w-[60%] flex flex-col ml-[40px]">
                        <div className="flex flex-row">
                          <div>평점</div>
                          <ReviewStarView {...restaurantReivew} school={restaurantReivew} />
                        </div>
                        <div>{restaurantReivew.content}</div>
                      </div>
                      <div className="w-[20%] flex flex-col items-center justify-center">
                        <div>{restaurantReivew.writeDate}</div>
                        <button
                          onClick={handleModify}
                          id={restaurantReivew.restaurantReviewId}
                          className="w-[105px] h-[35px] mt-[20px] bg-[#FF611D] rounded-[5px] text-white"
                        >
                          수정하기
                        </button>
                        <button
                          id={restaurantReivew.restaurantReviewId}
                          value={restaurantReivew.restaurantId}
                          onClick={handleDelete}
                          className="w-[105px] h-[35px] mt-[5px] bg-[#FF611D] rounded-[5px] text-white"
                        >
                          삭제하기
                        </button>
                      </div>
                    </div>
                  ) : (
                    <div
                      key={restaurantReivew.restaurantReviewId}
                      className="flex flex-row my-[19px]"
                    >
                      <div className="w-[80%] flex flex-col ml-[40px]">
                        <div className="flex flex-row">
                          <div>평점</div>
                          <ReviewStarView {...restaurantReivew} school={restaurantReivew} />
                        </div>
                        <div>{restaurantReivew.content}</div>
                      </div>
                      <div className="w-[20%] flex flex-col items-center justify-center">
                        <div>{restaurantReivew.writeDate}</div>
                        <button
                          onClick={handleModify}
                          id={restaurantReivew.restaurantReviewId}
                          className="w-[105px] h-[35px] mt-[20px] bg-[#FF611D] rounded-[5px] text-white"
                        >
                          수정하기
                        </button>
                        <button
                          id={restaurantReivew.restaurantReviewId}
                          value={restaurantReivew.restaurantId}
                          onClick={handleDelete}
                          className="w-[105px] h-[35px] mt-[5px] bg-[#FF611D] rounded-[5px] text-white"
                        >
                          삭제하기
                        </button>
                      </div>
                    </div>
                  )}
                </>
              ))}
              {!last && (
                <div className="flex items-center justify-center ">
                  <button
                    onClick={handleClickButton}
                    className="w-[100px] h-[40px] mb-[15px] rounded-[5px] text-[16px] text-white bg-[#FF611D]"
                  >
                    리뷰 더보기
                  </button>
                </div>
              )}
            </div>
            <div className="w-full h-[3px] border-[#BCBCBC] bg-[#BCBCBC]"></div>
            {isModal && (
              <MypageReviewModal onClickToggleModal={onClickToggleModal} reviewId={reviewId} />
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default MypageBoardReview;
