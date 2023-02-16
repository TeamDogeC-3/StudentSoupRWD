import React from 'react';
import { Link } from 'react-router-dom';
import { Desktop, Mobile } from '../../mediaQuery';
import MainNavbar from '../common/mainNavbar';
import './login.scss';

const Login = () => {
  return (
    <>
      <div className="background">
        <MainNavbar />
        <div className="main">
          <h2>로그인</h2>
          <form>
            <input
              type="text"
              className="id"
              placeholder="아이디 또는 이메일을 입력해주세요"
            ></input>
            <input
              type="password"
              className="password"
              placeholder="비밀번호를 입력해주세요"
            ></input>
            <div className="login-keep-wrap">
              <div className="remember-wrap">
                <div className="unchecked-remember-id" />
                <span>아이디 저장</span>
              </div>
              <Link to="/">아이디/비밀번호 찾기</Link>
            </div>
            <button className="login-button">로그인</button>
            <Link to="/" className="signup-link">
              <button className="signup-button">회원가입</button>
            </Link>
          </form>
        </div>
      </div>
    </>
  );
};

export default Login;
