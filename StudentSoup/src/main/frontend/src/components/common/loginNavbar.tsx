import { useHistory } from 'react-router-dom';
import mainLogo from '../../img/mainLogo.svg';

const LoginNavbar = () => {
  const history = useHistory();

  return (
    <div className="w-full h-[80px] flex justify-between items-center sticky border-b border-[#FF4D14]">
      <img
        src={mainLogo}
        alt=""
        className="w-[106px] h-[30px] ml-[28px] cursor-pointer"
        onClick={() => {
          history.push('/');
        }}
      />
      <div className="mr-[24px] flex items-center">
        <button
          onClick={() => {
            history.push('/login');
          }}
          className="w-[93px] h-[40px] border-[1.2px] border-[#FF4D14] rounded-[41px] cursor-pointer text-[20px] text-[#FF4D14] bg-white"
        >
          로그인
        </button>
      </div>
    </div>
  );
};

export default LoginNavbar;
