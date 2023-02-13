import axios from 'axios';
import { ReactComponent as ReviewWriteClose } from '../../img/ReviewWriteClose.svg';
import { ReactComponent as BoardImgCloseIcon } from '../../img/boardImgCloseIcon.svg';
import React, { useEffect, useRef, useState } from 'react';
import MypageNavbar from '../common/mypageNavbar';
import { Link, useHistory, useLocation } from 'react-router-dom';

const boardEditWrite = () => {
  const state = useLocation<any>();
  const getBoardId = state.state[4];
  const [titled, setTitled] = useState<string>(state.state[0]);
  const [contented, setContented] = useState<string>(state.state[1]);
  const [selectedCategory, setSelectedCategory] = useState<string>(state.state[2]);
  const [departmentlist, setDepartMentList] = useState<any>([]);
  const [boardcategoryList, setBoardCategoryList] = useState<any>([]);
  const [boardcategory, setBoardCategory] = useState<string>('');
  const [title, setTitle] = useState<string>('');
  const [departmentId, setDepartMentId] = useState<number>();
  const [content, setContent] = useState<string>('');
  const history = useHistory();
  const saveMemberId = sessionStorage.getItem('memberId');
  const saveSchoolId = sessionStorage.getItem('schoolId');
  const schoolName = sessionStorage.getItem('schoolName');
  const [selectDepartmentId, setSelectDepartmentId] = useState<number>(state.state[3]);
  const [category, setCategory] = useState<string>('');
  const url = `/board/create/${saveMemberId}/${saveSchoolId}`;

  useEffect(() => {
    axios
      .get(url)
      .then(res => {
        setBoardCategoryList(res.data.category);
        setDepartMentList(res.data.departments);
      })
      .catch(err => {
        console.error(err);
      });
  }, []);
  const handleBoardWrite = (e: any) => {
    if (titled.length < 2 || !titled) {
      alert('제목을 2글자 이상 입력해야합니다.');
      return;
    }
    if (!contented || contented.length < 5) {
      alert('내용을 5글자 이상 입력해야합니다.');
      return;
    }

    if (!selectedCategory) {
      alert('게시판을 선택해주세요.');
    } else {
      // board/{boardId}/{memberId} 진짜 데이터
      axios
        .patch(
          `/board/${getBoardId}/${saveMemberId}`,
          {
            title: titled,
            departmentId: selectDepartmentId,
            boardCategory: selectedCategory,
            content: contented,
          },
          {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          },
        )
        .then(res => {
          alert('게시글이 수정완료 되었습니다.');
          history.goBack();
        })
        .catch(err => {
          console.error(err);
        });
    }
  };

  useEffect(() => {
    if (selectedCategory === 'FREE') {
      setCategory('자유게시판');
    } else if (selectedCategory === 'CONSULTING') {
      setCategory('상담게시판');
    } else if (selectedCategory === 'TIP') {
      setCategory('팁게시판');
    } else if (selectedCategory === 'NOTICE') {
      setCategory('공지사항');
    } else if (selectedCategory === 'EMPLOYMENT') {
      setCategory('취업게시판');
    }
  });

  const handleSetTitleValue = (e: any) => {
    setTitle(e.target.value);
    setTitled(e.target.value);
  };
  const handleSetContentValue = (e: any) => {
    setContent(e.target.value);
    setContented(e.target.value);
  };
  const handleSetDepartMentId = (e: any) => {
    setSelectDepartmentId(e.target.value);
    setDepartMentId(e.target.value);
  };
  const handleSetBoardCategory = (e: any) => {
    setSelectedCategory(e.target.value);
    setBoardCategory(e.target.value);
  };
  const handleCancelClickButton = () => {
    if (confirm('게시글 수정을 취소하시겠습니까? (작성중이던 글은 삭제됩니다.)')) {
      history.goBack();
    }
  };
  return (
    <>
      <MypageNavbar />
      <div className="flex flex-row mt-[103px] justify-center">
        <div className="mr-[155px] w-[296px] h-[60px] font-bold text-[24px] leading-[29px] flex items-center">
          게시글 수정
        </div>
        <div className="font-medium mt-[13px]">전체/학과</div>
        <select
          value={selectDepartmentId}
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
          value={selectedCategory}
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
            value={titled}
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
            value={contented}
            onChange={e => {
              handleSetContentValue(e);
            }}
            maxLength={1000}
            placeholder="내용(5~1000자)"
            className="pt-[8px] pl-[14px] h-[374px] border-[1px] border-[#BCBCBC]"
          ></textarea>
          <div className="h-auto bg-[#F0F0F0] rounded-[1px] border-x-[1px] border-b-[1px] border-[#BCBCBC]">
            <div className="flex flex-row justify-center">
              <div className="mt-[41px] mb-[30px] ml-[15px] font-medium text-[16px] leading-[21px] text-[#9F9F9F]">
                게시글 수정단계에선 사진 첨부가 불가능합니다.
              </div>
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
          <div className="ml-[200px] mt-[22px]">수정하기</div>
        </div>
      </div>
    </>
  );
};

export default boardEditWrite;
