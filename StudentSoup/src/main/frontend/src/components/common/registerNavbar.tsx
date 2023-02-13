import { useHistory } from 'react-router-dom';
import mainLogo from '../../img/mainLogo.svg';

const RegisterNavbar = () => {
  const history = useHistory();

  return (
    <div className="w-full h-[88px] items-center sticky flex justify-between border-b-[1px] border-[#FF611D]">
      <img src={mainLogo} alt="" className="w-[103px] h-[30px] ml-[28px] cursor-pointer"
        onClick={() => {
          history.push('/');
        }} />
    </div>
  );
};

export default RegisterNavbar;
