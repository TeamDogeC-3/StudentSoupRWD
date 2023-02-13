import mainLogo from '../../img/mainLogo.svg';
import Board from '../../img/board.jpg';
import Restaurant from '../../img/restaurant.jpg';
import Circle_human from '../../img/circle_human.png';
import Logout from '../../img/logout.jpg';
import { useHistory } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';

const mypageNavbar = () => {
  const history = useHistory();

  const IMAGE_FILE_ID = sessionStorage.getItem('fileName');
  const mySchool = sessionStorage.getItem('schoolName');

  const logoutUrl = '/members/logout';
  const handleClickLogout = () => {
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
      history.push('/');
    } else {
      sessionStorage.clear();
      history.push('/');
    }
  };

  const handlePushRestaurant = (e: any) => {
    history.push('/restaurant', mySchool);
  };

  const handleImgError = (e: any) => {
    e.target.src = Circle_human;
  };

  const handlePushBoard = (e: any) => {
    history.push('/board/all');
  };

  return (
    <div className="w-full h-[80px] items-center sticky flex justify-between border-b-[1px] border-[#FF611D] z-[2] shadow-lg">
      <img
        src={mainLogo}
        alt=""
        onClick={() => {
          history.push('/');
        }}
        className="w-[106px] h-[30px] ml-[28px] cursor-pointer"
      />
      <div className="flex items-center mr-[32px] m-5">
        <div
          onClick={handlePushBoard}
          className="flex justify-center items-center w-[100px] cursor-pointer"
        >
          <img src={Board} alt="" className="mr-[13.6px] w-[14.4px] h-[16px]" />
          <span className="text-[16px] fw-400 leading-[19px] text-[#353535] mr-[16px]">BOARD</span>
        </div>
        <span className="w-[1px] h-[30.5px] bg-[#B1B1B1] mr-[16px]"></span>
        <div
          onClick={handlePushRestaurant}
          className="flex justify-center items-center w-[150px] cursor-pointer"
        >
          <img src={Restaurant} alt="" className="mr-[10px] w-[16px] h-[16px]" />
          <span className="text-[16px] fw-400 leading-[19px] text-[#353535] mr-[16px]">
            RESTAURANT
          </span>
        </div>
        <span className="w-[1px] h-[30.5px] bg-[#B1B1B1] mr-[19px]"></span>
        <div
          onClick={handleClickLogout}
          className="flex justify-center items-center w-[110px] z-10 cursor-pointer"
        >
          <img src={Logout} alt="" className="mr-[6px] w-[16px] h-[16px]" />
          <span className="text-[16px] fw-400 leading-[19px] text-[#353535] mr-[30px]">LOGOUT</span>
        </div>
        <div className="flex flex-col items-center">
          <img
            src={`/image/${IMAGE_FILE_ID}`}
            onError={handleImgError}
            className='relative top-[34px] bg-cover w-[40px] h-[40px] bg-[url("./img/circle_human.png")] rounded-full mb-[70px] cursor-pointer'
            onClick={() => {
              history.push('/mypage');
            }}
          />
        </div>
      </div>
    </div>
  );
};

export default mypageNavbar;
