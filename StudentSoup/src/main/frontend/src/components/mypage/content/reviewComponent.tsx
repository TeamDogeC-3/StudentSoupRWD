import _ from 'lodash';
import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import cn from 'clsx';
import axios from 'axios';
import { ReactComponent as Star } from '../../../img/restaurantDetailStar.svg';

const ReviewComponent = () => {
  const state = useLocation<any>();
  const savedMemberId = sessionStorage.getItem('memberId');
  const [set, isSet] = useState<any[]>();
  const [content, setContent] = useState<string>();
  const [likedCount, setLikedCount] = useState<number>();
  const [starLiked, setStarLiked] = useState<number>();
  const [size, setSize] = useState<number>(6);
  const starArr = [0, 1, 2, 3, 4];

  const url = '/mypage/restaurantReview';
  useEffect(() => {
    axios
      .post(url, {
        memberId: savedMemberId,
      })
      .then(res => {
        isSet(res.data.content);
      })
      .catch(err => {
        console.log(err);
      });
  }, []);

  return (
    <div className="text-[14px]">
      <div className='h-[50px] px-[34px] flex items-center text-[#353535] border-b border-[#D9D9D9]'>
        <span className='w-[60%] '>제목</span>
        <span className='w-[20%] text-center'>좋아요</span>
        <span className='w-[20%] text-center'>별점</span>
      </div>
      {set?.map((review: any) => (
        <>
        <div key={review.memberId} className='h-[50px] px-[34px] flex items-center text-[#353535] border-b border-[#D9D9D9]'>
          <span className='w-[60%] truncate text-[#909090]'>{review.content}</span>
          <span className='w-[20%] text-center text-[#909090]'>{review.likedCount}</span>
          <span key={review.starLiked} className='w-[20%] text-center text-[#909090]'>
            <div className="ml-[5px] flex flex-row justify-center">
              {starArr?.map((starLiked, index) => {
                index += 1;
                return (
                  <>
                  <span key={review.starLiked}>
                    <svg
                      key={review.starLiked}
                      className={
                        index <= review.starLiked
                          ? 'px-[1.2px] fill-[#FF611D]'
                          : 'px-[1.2px] fill-[#CDCDCD]'
                      }
                      width="11"
                      height="11"
                      viewBox="0 0 11 11"
                      fill="none"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        fill="correct"
                        d="M8.57023 6.26491C8.43295 6.39794 8.36988 6.59034 8.40115 6.77903L8.87234 9.38676C8.9121 9.60778 8.81881 9.83145 8.63383 9.95919C8.45256 10.0917 8.2114 10.1076 8.0137 10.0016L5.66622 8.77723C5.58459 8.73377 5.49396 8.71045 5.4012 8.7078H5.25757C5.20775 8.71522 5.15898 8.73112 5.11446 8.7555L2.76645 9.98569C2.65037 10.044 2.51892 10.0647 2.39013 10.044C2.07635 9.98463 1.86699 9.68569 1.9184 9.37033L2.39013 6.7626C2.4214 6.57232 2.35833 6.37886 2.22105 6.24371L0.307125 4.38861C0.147057 4.23332 0.0914047 4.00011 0.164548 3.78969C0.235572 3.5798 0.416841 3.42662 0.635741 3.39217L3.26997 3.01002C3.47032 2.98935 3.64629 2.86744 3.73639 2.68723L4.89715 0.307415C4.92471 0.254412 4.96022 0.20565 5.00316 0.164308L5.05086 0.127206C5.07577 0.0996449 5.10439 0.0768538 5.13619 0.0583029L5.19396 0.0371018L5.28407 0H5.50721C5.7065 0.020671 5.88194 0.139927 5.97363 0.318016L7.14976 2.68723C7.23456 2.86055 7.3994 2.98087 7.58968 3.01002L10.2239 3.39217C10.4465 3.42397 10.6326 3.57768 10.7062 3.78969C10.7757 4.00223 10.7158 4.23544 10.5525 4.38861L8.57023 6.26491Z"
                      />
                    </svg>
                  </span>
                  </>
                );
              })}
            </div>
          </span>
        </div>
        </>
      ))}
    </div>
  );
};

export default ReviewComponent;
