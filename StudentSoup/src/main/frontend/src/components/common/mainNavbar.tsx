import React from 'react';
import './mainNavbar.scss';
import { Link } from 'react-router-dom';
import { Desktop, Mobile } from '../../mediaQuery';
import mainLogo from '../../img/mainLogo.svg';

const mainNavbar = () => {
  return (
    <>
      <Desktop>
        <nav className="NavbarItems">
          <Link to="/" className="navbar-logo-links">
            <img src={mainLogo} className="navbar-logo" />
          </Link>
          <ul className="nav-menu">
            <li className="nav-li">
              <Link to="/notice" className="nav-links">
                <i>공지사항</i>
              </Link>
            </li>
            <li className="nav-li">
              <Link to="/help" className="nav-links">
                <i>고객센터</i>
              </Link>
            </li>
            <li className="nav-li">
              <Link to="/login" className="nav-links">
                <i>로그인</i>
              </Link>
            </li>
          </ul>
        </nav>
      </Desktop>
      <Mobile>
        <div>모바일 화면 입니다</div>
      </Mobile>
    </>
  );
};

export default mainNavbar;
