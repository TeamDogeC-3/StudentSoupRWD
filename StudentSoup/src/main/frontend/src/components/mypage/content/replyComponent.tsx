import _ from 'lodash';
import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import cn from 'clsx';
import axios from 'axios';

const ReplyComponent = () => {
  const state = useLocation<any>();
  const savedMemberId = sessionStorage.getItem('memberId');
  const [set, isSet] = useState<any[]>();
  const [content, setSontent] = useState<string>();
  const [writeDate, setWriteDate] = useState<string>();
  const [likedCount, setLikedCount] = useState<number>();
  const [size, setSize] = useState<number>(6);

  const url = '/mypage/boardReply';
  useEffect(() => {
    axios
      .post(url, {
        memberId: savedMemberId,
      })
      .then(res => {
        isSet(res.data.content);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  return (
    <div className='text-[14px]'>
      <div className='h-[50px] px-[34px] flex items-center overflow-hidden text-[#353535] border-b border-[#D9D9D9]'>
        <span className='w-[60%] '>댓글</span>
        <span className='w-[20%] text-center'>작성일</span>
        <span className='w-[20%] text-center'>좋아요</span>
      </div>
      {set?.map((reply: any) => (
        <>
        <div key={reply.memberId} className='h-[50px] px-[34px] flex flex-row items-center overflow-hidden text-[#353535] border-b border-[#D9D9D9]'>
          <span className='w-[60%] truncate text-[#909090]'>{reply.content}</span>
          <span className='w-[20%] text-center text-[#909090]'>{reply.writeDate}</span>
          <span className='w-[20%] text-center text-[#909090]'>{reply.likedCount}</span>
        </div>
        </>
      ))}
    </div>
  );
};

export default ReplyComponent;
