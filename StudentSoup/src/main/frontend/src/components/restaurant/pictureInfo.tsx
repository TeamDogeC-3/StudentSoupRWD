import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { ReactComponent as MoreInfo } from '../../img/moreicon.svg';
import { useLocation } from 'react-router-dom';

const pictureInfo = () => {
  const state = useLocation<any>();
  const restaurantNumber = state.state[0];
  const [image, setImage] = useState<any>([]);
  const [size, setSize] = useState<number>(6);
  const [totalSize, setTotalSize] = useState<any>();
  const [moreButtonClick, setMoreButtonClick] = useState<number>(0);
  const url = `/restaurantReview/${restaurantNumber}/images`;
  useEffect(() => {
    axios
      .get(url, { params: { size } })
      .then(res => {
        setImage(res.data.content);
        setTotalSize(res.data.totalElements);
      })
      .catch(err => {
        console.error(err);
      });
  }, [size]);
  const handleClickButton = () => {
    setMoreButtonClick(moreButtonClick + 1);
    setSize(size + 6);
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
      setSize(size + 6);
      if (size >= totalSize) {
        setSize(totalSize);
      }
    }
  };

  return (
    <>
      {image.length ? (
        <>
          <div className="grid grid-cols-[226px_minmax(226px,_0fr)_226px] mt-[14px] ml-[25px] gap-[6px]">
            {image.map((school: any) => (
              <>
                <img
                  key={school}
                  src={`/image/${school}`}
                  className='className="mt-[14px] w-[226px] h-[190px]'
                />
              </>
            ))}
          </div>
          {moreButtonClick === 0 && totalSize > 6 ? (
            <>
              <div
                onClick={handleClickButton}
                className="mt-[14px] mb-[18px] ml-[649px] font-[400] text-[16px] leading-[22px] flex items-center cursor-pointer"
              >
                더보기
                <div className="mb-[2px] ml-[2px] w-[14px] h-[14px] rounded-full border border-[#FF611D] bg-[#FF611D]">
                  <MoreInfo className="ml-[2.22px] mt-[3.5px]" />
                </div>
              </div>
            </>
          ) : (
            <div className="h-[50px]"></div>
          )}
        </>
      ) : (
        <div className="text-center mt-[100px]">이미지가 없습니다. 리뷰를 달아보세요!</div>
      )}
    </>
  );
};

export default pictureInfo;
