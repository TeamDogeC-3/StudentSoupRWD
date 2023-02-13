import MypageNavbar from '../common/mypageNavbar';
import BoardContent from './boardContent';
import BoardSidebar from './boardSidebar';

const BoardConsulting = () => {
  return (
    <>
      <MypageNavbar />
      <div className="flex flex-row">
        <div className="z-[2]">
          <BoardSidebar boardCategory={'CONSULTING'} />
        </div>
        <div className="w-full bg-[#f4f4f5]">
          <BoardContent boardCategory={'CONSULTING'} />
        </div>
      </div>
    </>
  );
};

export default BoardConsulting;
