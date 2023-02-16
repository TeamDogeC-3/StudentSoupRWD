import './mainNavbar.scss';
import { Link } from 'react-router-dom';
import { DesktopHeader, MobileHeader } from '../../mediaQuery';
import mainLogo from '../../img/mainLogo.svg';
import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleRight } from '@fortawesome/free-solid-svg-icons';

const mainNavbar = () => {
  const [click, isClick] = useState<boolean>(false);

  const handleClickMenu = (e: any) => {
    isClick(!click);
    console.log(click);
  };
  return (
    <>
      <DesktopHeader>
        <nav className="navbar-items">
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
      </DesktopHeader>
      <MobileHeader>
        <nav className="mobile-navbar-items">
          <Link to="/" className="navbar-logo-links">
            <img src={mainLogo} className="navbar-logo" />
          </Link>
          <div className="mobile-nav-menu" onClick={handleClickMenu}>
            <i className="mobile-nav-menu-icon">메뉴</i>
          </div>
          <ul className={click ? 'mobile-nav-menu-list active' : 'mobile-nav-menu-list'}>
            <li>
              <div className="mobile-nav-list">
                <i className="mobile-nav-listItme">공지사항</i>
                <FontAwesomeIcon icon={faAngleRight} className="mobile-nav-icons" />
              </div>
            </li>
            <li className="mobile-nav-list">
              <i className="mobile-nav-listItme">고객센터</i>
              <FontAwesomeIcon icon={faAngleRight} className="mobile-nav-icons" />
            </li>
            <li className="mobile-nav-list">
              <i className="mobile-nav-listItme">로그인</i>
              <FontAwesomeIcon icon={faAngleRight} className="mobile-nav-icons" />
            </li>
          </ul>
        </nav>
      </MobileHeader>
    </>
  );
};

export default mainNavbar;
