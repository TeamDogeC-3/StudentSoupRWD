import React, { useEffect, useState } from 'react';
import Reddit from '../../img/Reddit.svg';
import { Link, useHistory, useLocation } from 'react-router-dom';
import cn from 'clsx';
import axios from 'axios';
import _ from 'lodash';

function LoginForm() {
  const url = '/members/login';

  const history = useHistory();
  const state = useLocation<any>();

  const [id, setId] = useState<string>();
  const [pwd, setPwd] = useState<string>();

  const [checked, setChecked] = useState<boolean>(false);

  useEffect(() => {
    const userId = sessionStorage.getItem('id') ?? '';
    if (userId.length > 0) {
      setId(userId);
    }
  }, []);

  useEffect(() => {
    const savedCheck = String(sessionStorage.getItem('saved'));
    if (savedCheck === 'true') {
      setChecked(true);
    } else {
      setChecked(false);
    }
  }, []);

  useEffect(() => {}, [checked]);

  const handleSubmit = (e: any) => {
    e.preventDefault();

    axios
      .post(url, {
        id,
        pwd,
      })
      .then(function (response) {
        if (state.state) {
          history.push(state.state.pathName.pathname);
          sessionStorage.setItem('email', response.data.email);
          sessionStorage.setItem('nickname', response.data.nickname);
          sessionStorage.setItem('id', response.data.id);
          sessionStorage.setItem('departmentId', response.data.departmentId);
          sessionStorage.setItem('departmentName', response.data.departmentName);
          sessionStorage.setItem('fileName', response.data.fileName);
          sessionStorage.setItem('memberId', response.data.memberId);
          sessionStorage.setItem('schoolId', response.data.schoolId);
          sessionStorage.setItem('schoolName', response.data.schoolName);
          sessionStorage.setItem('registrationDate', response.data.registrationDate);
          sessionStorage.setItem('saved', String(checked));
        } else {
          history.push('/');
          sessionStorage.setItem('email', response.data.email);
          sessionStorage.setItem('nickname', response.data.nickname);
          sessionStorage.setItem('id', response.data.id);
          sessionStorage.setItem('departmentId', response.data.departmentId);
          sessionStorage.setItem('departmentName', response.data.departmentName);
          sessionStorage.setItem('fileName', response.data.fileName);
          sessionStorage.setItem('memberId', response.data.memberId);
          sessionStorage.setItem('schoolId', response.data.schoolId);
          sessionStorage.setItem('schoolName', response.data.schoolName);
          sessionStorage.setItem('registrationDate', response.data.registrationDate);
          sessionStorage.setItem('saved', String(checked));
        }
      })
      .catch(function (error) {
        alert(error.response.data.message);
      });
  };

  const onClickSignup = () => {
    history.push('/register/1');
  };

  return (
    <div className="pb-[201px] flex flex-col justify-center items-center">
      {/* icon-version */}
      {/* <div className="mt-[88px] flex justify-center">
        <img src={Reddit} alt="" />
      </div> */}
      {/* font-version */}
      <div className="w-[152px] h-[74px] mt-[123px] mb-[26px] flex justify-center text-[48px] text-[#353535] font-Helvetica font-bold">
        LOGIN
      </div>
      <form onSubmit={handleSubmit}>
        <div className="flex flex-col justify-center items-center">
          <div className="flex flex-col justify-center">
            <input
              id="id"
              name="id"
              placeholder="아이디를 입력해주세요"
              onChange={e => {
                setId(e.target.value);
              }}
              value={id}
              required
              className="w-[558px] h-[60px] mt-[20px] p-[15px] border border-1 border-[#BCBCBC] rounded-[5px] text-[16px] text-[#A0A0A0]"
            />
            <input
              id="password"
              name="password"
              type="password"
              placeholder="비밀번호를 입력해주세요"
              onChange={e => {
                setPwd(e.target.value);
              }}
              value={pwd}
              required
              className="w-[558px] h-[60px] mt-[10px] p-[15px] border border-1 border-[#BCBCBC] rounded-[5px] text-[16px] text-[#A0A0A0]"
            />
          </div>
          <div className="w-[558px] mt-[25px] flex justify-between items-center">
            <div>
              <label htmlFor="saveId" className="flex items-center">
                <input
                  type="checkbox"
                  id="saveId"
                  name="saveId"
                  checked={checked}
                  onChange={e => {
                    setChecked(!checked);
                  }}
                  className={cn(
                    'w-[29px] h-[29px] inline-block border border-[#A0A0A0] rounded-full cursor-pointer appearance-none',
                    'checked:border-[#FF611D] checked:bg-[#FF611D]',
                  )}
                  // checked
                />
                <span className="ml-[13px] text-[#3E3E3E]">아이디 저장</span>
              </label>
            </div>
            <div>
              <a href='/login/findAccount' className="text-[#3E3E3E] cursor-pointer">아이디/비밀번호 찾기</a>
            </div>
          </div>
        </div>
        <div className="mt-[58px] flex flex-col justify-center items-center">
          <button
            type="submit"
            className="w-[558px] h-[60px] rounded-[20px] text-[20px] font-bold text-white bg-[#FF611D]"
          >
            로그인
          </button>
          <button
            type="button"
            onClick={onClickSignup}
            className="w-[558px] h-[60px] mt-[10px] rounded-[20px] text-[20px] font-bold border border-[#FF611D] text-[#FF611D] bg-white"
          >
            회원가입
          </button>
        </div>
      </form>
    </div>
  );
}

export default LoginForm;
