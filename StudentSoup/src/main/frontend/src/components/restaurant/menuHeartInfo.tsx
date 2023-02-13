import { useState } from 'react';
import axios from 'axios';
import { useHistory, useLocation } from 'react-router-dom';

const menuItem = (data: any) => {
  const history = useHistory();
  const [clicklike, isClickLike] = useState<boolean>();
  const [like, isLike] = useState<boolean>(data.like);
  const [likeCount, setlikeCount] = useState<number>();
  const state = useLocation<any>();
  const restaurantNumber = state.state[0];
  const saveMemberId = sessionStorage.getItem('memberId');

  const handleHeartCount = async (e: any) => {
    const saveMenuId = e.target.id;
    if (!saveMemberId) {
      if (confirm('로그인후 이용가능한 기능입니다. 로그인하시겠습니까?')) {
        history.push('/login');
      } else {
        /* empty */
      }
    } else {
      await axios
        .post(`/restaurant/${restaurantNumber}/menu/like`, {
          restaurantMenuId: saveMenuId,
          memberId: saveMemberId,
        })
        .then(res => {
          isLike(res.data.data.like);
          setlikeCount(res.data.data.likedCount);
        });
      isClickLike(!clicklike);
      isLike(!like);
      if (like) {
        alert('좋아요가 취소되었습니다.');
      }
    }
  };

  return (
    <>
      <div
        id={data.restaurantMenuId}
        key={data.restaurantMenuId}
        className=" ml-[105px] mt-[5px] w-[58px] h-[27px] rounded-[15px] bg-[#FF611D]"
      >
        <div
          onClick={handleHeartCount}
          id={data.restaurantMenuId}
          className="flex flex-row cursor-pointer"
        >
          {like ? (
            <svg
              id={data.restaurantMenuId}
              className="ml-[10px] mt-[7px]"
              width="15"
              height="14"
              viewBox="0 0 15 14"
              fill="white"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                id={data.restaurantMenuId}
                d="M4.26787 1C2.46308 1 1 2.44845 1 4.23519C1 5.67763 1.57166 9.10086 7.20079 12.5619C7.40464 12.6873 7.66685 12.6873 7.8707 12.5619C13.4998 9.10086 14.0715 5.67763 14.0715 4.23519C14.0715 2.44845 12.6084 1 10.8036 1C8.99884 1 7.53575 2.96072 7.53575 2.96072C7.53575 2.96072 6.07267 1 4.26787 1Z"
                stroke="white"
                strokeWidth="1.30715"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
            </svg>
          ) : (
            <svg
              id={data.restaurantMenuId}
              className="ml-[10px] mt-[7px]"
              width="15"
              height="14"
              viewBox="0 0 15 14"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                id={data.restaurantMenuId}
                d="M4.26787 1C2.46308 1 1 2.44845 1 4.23519C1 5.67763 1.57166 9.10086 7.20079 12.5619C7.40464 12.6873 7.66685 12.6873 7.8707 12.5619C13.4998 9.10086 14.0715 5.67763 14.0715 4.23519C14.0715 2.44845 12.6084 1 10.8036 1C8.99884 1 7.53575 2.96072 7.53575 2.96072C7.53575 2.96072 6.07267 1 4.26787 1Z"
                stroke="white"
                strokeWidth="1.30715"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
            </svg>
          )}
          <div
            id={data.restaurantMenuId}
            key={data.restaurantMenuId}
            className="ml-[2.93px] mt-[4px] font-[400] text-[16px] leading-[22px] flex items-center text-[#FFFFFF]"
          >
            {clicklike ? likeCount : data.likedCount}
          </div>
        </div>
      </div>
    </>
  );
};

export default menuItem;
