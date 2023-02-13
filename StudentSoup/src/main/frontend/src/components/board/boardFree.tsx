import MypageNavbar from '../common/mypageNavbar';
import BoardContent from './boardContent';
import BoardSidebar from './boardSidebar';

const BoardFree = () => {
  return (
    <>
      <MypageNavbar />
      <div className="flex flex-row">
        <div className="z-[2]">
          <BoardSidebar boardCategory={'FREE'} />
        </div>
        <div className="w-full bg-[#f4f4f5]">
          <BoardContent boardCategory={'FREE'} />
        </div>
      </div>
    </>
  );
};

export default BoardFree;
