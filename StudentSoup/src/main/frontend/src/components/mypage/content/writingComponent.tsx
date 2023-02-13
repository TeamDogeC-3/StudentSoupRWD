import _ from 'lodash';
import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import cn from 'clsx';
import axios from 'axios';

// interface WritingList {
//   title: string;
//   viewCount: number;
//   likedCount: number;
// }

const WritingComponent = (data: any) => {
  const state = useLocation<any>();
  const savedMemberId = sessionStorage.getItem('memberId');
  const [set, isSet] = useState<any>();
  const [viewCount, setViewCount] = useState<number>();
  const [likedCount, setLikedCount] = useState<number>();
  const [title, setTitle] = useState<string[]>([]);
  const [size, setSize] = useState<number>(6);

  // const [writingComponent, setWritingComponent] = useState<WritingList>([]);

  const url = '/mypage/board';
  useEffect(() => {
    axios
      .post(url, {
        memberId: savedMemberId,
      })
      .then(res => {
        isSet(res.data.content);
        // setViewCount(res.data.data.viewCount)
        // setLikedCount(res.data.data.likedCount);
      })
      .catch(res => {
        // console.log(err);
        console.log(res.data);
      });
  }, []);

  return (
    <div className="text-[14px]">
      <div className='h-[50px] px-[34px] flex items-center text-[#353535] border-b border-[#D9D9D9]'>
        <span className='w-[60%] '>제목</span>
        <span className='w-[20%] text-center'>조회수</span>
        <span className='w-[20%] text-center'>좋아요</span>
      </div>
      {set?.map((writing: any) => (
        <>
        <div key={writing.memberId} className='h-[50px] px-[34px] flex flex-row items-center text-[#353535] border-b border-[#D9D9D9]'>
          <span className='w-[60%] truncate text-[#909090]'>{writing.title}</span>
          <span className='w-[20%] text-center text-[#909090]'>{writing.viewCount}</span>
          <span className='w-[20%] text-center text-[#909090]'>{writing.likedCount}</span>
        </div>
        </>
      ))}
    </div>
  );
};

export default WritingComponent;
