import React from 'react';
import { Desktop, Mobile, Tablet } from '../../mediaQuery';

const Home = () => {
  return (
    <>
      <Desktop>
        <p>데스크탑 화면</p>
      </Desktop>
      <Tablet>
        <p>테블릿 화면</p>
      </Tablet>
      <Mobile>
        <p>모바일화면</p>
      </Mobile>
    </>
  );
};

export default Home;
