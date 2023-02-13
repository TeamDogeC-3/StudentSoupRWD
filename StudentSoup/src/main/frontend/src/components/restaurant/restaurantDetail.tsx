import React, { useState, useEffect } from 'react';
import RestaurantNavbar from '../common/restaurantNavbar';
import { ReactComponent as Star } from '../../img/restaurantDetailStar.svg';
import { ReactComponent as Share } from '../../img/Share.svg';
import { ReactComponent as Review } from '../../img/Review.svg';
import { ReactComponent as Location } from '../../img/location.svg';
import { ReactComponent as Phone } from '../../img/Phone.svg';
import { ReactComponent as Clock } from '../../img/Clock.svg';
import { ReactComponent as PlusCircle } from '../../img/pluscircle.svg';
import { ReactComponent as InfoHeart } from '../../img/InfoHeart.svg';
import MenuInfopage from './menuInfo';
import Reviewpage from './reviewInfo';
import Picturepage from './pictureInfo';
import cn from 'clsx';
import axios from 'axios';
import { useHistory, useLocation } from 'react-router-dom';
import Footer from '../common/footer';
const kakao = (window as any).kakao;

const restaurant = () => {
  const history = useHistory();
  const saveMemberId = sessionStorage.getItem('memberId');
  const [restaurantDetail, setRestaurantDetail] = useState<any>([]);
  const state = useLocation<any>();
  const [clickPage, setClickPage] = useState<any>(1);
  const [latitude, setLatitude] = useState<any>();
  const [longitude, setLongitude] = useState<any>();
  const [heart, isHeart] = useState<boolean>();
  const [clickHeart, isClickHeart] = useState<boolean>();
  const [likedCount, setlikedCount] = useState<number>();
  const [image, setImage] = useState<any>([]);
  const [isDelivery, setIsDelivery] = useState<string>('');
  const restaurantNumber = state.state[0];
  const schoolName = state.state[1];

  const handleClickPage = (e: any) => {
    setClickPage(e);
  };

  const handleMoveScrool = (e: any) => {
    if (clickPage !== 2) {
      setClickPage(2);
      window.scrollTo(0, document.body.scrollHeight);
    } else if (clickPage === 2) {
      alert('현재 리뷰 페이지에 있습니다.');
    }
  };

  const url = `/restaurant/${restaurantNumber}`;
  useEffect(() => {
    axios
      .post(url, {
        schoolName,
        restaurantId: restaurantNumber,
        memberId: saveMemberId,
      })
      .then(res => {
        setIsDelivery(res.data.restaurant.isDelivery);
        setImage(res.data.restaurant.fileName);
        setRestaurantDetail(res.data.restaurant);
        setLatitude(Number(res.data.restaurant.latitude));
        setLongitude(Number(res.data.restaurant.longitude));
        isHeart(res.data.restaurant.like);
      })
      .catch(err => {
        console.log(err);
        if (!restaurantNumber || !schoolName) {
          alert('학교가 등록되지 않았거나 없는 음식점입니다.');
          history.go(-1);
        }
      });
  }, []);

  const MapLocation = [longitude, latitude];
  useEffect(() => {
    const container = document.getElementById('map');
    const options = {
      center: new kakao.maps.LatLng(...MapLocation),
      level: 2,
    };
    const map = new kakao.maps.Map(container, options);
    const markerPosition = new kakao.maps.LatLng(...MapLocation);
    const marker = new kakao.maps.Marker({
      position: markerPosition
    });
    marker.setMap(map);
  });
  const handleHeartCount = () => {
    if (!saveMemberId) {
      if (confirm('로그인후 이용가능한 기능입니다. 로그인하시겠습니까?')) {
        history.push('/login');
      } else {
        /* empty */
      }
    } else {
      void axios
        .post(`/restaurant/${saveMemberId}/like`, {
          restaurantId: restaurantNumber,
          memberId: saveMemberId,
        })
        .then(res => {
          setlikedCount(res.data.data.likedCount);
          isClickHeart(res.data.data.like);
        });
      isHeart(!heart);
      isClickHeart(!clickHeart);
    }
  };

  const handleCopyClipBoard = async (text: string) => {
    try {
      await navigator.clipboard.writeText(text);

      alert('url 주소를 복사하였습니다.');
    } catch (error) {
      alert('url 주소가 생성되지 않았습니다.');
    }
  };
  const imgArr = image.slice(1);
  return (
    <>
      <RestaurantNavbar />
      <div className='min-h-screen'>
      <div className="h-auto flex m-[49px] justify-center">
        <div className="w-[281px] h-auto  bg-[#FFFFFF] shadow-[0px_2px_10px_rgba(0,0,0,0.1)] rounded-[5px] mr-[14px]">
          <div id="map" className="w-[238px] h-[239px] ml-[20px] mt-[20px]"></div>
          <div className="ml-[22px] mt-[13px] whitespace-normal h-auto font-semibold text-[25px] leading-[30px] flex items-center">
            {restaurantDetail.name}
          </div>
          <div className="ml-[22px] mt-[8px] w-[230px] h-auto font-[400] leading-[21px] text-[16px] flex items-center text-[#717171]">
            {restaurantDetail.tag}
          </div>
          <div className="flex flex-row">
            <Star className="ml-[22px] mt-[10px]" />
            <div className="ml-[5.92px] mt-[11px] h-[16px] font-bold text-[16px] leading-[23px] flex items-center text-[#515151]">
              {restaurantDetail.starLiked}
            </div>
            <div className="ml-[10px] mt-[11px] h-[16px] font-[400] text-[13px] leading-[17px] flex items-center text-[#9C9C9C]">
              {restaurantDetail.reviewCount}개의 리뷰
            </div>
          </div>
          <div className="ml-[22px] mr-[21px] mt-[20px] border-[1px] border-[#DEDEDE] bg-[#DEDEDE]"></div>
          <div className="flex flex-row w-[238px]">
            <div className="flex flex-col">
              {heart ? (
                <svg
                  onClick={handleHeartCount}
                  className="ml-[47px] mt-[17px] mb-[12.26px] cursor-pointer"
                  width="16"
                  height="14"
                  viewBox="0 0 16 14"
                  fill="#FF611D"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M13.4222 2.0379C13.0933 1.70886 12.7028 1.44783 12.273 1.26975C11.8432 1.09166 11.3826 1 10.9173 1C10.4521 1 9.99142 1.09166 9.56163 1.26975C9.13183 1.44783 8.74134 1.70886 8.41245 2.0379L7.72988 2.72046L7.04732 2.0379C6.38298 1.37356 5.48195 1.00034 4.54244 1.00034C3.60292 1.00034 2.70189 1.37356 2.03756 2.0379C1.37322 2.70224 1 3.60327 1 4.54278C1 5.48229 1.37322 6.38333 2.03756 7.04766L2.72012 7.73023L7.72988 12.74L12.7396 7.73023L13.4222 7.04766C13.7513 6.71877 14.0123 6.32827 14.1904 5.89848C14.3684 5.46868 14.4601 5.00801 14.4601 4.54278C14.4601 4.07755 14.3684 3.61688 14.1904 3.18708C14.0123 2.75729 13.7513 2.36679 13.4222 2.0379Z"
                    stroke="#FF611D"
                    strokeWidth="1.2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  />
                </svg>
              ) : (
                <svg
                  onClick={handleHeartCount}
                  className="ml-[47px] mt-[17px] mb-[12.26px] cursor-pointer"
                  width="16"
                  height="14"
                  viewBox="0 0 16 14"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M13.4222 2.0379C13.0933 1.70886 12.7028 1.44783 12.273 1.26975C11.8432 1.09166 11.3826 1 10.9173 1C10.4521 1 9.99142 1.09166 9.56163 1.26975C9.13183 1.44783 8.74134 1.70886 8.41245 2.0379L7.72988 2.72046L7.04732 2.0379C6.38298 1.37356 5.48195 1.00034 4.54244 1.00034C3.60292 1.00034 2.70189 1.37356 2.03756 2.0379C1.37322 2.70224 1 3.60327 1 4.54278C1 5.48229 1.37322 6.38333 2.03756 7.04766L2.72012 7.73023L7.72988 12.74L12.7396 7.73023L13.4222 7.04766C13.7513 6.71877 14.0123 6.32827 14.1904 5.89848C14.3684 5.46868 14.4601 5.00801 14.4601 4.54278C14.4601 4.07755 14.3684 3.61688 14.1904 3.18708C14.0123 2.75729 13.7513 2.36679 13.4222 2.0379Z"
                    stroke="#515151"
                    strokeWidth="1.2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  />
                </svg>
              )}

              <div
                onClick={handleHeartCount}
                className="ml-[38px] w-[39px] h-[11px] font-[400] text-[13px] leading-[17px] flex items-center text-[#515151] cursor-pointer"
              >
                좋아요
              </div>
            </div>
            <div className="ml-[20px] mt-[17px] border-[1px] border-[#DEDEDE] rotate-[90] bg-[#DEDEDE]"></div>
            <div className="flex flex-col">
              <Share
                onClick={async => {
                  void handleCopyClipBoard(`/restaurant/${restaurantNumber}`);
                }}
                className="ml-[36px] mt-[15px] cursor-pointer"
              />
              <div
                onClick={async => {
                  void handleCopyClipBoard(`/restaurant/${restaurantNumber}`);
                }}
                className="ml-[31.5px] w-[26px] mt-[13px] h-[11px] font-[400] text-[13px] leading-[22px] flex items-center cursor-pointer"
              >
                공유
              </div>
            </div>
            <div className="ml-[27px] mt-[16px] border-[1px] border-[#DEDEDE] rotate-[90] bg-[#DEDEDE]"></div>
            <div className="flex flex-col">
              <Review onClick={handleMoveScrool} className="ml-[32px] mt-[15px] cursor-pointer" />
              <div
                onClick={handleMoveScrool}
                className="ml-[28px] w-[26px] mt-[12.5px] h-[11px] font-[400] text-[13px] leading-[17px] flex items-center cursor-pointer"
              >
                리뷰
              </div>
            </div>
          </div>
          {isDelivery === 'Y' ? (
            <div className="mt-[30px] ml-[22px] w-[238px] h-[38px] border-[0.7px] rounded-[5px] border-[#FF611D]">
              <div className="ml-[83px] mt-[13px] mb-[15px] w-[82px] h-[11px] font-[400] text-[13px] leading-[18px] flex items-center text-[#FF611D]">
                배달가능 업체
              </div>
            </div>
          ) : (
            <div className="mt-[30px] ml-[22px] w-[238px] h-[38px] border-[0.7px] rounded-[5px] border-[#515151]">
              <div className="ml-[73px] mt-[13px] mb-[15px] w-auto h-[11px] font-[400] text-[13px] leading-[18px] flex items-center text-[#515151]">
                배달불가능 업체
              </div>
            </div>
          )}
        </div>
        <div className="w-[744px] h-auto bg-[#FFFFFF] shadow-[0px_2px_10px_rgba(0,0,0,0.1)] rounded-[5px]">
          <div className="grid grid-row-2 grid-cols-[268px_minmax(214px,_0fr)_214px] gap-1 ml-[20px] mt-[20px]">
            <img src={`/image/${image[0]}`} className="row-span-2 w-[268px] h-[290px] " />
            {imgArr.map((school: any) => (
              <img key={school} src={`/image/${school}`} className="w-[214px] h-[143px]" />
            ))}
          </div>
          <div className="ml-[21px] mt-[32px] font-[400] text-[24px] flex items-center">
            매장정보
          </div>
          <div className="mt-[6px]">
            <span className="ml-[21px] w-auto font-[400] text-[16px] leading-[21px] text-[#515151]">
              <Location className="inline mb-[3px] mr-[5px]" /> {restaurantDetail.address}&nbsp;
            </span>
            <span className="w-auto font-semibold  text-[#515151]">
              {restaurantDetail.schoolName}
            </span>
            <span className="ml-[5px] font-normal text-[#7B7B7B]">에서</span>
            <span className="ml-[5px] font-normal text-[#FF611D]">{restaurantDetail.distance}</span>
          </div>
          <div className="flex flex-row">
            <Phone className="ml-[21px] mt-[12px]" />
            <div className="ml-[9.61px] mt-[13px] h-[12px] font-[400] text-[16px] leading-[21px] flex items-center text-[#515151]">
              {restaurantDetail.tel}
            </div>
          </div>
          <div className="flex flex-row">
            <Clock className="ml-[21px] mt-[14px]" />
            <div className="ml-[8px] mt-[13px] h-[16px] font-[400] text-[16px] leading-[21px] flex items-center text-[#515151]">
              영업시간 AM {restaurantDetail.startTime}- PM {restaurantDetail.endTime}
            </div>
          </div>
          <div className="flex flex-row">
            <PlusCircle className="ml-[21px] mt-[14px]" />
            <div className="w-[700px] ml-[9px] mt-[12px] h-auto font-normal text-[16px] leading-[21px] flex items-center text-[#515151]">
              {restaurantDetail.detail}
            </div>
          </div>
          <div className="flex flex-row">
            <InfoHeart className="ml-[21px] mt-[8px]" />
            <div className="ml-[7px] mb-[20px] mt-[6px] h-auto font-[400] leading-[21px] flex items-center text-[#515151]">
              이 식당에 {clickHeart ? likedCount : restaurantDetail.likedCount}명의 좋아요한
              사용자가 있습니다.
            </div>
          </div>
        </div>
      </div>
      <div className="w-full h-auto flex mb-[54px] justify-center">
        <div className="ml-[298px] w-[744px] h-full bg-[#FFFFFF] shadow-[0px_2px_10px_rgba(0,0,0,0.1)] rounded-[5px]">
          <div className="flex flex-row ml-[10px]">
            <div
              onClick={() => {
                setClickPage(1);
              }}
              className={cn(
                'ml-[86px] mt-[43px] h-[16px] text-[24px] leading-[34px] flex items-center cursor-pointer',
                {
                  'font-bold text-[#FF611D]': clickPage === 1,
                  'font-[400] text-[#515151]': clickPage !== 1,
                },
              )}
            >
              메뉴정보
            </div>
            <div
              onClick={() => {
                setClickPage(2);
              }}
              className={cn(
                'ml-[170px] mt-[43px] h-[16px] text-[24px] leading-[34px] flex items-center cursor-pointer',
                {
                  'font-bold text-[#FF611D]': clickPage === 2,
                  'font-[400] text-[#515151]': clickPage !== 2,
                },
              )}
            >
              리뷰
            </div>
            <div
              onClick={() => {
                setClickPage(3);
              }}
              className={cn(
                'ml-[191px] mt-[43px] h-[16px]  text-[24px] leading-[34px] flex items-center cursor-pointer',
                {
                  'font-bold text-[#FF611D]': clickPage === 3,
                  'font-[400] text-[#515151]': clickPage !== 3,
                },
              )}
            >
              사진
            </div>
          </div>
          <div className="flex flex-row">
            <div className="ml-[25px] mt-[25px] w-[687px] h-[2px] bg-[#BCBCBC] border-[1px] border-[#BCBCBC]">
              <div
                className={cn(
                  'm-[-1px] w-[229px] h-[2px] bg-[#FF611D] border-[1px] border-[#FF611D]',
                  {
                    '': clickPage === 1,
                    'ml-[229px]': clickPage === 2,
                    'ml-[458px]': clickPage === 3,
                  },
                )}
              ></div>
            </div>
          </div>
          {clickPage === 1 && <MenuInfopage />}
          {clickPage === 2 && <Reviewpage {...restaurantDetail} />}
          {clickPage === 3 && <Picturepage />}
        </div>
      </div>
      </div>
      <Footer />
    </>
  );
};
export default restaurant;
