import axios from 'axios';
import { useEffect } from 'react';
import { useHistory } from 'react-router-dom';

const Modal = (props: any) => {
  const history = useHistory();

  const logoutUrl = '/members/logout';

  const handleClick = (e: any) => {
    e.stopPropagation();
    if (e.target.innerHTML === '로그아웃') {
      axios
        .post(logoutUrl)
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
      if (sessionStorage.getItem('saved') === String(true)) {
        sessionStorage.removeItem('email');
        sessionStorage.removeItem('nickname');
        sessionStorage.removeItem('departmentId');
        sessionStorage.removeItem('departmentName');
        sessionStorage.removeItem('fileName');
        sessionStorage.removeItem('memberId');
        sessionStorage.removeItem('schoolId');
        sessionStorage.removeItem('schoolName');
        sessionStorage.removeItem('registrationDate');
        props.setIsLogin(false);
        alert('로그아웃되었습니다.');
        props.onClickToggleModal();
      } else {
        sessionStorage.clear();
        props.setIsLogin(false);
        alert('로그아웃되었습니다.');
        props.onClickToggleModal();
      }
    }
  };

  const handleClickMypage = (e: any) => {
    history.push('/mypage');
  };

  const handleClickBoard = (e: any) => {
    history.push('/board/all');
  };

  return (
    <div
      onClick={() => props.onClickToggleModal()}
      className="w-full h-full bg-[rgba(0,0,0,0.1)] flex fixed top-0 right-0 justify-center items-center z-[55]"
    >
      <div className="w-[290px] h-[200.45px] absolute top-0 right-0 mr-[24px] mt-[108.89px] border-[1px] border-white bg-white rounded-[5px] z-[56]">
        <div className="topModal">
          <ul className="text-[14px] fw-400 leading-[18px]">
            <li
              onClick={handleClickMypage}
              className="flex items-center justify-between cursor-pointer hover:underline underline-offset-[1px]"
            >
              <div className="mt-[21px] mr-[19px] mb-[2px] ml-[19px]">마이페이지</div>
              <p className="mt-[21px] mr-[19px] mb-[2px] ml-[19px]">&gt;</p>
            </li>
            <li
              onClick={handleClickBoard}
              className="flex items-center justify-between cursor-pointer hover:underline underline-offset-[1px]"
            >
              <div className="mt-[21px] mr-[19px] mb-[2px] ml-[19px]">게시판</div>
              <p className="mt-[21px] mr-[19px] mb-[2px] ml-[19px]">&gt;</p>
            </li>
          </ul>
        </div>
        <div className="border-t-[1px] h-[100px] border-[#515151] mt-[25px]">
          <div className="flex h-full items-center justify-center">
            <div className="flex flex-col items-center justify-center">
              <button
                onClick={handleClick}
                className="w-[252px] h-[32px] bg-[#FF611D] rounded-[5px] text-white mb-[9px] border-none fw-400 leading-[18px] text-[14px] cursor-pointer"
              >
                로그아웃
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Modal;
