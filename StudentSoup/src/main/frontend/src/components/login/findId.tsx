import React, { useEffect, useState } from 'react';
import cn from 'clsx';
import axios from 'axios';
import _ from 'lodash';

const FindId = () => {
  const url = '/members/find/id';
  const [email, setEmail] = useState<string>('');

  const [isSubmit, setIsSubmit] = useState<boolean>(false);

  const handleSubmit = (e: React.MouseEvent) => {
    e.preventDefault();
    axios
      .post(url, {
        email,
      })
      .then(function (response) {
        setIsSubmit(true);
      })
      .catch(function (error) {
        alert(error.response.data.message);
        setIsSubmit(false);
      });
  };

  return (
    <div className="w-full">
      <div className="w-full px-[22px] py-[18px] flex flex-col  bg-[#F6F6F6]">
        <p className="mb-[12px] text-[#484848]">아이디 찾기</p>
        <ul className="ml-[10px] space-y-[12px] list-disc list-outside break-keep marker:text-[#939393] text-[#939393]">
          <li>회원정보에 등록된 정보로 아이디를 찾습니다.</li>
          <li>
            회원가입시 등록하신 이메일 주소를 입력해주세요.
            <br />
            회원정보와 다를 경우 조회가 되지 않습니다.
          </li>
        </ul>
      </div>
      <form>
        <div className="mt-[18px] flex space-x-[25px]">
          <input
            id="email"
            name="email"
            placeholder="이메일 입력"
            onChange={e => {
              setEmail(e.target.value);
            }}
            value={email}
            required
            className="w-full h-[54px] px-[23px] py-[16px] text-[#939393] border border-[#BCBCBC] outline-none"
          />
        </div>
        {isSubmit ? (
          <div>
            <span className="text-[12px] fw-400 leading-[16px] text-[#FF611D] mt-[8px]">
              아이디를 메일로 전송하였습니다. 이메일을 확인해주세요.
            </span>
            <button
              type="submit"
              disabled
              className={'w-full h-[54px] bg-[#B8B8B8] mt-[24px] mb-[220px] text-white'}
            >
              인증하기
            </button>
          </div>
        ) : (
          <button
            type="submit"
            onClick={handleSubmit}
            className={cn('w-full h-[54px] mt-[24px] mb-[220px] text-white', {
              ['bg-[#B8B8B8]']: email === '',
              ['bg-[#FF6B2B]']: email !== '',
            })}
          >
            인증하기
          </button>
        )}
      </form>
    </div>
  );
};
export default FindId;
