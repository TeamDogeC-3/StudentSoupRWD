/* eslint-disable @typescript-eslint/restrict-plus-operands */
import axios from 'axios';
import { ReactComponent as BoardImgCloseIcon } from '../../img/boardImgCloseIcon.svg';
import React, { useEffect, useRef, useState } from 'react';
import MypageNavbar from '../common/mypageNavbar';
import { useHistory } from 'react-router-dom';
import Footer from '../common/footer';

const boardWrite = () => {
  const [departmentlist, setDepartMentList] = useState<any>([]);
  const [boardcategoryList, setBoardCategoryList] = useState<any>([]);
  const [boardcategory, setBoardCategory] = useState<string>('');
  const [title, setTitle] = useState<string>('');
  const [departmentId, setDepartMentId] = useState<number>();
  const [content, setContent] = useState<string>('');
  const history = useHistory();
  const [showImages, setShowImages] = useState([]);
  const [imgs, setImgs] = useState<any>();
  const imageUploader = useRef<any>(null);
  const saveMemberId = sessionStorage.getItem('memberId');
  const saveSchoolId = sessionStorage.getItem('schoolId');

  const schoolName = sessionStorage.getItem('schoolName');

  const url = `/board/create/${saveMemberId}/${saveSchoolId}`;
  useEffect(() => {
    axios
      .get(url)
      .then(res => {
        console.log(res.data);
        setBoardCategoryList(res.data.category);
        setDepartMentList(res.data.departments);
      })
      .catch(err => {
        console.error(err);
      });
  }, []);
  const handleBoardWrite = (e: any) => {
    if (title.length < 2 || !title) {
      alert('제목은 2글자 이상 입력해야합니다.');
      return;
    }
    if (!content || content.length < 5) {
      alert('내용은 5글자 이상 입력해야합니다.');
      return;
    }
    if (boardcategory === 'null' || !boardcategory) {
      alert('게시판을 선택해주세요.');
    } else if (!imgs) {
      axios
        .put(
          `/board/${saveMemberId}`,
          {
            title,
            departmentId,
            boardCategory: boardcategory,
            content,
          },
          {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          },
        )
        .then(res => {
          history.goBack();
        })
        .catch(err => {
          console.error(err);
        });
      alert('작성이 완료되었습니다.');
    } else {
      axios
        .put(
          `/board/${saveMemberId}`,
          {
            title,
            departmentId,
            boardCategory: boardcategory,
            content,
            multipartFileList: [...imgs],
          },
          {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          },
        )
        .then(res => {
          history.goBack();
        })
        .catch(err => {
          console.error(err);
        });
      alert('작성이 완료되었습니다.');
    }
  };
  const handleAddImages = (event: any) => {
    const maxFilesizeAll = 4 * 1024 * 1000;
    const imageLists = event.target.files;
    setImgs(event.target.files);

    let imageUrlLists = [...showImages];

    for (let i = 0; i < imageLists.length; i++) {
      if (!/\.(gif|jpg|png|jpeg|bmp|svg)$/i.test(imageLists[i].name)) {
        alert('해당파일은 업로드가 불가능한 파일입니다.');
        return;
      } else if (imageLists[i].size > maxFilesizeAll) {
        alert('업로드 가능한 최대 용량은 4MB입니다.');
        return;
      }

      const currentImageUrl = URL.createObjectURL(imageLists[i]) as never;

      imageUrlLists.push(currentImageUrl);
    }

    if (imageUrlLists.length < 6) {
      imageUrlLists = imageUrlLists.slice(0, 6);
    } else {
      alert('이미지파일은 5개이하만 업로드 할수 있습니다.');
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
  const handleSetTitleValue = (e: any) => {
    setTitle(e.target.value);
  };
  const handleSetContentValue = (e: any) => {
    setContent(e.target.value);
  };
  const handleSetDepartMentId = (e: any) => {
    setDepartMentId(e.target.value);
  };
  const handleSetBoardCategory = (e: any) => {
    setBoardCategory(e.target.value);
  };
  const handleCancelClickButton = () => {
    if (confirm('게시글 작성을 취소하시겠습니까? (작성중이던 글은 삭제됩니다.)')) {
      history.goBack();
    }
  };
  return (
    <>
      <MypageNavbar />
      <div className='min-h-screen'>
      <div className="flex flex-row mt-[103px] justify-center">
        <div className="mr-[155px] w-[296px] h-[60px] font-bold text-[24px] leading-[29px] flex items-center">
          게시글 쓰기
        </div>
        <div className="font-medium mt-[13px]">전체/학과</div>
        <select
          onChange={handleSetDepartMentId}
          className="w-[165px] h-[46px] bg-[#FFFFFF] shadow-[2px_2px_6px_rgba(0,0,0,0.05)] border-[1px] rounded-[5px] text-[#A4A4A4] cursor-pointer focus:text-[#A4A4A4] mr-[20px] ml-[16px]"
        >
          <option
            value={0}
            className="font-normal text-[16px] leading-[22px] flex items-center text-[#A4A4A4]"
          >
            {schoolName}
          </option>
          {departmentlist.map((school: any) => (
            <option key={school.departmentId} value={school.departmentId}>
              {school.departmentName}
            </option>
          ))}
        </select>
        <div className="font-medium mt-[13px]">게시판</div>
        <select
          onChange={handleSetBoardCategory}
          className="ml-[16px] w-[165px] h-[46px] bg-[#FFFFFF] shadow-[2px_2px_6px_rgba(0,0,0,0.05)] border-[1px] rounded-[5px] text-[#A4A4A4] cursor-pointer focus:text-[#A4A4A4]"
        >
          <option value="null">선택</option>
          {boardcategoryList.map((school: any) => (
            <option key={school.categoryKey} value={school.categoryKey}>
              {school.categoryValue}
            </option>
          ))}
        </select>
      </div>
      <div className="flex justify-center">
        <div className="flex flex-col w-[938px] h-auto bg-[#FFFFFF] border-[1px] shadow-[2px_2px_6px_rgba(0,0,0,0.05)]">
          <input
            onChange={e => {
              handleSetTitleValue(e);
            }}
            maxLength={50}
            placeholder="제목(2~50자)"
            type="text"
            className="pl-[14px] w-[936px] h-[40px] resize-none border-[#C4C4C4] border-[1px]"
          ></input>
          <div className="w-[936px] h-[124px] bg-[#F0F0F0] border-x-[1px] border-[#C4C4C4]">
            <div className="ml-[26px] mt-[28px] w-[744px] h-[41px] font-normal text-[14px] leading-[26px] items-center text-[#6D6D6D]">
              <span>글 작성하기 이전, 상단에 있는 카테고리를 클릭하여 </span>
              <span className="underline underline-offset-2">
                주제에 맞는 카테고리를 선택하여 게시글을 작성
              </span>
              <span>해주시길 바랍니다. 건강한 게시판 운영을 위해 </span>
              <span className="underline underline-offset-2">
                불법사진,혹은 상대를 향한 명예훼손 혹은 폭언등에 대한 작성은 불가 및 이용이
                제한됩니다.
              </span>
              <td className="mt-[5px] underline underline-offset-2 font-semibold flex items-center text-[16px] leading-[26px] text-[#CF2424]">
                이용규칙 더보러가기
              </td>
            </div>
          </div>
          <textarea
            onChange={e => {
              handleSetContentValue(e);
            }}
            maxLength={1000}
            placeholder="내용(5~1000자)"
            className="pt-[8px] pl-[14px] h-[374px] border-[1px] border-[#BCBCBC]"
          ></textarea>
          <div className="h-auto bg-[#F0F0F0] rounded-[1px] border-x-[1px] border-b-[1px] border-[#BCBCBC]">
            <div className="flex flex-row justify-center">
              <div className="ml-[26px] mt-[39px] font-semibold text-[16px] leading-[26px] items-center text-[#6D6D6D]">
                사진첨부
              </div>
              <div
                onChange={handleAddImages}
                onClick={onCickImageUpload}
                className="ml-[12px] mt-[36px] w-[79px] h-[29px] border-[1px] border-[#FF611D] bg-[#FFFFFF] rounded-[5px] cursor-pointer"
              >
                <input
                  type="file"
                  multiple
                  accept=".png,.jpg,.gif,.jpeg,.bmp,.svg"
                  ref={imageUploader}
                  className="hidden"
                />
                <div className="mt-[2px] ml-[10px] font-semibold text-[16px] leading-[26px] text-[#FF661D]">
                  사진첨부
                </div>
              </div>
              <div className="mt-[39px] ml-[13px] font-normal text-[16px] leading-[26px] text-[#6D6D6D]">
                {showImages.length}/5
              </div>
              <div className="mt-[41px] mb-[30px] ml-[15px] font-medium text-[16px] leading-[21px] text-[#9F9F9F]">
                사진은 최대 4MB 이하의 JPG, PNG, GIF 파일 5장까지 첨부 가능합니다.
              </div>
            </div>
            <div className="flex flex-row justify-center mr-[20px]">
              {showImages.map((image, id) => (
                <>
                  <div key={id}>
                    <img
                      className="w-[180px] h-[150px] ml-[11px] border-[1px] rounded-[5px] border-[#BCBCBC]"
                      src={image}
                      alt={`${image}-${id}`}
                    />
                    <BoardImgCloseIcon
                      onClick={() => {
                        handleDeleteImage(id);
                      }}
                      className="w-[20px] h-[20px] mb-[15px] ml-[85px] mt-[5.5px] cursor-pointer"
                    />
                  </div>
                </>
              ))}
            </div>
          </div>
        </div>
      </div>
      <div className="flex flex-row mt-[17px] mb-[79px] justify-center">
        <div
          onClick={handleCancelClickButton}
          className="w-[469px] h-[67px] rounded-l-lg border-r-[0.5px] border-y-[1px] border-l-[1px] bg-[#FFFFFF] border-[#BCBCBC] shadow-[2px_2px_6px_rgba(0,0,0,0.05)] text-[#9F9F9F] font-semibold leading-[22px] cursor-pointer"
        >
          <div className="ml-[200px] mt-[22px]">취소하기</div>
        </div>
        <div
          onClick={handleBoardWrite}
          className="w-[469px] h-[67px] rounded-r-lg border-r-[1px] border-y-[1px] bg-[#FFFFFF] border-[#BCBCBC] shadow-[2px_2px_6px_rgba(0,0,0,0.05)] text-[#FF611D] font-semibold leading-[22px] cursor-pointer"
        >
          <div className="ml-[200px] mt-[22px]">작성하기</div>
        </div>
      </div>
      </div>
      <Footer />
    </>
  );
};

export default boardWrite;
