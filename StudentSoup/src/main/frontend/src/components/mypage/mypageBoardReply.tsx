import axios from 'axios';
import { useEffect, useState } from 'react';
import { ReactComponent as RightIcon } from '../../img/icon_right.svg';
import { ReactComponent as LeftIcon } from '../../img/icon_left.svg';
import { ReactComponent as LeftFillNoneIcon } from '../../img/icon_left_fillnone.svg';
import { ReactComponent as RightFillNoneIcon } from '../../img/icon_right_fillnone.svg';
import { useHistory } from 'react-router-dom';

const MypageBoardReply = () => {
  const history = useHistory();
  const boardReplyUrl = '/mypage/boardReply';
  const memberId = sessionStorage.getItem('memberId');
  const [boardReply, setBoardReply] = useState<any>([]);
  const [replylastPage, isReplyLastPage] = useState<boolean>();
  const [totalReplyPage, setTotalReplyPage] = useState<number>();
  const [selectedReply, setSelectedReply] = useState<number>(1);
  const [replyPage, setReplyPage] = useState<number>(0);
  const [clickReplyNextPage, setClickReplyNextPage] = useState<number>(0);
  const [clickReplyPage, setClickReplyPage] = useState<number>(1);
  const [totalBoardReply, setTotalBoardReply] = useState<any>();

  useEffect(() => {
    axios
      .post(
        boardReplyUrl,
        {
          memberId,
        },
        {
          params: {
            page: replyPage,
          },
        },
      )
      .then(function (response) {
        setBoardReply(response.data.content);
        setTotalReplyPage(response.data.totalPages);
        setTotalBoardReply(response.data.totalElements);
        isReplyLastPage(response.data.last);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [replyPage]);

  const setReplyPageNumbers = [...Array(totalReplyPage)].map((v, i) => i + 1);
  const setReplyPageNumbersArr = [];

  for (let i = 0; i < setReplyPageNumbers.length; i += 5) {
    setReplyPageNumbersArr.push(setReplyPageNumbers.slice(i, i + 5));
  }

  const handleReplyPageNumberClick = (e: any, idx: any) => {
    setSelectedReply(idx);
    setReplyPage(idx - 1);
    setClickReplyPage(idx);
  };

  const handlePushBoard = (e: any) => {
    const id = e.target.id;
    history.push('/board/detail', id);
  };

  return (
    <div className="w-[962px] h-[225px] flex flex-col mt-[25px]">
      <div className="w-full h-[2px] border-[1px] border-[#FF611D] bg-[#FF611D]"></div>
      <div className="flex flex-row mt-[18px]">
        <div className="w-[30px] h-[19px] text-[16px] leading-[22px] text-[#353535] ml-[50px]">
          내용
        </div>
        <div className="ml-[525px] text-[#353535]">작성일</div>
        <div className="w-[63px] h-[19px] ml-[172px] text-[16px] leading-[22px] text-[#353535]">
          좋아요
        </div>
      </div>
      <div className="w-full h-[2px] border-[1px] border-[#FF611D] bg-[#FF611D] mt-[19px]"></div>
      {boardReply?.map((boardReply: any) => (
        <div
          id={boardReply.boardId}
          key={boardReply.boardId}
          onClick={handlePushBoard}
          className="text-[14px] hover:underline underline-offset-[3px] decoration-[#909090]"
        >
          <div
            id={boardReply.boardId}
            className="h-[50px] px-[34px] flex items-center text-[#353535] border-b border-[#D9D9D9]"
          >
            <span id={boardReply.boardId} className="w-[60%] truncate text-[#909090]">
              {boardReply.content}
            </span>
            <span id={boardReply.boardId} className="w-[20%] truncate text-[#909090]">
              {boardReply.writeDate}
            </span>
            <span id={boardReply.boardId} className="w-[20%] text-center text-[#909090]">
              {boardReply.likedCount}
            </span>
          </div>
        </div>
      ))}
      {boardReply?.length === 0 && (
        <div className="w-full h-[164px] font-bold text-[20px] leading-[28px] text-[#353535] flex items-center justify-center">
          작성된 댓글이 없습니다.
        </div>
      )}
      <div className="w-full h-[2px] bg-[#BCBCBC]"></div>
      {totalBoardReply >= 6 && (
        <div className="right-[400px] ml-[120px] top-[620px]">
          <div className="flex flex-row mb-[55px]">
            {clickReplyPage === 1 ? (
              <LeftFillNoneIcon className="ml-[234px] mt-[55.63px]" />
            ) : (
              <LeftIcon
                onClick={() => {
                  setSelectedReply(selectedReply - 1);
                  setReplyPage(replyPage - 1);
                  setClickReplyPage(clickReplyPage - 1);
                  if (replyPage % 5 === 0) {
                    setClickReplyNextPage(clickReplyNextPage - 1);
                  }
                }}
                className="ml-[234px] mt-[55.63px] cursor-pointer"
              />
            )}
            {setReplyPageNumbersArr[clickReplyNextPage].map((school: any) => (
              <>
                <div
                  id={school}
                  key={school}
                  className={
                    selectedReply === school
                      ? 'ml-[9.5px] mt-[43px] w-[38px] h-[38px] border border-[#FF611D] rounded-full cursor-pointer font-bold text-[#FF611D]'
                      : 'ml-[9.5px] mt-[43px] w-[38px] h-[38px] border border-[#B4B4B4] rounded-full cursor-pointer font-normal text-[#B4B4B4]'
                  }
                  onClick={e => {
                    handleReplyPageNumberClick(e, school);
                  }}
                >
                  <div id={school} className="mt-[4px] text-[20px] text-center">
                    {school}
                  </div>
                </div>
              </>
            ))}
            {replylastPage ? (
              <RightFillNoneIcon className="relative left-[15px] top-[53.63px]" />
            ) : (
              <RightIcon
                onClick={() => {
                  setSelectedReply(selectedReply + 1);
                  setReplyPage(replyPage + 1);
                  setClickReplyPage(clickReplyPage + 1);
                  if (clickReplyPage % 5 === 0) {
                    setClickReplyNextPage(clickReplyNextPage + 1);
                  }
                }}
                className="relative left-[15px] top-[53.63px] cursor-pointer"
              />
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default MypageBoardReply;
