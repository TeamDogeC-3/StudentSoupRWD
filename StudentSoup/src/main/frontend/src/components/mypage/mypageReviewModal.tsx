import axios from 'axios';
import { useRef, useState, useEffect } from 'react';

const MypageReviewModal = (props: any) => {
  const memberId = sessionStorage.getItem('memberId');

  const reviewWriteStarArr = [0, 1, 2, 3, 4];
  const [imgs, setImgs] = useState<any>();
  const imageUploader = useRef<any>(null);
  const [showImages, setShowImages] = useState([]);
  const [textValue, setTextValue] = useState<any>('');
  const [rating, setRating] = useState(0);
  const [hover, setHover] = useState(0);
  const [count, setCount] = useState<number>(0);
  const [maxCount, setMaxCount] = useState<number>(400);
  const [clicked, setClicked] = useState([false, false, false, false, false]);
  const [valueChange, setValueChange] = useState<boolean>(false);

  const score = clicked.filter(Boolean).length;

  useEffect(() => {
    axios
      .get(`/restaurantReview/${memberId}/${props.reviewId}`)
      .then(function (response) {
        console.log(response.data);
        setTextValue(response.data.content);
        setRating(response.data.starLiked);
        setImgs(response.data.imageFileList);
        setShowImages(response.data.imageFileList);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, []);

  const handleStarClick = (index: number) => {
    const clickStates = [...clicked];
    for (let i = 0; i < 5; i++) {
      clickStates[i] = i <= index;
    }
    setClicked(clickStates);
  };

  const handleSetValue = (e: any) => {
    setTextValue(e.target.value);
    setCount(e.target.value.length);
    setMaxCount(maxCount);
  };

  useEffect(() => {
    setCount(textValue.length);
  }, [valueChange]);

  const cancelReviewValue = (e: any) => {
    if (confirm('게시글 작성을 취소하시겠습니까? (작성중이던 글은 삭제됩니다.)')) {
      setShowImages([]);
      setRating(0);
      setHover(0);
      setClicked([false, false, false, false, false]);
      setTextValue('');
    }
    props.onClickToggleModal();
  };

  const handleReviewValue = (e: any) => {
    if (count < 5) {
      alert('5자 이상 입력해야 합니다.');
    } else if (rating === 0) {
      alert('별점은 최소 1점부터 가능합니다.');
    } else {
      axios
        .patch(
          `/restaurantReview/${memberId}/${props.reviewId}`,
          {
            content: textValue,
            starLiked: rating,
          },
          {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          },
        )
        .then(function (response) {
          alert('리뷰 수정이 완료되었습니다.');
          setShowImages([]);
          setRating(0);
          setHover(0);
          setClicked([false, false, false, false, false]);
          setTextValue('');
          location.reload();
        })
        .catch(function (error) {
          console.log(error);
        });
    }
    props.onClickToggleModal();
  };

  return (
    <div className="w-full h-full bg-[rgba(0,0,0,0.1)] flex flex-col fixed top-0 right-0 justify-center items-center z-[55]">
      <div className="bg-white rounded-[10px]">
        <div className="mt-[27px] ml-[289px] h-[16px] font-normal text-[24px] leading-[33px] flex items-center">
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
                  setValueChange(true);
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
        <div className="ml-[210px] mt-[19.56px] h-[16px] font-normal text-[14px] leading-[18px] flex items-center text-[#9F9F9F]">
          고객님의 리뷰가 다른 고객들에게 도움이 될 수 있어요!
        </div>
        <div>
          <textarea
            className="w-[694px] h-[220px] mx-[25px] mt-[20px] resize-none border-[2px] bg-[#D9D9D9]"
            placeholder="업주와 다른 사용자들이 상처받지 않도록 좋은 표현과 주문하신 메뉴 및 매장 서비스에 대해서 작성해주세요. 유용한 팁도 남겨주시면 감사합니다:)"
            value={textValue}
            maxLength={399}
            onChange={e => {
              handleSetValue(e);
              setValueChange(true);
            }}
          ></textarea>
          <div className="relative w-[150px] h-[20px] left-[573px] bottom-[40px] font-normal text-[16px] leading-[22px] text-[#5F5F5F]">
            {count}/400 (최소 5자)
          </div>
        </div>
        <div className="flex flex-row ml-[14px]">
          {showImages.map((image, id) => (
            <>
              <div key={id}>
                <img
                  className="w-[130px] h-[121px] ml-[11px] border-[1px] rounded-[5px] border-[#BCBCBC]"
                  src={`/image/${image}`}
                  alt={`${image}-${id}`}
                />
              </div>
            </>
          ))}
        </div>
        <div className="ml-[27px] mt-[12px] h-[21px] font-normal text-[16px] leading-[21px] text-[#9F9F9F]">
          리뷰 수정 시에는 사진 첨부 및 삭제가 불가합니다.
        </div>
        <div className="flex flex-row pb-[25px]">
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
      </div>
    </div>
  );
};

export default MypageReviewModal;
