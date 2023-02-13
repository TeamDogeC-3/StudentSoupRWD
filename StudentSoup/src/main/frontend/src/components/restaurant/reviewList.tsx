import { ReactComponent as MoreInfo } from '../../img/moreicon.svg';
import { ReactComponent as ReviewSmallHeart } from '../../img/ReviewSmallHeart.svg';
import { ReactComponent as ReviewSmallHeartActive } from '../../img/ReviewSmallHeartActive.svg';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import ReviewStarView from './reviewStarView';
import ReviewMoreStarView from './reviewMoreStarView';
import ReviewMoreHeartInfo from './reviewMoreHeartInfo';
import ReviewMoreImageView from './reviewMoreImageView';
import { ReactComponent as RightIcon } from '../../img/icon_right.svg';
import { ReactComponent as LeftIcon } from '../../img/icon_left.svg';
import { ReactComponent as LeftFillNoneIcon } from '../../img/icon_left_fillnone.svg';
import { ReactComponent as RightFillNoneIcon } from '../../img/icon_right_fillnone.svg';
import cn from 'clsx';
import Circle_human from '../../img/circle_human.png';

const reviewWrite = () => {
  const [reviewList, setReviewList] = useState<any>([]);
  const [clickMoreButton, isClickMoreButton] = useState<boolean>(false);
  const state = useLocation<any>();
  const [page, setPage] = useState<number>(0);
  const [clickPage, setClickPage] = useState<number>(1);
  const [totalPage, setTotalPage] = useState<number>();
  const [clickNextPage, setClickNextPage] = useState<number>(0);
  const [sort, setSort] = useState<string>('liked');
  const [selected, setSelected] = useState<number>(1);
  const [lastPage, isLastPage] = useState<boolean>();
  const restaurantNumber = state.state[0];
  const saveMemberId = sessionStorage.getItem('memberId');
  const url = `/restaurantReview/${restaurantNumber}`;
  useEffect(() => {
    axios
      .post(
        url,
        {
          restaurantId: restaurantNumber,
          memberId: saveMemberId,
        },
        {
          params: {
            page,
            sorted: sort,
          },
        },
      )
      .then(res => {
        setReviewList(res.data.content);
        setTotalPage(res.data.totalPages);
        isLastPage(res.data.last);
      })
      .catch(err => {
        console.error(err);
      });
  }, [clickPage, sort]);

  const handleClickPage = (e: any) => {
    setClickPage(e);
  };

  const handleMoreButton = () => {
    isClickMoreButton(!clickMoreButton);
  };

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

  const handleImgError = (e: any) => {
    e.target.src = Circle_human;
  };

  return (
    <>
      <div className="flex flex-row ml-[5px] mt-[5px]">
        <div
          onClick={() => {
            setSort('newest');
          }}
          className={cn(
            'ml-[21px] mt-[13px] w-[70px] h-[29px] border-[1px] rounded-[10px] cursor-pointer',
            {
              'border-[#FF611D] text-[#FF611D]': sort === 'newest',
              'border-[#9C9C9C] text-[#9C9C9C]': sort !== 'newest',
            },
          )}
        >
          <div className="ml-[13.5px] mt-[4px] font-[400] text-[16px] leading-[21px]">최신순</div>
        </div>
        <div
          onClick={() => {
            setSort('liked');
          }}
          className={cn(
            'ml-[7px] mt-[13px] w-[70px] h-[29px] border-[1px]  rounded-[10px] cursor-pointer',
            {
              'border-[#FF611D] text-[#FF611D]': sort === 'liked',
              'border-[#9C9C9C] text-[#9C9C9C]': sort !== 'liked',
            },
          )}
        >
          <div className="ml-[13.5px] mt-[4px] font-[400] text-[16px] leading-[21px]">추천순</div>
        </div>
        <div className="mt-[20px] ml-[185px] mr-[26px] w-auto h-[16px] fw-400 text-[13px] leading-[17px] flex items-center text-[#9F9F9F]">
          ※홍보 및 비방 등 부적절한 평가는 평점 산정에서 제외될수있습니다.
        </div>
      </div>
      {clickMoreButton ? (
        <>
          <div className="ml-[18px] mt-[20px] w-[687px] h-[1px] border border-[#D5D5D5] bg-[#D5D5D5]"></div>
          <div className="w-[743px] h-auto">
            <div className="flex flex-col">
              {reviewList.map((school: any, idx: any) => (
                <>
                  <div className="w-[743px] h-auto border-1">
                    <div className="flex flex-row">
                      <div className="flex flex-row" key={school.restaurantReviewId}>
                        <img
                          key={school.restaurantReviewId}
                          src={`/image/${school.memberProfileImageName}`}
                          onError={handleImgError}
                          className="ml-[32px] mt-[27px] w-[74px] h-[74px] rounded-full"
                        />
                        <div className="flex flex-col w-[60%]">
                          <div className="ml-[12px] mt-[37px] w-[800px] h-[16px] font-normal leading-[28px] text-[20px] flex items-center text-[#515151]">
                            {school.nickName}
                          </div>
                          <div className="flex flex-row">
                            <ReviewMoreStarView {...school} school={school} />
                            <div className="w-[90px] h-[16px] ml-[9.4px] mt-[5px] font-normal text-[16px] leading-[21px] flex items-center text-[#A5A5A5]">
                              {school.writeDate}
                            </div>
                            <ReviewMoreHeartInfo {...school} school={school} />
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="flex flex-rows ml-[118px]">
                      <ReviewMoreImageView {...school} school={school} />
                    </div>
                    <div className="w-[593px] h-auto mt-[10px] ml-[118px] text-[16px] font-normal leading-[21px] text-[#6B6B6B]">
                      {school.content}
                    </div>
                  </div>
                  <div className="mt-[20px] ml-[25px] w-[687px] h-[1px] bg-[#BCBCBC]"></div>
                </>
              ))}
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
          </div>
        </>
      ) : (
        <>
          <div className="ml-[25px] mt-[18px] w-[687px] h-[1px] border border-[#D5D5D5] bg-[#D5D5D5] "></div>
          <div className="grid grid-cols-[235px_minmax(235px,_0fr)_235px]">
            {reviewList.map((school: any) => (
              <>
                <div
                  key={school.restaurantReviewId}
                  className="ml-[20px] mt-[18px] w-[220px] h-[312px] border-[1px] border-[#D2D2D2] shadow-[1px_1px_4px_rgba(0,0,0,0.07)] rounded-[5px]"
                >
                  <div className="flex flex-row">
                    <img
                      key={school.restaurantReviewId}
                      src={`/image/${school.memberProfileImageName}`}
                      onError={handleImgError}
                      className="w-[39px] h-[40px] ml-[13px] mt-[15px] rounded-full border-[#9C9C9C]"
                    />
                    <div className="">
                      <div className="ml-[5px] mt-[21px] w-auto h-[10px] font-[400] text-[12px] leading-[20px] flex items-center">
                        {school.nickName}
                      </div>
                      <ReviewStarView {...school} school={school} />
                    </div>
                  </div>
                  {school.imageFileNameList.length ? (
                    <>
                      <img
                        src={`/image/${school.imageFileNameList[0]}`}
                        className="ml-[20px] mt-[13.12px] w-[180px] h-[120px] border border-[#DDDDDD] rounded-[10px]"
                      />
                      <div className="ml-[20px] mt-[11px] w-[184px] h-[62px] font-[400] text-[12px] leading-[16px] text-[#6B6B6B] text-clip overflow-hidden">
                        {school.content}
                      </div>
                    </>
                  ) : (
                    <>
                      <div className="ml-[20px] mt-[11px] w-[184px] h-[194.12px] font-[400] text-[12px] leading-[16px] text-[#6B6B6B] text-clip overflow-hidden">
                        {school.content}
                      </div>
                    </>
                  )}

                  <div className="flex flex-row w-[209px]">
                    <div className="ml-[20px] mt-[18px] h-[16px] font-normal text-[11px] leading-[14px] flex items-center text-[#A5A5A5]">
                      {school.writeDate}
                    </div>
                    {school.like ? (
                      <ReviewSmallHeartActive className="ml-[89px] mt-[20px]" />
                    ) : (
                      <ReviewSmallHeart className="ml-[89px] mt-[20px]" />
                    )}
                    <div className="ml-[4.31px] mt-[18px] w-[18px] h-[16px] font-normal text-[11px] leading-[14px] flex items-center text-[#A5A5A5]">
                      {school.likedCount}
                    </div>
                  </div>
                </div>
              </>
            ))}
          </div>
          <div className="ml-[25px] mt-[13px] w-[687px] h-[1px] border border-[#D5D5D5] bg-[#D5D5D5] "></div>
          {reviewList.length ? (
            <div
              onClick={handleMoreButton}
              className="mt-[14px] mb-[18px] ml-[649px] font-[400] text-[16px] leading-[22px] flex items-center cursor-pointer"
            >
              더보기
              <div className="mb-[2px] ml-[2px] w-[14px] h-[14px] rounded-full border border-[#FF611D] bg-[#FF611D]">
                <MoreInfo className="ml-[2.22px] mt-[3.5px]" />
              </div>
            </div>
          ) : (
            <div className="mt-[24px]">
              <div className="text-center">
                아직 리뷰가 작성되지 않았어요! 첫 리뷰를 작성해보세요!
              </div>
            </div>
          )}
        </>
      )}
    </>
  );
};

export default reviewWrite;
