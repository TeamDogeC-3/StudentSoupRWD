import School from '../../img/school.png';
import Major from '../../img/major.png';
import { ReactComponent as X } from '../../img/x.svg';
import Human from '../../img/circle_human.png';
import { useState, useRef, useEffect } from 'react';
import cn from 'clsx';
import axios from 'axios';
import WritingComponent from './content/writingComponent';
import ReplyComponent from './content/replyComponent';
import ReviewComponent from './content/reviewComponent';

interface propTypes {
  onClickMenu: Function;
}

const MypageHome = (props: propTypes) => {
  const uploadImage = useRef<any>(null);
  const imageUploader = useRef<any>(null);

  const memberId = sessionStorage.getItem('memberId');
  const profileName = sessionStorage.getItem('fileName');
  console.log(memberId);
  const savedName = sessionStorage.getItem('nickname');
  const savedSchool = sessionStorage.getItem('schoolName');
  const savedDepart = sessionStorage.getItem('departmentName');
  const registrationDate = String(sessionStorage.getItem('registrationDate'));
  const [year, month, day] = registrationDate.split('-');

  const [content, setContent] = useState<String>('WRITING');
  const [showContent, setShowContent] = useState(false);
  const contentRef: any = useRef(null);

  const [imgNull, isImgNull] = useState<boolean>();

  const [id, setId] = useState<string>('home');
  const onClickMypageBoardReview = (e: React.MouseEvent<Element, MouseEvent>) => {
    setId('boardReview');
    console.log(id);
    props.onClickMenu('boardReview');
  };

  const url = '/mypage';

  const formatDate = `${year}년 ${month}월 ${day}일`;

  useEffect(() => {
    if (profileName !== 'null') {
      isImgNull(false);
    } else {
      isImgNull(true);
    }
  }, [imgNull]);

  const handleImageUpload = async (e: any) => {
    console.log(e.target.files);
    const [file] = e.target.files;
    console.log(file);
    if (file) {
      await axios
        .post(
          '/members/edit/image',
          {
            memberId,
            multipartFile: file,
            content,
          },
          {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          },
        )
        .then(function (response) {
          sessionStorage.setItem('fileName', response.data.fileName);
          isImgNull(false);
          alert('프로필 사진이 변경되었습니다.');
        })
        .catch(function (error) {
          console.log(error);
        });
    }
    location.reload();
  };

  const handleDeleteProfileImage = () => {
    if (imgNull) {
      alert('이미 기본이미지 입니다.');
    } else {
      axios
        .delete('/members/delete/image', { params: { memberId } })
        .then(function (response) {
          sessionStorage.setItem('fileName', response.data.fileName);
          alert('기본 이미지로 변경되었습니다.');
          isImgNull(true);
          location.reload();
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  };

  const handleModify = (e: any) => {
    props.onClickMenu('modify');
  };

  return (
    <div className="flex-[9] h-[150vh] min-h-full z-[1] bg-[#1E1E1E]/5">
      <div className="w-full h-[259px] flex items-center justify-center bg-[#B0B0B0]"></div>
      <div className="flex flex-row w-full h-[100vh]">
        <div className="w-[451px] relative bottom-[140px]">
          <div className="flex flex-col items-center w-[319px] h-[425px] ml-[75px] mr-[57px] bg-white shadow-lg rounded-[5px] z-[2]">
            <div
              onClick={() => imageUploader.current.click()}
              className="w-[122px] h-[122px] mt-[20px]"
            >
              <input
                type="file"
                accept="image/*"
                onChange={handleImageUpload}
                ref={imageUploader}
                className="hidden"
              />
              <img
                ref={uploadImage}
                src={`/image/${sessionStorage.getItem('fileName')}`}
                className='w-[122px] h-[122px] bg-[url("./img/circle_human.png")] rounded-full mb-[70px]'
              />
              <X className="relative bottom-[87px] left-[109px] rotate-45" />
            </div>
            <button
              onClick={handleDeleteProfileImage}
              className="h-[25px] px-[5px] text-[#919191] mt-[90px] border-[1px] border-[#919191] rounded-[11px] text-[15px] leading-[21px] relative bottom-[70px]"
            >
              기본 프로필로 지정
            </button>
            <div className="relative bottom-[60px]">
              <span className="text-[28px] leading-39px text-[#353535]">{savedName}</span>
            </div>
            <div className="flex flex-col relative bottom-[40px]">
              <div className="flex flex-row justify-center items-center">
                <div className="mr-[10px]">
                  <img src={School} alt="" />
                </div>
                <span className="text-[12px] leading-[17px] text-[#8D8D8D]">{savedSchool}</span>
              </div>
              <div className="flex flex-row items-center text-left">
                <div className="mr-[10px]">
                  <img src={Major} alt="" />
                </div>
                <span className="text-[12px] leading-[17px] text-[#8D8D8D]">{savedDepart}</span>
              </div>
            </div>
            <div className="w-[262px] h-[44px]">
              <button
                onClick={handleModify}
                className="w-full h-full bg-[#FF611D] text-white rounded-[11px] text-[15px] leading-[21px] relative bottom-[10px]"
              >
                내 프로필 편집
              </button>
            </div>
            <div>
              <span className="text-[10px] leading-[14px] text-[#8D8D8D]">
                가입일 : {formatDate}
              </span>
            </div>
          </div>
        </div>
        <div className="w-[635px] h-full flex flex-col">
          <div className="mt-[47px] text-[24px] text-[#353535]">미리보기</div>
          {/* tap menus */}
          <div className="flex flex-row gap-x-[40px] mt-[36px] mb-[32px]">
            <button
              className={cn('w-[95px] h-[34px] rounded-[38px] text-[16px] font-semibold', {
                'text-white bg-[#FF611D]': content === 'WRITING',
                'text-[#353535] bg-zinc-100': content !== 'WRITING',
              })}
              onClick={() => {
                setContent('WRITING');
              }}
              value="WRITING"
            >
              작성 글
            </button>
            <button
              className={cn('w-[95px] h-[34px] rounded-[38px] text-[16px] font-semibold', {
                'text-white bg-[#FF611D]': content === 'REPLY',
                'text-[#353535] bg-zinc-100': content !== 'REPLY',
              })}
              onClick={() => {
                setContent('REPLY');
              }}
              value="REPLY"
            >
              작성 댓글
            </button>
            <button
              className={cn('w-[95px] h-[34px] rounded-[38px] text-[16px] font-semibold', {
                'text-white bg-[#FF611D]': content === 'REVIEW',
                'text-[#353535] bg-zinc-100': content !== 'REVIEW',
              })}
              onClick={() => {
                setContent('REVIEW');
              }}
              value="REVIEW"
            >
              작성 리뷰
            </button>
          </div>
          {/* tap contents */}
          <div className="w-[573px] h-[383px] py-1 overflow-hidden rounded-[5px] border border-[#E1E1E1] shadow-lg bg-white">
            <div
              className={cn({
                hidden: content !== 'WRITING',
              })}
            >
              <WritingComponent />
            </div>
            <div
              className={cn({
                hidden: content !== 'REPLY',
              })}
            >
              <ReplyComponent />
            </div>
            <div
              className={cn({
                hidden: content !== 'REVIEW',
              })}
            >
              <ReviewComponent />
            </div>
          </div>
          <button
            className="w-[573px] h-[52px] mt-[12px] text-white border rounded-[5px] border-[#E1E1E1] bg-[#FF611D]"
            onClick={onClickMypageBoardReview}
          >
            자세히 보러가기
          </button>
        </div>
      </div>
    </div>
  );
};

export default MypageHome;
