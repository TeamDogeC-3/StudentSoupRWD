import SearchComponent from './content/searchComponent';
import TitleComponent, { RANGE } from './content/titleComponent';

import PencilIcon from '../../img/board/icon_pencil.png';
import BoardListComponent, { BoardListType } from './content/boardListComponent';
import TopListComponent from './content/topListComponent';
import HotListComponent from './content/hotListComponent';
import { useEffect, useState } from 'react';
import _ from 'lodash';
import useBoardData, { DataResType } from './data/useBoardData';
import { useHistory } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { departmentState, rangeState } from './data/boardRecoil';
interface PropsType {
  boardCategory: string;
}

const BoardContent = (props: PropsType) => {
  const history = useHistory();
  const { boardCategory } = props;
  const { getBoardList } = useBoardData();
  const [list, setList] = useState<BoardListType[]>([]);
  const [pageInfo, setPageInfo] = useState<number>(0);
  const [topList, setTopList] = useState<BoardListType[]>([]);
  const [hotList, setHotList] = useState<BoardListType[]>([]);

  const [searchValue, setSearchValue] = useState('');
  const [page, setPage] = useState(1);
  const [column, setColumn] = useState('title');
  const [sort, setSort] = useState(0);

  const [department, setDepartment] = useRecoilState(departmentState);
  const [range, setRange] = useRecoilState(rangeState);

  const size = boardCategory === 'ALL' ? 7 : 12;

  useEffect(() => {
    handleSearchButton();
  }, [sort, range, page, department]);

  const handleSearchButton = () => {
    const request = {
      column,
      value: searchValue,
      category: boardCategory,
      sorted: sort,
      page: page - 1,
      size,
      departmentId: range === RANGE.SUBJECT ? Number(department.id) : undefined,
    };
    getBoardList(request, (res: DataResType) => {
      setList(res.boards.content);
      setPageInfo(res.boards.totalPages);
      if (res.bestBoards) setTopList(res.bestBoards);
      if (res.hotBoards) setHotList(res.hotBoards);
    });
  };

  return (
    <div className="h-[120vh] py-[52px] px-[76px] max-w-[1100px]">
      <TitleComponent boardCategory={boardCategory} range={range} setRange={setRange} />
      <SearchComponent
        range={range}
        setSearchValue={setSearchValue}
        searchValue={searchValue}
        sort={sort}
        setSort={setSort}
        column={column}
        setColumn={setColumn}
        department={department}
        setDepartment={setDepartment}
        handleSearchButton={handleSearchButton}
        page={page}
        setPage={setPage}
      />
      {boardCategory === 'ALL' && (
        <div className="flex justify-between">
          <TopListComponent topList={topList} />
          <HotListComponent hotList={hotList} />
        </div>
      )}
      <div className="flex justify-end mt-[21px]">
        <div
          onClick={() => history.push('/board/write')}
          className="flex justify-center gap-x-[5px] cursor-pointer text-[14px] leading-[30px] text-center text-[#FF611D] w-[89px] h-[32px] border border-solid border-[#FF611D] rounded-[22px] bg-white"
        >
          <img src={PencilIcon} alt="write" className="self-center" />
          <span>글쓰기</span>
        </div>
      </div>
      <BoardListComponent
        list={list}
        totalPages={pageInfo}
        boardCategory={boardCategory}
        page={page}
        setPage={setPage}
        size={size}
      />
    </div>
  );
};

export default BoardContent;
