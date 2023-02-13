import { useState } from 'react';
import MypageNavbar from '../common/mypageNavbar';
import MypageHome from './mypageHome';
import MypageModify from './mypageModify';
import MypageSidebar from './mypageSidebar';
import MypageBoardReivew from './mypageBoardReview';

const Mypage = () => {
  const [menu, setMenu] = useState<string>('home');

  const onClickMenu = (id: string) => {
    setMenu(id);
  };
  return (
    <div>
      <MypageNavbar />
      <div className="flex flex-row">
        <div className="z-[2]">
          <MypageSidebar onClickMenu={onClickMenu} menu={menu} />
        </div>
        <div className="w-full">
          <div>{menu === 'home' && <MypageHome onClickMenu={onClickMenu} />}</div>
          <div>{menu === 'modify' && <MypageModify onClickMenu={onClickMenu} />}</div>
          <div>{menu === 'boardReview' && <MypageBoardReivew />}</div>
        </div>
      </div>
    </div>
  );
};

export default Mypage;
