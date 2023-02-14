import React from 'react';
import MainNavbar from '../common/mainNavbar';
import { Desktop, Mobile } from '../../mediaQuery';
import './home.css';
import MainLogo_white from '../../img/mainLogo_white.svg';
import Search_icon from '../../img/search_icon.svg';
const Home = () => {
  return (
    <>
      <MainNavbar />
      <Desktop>
        <div className="hero-text">
          <img src={MainLogo_white} />
          <p>대학생들을 위한</p>
          <h2 className="link-texts">대학 주변 맛집 추천</h2>
          <div className="school_search_bar">
            <img src={Search_icon} />
            <input placeholder="입력"></input>
            <button>검색</button>
          </div>
        </div>
      </Desktop>
      <Mobile>
        <div>모바일</div>
      </Mobile>
    </>
  );
};

export default Home;
