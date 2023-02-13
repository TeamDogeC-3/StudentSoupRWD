import RegisterNavbar from '../common/registerNavbar';
import cn from 'clsx';
import axios from 'axios';
import { useEffect, useRef, useState } from 'react';
import { useHistory } from 'react-router-dom';

const Register3 = () => {
  const history = useHistory();

  const userId = sessionStorage.getItem('id');
  const userPwd = sessionStorage.getItem('pwd');

  const emailReg = /[a-z0-9]+@[a-z]+.[a-z]{2,3}/;

  const renderUrl = '/members/signUp/3';

  const [schoolArray, setSchoolArray] = useState<any[]>();
  const [departArray, setDepartArray] = useState<any[]>();
  const [selectDepartId, setSelectDepartId] = useState<number>();
  const [gender, setGender] = useState<string>();
  const [selectedId, setSelectedId] = useState<number>(0);
  const [email, setEmail] = useState<string>('');
  const [nickname, setNickname] = useState<string>('');
  const [checkEmail, setCheckEmail] = useState<boolean>(false);
  const [checkNickname, setCheckNickname] = useState<boolean>(false);
  const [checkGender, setCheckGender] = useState<boolean>(false);
  const [checkSubmit, setCheckSubmit] = useState<boolean>(false);
  const [checkLength, setCheckLength] = useState<boolean>(false);

  const [notSame, setNotSame] = useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string>();

  const handleSelect = (e: any) => {
    setSelectedId(e.target.value);
    console.log(e.target.value);
  };

  const handleSelectDepart = (e: any) => {
    setSelectDepartId(e.target.value);
    console.log(e.target.value);
  };

  const handleClickRadio = (e: any) => {
    setGender(e.target.id);
    setCheckGender(true);
  };

  const fetchData = () => {
    axios
      .get(renderUrl)
      .then(res => {
        setSchoolArray(res.data);
      })
      .catch(err => {
        console.error(err);
      });
  };

  useEffect(() => {
    fetchData();
  }, [selectedId]);

  useEffect(() => {
    if (nickname === '') {
      setCheckNickname(false);
    } else {
      setCheckNickname(true);
    }
  }, [nickname]);

  useEffect(() => {
    setCheckEmail(emailReg.test(email));
  }, [email]);

  useEffect(() => {
    if (checkEmail && checkNickname && checkLength && checkGender && selectedId && selectDepartId) {
      setCheckSubmit(true);
    } else {
      setCheckSubmit(false);
    }
  });

  useEffect(() => {
    if (selectedId !== null) {
      axios
        .post(`/members/signUp/3/${selectedId}`, {
          schoolId: selectedId,
        })
        .then(function (response) {
          setDepartArray(response.data);
          console.log(response);
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  }, [selectedId]);

  const handleRegister = (e: any) => {
    e.preventDefault();
    if (checkSubmit) {
      axios
        .post(renderUrl, {
          id: userId,
          pwd: userPwd,
          nickname,
          email,
          gender,
          schoolId: selectedId,
          departmentId: selectDepartId,
        })
        .then(function (response) {
          console.log(response);
          sessionStorage.clear();
          alert('로그인 페이지로 이동합니다.');
          history.push('/login');
        })
        .catch(function (error) {
          setNotSame(true);
          setErrorMessage(error.response.data.message);
        });
    }
  };

  return (
    <>
      <RegisterNavbar />
      <div className="w-full h-[calc(100vh-88px)] flex flex-col justify-center items-center">
        <div className="flex flex-col text-center w-[530px]">
          <span className="text-[40px] fw-400 leading-[56px] flex justify-center font-bold mb-[47px]">
            신규 회원가입
          </span>
          <div className="flex flex-row  justify-center items-center">
            <div className="flex flex-col items-center w-[88px]">
              <div className="w-[45px] h-[45px] bg-[#FF611D] rounded-full bg-cover relative top-[12px]"></div>
              <div className='w-[20px] h-[15px] bg-[url("./img/check_white.png")] bg-[length:20px_15px] bg-no-repeat relative bottom-[18px]' />
              <span className="text-[#FF611D] text-[16px] fw-400 leading-[21px]">
                이용약관 동의
              </span>
            </div>
            <span className="w-[110px] h-[3px] bg-[#FF611D] relative bottom-[10px]"></span>
            <div className="flex flex-col items-center w-[88px]">
              <div className="w-[45px] h-[45px] bg-[#FF611D] rounded-full bg-cover relative top-[12px]"></div>
              <div className='w-[20px] h-[15px] bg-[url("./img/check_white.png")] bg-[length:20px_15px] bg-no-repeat relative bottom-[18px]' />
              <span className="text-[#FF611D] text-[16px] fw-400 leading-[21px]">회원가입</span>
            </div>
            <span className="w-[110px] h-[3px] bg-[#FF611D] relative bottom-[10px]"></span>
            <div className="flex flex-col items-center w-[88px]">
              <div className="w-[45px] h-[45px] bg-[#FF611D] rounded-full bg-cover relative top-[12px]"></div>
              <div className='w-[20px] h-[15px] bg-[url("./img/check_white.png")] bg-[length:20px_15px] bg-no-repeat relative bottom-[18px]' />
              <span className="text-[#FF611D] text-[16px] fw-400 leading-[21px]">
                개인정보 입력
              </span>
            </div>
          </div>
          <div className="mt-[49px]">
            <span className="flex text-[26px] leading-[37px] text-left text-[#161616] font-semibold">
              SFOO의 서비스를 위한 개인정보를 <br />
              입력해주세요.
            </span>
          </div>
          {/* 성별 */}
          <div className="flex flex-row justify-between items-center">
            <div className="flex text-left mt-[46px] mb-[20px]">
              성별
              <div className="ml-[32px]">
                <input
                  className="form-check-input appearance-none rounded-full h-4 w-4 border border-gray-300 bg-white checked:bg-[#FF611D] checked:border-[#FF611D] focus:outline-none transition duration-200 mt-1 align-top bg-no-repeat bg-center bg-contain float-left mr-[9px] cursor-pointer"
                  type="radio"
                  name="flexRadioDefault"
                  id="WOMAN"
                  checked={gender === 'WOMAN'}
                  onChange={handleClickRadio}
                />
                <label className="form-check-label inline-block text-gray-800 mr-[9px]">여</label>
              </div>
              <div>
                <input
                  className="form-check-input appearance-none rounded-full h-4 w-4 border border-gray-300 bg-white checked:bg-[#FF611D] checked:border-[#FF611D] focus:outline-none transition duration-200 mt-1 align-top bg-no-repeat bg-center bg-contain float-left mr-[9px] cursor-pointer"
                  type="radio"
                  name="flexRadioDefault"
                  id="MAN"
                  checked={gender === 'MAN'}
                  onChange={handleClickRadio}
                />
                <label className="form-check-label inline-block text-gray-800 mr-[9px]">남</label>
              </div>
            </div>
            <div className="flex justify-end">
              {notSame && (
                <span className="text-[12px] fw-400 leading-[16px] text-[#FF611D] pt-[10px]">
                  {errorMessage}
                </span>
              )}
            </div>
          </div>
          {/* 이메일 */}
          <div className="flex flex-row justify-between mt-[5px] mb-[9px]">
            <div className="w-full flex flex-col text-left">
              <span className="text-[16px] fw-400 leading-[22px] text-[#484848]">MAIL</span>
              <input
                name="MAIL"
                id="MAIL"
                placeholder="이메일 입력"
                value={email}
                onChange={e => {
                  setEmail(e.target.value);
                }}
                required
                className="text-[16px] fw-400 leading-[21px] mt-[6px] py-[16px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
              ></input>
            </div>
          </div>
          {/* 학교 */}
          <div className="flex flex-col text-start">
            <div>학교</div>
          </div>
          <div className="flex flex-col text-start overflow-y-auto max-h-[150px]">
            <select
              onChange={handleSelect}
              className="relative text-start text-[#939393] text-[16px] fw-400 leading-[21px] mt-[6px] py-[16px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
            >
              <option value="null">학교 선택</option>
              {schoolArray?.map(school => (
                <option
                  value={school.schoolId}
                  key={school.schoolId}
                  id={school.schoolId}
                  className="flex text-start text-[#939393] text-[16px] fw-400 leading-[21px] mt-[6px] py-[16px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px] cursor-pointer hover:bg-[#DDDDDD]"
                >
                  {school.schoolName}
                </option>
              ))}
            </select>
          </div>
          <div className="flex flex-row justify-between text-start mt-[14px]">
            {/* 닉네임 */}
            <div className="flex flex-col w-[46%]">
              <div>닉네임</div>
              <input
                type="text"
                value={nickname}
                onChange={e => {
                  setNickname(e.target.value);
                  if (e.target.value.length >= 2 && e.target.value.length <= 12) {
                    setCheckLength(true);
                  } else {
                    setCheckLength(false);
                  }
                }}
                placeholder="닉네임 입력"
                className="text-[16px] fw-400 leading-[21px] mt-[6px] py-[16px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
              />
            </div>
            {/* 전공 */}
            <div className="flex flex-col w-[46%]">
              <div className="w-full">
                <div>전공</div>
              </div>
              <div>
                <select
                  onChange={handleSelectDepart}
                  className="relative w-full text-start text-[#939393] text-[16px] fw-400 leading-[21px] mt-[6px] py-[16px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
                >
                  <option value="null">학과 선택</option>
                  {departArray?.map(depart => (
                    <option
                      value={depart.departmentId}
                      key={depart.departmentId}
                      id={depart.departmentId}
                      className="flex text-start text-[#939393] text-[16px] fw-400 leading-[21px] mt-[6px] py-[16px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px] cursor-pointer hover:bg-[#DDDDDD]"
                    >
                      {depart.departmentName}
                    </option>
                  ))}
                </select>
              </div>
            </div>
          </div>
          {/* 완료 */}
          <div
            className={cn('w-[530px] h-[54px] mt-[24px] flex justify-center items-center', {
              ['bg-[#FF611D]']: checkSubmit,
              ['bg-[#B8B8B8]']: !checkSubmit,
            })}
          >
            <button
              onClick={handleRegister}
              className="w-full h-full text-[16px] fw-400 leading-[22px] text-white"
            >
              완료
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default Register3;
