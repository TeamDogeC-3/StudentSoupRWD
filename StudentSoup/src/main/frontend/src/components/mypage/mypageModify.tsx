import Upload from '../../img/upload.png';
import cn from 'clsx';
import { useState, ChangeEvent, useEffect } from 'react';
import axios from 'axios';
import Circle_human from '../../img/circle_human.png';

interface propTypes {
  onClickMenu: Function;
}

const MypageModify = (props: propTypes) => {
  const renderUrl = '/members/signUp/3';

  const IMAGE_FILE_ID = String(sessionStorage.getItem('fileName'));

  const [lowAndUpValidated, setLowAndUpValidated] = useState<boolean>(false);
  const [numberValidated, setNumberValidated] = useState<boolean>(false);
  const [lengthValidated, setLengthValidated] = useState<boolean>(false);
  const [matchPassword, setMatchPassword] = useState<boolean>(false);
  const [checkSubmit, setCheckSubmit] = useState<boolean>(false);
  const [validationPwd, setValidationPwd] = useState<boolean>(true);

  const [checkPwd, setCheckPwd] = useState<boolean>(false);
  const [pwd, setPwd] = useState<string>('');

  const [schoolArray, setSchoolArray] = useState<any[]>();
  const [departArray, setDepartArray] = useState<any[]>();
  const [selectedId, setSelectedId] = useState<number>(0);
  const [selectDepartId, setSelectDepartId] = useState<number>();

  const [newPwd, setNewPwd] = useState<string>('');
  const [newConfirmPwd, setNewConfirmPwd] = useState<string>('');

  const memberId = Number(sessionStorage.getItem('memberId'));
  const id = String(sessionStorage.getItem('id'));
  const nickname = String(sessionStorage.getItem('nickname'));
  const email = String(sessionStorage.getItem('email'));

  const [newEmail, setNewEmail] = useState<string>(email);
  const [changeNickname, setChangeNickname] = useState<string>(nickname);
  const [changeCheck, setChangeCheck] = useState<boolean>(true);

  const handleSelect = (e: any) => {
    setSelectedId(e.target.value);
    console.log(e);
  };

  const handleSelectDepart = (e: any) => {
    setSelectDepartId(e.target.value);
    console.log(e);
  };

  useEffect(() => {
    if (newEmail && changeCheck && matchPassword && selectedId && selectDepartId) {
      setCheckSubmit(true);
    } else {
      setCheckSubmit(false);
    }
  });

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
    if (selectedId !== null) {
      axios
        .post(`/members/signUp/3/${selectedId}`, {
          schoolId: selectedId,
        })
        .then(function (response) {
          setDepartArray(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  }, [selectedId]);

  const handleValidation = () => {
    axios
      .post(`members/edit/${memberId}/validation`, {
        memberId,
        id,
        pwd,
      })
      .then(function () {
        setCheckPwd(true);
      })
      .catch(function (error) {
        setValidationPwd(false);
        console.log(error);
      });
  };

  useEffect(() => {
    if (newPwd === newConfirmPwd && newConfirmPwd !== '') {
      setMatchPassword(true);
    } else if (newConfirmPwd == null) {
      setMatchPassword(false);
    } else {
      setMatchPassword(false);
    }
  }, [newPwd, newConfirmPwd]);

  const handleChangeInfo = (e: any) => {
    e.preventDefault();
    if (checkSubmit) {
      axios
        .post(`members/edit/${memberId}`, {
          id,
          memberId,
          pwd: newPwd,
          nickname: changeNickname,
          email: newEmail,
          schoolId: selectedId,
          departmentId: selectDepartId,
        })
        .then(function (response) {
          console.log(response);
          alert('변경되었습니다.');
          sessionStorage.setItem('email', response.data.email);
          sessionStorage.setItem('nickname', response.data.nickname);
          sessionStorage.setItem('id', response.data.id);
          sessionStorage.setItem('departmentId', response.data.departmentId);
          sessionStorage.setItem('departmentName', response.data.departmentName);
          sessionStorage.setItem('fileName', response.data.fileName);
          sessionStorage.setItem('memberId', response.data.memberId);
          sessionStorage.setItem('schoolId', response.data.schoolId);
          sessionStorage.setItem('schoolName', response.data.schoolName);
          sessionStorage.setItem('registrationDate', response.data.registrationDate);
          props.onClickMenu('home');
        })
        .catch(function (error) {
          alert(error.response.data.message);
        });
    }
  };

  const handleNewPwd = (e: ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setNewPwd(value);
    const lowAndUp = /(?=.*[a-z])(?=.*[A-Z]).*/;
    const number = /(?=.*[0-9])/;
    const length = /(?=.{8,20})/;

    if (lowAndUp.test(value)) {
      setLowAndUpValidated(true);
    } else {
      setLowAndUpValidated(false);
    }
    if (number.test(value)) {
      setNumberValidated(true);
    } else {
      setNumberValidated(false);
    }
    if (length.test(value)) {
      setLengthValidated(true);
    } else {
      setLengthValidated(false);
    }
  };

  const handleCheckPwd = (e: ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setNewConfirmPwd(value);
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setPwd(value);
  };

  const handleChangeNickname = (e: ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    console.log(e.target.value.length);
    setChangeNickname(value);
    if (nickname !== value) {
      if (e.target.value.length >= 2 && e.target.value.length <= 12) {
        setChangeCheck(true);
      } else {
        setChangeCheck(false);
      }
    } else {
      setChangeCheck(true);
    }
  };

  const handleChangeEmail = (e: ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setNewEmail(value);
  };

  const handleImgError = (e: any) => {
    e.target.src = Circle_human;
  };

  return (
    <div className="flex flex-[9] w-full h-[150vh] z-[1] bg-zinc-100">
      {checkPwd ? (
        <div className="w-[814px] h-[674px] flex flex-col ml-[161px] mt-[88px]">
          <div className="flex flex-col">
            <div className="text-[24px] leading-[33px] text-[#262626] font-bold">프로필 설정</div>
            <div className="w-full h-[526px] flex flex-row border-[1px] border-[#B1B1B1] bg-white rounded-[10px] mt-[18px]">
              <div className="w-[200px] h-full flex flex-col">
                <img
                  src={`/image/${IMAGE_FILE_ID}`}
                  onError={handleImgError}
                  className='w-[92px] h-[92px] bg-[url("./img/circle_human.png")] bg-cover mt-[44px] ml-[51px] rounded-full'
                />
              </div>
              <div className="w-[1px] h-[420px] border-[#B1B1B1] border-[1px] mt-[44px] mb-[63px]"></div>
              <div className="w-[526px] h-[420px] flex flex-col">
                <div className="flex flex-col ml-[35px] mt-[43px] font-medium">
                  <span className="text-[16px] fw-400 leading-[22px] text-[#6B6B6B]">아이디</span>
                  <input
                    name="ID"
                    disabled
                    value={id}
                    className="w-[246px] h-[38px] text-[16px] fw-400 leading-[21px] mt-[5px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px] text-[#515151]"
                  ></input>
                  <div className="mt-[20px] flex flex-row">
                    <div className="flex flex-col">
                      <span className="text-[16px] fw-400 leading-[22px] text-[#6B6B6B]">
                        닉네임
                      </span>
                      <input
                        name="Nickname"
                        value={changeNickname}
                        onChange={handleChangeNickname}
                        className="w-[246px] h-[38px] text-[16px] fw-400 leading-[21px] mt-[5px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
                      ></input>
                    </div>
                  </div>
                </div>
                <div className="w-full flex flex-row ml-[35px]">
                  <div className="flex flex-col mr-[34px]">
                    <span className="text-[16px] fw-400 leading-[22px] text-[#6B6B6B] mt-[20px]">
                      새 비밀번호
                    </span>
                    <input
                      name="newPW"
                      type="password"
                      value={newPwd}
                      onChange={handleNewPwd}
                      className="w-[246px] h-[38px] text-[16px] fw-400 leading-[21px] mt-[5px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
                    ></input>
                    <div className="flex flex-row mt-[5px] mb-[12px]">
                      <div className="flex flex-row items-center">
                        <span
                          className={cn('text-[12px] fw-400 leading-[16px] mr-[6px]', {
                            ['text-[#FF611D]']: lowAndUpValidated,
                            ['text-[#939393]']: !lowAndUpValidated,
                          })}
                        >
                          대소문자
                        </span>
                        <div
                          className={cn(
                            'w-[11px] h-[7px] bg-cover mr-[10px] relative bottom-[1px]',
                            {
                              ['bg-[url("./img/Vector15.jpg")]']: lowAndUpValidated,
                              ['bg-[url("./img/check_gray.jpg")]']: !lowAndUpValidated,
                            },
                          )}
                        ></div>
                      </div>
                      <div className="flex flex-row items-center">
                        <span
                          className={cn('text-[12px] fw-400 leading-[16px] mr-[6px]', {
                            ['text-[#FF611D]']: numberValidated,
                            ['text-[#939393]']: !numberValidated,
                          })}
                        >
                          숫자
                        </span>
                        <div
                          className={cn(
                            'w-[11px] h-[7px] bg-cover mr-[10px] relative bottom-[1px]',
                            {
                              ['bg-[url("./img/Vector15.jpg")]']: numberValidated,
                              ['bg-[url("./img/check_gray.jpg")]']: !numberValidated,
                            },
                          )}
                        ></div>
                      </div>
                      <div className="flex flex-row items-center">
                        <span
                          className={cn('text-[12px] fw-400 leading-[16px] mr-[6px]', {
                            ['text-[#FF611D]']: lengthValidated,
                            ['text-[#939393]']: !lengthValidated,
                          })}
                        >
                          8~20글자 이내
                        </span>
                        <div
                          className={cn('w-[11px] h-[7px] bg-cover relative bottom-[1px]', {
                            ['bg-[url("./img/Vector15.jpg")]']: lengthValidated,
                            ['bg-[url("./img/check_gray.jpg")]']: !lengthValidated,
                          })}
                        ></div>
                      </div>
                    </div>
                  </div>
                  <div className="flex flex-col">
                    <span className="text-[16px] fw-400 leading-[22px] text-[#6B6B6B] mt-[20px]">
                      비밀번호 확인
                    </span>
                    <input
                      name="PWconfirm"
                      type="password"
                      value={newConfirmPwd}
                      onChange={handleCheckPwd}
                      className="w-[246px] h-[38px] text-[16px] fw-400 leading-[21px] mt-[5px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
                    ></input>
                    <div className="flex items-center mt-[5px]">
                      <span
                        className={cn('text-[12px] fw-400 leading-[16px] mr-[6px]', {
                          ['text-[#939393]']: !matchPassword,
                          ['text-[#FF611D]']: matchPassword,
                        })}
                      >
                        {matchPassword ? '일치합니다.' : '일치하지 않습니다.'}
                      </span>
                    </div>
                  </div>
                </div>
                <div className="flex flex-row">
                  <div className="flex flex-col ml-[35px]">
                    <span className="text-[16px] fw-400 leading-[22px] text-[#6B6B6B]">이메일</span>
                    <input
                      name="Email"
                      value={newEmail}
                      onChange={handleChangeEmail}
                      className="w-[526px] h-[38px] text-[16px] fw-400 leading-[21px] mt-[5px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
                    ></input>
                  </div>
                </div>
                <div className="flex flex-row mt-[20px]">
                  <div className="flex flex-col ml-[35px]">
                    <span className="text-[16px] fw-400 leading-[22px] text-[#6B6B6B]">학교</span>
                    <select
                      onChange={handleSelect}
                      className="w-[246px] h-[38px] relative text-start text-[#939393] text-[16px] fw-400 leading-[21px] mt-[6px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
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
                  <div className="flex flex-col ml-[35px]">
                    <span className="text-[16px] fw-400 leading-[22px] text-[#6B6B6B]">전공</span>
                    <select
                      onChange={handleSelectDepart}
                      className="w-[246px] h-[38px] relative text-start text-[#939393] text-[16px] fw-400 leading-[21px] mt-[6px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
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
            </div>
          </div>
          <div
            className={cn('w-[814px] h-[54px] flex justify-center items-center mt-[34px]', {
              ['bg-[#FF611D]']: checkSubmit,
              ['bg-[#B8B8B8]']: !checkSubmit,
            })}
          >
            <button
              onClick={handleChangeInfo}
              className="w-full h-full text-[16px] fw-400 leading-[22px] text-white font-bold"
            >
              변경 저장하기
            </button>
          </div>
        </div>
      ) : (
        <div className="w-[814px] h-[263px] flex flex-col items-center justify-center ml-[117px] mt-[148px] bg-white">
          <div>
            <span className="text-[16px] leading-[22px] text-[#6B6B6B]">
              내 정보 수정을 하려면 비밀번호 확인이 필요합니다.
            </span>
          </div>
          <div className="mt-[41px]">
            <input
              name="checkPW"
              type="password"
              value={pwd}
              onChange={handleChange}
              className="w-[317px] h-[38px] text-[16px] fw-400 leading-[21px] pl-[23px] border-[1px] border-[#BCBCBC] rounded-[3px]"
            ></input>
            <button
              onClick={handleValidation}
              className="w-[75px] h-[38px] bg-[#FF611D] border-[1px] border-[#FF611D] rounded-[3px] ml-[11px] text-white text-[16px]"
            >
              확인
            </button>
          </div>
          <div className="mt-[41px]">
            <span className="text-[20px] leading-[28px] text-[#B44747] font-semibold absolute top-[415px] left-[750px]">
              {validationPwd ? '' : '비밀번호가 일치하지 않습니다.'}
            </span>
          </div>
        </div>
      )}
    </div>
  );
};

export default MypageModify;

// mypage 내정보수정
