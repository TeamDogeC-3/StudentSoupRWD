import MypageNavbar from '../common/mypageNavbar';
import BoardContent from './boardContent';
import BoardSidebar from './boardSidebar';

const BoardTip = () => {
  return (
    <>
      <MypageNavbar />
      <div className="flex flex-row">
        <div className="z-[2]">
          <BoardSidebar boardCategory={'TIP'} />
        </div>
        <div className="w-full bg-[#f4f4f5]">
          <BoardContent boardCategory={'TIP'} />
        </div>
      </div>
    </>
  );
};

export default BoardTip;
