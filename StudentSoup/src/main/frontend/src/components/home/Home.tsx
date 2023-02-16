import React from 'react';
import MainNavbar from '../common/mainNavbar';
import './home.scss';
import MainLogo_white from '../../img/mainLogo_white.svg';
import Search_icon from '../../img/search_icon.svg';
const Home = () => {
  return (
    <>
      <MainNavbar />
      <div className="hero-text">
        <img className="sfoo-image" src={MainLogo_white} />
        <p>대학생들을 위한</p>
        <h2 className="link-texts">대학 주변 맛집 추천</h2>
        <div className="school_search_bar">
          <img src={Search_icon} />
          <input placeholder="지역 학교 명을 입력하세요."></input>
          <button>검색</button>
        </div>
      </div>
    </>
  );
};

export default Home;
