import MypageNavbar from '../common/mypageNavbar';
import BoardContent from './boardContent';
import BoardSidebar from './boardSidebar';

const BoardAll = () => {
  return (
    <>
      <MypageNavbar />
      <div className="flex flex-row">
        <div className="z-[2]">
          <BoardSidebar boardCategory="ALL" />
        </div>
        <div className="w-full bg-[#f4f4f5]">
          <BoardContent boardCategory="ALL" />
        </div>
      </div>
    </>
  );
};

export default BoardAll;
