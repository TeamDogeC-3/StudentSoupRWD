import MypageNavbar from '../common/mypageNavbar';
import BoardContent from './boardContent';
import BoardSidebar from './boardSidebar';

const BoardNotice = () => {
  return (
    <>
      <MypageNavbar />
      <div className="flex flex-row">
        <div className="z-[2]">
          <BoardSidebar boardCategory={'ANNOUNCEMENT'} />
        </div>
        <div className="w-full bg-[#f4f4f5]">
          <BoardContent boardCategory={'ANNOUNCEMENT'} />
        </div>
      </div>
    </>
  );
};

export default BoardNotice;
