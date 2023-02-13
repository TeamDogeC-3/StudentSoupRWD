import axios from 'axios';
import { useEffect, useState } from 'react';
import { ReactComponent as RightIcon } from '../../img/icon_right.svg';
import { ReactComponent as LeftIcon } from '../../img/icon_left.svg';
import { ReactComponent as LeftFillNoneIcon } from '../../img/icon_left_fillnone.svg';
import { ReactComponent as RightFillNoneIcon } from '../../img/icon_right_fillnone.svg';
import { useHistory } from 'react-router-dom';

const MypageBoard = () => {
  const history = useHistory();

  const boardUrl = '/mypage/board';
  const memberId = sessionStorage.getItem('memberId');
  const [board, setBoard] = useState<any>([]);
  const [totalBoard, setTotalBoard] = useState<any>();
  const [totalPage, setTotalPage] = useState<number>();
  const [lastPage, isLastPage] = useState<boolean>();
  const [page, setPage] = useState<number>(0);
  const [clickPage, setClickPage] = useState<number>(1);
  const [selected, setSelected] = useState<number>(1);
  const [clickNextPage, setClickNextPage] = useState<number>(0);

  useEffect(() => {
    axios
      .post(
        boardUrl,
        {
          memberId,
        },
        {
          params: {
            page,
          },
        },
      )
      .then(function (response) {
        setBoard(response.data.content);
        setTotalBoard(response.data.totalElements);
        setTotalPage(response.data.totalPages);
        isLastPage(response.data.last);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [page]);

  const setPageNumbers = [...Array(totalPage)].map((v, i) => i + 1);
  const setPageNumbersArr = [];

  for (let i = 0; i < setPageNumbers.length; i += 5) {
    setPageNumbersArr.push(setPageNumbers.slice(i, i + 5));
  }

  const handlePageNumberClick = (e: any, idx: any) => {
    setSelected(idx);
    setPage(idx - 1);
    setClickPage(idx);
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
          제목
        </div>
        <div className="ml-[400px] mr-[143px] text-[#353535]">작성일</div>
        <div className="w-[65px] h-[19px] ml-[40px] text-[16px] leading-[22px] text-[#353535]">
          조회수
        </div>
        <div className="w-[63px] h-[19px] ml-[70px] text-[16px] leading-[22px] text-[#353535]">
          좋아요
        </div>
      </div>
      <div className="w-full h-[2px] border-[1px] border-[#FF611D] bg-[#FF611D] mt-[19px]"></div>
      {board.length ? (
        board?.map((board: any) => (
          <div
            id={board.boardId}
            key={board.boardId}
            onClick={handlePushBoard}
            className="text-[14px] hover:underline underline-offset-[3px] decoration-[#909090]"
          >
            <div
              id={board.boardId}
              className="h-[50px] px-[34px] flex items-center text-[#353535] border-b border-[#D9D9D9]"
            >
              <span id={board.boardId} className="w-[60%] truncate text-[#909090]">
                {board.title}
              </span>
              <span id={board.boardId} className="w-[30%] truncate text-[#909090]">
                {board.writeDate}
              </span>
              <span id={board.boardId} className="w-[20%] text-center text-[#909090]">
                {board.viewCount}
              </span>
              <span id={board.boardId} className="w-[20%] text-center text-[#909090]">
                {board.likedCount}
              </span>
            </div>
          </div>
        ))
      ) : (
        <div className="w-full h-[164px] font-bold text-[20px] leading-[28px] text-[#353535] flex items-center justify-center">
          작성된 게시글이 없습니다.
        </div>
      )}
      <div className="w-full h-[2px] bg-[#BCBCBC]"></div>
      {totalBoard >= 6 && (
        <div className="right-[400px] ml-[120px] top-[620px]">
          <div className="flex flex-row mb-[55px]">
            {clickPage === 1 ? (
              <LeftFillNoneIcon className="ml-[234px] mt-[55.63px]" />
            ) : (
              <LeftIcon
                onClick={() => {
                  setSelected(selected - 1);
                  setPage(page - 1);
                  setClickPage(clickPage - 1);
                  if (page % 5 === 0) {
                    setClickNextPage(clickNextPage - 1);
                  }
                }}
                className="ml-[234px] mt-[55.63px] cursor-pointer"
              />
            )}
            {setPageNumbersArr[clickNextPage].map((school: any) => (
              <>
                <div
                  id={school}
                  key={school}
                  className={
                    selected === school
                      ? 'ml-[9.5px] mt-[43px] w-[38px] h-[38px] border border-[#FF611D] rounded-full cursor-pointer font-bold text-[#FF611D]'
                      : 'ml-[9.5px] mt-[43px] w-[38px] h-[38px] border border-[#B4B4B4] rounded-full cursor-pointer font-normal text-[#B4B4B4]'
                  }
                  onClick={e => {
                    handlePageNumberClick(e, school);
                  }}
                >
                  <div id={school} className="mt-[4px] text-[20px] text-center">
                    {school}
                  </div>
                </div>
              </>
            ))}
            {lastPage ? (
              <RightFillNoneIcon className="relative left-[15px] top-[53.63px]" />
            ) : (
              <RightIcon
                onClick={() => {
                  setSelected(selected + 1);
                  setPage(page + 1);
                  setClickPage(clickPage + 1);
                  if (clickPage % 5 === 0) {
                    setClickNextPage(clickNextPage + 1);
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

export default MypageBoard;
