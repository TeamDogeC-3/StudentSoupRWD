import _ from 'lodash';
import { useState, useRef, useEffect } from 'react';
import { useHistory, useLocation } from 'react-router-dom';
import cn from 'clsx';
import axios from 'axios';
const kakao = (window as any).kakao;

interface sortItem {
  title: string;
  value: number;
}

interface Category {
  id: string;
  name: string;
}

const sortList = [
  { title: '등록순', value: 0 },
  { title: '별점순', value: 1 },
  { title: '좋아요순', value: 2 },
  { title: '리뷰순', value: 3 },
  { title: '거리순', value: 4 },
];

const RestaurantMain = (_props: any) => {
  const [set, isSet] = useState<any[]>();
  const [total, isTotal] = useState<number>();
  const [click, setClick] = useState<number>(0);
  const [size, setSize] = useState<number>(6);
  const [latitude, setLatitude] = useState<any>();
  const [longitude, setLongitude] = useState<any>();
  const [closeList, setCloseList] = useState<boolean>(false);
  const listRef = useRef<HTMLDivElement>(null);
  const [sort, setSort] = useState<number>(0);
  const [category, setCategory] = useState<String>('ALL');
  const [showSorts, setShowSorts] = useState(false);
  const [showCategorys, setShowCategorys] = useState(false);

  const sortRef: any = useRef(null);
  const categoryRef: any = useRef(null);

  const history = useHistory();
  const state = useLocation<any>();
  const url = '/restaurants';
  const getSchool = () => {
    axios
      .post(
        url,
        {
          schoolName: state.state,
        },
        {
          params: {
            size,
            sorted: sort,
            category,
          },
        },
      )
      .then(res => {
        isTotal(res.data.restaurant.totalElements);
        isSet(res.data.restaurant.content);
        setLatitude(Number(res.data.school.schoolLatitude));
        setLongitude(Number(res.data.school.schoolLongitude));
      })
      .catch(function (_error) {
        if (!state.state) {
          alert('비정상적인 경로입니다. 메인페이지로 이동합니다.');
          history.push('/');
        }
      });
  };

  const MapLocation = [longitude, latitude];

  useEffect(() => {
    getSchool();
  }, [click, sort, category]);

  useEffect(() => {
    // scroll event listener 등록
    window.addEventListener('scroll', handleScroll);
    return () => {
      // scroll event listener 해제
      window.removeEventListener('scroll', handleScroll);
    };
  });

  useEffect(() => {
    const container = document.getElementById('map');
    const options = {
      center: new kakao.maps.LatLng(...MapLocation),
      level: 3,
    };
    const map = new kakao.maps.Map(container, options);
    const markerPosition = new kakao.maps.LatLng(...MapLocation);
    const marker = new kakao.maps.Marker({
      position: markerPosition,
    });
    marker.setMap(map);
  });

  const handleScroll = () => {
    const scrollHeight = document.documentElement.scrollHeight;
    const scrollTop = document.documentElement.scrollTop;
    const clientHeight = document.documentElement.clientHeight;

    if (scrollTop + clientHeight >= scrollHeight && click !== 0) {
      setClick(click + 1);
      setSize(size + 6);
    }
  };

  const handleClickButton = (_e: any) => {
    setClick(click + 1);
    setSize(size + 6);
  };

  const handleDetailPage = (e: any) => {
    const value = e.target.id;

    history.push('/restaurant/detail', [value, state.state]);
  };

  useEffect(() => {
    const handleOutside = (e: any) => {
      if (sortRef.current && !sortRef.current.contains(e.target)) setShowSorts(false);
    };
    document.addEventListener('mousedown', handleOutside);
    return () => {
      document.removeEventListener('mousedown', handleOutside);
    };
  }, [sortRef]);

  return (
    <div className="min-h-screen pt-[60px] pb-[58px] bg-[#1E1E1E]/5">
      <div className="w-[1039px] mx-auto relative">
        <div className="mb-[31px] relative flex justify-between">
          <div className="font-semibold">
            <span className="pr-2 text-[32px] text-[#FF611D]">{state.state}</span>

            <span className="text-[32px] text-[#5A5A5A]">근처 인기 맛집 검색어</span>
          </div>
          <button
            id="filter-button"
            className={cn(
              'dropdown w-[110px] h-[39px] flex justify-center items-center gap-x-1 rounded-[23.5px] text-[20px] font-semibold text-[#FF611D] bg-white',
            )}
            onClick={() => {
              setShowSorts(prev => !prev);
            }}
          >
            <svg
              width="17"
              height="19"
              viewBox="0 0 17 19"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M5.14055 2.05874C4.86788 2.05874 4.60638 2.16706 4.41357 2.35987C4.22076 2.55268 4.11244 2.81418 4.11244 3.08685C4.11244 3.35952 4.22076 3.62103 4.41357 3.81384C4.60638 4.00664 4.86788 4.11496 5.14055 4.11496C5.41323 4.11496 5.67473 4.00664 5.86754 3.81384C6.06035 3.62103 6.16866 3.35952 6.16866 3.08685C6.16866 2.81418 6.06035 2.55268 5.86754 2.35987C5.67473 2.16706 5.41323 2.05874 5.14055 2.05874ZM2.231 2.05874C2.44341 1.45675 2.83732 0.935462 3.35843 0.566736C3.87953 0.19801 4.50219 0 5.14055 0C5.77892 0 6.40157 0.19801 6.92268 0.566736C7.44379 0.935462 7.8377 1.45675 8.05011 2.05874H15.4217C15.6943 2.05874 15.9558 2.16706 16.1486 2.35987C16.3415 2.55268 16.4498 2.81418 16.4498 3.08685C16.4498 3.35952 16.3415 3.62103 16.1486 3.81384C15.9558 4.00664 15.6943 4.11496 15.4217 4.11496H8.05011C7.8377 4.71696 7.44379 5.23824 6.92268 5.60697C6.40157 5.97569 5.77892 6.1737 5.14055 6.1737C4.50219 6.1737 3.87953 5.97569 3.35843 5.60697C2.83732 5.23824 2.44341 4.71696 2.231 4.11496H1.02811C0.755439 4.11496 0.493935 4.00664 0.301127 3.81384C0.108319 3.62103 0 3.35952 0 3.08685C0 2.81418 0.108319 2.55268 0.301127 2.35987C0.493935 2.16706 0.755439 2.05874 1.02811 2.05874H2.231ZM11.3092 8.22741C11.0365 8.22741 10.775 8.33572 10.5822 8.52853C10.3894 8.72134 10.2811 8.98284 10.2811 9.25552C10.2811 9.52819 10.3894 9.78969 10.5822 9.9825C10.775 10.1753 11.0365 10.2836 11.3092 10.2836C11.5819 10.2836 11.8434 10.1753 12.0362 9.9825C12.229 9.78969 12.3373 9.52819 12.3373 9.25552C12.3373 8.98284 12.229 8.72134 12.0362 8.52853C11.8434 8.33572 11.5819 8.22741 11.3092 8.22741ZM8.39966 8.22741C8.61207 7.62541 9.00598 7.10413 9.52709 6.7354C10.0482 6.36667 10.6708 6.16866 11.3092 6.16866C11.9476 6.16866 12.5702 6.36667 13.0913 6.7354C13.6125 7.10413 14.0064 7.62541 14.2188 8.22741H15.4217C15.6943 8.22741 15.9558 8.33572 16.1486 8.52853C16.3415 8.72134 16.4498 8.98284 16.4498 9.25552C16.4498 9.52819 16.3415 9.78969 16.1486 9.9825C15.9558 10.1753 15.6943 10.2836 15.4217 10.2836H14.2188C14.0064 10.8856 13.6125 11.4069 13.0913 11.7756C12.5702 12.1444 11.9476 12.3424 11.3092 12.3424C10.6708 12.3424 10.0482 12.1444 9.52709 11.7756C9.00598 11.4069 8.61207 10.8856 8.39966 10.2836H1.02811C0.755439 10.2836 0.493935 10.1753 0.301127 9.9825C0.108319 9.78969 0 9.52819 0 9.25552C0 8.98284 0.108319 8.72134 0.301127 8.52853C0.493935 8.33572 0.755439 8.22741 1.02811 8.22741H8.39966ZM5.14055 14.3961C4.86788 14.3961 4.60638 14.5044 4.41357 14.6972C4.22076 14.89 4.11244 15.1515 4.11244 15.4242C4.11244 15.6969 4.22076 15.9584 4.41357 16.1512C4.60638 16.344 4.86788 16.4523 5.14055 16.4523C5.41323 16.4523 5.67473 16.344 5.86754 16.1512C6.06035 15.9584 6.16866 15.6969 6.16866 15.4242C6.16866 15.1515 6.06035 14.89 5.86754 14.6972C5.67473 14.5044 5.41323 14.3961 5.14055 14.3961ZM2.231 14.3961C2.44341 13.7941 2.83732 13.2728 3.35843 12.9041C3.87953 12.5353 4.50219 12.3373 5.14055 12.3373C5.77892 12.3373 6.40157 12.5353 6.92268 12.9041C7.44379 13.2728 7.8377 13.7941 8.05011 14.3961H15.4217C15.6943 14.3961 15.9558 14.5044 16.1486 14.6972C16.3415 14.89 16.4498 15.1515 16.4498 15.4242C16.4498 15.6969 16.3415 15.9584 16.1486 16.1512C15.9558 16.344 15.6943 16.4523 15.4217 16.4523H8.05011C7.8377 17.0543 7.44379 17.5756 6.92268 17.9443C6.40157 18.313 5.77892 18.511 5.14055 18.511C4.50219 18.511 3.87953 18.313 3.35843 17.9443C2.83732 17.5756 2.44341 17.0543 2.231 16.4523H1.02811C0.755439 16.4523 0.493935 16.344 0.301127 16.1512C0.108319 15.9584 0 15.6969 0 15.4242C0 15.1515 0.108319 14.89 0.301127 14.6972C0.493935 14.5044 0.755439 14.3961 1.02811 14.3961H2.231Z"
                fill="#FF611D"
              />
            </svg>
            필터
          </button>

          {/* filter */}
          {showSorts && (
            <div className="px-2 py-4 z-10 flex flex-col gap-y-1 absolute top-12 right-0 rounded-[23.5px] bg-white shadow-sm shadow-black/25">
              {_.map(sortList, (item, index) => {
                return (
                  <div
                    className={cn(
                      'w-[110px] z-10 px-4 py-1 mx-auto top-12 right-0 text-center cursor-pointer rounded-[23.5px]',
                      {
                        'font-bold text-[#FFFFFF] bg-[#FF611D]': sort === index,
                        'font-[400] text-[#808080] hover:text-white hover:bg-[#FF611D]/50':
                          sort !== index,
                      },
                      // 'buttonContainer overflow-hidden ease-out dalay-[30s]'
                    )}
                    onClick={() => {
                      setSort(index);
                      setShowSorts(false);
                    }}
                    key={index}
                  >
                    {item.title}
                    {/* sortList.title */}
                  </div>
                );
              })}
            </div>
          )}
        </div>
        <div className="w-[1039px] pb-[47px] mx-auto rounded-[10px] drop-shadow-md bg-white">
          <div className="w-[810px] mx-auto py-[31px] flex flex-wrap gap-x-[20px] gap-y-[32px]">
            <button
              className={cn(
                'px-[19px] py-[7px] border border-[#FF611D] rounded-[38px] cursor-pointer',
                {
                  'font-semibold text-[#FFFFFF] bg-[#FF611D]': category === 'ALL',
                  'font-[400] text-[#808080] hover:text-white hover:bg-[#FF611D]/50':
                    category !== 'ALL',
                },
              )}
              onClick={() => {
                setCategory('ALL');
              }}
              value="ALL"
            >
              전체보기
            </button>
            <button
              className={cn(
                'px-[19px] py-[7px] border border-[#FF611D] rounded-[38px] cursor-pointer',
                {
                  'font-semibold text-[#FFFFFF] bg-[#FF611D]': category === 'KOREAN',
                  'font-[400] text-[#808080] hover:text-white hover:bg-[#FF611D]/50':
                    category !== 'KOREAN',
                },
              )}
              onClick={() => {
                setCategory('KOREAN');
              }}
              value="KOREAN"
            >
              한식
            </button>
            <button
              className={cn(
                'px-[19px] py-[7px] border border-[#FF611D] rounded-[38px] cursor-pointer',
                {
                  'font-semibold text-[#FFFFFF] bg-[#FF611D]': category === 'WESTERN',

                  'font-[400] text-[#808080] hover:text-white hover:bg-[#FF611D]/50':
                    category !== 'WESTERN',
                },
              )}
              onClick={() => {
                setCategory('WESTERN');
              }}
              value="WESTERN"
            >
              양식
            </button>
            <button
              className={cn(
                'px-[19px] py-[7px] border border-[#FF611D] rounded-[38px] cursor-pointer',
                {
                  'font-semibold text-[#FFFFFF] bg-[#FF611D]': category === 'FASTFOOD',

                  'font-[400] text-[#808080] hover:text-white hover:bg-[#FF611D]/50':
                    category !== 'FASTFOOD',
                },
              )}
              onClick={() => {
                setCategory('FASTFOOD');
              }}
              value="FASTFOOD"
            >
              패스트푸드
            </button>
            <button
              className={cn(
                'px-[19px] py-[7px] border border-[#FF611D] rounded-[38px] cursor-pointer',
                {
                  'font-semibold text-[#FFFFFF] bg-[#FF611D]': category === 'ASIAN',

                  'font-[400] text-[#808080] hover:text-white hover:bg-[#FF611D]/50':
                    category !== 'ASIAN',
                },
              )}
              onClick={() => {
                setCategory('ASIAN');
              }}
              value="ASIAN"
            >
              아시안음식
            </button>
            <button
              className={cn(
                'px-[19px] py-[7px] border border-[#FF611D] rounded-[38px] cursor-pointer',
                {
                  'font-semibold text-[#FFFFFF] bg-[#FF611D]': category === 'JAPAN',

                  'font-[400] text-[#808080] hover:text-white hover:bg-[#FF611D]/50':
                    category !== 'JAPAN',
                },
              )}
              onClick={() => {
                setCategory('JAPAN');
              }}
              value="JAPAN"
            >
              일식
            </button>
            <button
              className={cn(
                'px-[19px] py-[7px] border border-[#FF611D] rounded-[38px] cursor-pointer',
                {
                  'font-semibold text-[#FFFFFF] bg-[#FF611D]': category === 'CHINESE',

                  'font-[400] text-[#808080] hover:text-white hover:bg-[#FF611D]/50':
                    category !== 'CHINESE',
                },
              )}
              onClick={() => {
                setCategory('CHINESE');
              }}
              value="CHINESE"
            >
              중식
            </button>
            <button
              className={cn(
                'px-[19px] py-[7px] border border-[#FF611D] rounded-[38px] cursor-pointer',
                {
                  'font-semibold text-[#FFFFFF] bg-[#FF611D]': category === 'SNACK',

                  'font-[400] text-[#808080] hover:text-white hover:bg-[#FF611D]/50':
                    category !== 'SNACK',
                },
              )}
              onClick={() => {
                setCategory('SNACK');
              }}
              value="SNACK"
            >
              분식
            </button>
            <button
              className={cn(
                'px-[19px] py-[7px] border border-[#FF611D] rounded-[38px] cursor-pointer',
                {
                  'font-semibold text-[#FFFFFF] bg-[#FF611D]': category === 'CAFE',

                  'font-[400] text-[#808080] hover:text-white hover:bg-[#FF611D]/50':
                    category !== 'CAFE',
                },
              )}
              onClick={() => {
                setCategory('CAFE');
              }}
              value="CAFE"
            >
              카페
            </button>
            <button
              className={cn(
                'px-[19px] py-[7px] border border-[#FF611D] rounded-[38px] cursor-pointer',
                {
                  'font-semibold text-[#FFFFFF] bg-[#FF611D]': category === 'BUFFET',

                  'font-[400] text-[#808080] hover:text-white hover:bg-[#FF611D]/50':
                    category !== 'BUFFET',
                },
              )}
              onClick={() => {
                setCategory('BUFFET');
              }}
              value="BUFFET"
            >
              뷔페
            </button>
            <button
              className={cn(
                'px-[19px] py-[7px] border border-[#FF611D] rounded-[38px] cursor-pointer',
                {
                  'font-semibold text-[#FFFFFF] bg-[#FF611D]': category === 'OTHERS',

                  'font-[400] text-[#808080] hover:text-white hover:bg-[#FF611D]/50':
                    category !== 'OTHERS',
                },
              )}
              onClick={() => {
                setCategory('OTHERS');
              }}
              value="OTHERS"
            >
              기타
            </button>
          </div>
          <div className="w-[810px] mx-auto mb-[16px] flex justify-between">
            <div className="text-[18px] text-[#262626]">
              {state.state}
              <span className="text-[18px] text-[#FF611D]">({total}곳)</span>
            </div>
            <div>
              <span>현위치: </span>

              <span>{state.state}</span>
            </div>
          </div>
          <div
            id="map"
            className="w-[810px] h-[247px] mx-auto mb-[45px] relative rounded-[10px] bg-gray-100"
          ></div>
          <div className="w-[810px] mx-auto grid grid-cols-2 gap-x-[46px] justify-center place-content-stretch">
            {set?.map(school => (
              <div id={school.restaurantId} key={school.restaurantId}>
                <img
                  src={`/image/${school.fileName}`}
                  onClick={handleDetailPage}
                  id={school.restaurantId}
                  className="w-[382px] h-[225px] rounded-[10px] bg-gray-100 cursor-pointer"
                />

                <div id={school.restaurantId} className="mt-[20px] text-[28px] text-[#262626]">
                  {school.name}
                  <span className="px-2 text-[28px] text-[#FF611D]">{school.starLiked}</span>
                  <div className="text-[20px] text-[#696969] truncate">
                    {school.restaurantCategory} | {school.tag}
                  </div>
                  <div className="ml-[2px] mb-[25px] flex gap-x-9 items-center text-[20px] font-medium text-[#262626]">
                    <span>
                      <svg
                        width="14"
                        height="10"
                        viewBox="0 0 14 10"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                        className="inline-block mr-[8px] mb-[2px]"
                      >
                        <path
                          d="M7.28072 0.729492C4.24718 0.729492 1.65654 2.61635 0.606934 5.2798C1.65654 7.94325 4.24718 9.83011 7.28072 9.83011C10.3143 9.83011 12.9049 7.94325 13.9545 5.2798C12.9049 2.61635 10.3143 0.729492 7.28072 0.729492ZM7.28072 8.31334C5.60621 8.31334 4.24718 6.95432 4.24718 5.2798C4.24718 3.60529 5.60621 2.24626 7.28072 2.24626C8.95524 2.24626 10.3143 3.60529 10.3143 5.2798C10.3143 6.95432 8.95524 8.31334 7.28072 8.31334ZM7.28072 3.45968C6.27359 3.45968 5.4606 4.27267 5.4606 5.2798C5.4606 6.28694 6.27359 7.09993 7.28072 7.09993C8.28786 7.09993 9.10085 6.28694 9.10085 5.2798C9.10085 4.27267 8.28786 3.45968 7.28072 3.45968Z"
                          fill="black"
                        />
                      </svg>
                      {school.viewCount}
                    </span>
                    <span>
                      <svg
                        width="15"
                        height="13"
                        viewBox="0 0 15 13"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                        className="inline-block mr-[8px] mb-[2px]"
                      >
                        <path
                          d="M11.7028 8.08683C11.5215 8.25855 11.4382 8.5069 11.4795 8.75047L12.1017 12.1166C12.1542 12.4019 12.031 12.6906 11.7868 12.8555C11.5474 13.0265 11.229 13.047 10.9679 12.9102L7.86799 11.3298C7.76021 11.2737 7.64052 11.2436 7.51804 11.2401H7.32836C7.26257 11.2497 7.19818 11.2702 7.13939 11.3017L4.03879 12.8897C3.88551 12.9649 3.71194 12.9916 3.54186 12.9649C3.12751 12.8883 2.85105 12.5024 2.91894 12.0953L3.54186 8.72926C3.58315 8.48364 3.49986 8.23392 3.31859 8.05946L0.791218 5.66488C0.579846 5.46442 0.506355 5.16339 0.602943 4.89178C0.69673 4.62085 0.936099 4.42313 1.22516 4.37865L4.70371 3.88537C4.96827 3.85869 5.20064 3.70133 5.31963 3.46872L6.85243 0.396816C6.88882 0.328399 6.93572 0.265456 6.99241 0.212091L7.0554 0.1642C7.08829 0.128623 7.12609 0.0992039 7.16808 0.0752581L7.24438 0.0478915L7.36336 0H7.65802C7.92119 0.0266824 8.15286 0.18062 8.27394 0.410499L9.82704 3.46872C9.93902 3.69244 10.1567 3.84774 10.408 3.88537L13.8865 4.37865C14.1805 4.4197 14.4261 4.61811 14.5234 4.89178C14.6151 5.16613 14.536 5.46716 14.3205 5.66488L11.7028 8.08683Z"
                          fill="#FFB21D"
                        />
                      </svg>
                      {school.starLiked}
                    </span>
                    <span>
                      <svg
                        width="13"
                        height="12"
                        viewBox="0 0 13 12"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                        className="inline-block mr-[8px] mb-[2px]"
                      >
                        <path
                          d="M6.14663 11.7162L7.01024 10.93C10.0775 8.14861 12.1025 6.31419 12.1025 4.06286C12.1025 2.22844 10.6612 0.787109 8.82679 0.787109C7.79046 0.787109 6.79583 1.26954 6.14663 2.03189C5.49744 1.26954 4.5028 0.787109 3.46647 0.787109C1.63205 0.787109 0.190722 2.22844 0.190722 4.06286C0.190722 6.31419 2.21573 8.14861 5.28302 10.936L6.14663 11.7162Z"
                          fill="#FF611D"
                        />
                      </svg>
                      {school.likedCount}
                    </span>
                  </div>
                </div>
              </div>
            ))}
          </div>
          {click === 0 && (
            <button
              onClick={handleClickButton}
              className="w-[807px] h-[60px] mt-[70px] mx-[116px] rounded-[5px] text-[16px] text-white bg-[#FF611D]"
            >
              검색 결과 더보기
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default RestaurantMain;
