import { useCallback, useEffect, useRef, useState } from 'react';
import { Redirect, useHistory, useLocation } from 'react-router-dom';
import mainLogo from '../../img/mainLogo.svg';
import Modal from '../home/modal';
import Circle_human from '../../img/circle_human.png';

const Navbar = () => {
  const history = useHistory();
  const pathName = useLocation();

  const IMAGE_FILE_ID = String(sessionStorage.getItem('fileName'));

  const [isLogin, setIsLogin] = useState<boolean>(false);

  const [isModal, setModal] = useState<Boolean>(false);
  const onClickToggleModal = useCallback(() => {
    setModal(!isModal);
  }, [isModal]);

  useEffect(() => {
    if (sessionStorage.getItem('email') === null) {
      setIsLogin(false);
    } else {
      setIsLogin(true);
    }
  }, []);

  const handleClick = (e: any) => {
    if (e.target.innerText === '로그인') {
      history.push('/login', { pathName });
    }
  };

  const handleImgError = (e: any) => {
    e.target.src = Circle_human;
  };

  return (
    <div className="w-full h-[88px] items-center sticky flex justify-between bg-gradient-to-b from-[rgba(255,255,255,0.6)] to-[rgba(255,255,255,0)] border-b-[1px]">
      <img
        src={mainLogo}
        alt=""
        className="w-[103px] h-[30px] ml-[28px] cursor-pointer"
        onClick={() => {
          history.push('/');
        }}
      />
      <div className="flex items-center mr-[49px] m-5">
        <div onClick={onClickToggleModal}>
          {isLogin ? (
            <div className="flex sticky">
              <div className="flex flex-col items-center">
                <img
                  src={`/image/${IMAGE_FILE_ID}`}
                  onError={handleImgError}
                  id="로그아웃"
                  className='w-[50px] h-[50px] bg-[url("./img/circle_human.png")] rounded-full relative top-[7px] bg-cover mb-[10px] cursor-pointer'
                />
              </div>
            </div>
          ) : (
            <button
              onClick={handleClick}
              className="w-[93px] h-[40px] bg-[#FF4F14] border-[1.2px] border-[#FF4D14] rounded-[41px] hover:cursor-pointer text-[20px] text-white"
            >
              로그인
            </button>
          )}
        </div>
        {isModal && <Modal setIsLogin={setIsLogin} onClickToggleModal={onClickToggleModal} />}
      </div>
    </div>
  );
};

export default Navbar;
