import Menu from '../../img/menu.jpg';
import HomeFill from '../../img/home_fill.png';
import Home from '../../img/home.png';
import Modify from '../../img/modify.png';
import ModifyFill from '../../img/modify_fill.png';
import CheckRight from '../../img/check_right.png';
import { useHistory } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { ReactComponent as Document } from '../../img/document.svg';
import { ReactComponent as Document_fill } from '../../img/document_fill.svg';
import cn from 'clsx';

interface propTypes {
  onClickMenu: Function;
  menu: string;
}
const MypageSidebar = (props: propTypes) => {
  const [id, setId] = useState<string>('home');

  useEffect(() => {
    setId(props.menu);
  });

  const onClickMypageHome = () => {
    setId('home');
    props.onClickMenu('home');
  };
  const onClickMypageModify = () => {
    setId('modify');
    props.onClickMenu('modify');
  };
  const onClickMypageBoardReview = () => {
    setId('boardReview');
    props.onClickMenu('boardReview');
  };

  return (
    <div className="flex-[3] w-[354px] h-[150vh] items-center justify-center flex-col shadow-2xl z-[2]">
      <ul className="text-[20px] leading-[28px]">
        <li className="mb-[42px]">
          <div className="flex items-center w-full h-[54px] font-bold pt-[35px]">
            <img src={Menu} alt="" className="w-[15.5px] h-[11.64px] ml-[40px] mr-[13.47px]" />
            <span className="w-full font-Helvetica">Menu</span>
          </div>
        </li>
        <li className="mb-[20px]">
          <div
            className={cn('flex items-center w-full h-[54px] mt-[65px] cursor-pointer', {
              ['bg-[#F5F5F5]']: id === 'home',
              ['']: id !== 'home',
            })}
            onClick={onClickMypageHome}
          >
            <img
              src={id === 'home' ? HomeFill : Home}
              alt=""
              className="w-[16px] h-[15px] ml-[40px] mr-[13px]"
            />
            <span
              className={cn('w-full font-medium', {
                ['text-[#FF611D]']: id === 'home',
                ['']: id !== 'home',
              })}
            >
              홈
            </span>
            <div
              className={cn('w-[4px] h-[54px]', {
                ['bg-[#FF611D]']: id === 'home',
                ['']: id !== 'home',
              })}
            ></div>
          </div>
        </li>
        <li className="mb-[20px]">
          <div
            className={cn('flex items-center w-full h-[54px] mt-[26px] cursor-pointer', {
              ['bg-[#F5F5F5]']: id === 'modify',
              ['']: id !== 'modify',
            })}
            onClick={onClickMypageModify}
          >
            <img
              src={id === 'modify' ? ModifyFill : Modify}
              alt=""
              className="w-[15.5px] h-[15.5px] ml-[40px] mr-[13px]"
            />
            <span
              className={cn('w-full font-medium', {
                ['text-[#FF611D]']: id === 'modify',
                ['']: id !== 'modify',
              })}
            >
              내 정보 수정
            </span>
            <img
              src={id === 'modify' ? '' : CheckRight}
              alt=""
              className="w-[7px] h-[10px] mr-[26px]"
            />
            <div
              className={cn('w-[4px] h-[54px]', {
                ['bg-[#FF611D]']: id === 'modify',
                ['']: id !== 'modify',
              })}
            ></div>
          </div>
        </li>
        <li className="mb-[20px]">
          <div
            onClick={onClickMypageBoardReview}
            className={cn('flex items-center w-full h-[54px] mt-[26px] cursor-pointer', {
              ['bg-[#F5F5F5]']: id === 'boardReview',
              ['']: id !== 'boardReview',
            })}
          >
            <div>
              {id === 'boardReview' ? (
                <Document_fill className="ml-[40px] mr-[13px]" />
              ) : (
                <Document className="ml-[40px] mr-[13px]" />
              )}
            </div>
            <span
              className={cn('w-full font-medium', {
                ['text-[#FF611D]']: id === 'boardReview',
                ['']: id !== 'boardReview',
              })}
            >
              나의 게시판/리뷰
            </span>
            <img
              src={id === 'boardReview' ? '' : CheckRight}
              alt=""
              className="w-[7px] h-[10px] mr-[26px]"
            />
            <div
              className={cn('w-[4px] h-[54px]', {
                ['bg-[#FF611D]']: id === 'boardReview',
                ['']: id !== 'boardReview',
              })}
            ></div>
          </div>
        </li>
      </ul>
    </div>
  );
};

export default MypageSidebar;
