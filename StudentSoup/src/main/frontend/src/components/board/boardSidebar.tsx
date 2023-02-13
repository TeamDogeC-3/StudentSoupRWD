import Menu from '../../img/menu.jpg';
import CheckRight from '../../img/check_right.png';

import cn from 'clsx';
import _ from 'lodash';
import { categoryList } from './data/_constant';
import { useHistory } from 'react-router-dom';

interface propTypes {
  boardCategory: string;
}

const BoardSidebar = (props: propTypes) => {
  const { boardCategory } = props;
  const history = useHistory();

  return (
    <div className="flex-[3] w-[354px] h-[120vh] items-center justify-center flex-col shadow-2xl z-[2]">
      <ul className="text-[20px] leading-[28px]">
        <li className="mb-[65px]">
          <div className="flex items-center w-full h-[54px] font-bold pt-[35px]">
            <img src={Menu} alt="" className="w-[15.5px] h-[11.64px] ml-[40px] mr-[13.47px]" />
            <span className="w-full">Menu</span>
          </div>
        </li>

        {_.map(categoryList, (item, index) => {
          const { label, imgPath, activeImgPath, value, link } = item;
          const isActive = boardCategory === value;

          return (
            <li className="mb-[20px]" key={index}>
              <div
                className={cn('flex items-center w-full h-[54px] mt-[26px] cursor-pointer', {
                  ['bg-[#F5F5F5]']: isActive,
                  ['']: !isActive,
                })}
                onClick={() => {
                  history.push(`/board${link}`);
                }}
              >
                <img
                  src={isActive ? activeImgPath : imgPath}
                  alt="all"
                  className="w-[16px] h-[15px] ml-[40px] mr-[13px]"
                />
                <span
                  className={cn('w-full font-medium', {
                    ['text-[#FF611D]']: isActive,
                    ['']: !isActive,
                  })}
                >
                  {label}
                </span>
                <img
                  src={isActive ? '' : CheckRight}
                  alt=""
                  className="w-[7px] h-[10px] mr-[26px]"
                />
                <div
                  className={cn('w-[4px] h-[54px]', {
                    ['bg-[#FF611D]']: isActive,
                    ['']: !isActive,
                  })}
                ></div>
              </div>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default BoardSidebar;
