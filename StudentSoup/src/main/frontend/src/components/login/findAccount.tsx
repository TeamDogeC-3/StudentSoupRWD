import { useState, useRef, useEffect } from 'react';
import axios from 'axios';
import cn from 'clsx';
import RegisterNavbar from '../common/registerNavbar';
import FindId from './findId';
import FindPw from './findPw';

const FindAccount = () => {
  const [tab, setTab] = useState<string>('아이디');

  const onClickTab = (id: string) => {
    setTab(id);
  };
  const onClickGetId = () => {
    setTab('아이디');
    onClickTab('아이디');
  };
  const onClickGetPw = () => {
    setTab('비밀번호');
    onClickTab('비밀번호');
  };

  return (
    <div>
      <RegisterNavbar />
      <div className='w-[496px] mt-[105px] mx-auto flex flex-col'>
        <div className='mb-[25px] text-center text-[40px] text-[#161616]'>아이디/비밀번호 찾기</div>
        <div className="w-[496px] h-[36px] flex text-center">
          <div id='아이디' onClick={onClickGetId}
            className={cn(
              'w-[50%] border-b-2 cursor-pointer', {
                ['text-[#FF6B2B] border-[#FF6B2B]']: tab === '아이디',
                ['text-[#BCBCBC] border-[#BCBCBC]']: tab !== '아이디',
              })}>아이디 찾기</div>
          <div id='비밀번호' onClick={onClickGetPw}
            className={cn(
              'w-[50%] border-b-2 cursor-pointer', {
                ['text-[#FF6B2B] border-[#FF6B2B]']: tab === '비밀번호',
                ['text-[#BCBCBC] border-[#BCBCBC]']: tab !== '비밀번호',
              })}>비밀번호 찾기</div>
        </div>
        <div className='mt-[62px] mb-[36px] text-[26px] break-keep'>아래에 정보를 입력하여 SFOO의 {tab}를 찾아주세요.</div>
        <div>{ tab === '아이디' && <FindId />}</div>
        <div>{ tab === '비밀번호' && <FindPw />}</div>
      </div>
    </div>
  );
}
export default FindAccount;
