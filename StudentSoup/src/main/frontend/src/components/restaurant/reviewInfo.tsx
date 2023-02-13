import React, { useState, useEffect, useRef } from 'react';
import { ReactComponent as ReviewEdit } from '../../img/reviewedit.svg';
import { ReactComponent as ReviewWriteCamera } from '../../img/reviewwriteCamera.svg';
import { ReactComponent as ReviewWriteClose } from '../../img/ReviewWriteClose.svg';
import ReviewWrite from './reviewList';
import { useHistory, useLocation } from 'react-router-dom';
import axios from 'axios';

const review = (props: any) => {
  const history = useHistory();
  const [imgs, setImgs] = useState<any>();
  const imageUploader = useRef<any>(null);
  const totalReviewCount = props.reviewCount;
  const name = props.name;
  const totalStar = props.starLiked;
  const state = useLocation<any>();
  const restaurantNumber = state.state[0];
  const saveMemberId = sessionStorage.getItem('memberId');
  const saveMemberNickName = sessionStorage.getItem('nickname');
  const [showImages, setShowImages] = useState([]);
  const AVR_RATE = totalStar;
  const STAR_IDX_ARR = ['first', 'second', 'third', 'fourth', 'last'];
  const [starArr, setStarArr] = useState([0, 0, 0, 0, 0]);
  const [whichSort, setWhichSort] = useState<any>(2);
  const [reviewclick, isReviewClick] = useState<boolean>(false);
  const clickPage = (e: any) => {
    setWhichSort(e);
  };
  const [clicked, setClicked] = useState([false, false, false, false, false]);
  const reviewWriteStarArr = [0, 1, 2, 3, 4];
  const [rating, setRating] = useState(0);
  const [hover, setHover] = useState(0);
  const handleStarClick = (index: number) => {
    const clickStates = [...clicked];
    for (let i = 0; i < 5; i++) {
      clickStates[i] = i <= index;
    }
    setClicked(clickStates);
  };
  const score = clicked.filter(Boolean).length;

  const calcStarRates = () => {
    const tempStarRatesArr = [0, 0, 0, 0, 0];
    let starVerScore = (AVR_RATE * 120) / 5;
    let idx = 0;
    while (starVerScore > 24) {
      tempStarRatesArr[idx] = 24;
      idx += 1;
      starVerScore -= 24;
    }
    tempStarRatesArr[idx] = starVerScore;
    return tempStarRatesArr;
  };
  useEffect(() => {
    setStarArr(calcStarRates);
  }, []);
  const [textValue, setTextValue] = useState<any>('');
  const [count, setCount] = useState<number>(0);
  const [maxCount, setMaxCount] = useState<number>(400);
  const handleSetValue = (e: any) => {
    setTextValue(e.target.value);
    setCount(e.target.value.length);
    setMaxCount(maxCount);
  };
  const reviewButtonClick = () => {
    if (!saveMemberId) {
      if (confirm('로그인후 이용가능한 기능입니다. 로그인하시겠습니까?')) {
        history.push('/login');
      } else {
        /* empty */
      }
    } else isReviewClick(!reviewclick);
    if (reviewclick) {
      if (confirm('게시글 작성을 취소하시겠습니까? (작성중이던 글은 삭제됩니다.)')) {
        setShowImages([]);
        setRating(0);
        setHover(0);
        setClicked([false, false, false, false, false]);
        setTextValue('');
        isReviewClick(!reviewclick);
      } else {
        isReviewClick(reviewclick);
      }
    }
  };
  const cancelReviewValue = (e: any) => {
    if (confirm('게시글 작성을 취소하시겠습니까? (작성중이던 글은 삭제됩니다.)')) {
      setShowImages([]);
      setRating(0);
      setHover(0);
      setClicked([false, false, false, false, false]);
      setTextValue('');
      isReviewClick(!reviewclick);
    } else {
      isReviewClick(reviewclick);
    }
  };

  const handleReviewValue = async (e: any) => {
    if (count < 5) {
      alert('5자 이상 입력해야 합니다.');
    } else if (score === 0) {
      alert('별점은 최소 1점부터 가능합니다.');
    } else if (!imgs) {
      await axios.put(
        `/restaurantReview/${restaurantNumber}`,
        {
          restaurantName: name,
          memberId: saveMemberId,
          nickName: saveMemberNickName,
          content: textValue,
          starLiked: rating,
        },
        {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        },
      );
      alert('리뷰 작성이 완료되었습니다.');
      isReviewClick(!reviewclick);
      setShowImages([]);
      setRating(0);
      setHover(0);
      setClicked([false, false, false, false, false]);
      setTextValue('');
      location.reload();
    } else {
      await axios.put(
        `/restaurantReview/${restaurantNumber}`,
        {
          restaurantName: name,
          memberId: saveMemberId,
          nickName: saveMemberNickName,
          content: textValue,
          starLiked: rating,
          multipartFileList: [...imgs],
        },
        {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        },
      );
      alert('리뷰 작성이 완료되었습니다.');
      isReviewClick(!reviewclick);
      setShowImages([]);
      setRating(0);
      setHover(0);
      setClicked([false, false, false, false, false]);
      setTextValue('');
      location.reload();
    }
  };
  const handleAddImages = (event: any) => {
    const maxFilesizeAll = 5 * 1024 * 1000;
    const imageLists = event.target.files;
    setImgs(event.target.files);

    let imageUrlLists = [...showImages];

    for (let i = 0; i < imageLists.length; i++) {
      if (!/\.(gif|jpg|png|jpeg|bmp|svg)$/i.test(imageLists[i].name)) {
        alert('해당파일은 업로드가 불가능한 파일입니다.');
        return;
      } else if (imageLists[i].size > maxFilesizeAll) {
        alert('업로드 가능한 최대 용량은 5MB입니다.');
        return;
      }

      const currentImageUrl = URL.createObjectURL(imageLists[i]) as never;

      imageUrlLists.push(currentImageUrl);
    }

    if (imageUrlLists.length < 5) {
      imageUrlLists = imageUrlLists.slice(0, 5);
    } else {
      alert('이미지파일은 4개이하만 업로드 할수 있습니다.');
      return;
    }

    setShowImages(imageUrlLists);
  };
  const onCickImageUpload = () => {
    imageUploader.current.click();
  };

  const handleDeleteImage = (id: any) => {
    setShowImages(showImages.filter((_, index) => index !== id));
  };
  return (
    <>
      <div className="flex flex-row mt-[2px] w-[742px]">
        <div className="ml-[28px] mt-[26px] w-[300px] h-[16px] font-bold text-[24px] leading-[33px] flex items-center">
          {name}
        </div>
        {STAR_IDX_ARR.map((item, idx) => {
          return (
            <span
              className="flex inline-flex items-center ml-[1px] mt-[12.4px]"
              key={`${item}_${idx}`}
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24px"
                height="22px"
                viewBox="0 0 24 22"
                fill="#cacaca"
              >
                <clipPath id={`${item}StarClip`}>
                  <rect width={`${starArr[idx]}`} height="22" />
                </clipPath>
                <path
                  id={`${item}Star`}
                  d="M18.4751 13.8079C18.1745 14.0926 18.0364 14.5044 18.1048 14.9083L19.1366 20.4899C19.2236 20.963 19.0194 21.4417 18.6143 21.7151C18.2174 21.9988 17.6894 22.0328 17.2565 21.8059L12.1163 19.1853C11.9375 19.0922 11.7391 19.0423 11.536 19.0367H11.2215C11.1124 19.0525 11.0056 19.0866 10.9081 19.1388L5.76676 21.7719C5.51259 21.8967 5.22477 21.9409 4.94275 21.8967C4.25569 21.7696 3.79726 21.1298 3.90984 20.4547L4.94275 14.8731C5.01123 14.4659 4.87312 14.0518 4.57253 13.7625L0.381686 9.79185C0.0311914 9.45945 -0.0906691 8.96029 0.0694904 8.5099C0.225008 8.06065 0.621925 7.73279 1.10124 7.65905L6.86931 6.84109C7.30801 6.79685 7.69332 6.53592 7.89062 6.1502L10.4323 1.05643C10.4926 0.942983 10.5704 0.838612 10.6644 0.750123L10.7688 0.67071C10.8234 0.611718 10.8861 0.562936 10.9557 0.523229L11.0822 0.47785L11.2795 0.398438H11.7681C12.2045 0.442682 12.5886 0.697938 12.7894 1.07912L15.3647 6.1502C15.5504 6.52117 15.9114 6.7787 16.328 6.84109L22.0961 7.65905C22.5835 7.72712 22.9909 8.05611 23.1522 8.5099C23.3042 8.96482 23.1731 9.46399 22.8156 9.79185L18.4751 13.8079Z"
                />
                <use clipPath={`url(#${item}StarClip)`} href={`#${item}Star`} fill="#FFB21D" />
              </svg>
            </span>
          );
        })}
        <div className="ml-[8px] mt-[27px] w-[45px] h-[16px] font-bold text-[24px] leading-[33px] flex items-center text-[#FF611D]">
          {AVR_RATE}
        </div>
        <div className="ml-[5px] mt-[28px] w-[201px] h-[16px] font-[400] text-[14px] leading-[18px] flex items-center text-[#9F9F9F]">
          총 {totalReviewCount}명이 리뷰를 작성했어요.
        </div>
        <div
          onClick={reviewButtonClick}
          className="mt-[16px] ml-[1px] mr-[36px] w-[177px] h-[38px] bg-[#FF611D] rounded-[22px] cursor-pointer"
        >
          <div className="flex flex-row ml-[3px]">
            <ReviewEdit className="ml-[17px] mt-[12.39px]" />
            <button className="ml-[9.51px] mr-[10.46px] mt-[11.39px] w-[120px] h-[16px] leading-[20px] flex items-center fw-400 text-[14px] text-[#FFFFFF]">
              리뷰 작성하러 가기
            </button>
          </div>
        </div>
      </div>
      <div className="ml-[25px] mt-[20px] w-[687px] h-[1px] border border-[#D5D5D5] bg-[#D5D5D5] "></div>
      <div>
        {reviewclick ? (
          <>
            <div className="mt-[77px] ml-[289px] h-[16px] font-normal text-[24px] leading-[33px] flex items-center">
              맛있게 드셨나요?
            </div>
            <div className="flex flex-row ml-[258px]">
              {reviewWriteStarArr.map((star, index) => {
                index += 1;
                return (
                  <svg
                    key={star}
                    onClick={() => {
                      handleStarClick(star);
                      setRating(index);
                    }}
                    onMouseEnter={() => {
                      setHover(index);
                    }}
                    onMouseLeave={() => {
                      setHover(rating);
                    }}
                    className={
                      index <= (hover || rating)
                        ? 'ml-[4.15px] mt-[20px] fill-[#FFB21D] cursor-pointer'
                        : 'ml-[4.15px] mt-[20px] fill-[#CDCDCD] hover:fill-[#FFB21D] cursor-pointer peer-hover:text-yellow-400'
                    }
                    width="40"
                    height="37"
                    viewBox="0 0 40 37"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path d="M31.2343 22.6702C30.7261 23.1517 30.4926 23.8479 30.6084 24.5307L32.3527 33.967C32.4999 34.7668 32.1545 35.5762 31.4698 36.0384C30.7987 36.5179 29.906 36.5754 29.1741 36.1918L20.484 31.7614C20.1818 31.6041 19.8463 31.5197 19.503 31.5101H18.9712C18.7868 31.537 18.6063 31.5945 18.4415 31.6827L9.74939 36.1343C9.3197 36.3453 8.8331 36.4201 8.35631 36.3453C7.19475 36.1305 6.41972 35.0487 6.61005 33.9076L8.35631 24.4712C8.47207 23.7827 8.23858 23.0826 7.7304 22.5935L0.645285 15.8807C0.0527328 15.3187 -0.153287 14.4748 0.117482 13.7134C0.380402 12.9539 1.05144 12.3996 1.86178 12.2749L11.6134 10.8921C12.3551 10.8173 13.0065 10.3761 13.34 9.72404L17.637 1.11241C17.739 0.920619 17.8705 0.744167 18.0294 0.594567L18.206 0.46031C18.2982 0.360576 18.4042 0.278104 18.5219 0.210975L18.7358 0.134257L19.0693 0H19.8954C20.6331 0.0748003 21.2826 0.506341 21.622 1.15077L25.9759 9.72404C26.2898 10.3512 26.9 10.7866 27.6044 10.8921L37.356 12.2749C38.1801 12.39 38.8688 12.9462 39.1415 13.7134C39.3986 14.4825 39.1769 15.3264 38.5725 15.8807L31.2343 22.6702Z" />
                  </svg>
                );
              })}
            </div>
            <div className="ml-[210px] mt-[19.56px]  h-[16px] font-normal text-[14px] leading-[18px] flex items-center text-[#9F9F9F]">
              고객님의 리뷰가 다른 고객들에게 도움이 될 수 있어요!
            </div>
            <div>
              <textarea
                className="w-[694px] h-[220px] ml-[25px] mt-[20px] resize-none border-[2px] bg-[#D9D9D9]"
                placeholder="업주와 다른 사용자들이 상처받지 않도록 좋은 표현과 주문하신 메뉴 및 매장 서비스에 대해서 작성해주세요. 유용한 팁도 남겨주시면 감사합니다:)"
                value={textValue}
                maxLength={399}
                onChange={e => {
                  handleSetValue(e);
                }}
              ></textarea>
              <div className="relative w-[150px] h-[20px] left-[573px] bottom-[40px] font-normal text-[16px] leading-[22px] text-[#5F5F5F]">
                {count}/400 (최소 5자)
              </div>
            </div>
            <div className="flex flex-row ml-[14px]">
              <div
                className="w-[130px] h-[121px] ml-[11px] border-[1px] rounded-[5px] border-[#BCBCBC] border-dotted cursor-pointer"
                onChange={handleAddImages}
                onClick={onCickImageUpload}
              >
                <ReviewWriteCamera className="ml-[53px] mt-[36px] cursor-pointer" />
                <div className="flex flex-col">
                  <div className="h-[39px] font-normal text-[14px] leading-[20px] text-center text-[#878787] cursor-pointer">
                    사진 첨부
                    <div className="ml-[5px]">{showImages.length}/4</div>
                  </div>
                </div>
                <input
                  type="file"
                  multiple
                  accept=".png,.jpg,.gif,.jpeg,.bmp,.svg"
                  ref={imageUploader}
                  className="hidden"
                />
              </div>
              {showImages.map((image, id) => (
                <>
                  <div key={id}>
                    <img
                      className="w-[130px] h-[121px] ml-[11px] border-[1px] rounded-[5px] border-[#BCBCBC]"
                      src={image}
                      alt={`${image}-${id}`}
                    />
                    <ReviewWriteClose
                      onClick={() => {
                        handleDeleteImage(id);
                      }}
                      className="w-[20px] h-[20px] ml-[65px] mt-[5.5px] cursor-pointer"
                    />
                  </div>
                </>
              ))}
            </div>
            <div className="ml-[27px] mt-[12px] h-[21px] font-normal text-[16px] leading-[21px] text-[16px] text-[#9F9F9F]">
              사진은 최대 5MB 이하의 JPG, PNG, GIF, BMP, JPEG, SVG 파일 4장까지 첨부 가능합니다.
            </div>
            <div className="flex flex-row">
              <div
                onClick={cancelReviewValue}
                className="ml-[25px] mt-[26px] w-[335px] h-[54px] border-[1px] rounded-[5px] border-[#CCCCCC] cursor-pointer"
              >
                <button className="ml-[136px] mt-[16px] h-[18px] font-normal text-[16px] leading-[22px] text-[#9F9F9F]">
                  취소하기
                </button>
              </div>
              <div
                onClick={handleReviewValue}
                className="ml-[17px] mt-[26px] w-[335px] h-[54px] border-[1px] rounded-[5px] border-[#FF611D] cursor-pointer"
              >
                <button className="ml-[136px] mt-[15.5px] h-[18px] font-normal text-[16px] leading-[22px] text-[#FF611D]">
                  등록하기
                </button>
              </div>
            </div>
          </>
        ) : (
          ''
        )}
        <ReviewWrite />
      </div>
    </>
  );
};

export default review;
