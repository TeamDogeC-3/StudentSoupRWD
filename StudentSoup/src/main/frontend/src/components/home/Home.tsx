import React from 'react';
import { Desktop, Mobile } from '../../mediaQuery';

const Home = () => {
  return (
    <>
      <Desktop>
        <div>데스크탑 화면 입니다</div>
      </Desktop>
      <Mobile>
        <div>모바일 화면 입니다</div>
      </Mobile>
    </>
  );
};

export default Home;
