import { ChangeEvent, useEffect, useState } from 'react';
import RegisterNavbar from '../common/registerNavbar';
import cn from 'clsx';
import { useHistory } from 'react-router-dom';
import axios from 'axios';

const Register2 = () => {
  const history = useHistory();

  const url = '/members/signUp/2';

  const [lowAndUpValidated, setLowAndUpValidated] = useState<boolean>(false);
  const [numberValidated, setNumberValidated] = useState<boolean>(false);
  const [lengthValidated, setLengthValidated] = useState<boolean>(false);
  const [matchPassword, setMatchPassword] = useState<boolean>(false);
  const [checkButton, setCheckButton] = useState<boolean>(false);
  const [notSame, setNotSame] = useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string>();

  const [password, setPassword] = useState<string>('');
  const [id, setId] = useState<string>('');
  const [confirmPassword, setConfirmPassword] = useState<string>('');

  useEffect(() => {
    if (password === confirmPassword && confirmPassword !== '') {
      setMatchPassword(true);
    } else if (confirmPassword == null) {
      setMatchPassword(false);
    } else {
      setMatchPassword(false);
    }
  }, [password, confirmPassword]);

  useEffect(() => {
    if (lowAndUpValidated && numberValidated && lengthValidated && matchPassword) {
      setCheckButton(true);
    } else {
      setCheckButton(false);
    }
  }, [lowAndUpValidated, numberValidated, lengthValidated, matchPassword]);

  useEffect(() => {
    sessionStorage.clear();
  }, []);

  const onClickRegister3 = (e: any) => {
    e.preventDefault();
    if (checkButton) {
      axios
        .post(url, {
          id,
          pwd: password,
        })
        .then(function (response) {
          console.log(response.data);
          sessionStorage.setItem('id', response.data.id);
          sessionStorage.setItem('pwd', response.data.pwd);
          history.push('/register/3');
        })
        .catch(function (error) {
          setErrorMessage(error.response.data.message);
          setNotSame(true);
        });
    }
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const newValue = e.target.value;
    setPassword(newValue);
    const lowAndUp = /(?=.*[a-z])(?=.*[A-Z]).*/;
    const number = /(?=.*[0-9])/;
    const length = /(?=.{8,20})/;

    if (lowAndUp.test(newValue)) {
      setLowAndUpValidated(true);
    } else {
      setLowAndUpValidated(false);
    }
    if (number.test(newValue)) {
      setNumberValidated(true);
    } else {
      setNumberValidated(false);
    }
    if (length.test(newValue)) {
      setLengthValidated(true);
    } else {
      setLengthValidated(false);
    }
  };

  const checkPW = (e: ChangeEvent<HTMLInputElement>) => {
    const newValue = e.target.value;
    setConfirmPassword(newValue);
  };
  return (
    <div>
      <RegisterNavbar />
      <div className="w-full h-[calc(100vh-88px)] flex flex-col justify-center items-center">
        <div className="flex flex-col text-center w-[496px]">
          <span className="text-[40px] fw-400 leading-[56px] flex justify-center font-bold">
            신규 회원가입
          </span>
          <div className="flex flex-row  justify-center items-center mt-[47px]">
            <div className="flex flex-col items-center">
              <div className='w-[45px] h-[45px] bg-[url("./img/circle3.jpg")] bg-cover relative top-[12px]'></div>
              <div className='w-[20px] h-[15px] bg-[url("./img/Vector.jpg")] bg-cover relative bottom-[18px] right-[1px] mb-[10px]'></div>
              <span className="text-[#FF611D] text-[16px] fw-400 leading-[21px]">
                이용약관 동의
              </span>
            </div>
            <span className="w-[110px] h-[3px] bg-[#FF611D] relative bottom-[10px]"></span>
            <div className="flex flex-col items-center mx-[20px]">
              <div className='w-[45px] h-[45px] bg-[url("./img/circle1.jpg")] bg-cover relative top-[12px]'></div>
              <div className='w-[15px] h-[23px] bg-[url("./img/check2.jpg")] bg-cover relative bottom-[22px]'></div>
              <span className="text-[#FF611D] text-[16px] fw-400 leading-[21px]">회원가입</span>
            </div>
            <span className="w-[110px] h-[3px] bg-[#D9D9D9] relative bottom-[10px]"></span>
            <div className="flex flex-col items-center">
              <div className='w-[45px] h-[45px] bg-[url("./img/circle2.jpg")] bg-cover relative top-[12px]'></div>
              <div className='w-[15px] h-[23px] bg-[url("./img/number3.jpg")] bg-cover relative bottom-[22px]'></div>
              <span className="text-[#939393] text-[16px] fw-400 leading-[21px]">
                개인정보 입력
              </span>
            </div>
          </div>
          <div className="mt-[49px]">
            <span className="flex text-[26px] leading-[37px] text-left text-[#161616] font-semibold">
              로그인에 사용할 아이디/비밀번호를
              <br />
              입력해주세요.
            </span>
          </div>
          <div className="flex flex-col text-left mt-[21px]">
            <span className="text-[16px] fw-400 leading-[22px] text-[#484848]">ID</span>
            <input
              name="ID"
              value={id}
              onChange={e => {
                setId(e.target.value);
              }}
              required
              placeholder="아이디 입력"
              className="text-[16px] fw-400 leading-[21px] mt-[6px] py-[16px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
            ></input>
            {notSame && (
              <span className="text-[12px] fw-400 leading-[16px] text-[#FF611D] mt-[8px]">
                {errorMessage}
              </span>
            )}
          </div>
          <div className="flex flex-row mt-[12px]">
            <div className="w-full flex flex-col text-left">
              <span className="text-[16px] fw-400 leading-[22px] text-[#484848]">PW</span>
              <input
                name="PW"
                id="PW"
                value={password}
                type="password"
                onChange={handleChange}
                placeholder="비밀번호 입력"
                className="text-[16px] fw-400 leading-[21px] mt-[6px] py-[16px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
              ></input>
              <div className="flex flex-row mt-[8px] mb-[12px]">
                <div className="flex flex-row items-center">
                  <span
                    className={cn('text-[12px] fw-400 leading-[16px] mr-[6px]', {
                      'text-[#FF611D]': lowAndUpValidated,
                      'text-[#939393]': !lowAndUpValidated,
                    })}
                  >
                    대소문자
                  </span>
                  <div
                    className={cn('w-[11px] h-[7px] bg-cover mr-[10px] relative bottom-[1px]', {
                      'bg-[url("./img/Vector15.jpg")]': lowAndUpValidated,
                      'bg-[url("./img/check_gray.jpg")]': !lowAndUpValidated,
                    })}
                  ></div>
                </div>
                <div className="flex flex-row items-center">
                  <span
                    className={cn('text-[12px] fw-400 leading-[16px] mr-[6px]', {
                      'text-[#FF611D]': numberValidated,
                      'text-[#939393]': !numberValidated,
                    })}
                  >
                    숫자
                  </span>
                  <div
                    className={cn('w-[11px] h-[7px] bg-cover mr-[10px] relative bottom-[1px]', {
                      'bg-[url("./img/Vector15.jpg")]': numberValidated,
                      'bg-[url("./img/check_gray.jpg")]': !numberValidated,
                    })}
                  ></div>
                </div>
                <div className="flex flex-row items-center">
                  <span
                    className={cn('text-[12px] fw-400 leading-[16px] mr-[6px]', {
                      'text-[#FF611D]': lengthValidated,
                      'text-[#939393]': !lengthValidated,
                    })}
                  >
                    8~20글자 이내
                  </span>
                  <div
                    className={cn('w-[11px] h-[7px] bg-cover relative bottom-[1px]', {
                      'bg-[url("./img/Vector15.jpg")]': lengthValidated,
                      'bg-[url("./img/check_gray.jpg")]': !lengthValidated,
                    })}
                  ></div>
                </div>
              </div>
            </div>
          </div>
          <div className="w-full flex flex-col text-left mb-[24px]">
            <input
              name="PWconfirm"
              id="PWconfirm"
              type="password"
              value={confirmPassword}
              onChange={checkPW}
              placeholder="비밀번호 확인"
              className="text-[16px] fw-400 leading-[21px] mt-[6px] py-[16px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
            ></input>
            <div className="flex flex-row mt-[8px] mb-[12px]">
              <div className="mr-[18px] flex flex-row items-center">
                <span
                  className={cn('text-[12px] fw-400 leading-[16px] mr-[6px]', {
                    ['text-[#FF611D]']: matchPassword,
                    ['text-[#939393]']: !matchPassword,
                  })}
                >
                  비밀번호 일치
                </span>
                <div
                  className={cn('w-[11px] h-[7px] bg-cover mr-[11px] relative bottom-[1px]', {
                    ['bg-[url("./img/Vector15.jpg")]']: matchPassword,
                    ['bg-[url("./img/check_gray.jpg")]']: !matchPassword,
                  })}
                ></div>
              </div>
            </div>
          </div>
          <div
            className={cn('w-[496px] h-[54px] flex justify-center items-center', {
              'bg-[#FF611D]': checkButton,
              'bg-[#B8B8B8]': !checkButton,
            })}
            onClick={onClickRegister3}
          >
            <button className="w-full h-full text-[16px] fw-400 leading-[22px] text-white font-semibold">
              다음
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register2;
