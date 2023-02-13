import _ from 'lodash';
import HeartIcon from '../../../img/board/icon_heart.png';
import { useHistory } from 'react-router-dom';
import Pagination from 'react-js-pagination';
import { useEffect } from 'react';

export interface BoardListType {
  authentication: string;
  boardCategory: string;
  tag: string;
  boardId: number;
  likedCount: number;
  nickname: string;
  title: string;
  view: number;
  writeDate: string;
  reviewCount: number;
}

interface PropsType {
  list: BoardListType[];
  totalPages: number;
  boardCategory: string;
  page: number;
  setPage: React.Dispatch<React.SetStateAction<number>>;
  size: number;
}

const BoardListComponent = (props: PropsType) => {
  const { list, boardCategory, page, setPage, totalPages, size } = props;
  const history = useHistory();

  const handlePageChange = (page: any) => {
    setPage(page);
  };

  return (
    <div className="my-[15px]">
      <div className="bg-white p-[11px] rounded-[5px] border border-solid border-[#bcbcbc]">
        <table className="w-full text-center">
          <colgroup>
            <col style={{ width: '50%' }} />
            <col style={{ width: '20%' }} />
            <col style={{ width: '10%' }} />
            <col style={{ width: '10%' }} />
            <col style={{ width: '10%' }} />
          </colgroup>
          <thead>
            <tr className="text-[15px]">
              <td className="text-start py-[15px] px-[13px]">제목</td>
              <td className="py-[15px] px-[13px]">닉네임</td>
              <td className="py-[15px] px-[13px]">날짜</td>
              <td className="py-[15px] px-[13px]">조회</td>
              <td className="py-[15px] px-[13px]">추천</td>
            </tr>
          </thead>
          <tbody>
            {_.map(list, (item, index) => {
              if (item.boardCategory === '공지사항' && boardCategory === 'ANNOUNCEMENT') {
                return (
                  <tr
                    key={index}
                    className=" py-[15px] px-[13px] border-b-[1px] border-solid border-[#BCBCBC]"
                  >
                    <td
                      onClick={() => history.push('/board/detail', item.boardId)}
                      className="text-start text-[#FF611D] py-[15px] px-[13px] cursor-pointer hover:underline underline-offset-[1px] decoration-[#000000]"
                    >
                      [공지] <span className="text-black">{item.title}</span>
                    </td>
                    <td className="py-[15px] px-[13px]">{item.nickname}</td>
                    <td className="py-[15px] px-[13px]">{item.writeDate}</td>
                    <td className="py-[15px] px-[13px]">{item.view}</td>
                    <td className="flex py-[15px] px-[13px] gap-x-[5px] justify-center">
                      <img src={HeartIcon} alt="heart" className="self-center" />
                      {item.likedCount}
                    </td>
                  </tr>
                );
              }
              if (item.boardCategory === '공지사항') {
                return (
                  <tr
                    key={index}
                    className="bg-[#F3F3F3] py-[15px] px-[13px] border-b-[1px] border-solid border-[#BCBCBC]"
                  >
                    <td
                      onClick={() => history.push('/board/detail', item.boardId)}
                      className="text-start text-[#FF611D] py-[15px] px-[13px] border-l-[4px] border-solid border-[#FF611D] cursor-pointer hover:underline underline-offset-[1px]"
                    >
                      [공지] {item.title}
                    </td>
                    <td className="py-[15px] px-[13px]">{item.nickname}</td>
                    <td className="py-[15px] px-[13px]">{item.writeDate}</td>
                    <td className="py-[15px] px-[13px]">{item.view}</td>
                    <td className="flex py-[15px] px-[13px] gap-x-[5px] justify-center">
                      <img src={HeartIcon} alt="heart" className="self-center" />
                      {item.likedCount}
                    </td>
                  </tr>
                );
              }
              return (
                <tr
                  key={index}
                  className="py-[15px] px-[13px] border-b-[1px] border-solid border-[#BCBCBC]"
                >
                  <td
                    onClick={() => history.push('/board/detail', item.boardId)}
                    className="text-start py-[15px] px-[13px] cursor-pointer hover:underline underline-offset-[1px]"
                  >
                    {item.authentication === 'Y' && <span className="text-[#FF611D]">[BEST]</span>}{' '}
                    [{item.tag}] {item.title}{' '}
                    <span className="text-[#FF611D]">{item.reviewCount}</span>
                  </td>
                  <td className="py-[15px] px-[13px]">{item.nickname}</td>
                  <td className="py-[15px] px-[13px]">{item.writeDate}</td>
                  <td className="py-[15px] px-[13px]">{item.view}</td>
                  <td className="py-[15px] px-[13px]">
                    <div className="flex gap-x-[5px] justify-center">
                      <img src={HeartIcon} alt="heart" className="self-center" />
                      {item.likedCount}
                    </div>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
        <div>
          <Pagination
            innerClass="text-center my-[18px]"
            activePage={page}
            itemsCountPerPage={size}
            totalItemsCount={size * totalPages}
            pageRangeDisplayed={5}
            firstPageText={'<'}
            lastPageText={'>'}
            onChange={handlePageChange}
            linkClassFirst="text-orange mx-[5px] border-none"
            linkClassLast="text-orange mx-[5px] border-none"
            hideNavigation
            itemClass="inline-block"
            linkClass="inline-block text-[14px] text-center mx-[5px] w-[23px] h-[23px] border border-[#B4B4B4] rounded-full cursor-pointer font-normal text-[#B4B4B4]"
            activeLinkClass="border border-solid border-[#FF611C] text-orange inline-block text-[14px] w-[23px] h-[23px] rounded-full"
          />
        </div>
      </div>
    </div>
  );
};

export default BoardListComponent;
