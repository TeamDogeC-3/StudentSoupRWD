import React, { useState, useEffect, useCallback, useMemo } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import MenuHeartInfo from './menuHeartInfo';
import { ReactComponent as MoreInfo } from '../../img/moreicon.svg';
import { ReactComponent as CameraIcon } from '../../img/MenuInfoCamera.svg';

const menuInfo = () => {
  const state = useLocation<any>();
  const restaurantNumber = state.state[0];
  const saveMemberId = sessionStorage.getItem('memberId');
  const url = `/restaurant/${restaurantNumber}/menus`;
  const [MenuList, setMenuList] = useState<any>();
  const [size, setSize] = useState<number>(4);
  const [moreButtonClick, setMoreButtonClick] = useState<number>(0);
  const [totalsize, setTotalSize] = useState<any>();
  const [totalPage, setTotalPage] = useState<any>();
  const [last, isLast] = useState<boolean>();

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
            size,
          },
        },
      )
      .then(res => {
        isLast(res.data.last);
        setTotalPage(res.data.totalPages);
        setTotalSize(res.data.totalElements);
        setMenuList(res.data.content);
      })
      .catch(err => {
        console.error(err);
      });
  }, [size]);

  const handleClickButton = () => {
    setMoreButtonClick(moreButtonClick + 1);
    setSize(size + 12);
  };
  useEffect(() => {
    window.addEventListener('scroll', handleScroll);
    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  });
  const handleScroll = () => {
    const scrollHeight = document.documentElement.scrollHeight;
    const scrollTop = document.documentElement.scrollTop;
    const clientHeight = document.documentElement.clientHeight;

    if (scrollTop + clientHeight >= scrollHeight && moreButtonClick !== 0) {
      setMoreButtonClick(moreButtonClick + 1);
      setSize(size + 12);
    }
  };
  return (
    <>
      <div className="ml-[25px] mt-[22px] grid grid-cols-2">
        {MenuList?.map((school: any) => (
          <>
            {school.restaurantMenuCategory === '주메뉴' && (
              <div
                className="flex flex-row"
                id={school.restaurantMenuId}
                key={school.restaurantMenuId}
              >
                <div id={school.restaurantMenuId}>
                  {school.fileName ? (
                    <>
                      <img
                        className="w-[172px] h-[164px] rounded-[5px]"
                        key={school.fileName}
                        src={`/image/${school.fileName}`}
                      />
                    </>
                  ) : (
                    <div className="w-[172px] h-[164px] rounded-[5px] bg-[#A5A5A5]">
                      <CameraIcon className="relative left-[74px] top-[61px]" />
                      <div className="mt-[68px] ml-[39.5px] font-medium text-[16px] items-center text-[#515151]">
                        준비중입니다.
                      </div>
                    </div>
                  )}
                  <div className="relative bottom-[163px]">
                    <MenuHeartInfo {...school} school={school} />
                  </div>
                </div>
                <div className="flex flex-col">
                  <div className="w-[157px] h-[16px] ml-[14px] text-[20px] font-semibold leading-[28px] text-[#515151]flex items-center">
                    {school.restaurantMenuName}
                  </div>
                  <div className="ml-[14px] mt-[118px] font-bold text-[20px] leading-[28px] text-[#FF611D] flex items-center">
                    {school.cost}원
                  </div>
                </div>
              </div>
            )}
          </>
        ))}
      </div>
      <div className="ml-[25px] w-[687px] h-[4px] bg-[#EAEAEA]"></div>
      {totalPage === 1 && (
        <>
          <div className="flex flex-row">
            <div className="w-[377px] h-auto">
              <div className="ml-[22px] mt-[32px] text-[24px] font-semibold text-[#515151]">
                사이드
              </div>
              {MenuList.map((school: any) => (
                <>
                  {school.restaurantMenuCategory === '사이드메뉴' && (
                    <div className="ml-[27px] mt-[18px] w-[315px] h-auto font-normal text-[20px] leading-[28px] flex items-center text-[#808080]">
                      {school.restaurantMenuName} ------- {school.cost}원
                    </div>
                  )}
                </>
              ))}
            </div>
            <div className="w-[377px] h-auto border-l-[1px] border-[#D1D1D1]">
              <div className="ml-[22px] mt-[32px] text-[24px] font-semibold text-[#515151]">
                음료 및 주류
              </div>
              {MenuList.map((school: any) => (
                <>
                  {school.restaurantMenuCategory === '음료 및 주류' && (
                    <div className="ml-[27px] mt-[18px] w-[315px] h-auto font-normal text-[20px] leading-[28px] flex items-center text-[#808080]">
                      {school.restaurantMenuName} ------- {school.cost}원
                    </div>
                  )}
                </>
              ))}
            </div>
          </div>
        </>
      )}
      {!last ? (
        <div
          onClick={handleClickButton}
          className="mt-[14px] mb-[18px] ml-[649px] font-[400] text-[16px] leading-[22px] flex items-center cursor-pointer"
        >
          더보기
          <div className="mb-[2px] ml-[2px] w-[14px] h-[14px] rounded-full border border-[#FF611D] bg-[#FF611D]">
            <MoreInfo className="ml-[2.22px] mt-[3.5px]" />
          </div>
        </div>
      ) : (
        ''
      )}
      <div className="mb-[56px]"></div>
    </>
  );
};

export default menuInfo;
