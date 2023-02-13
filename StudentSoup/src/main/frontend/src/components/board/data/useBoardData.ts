import axios from 'axios';
import { BoardListType } from '../content/boardListComponent';

interface RequestType {
  column: string; // 검색 카테고리
  value: string; // 검색 내용

  departmentId?: number; // 학과 id

  category: string; // 게시판 카테고리
  sorted: number; // 정렬 0~4
  page: number; // 게시판 페이지
  size: number; // 게시판 크기 (default=12)
}

export interface DataResType {
  boards: { content: BoardListType[]; totalPages: number };
  bestBoards?: BoardListType[];
  hotBoards?: BoardListType[];
}

export interface DepartmentType {
  departmentId: number;
  departmentName: string;
}

const useBoardData = () => {
  const getDepartmentList = (callback: (data: DepartmentType[]) => void) => {
    const schoolId = Number(sessionStorage.getItem('schoolId')) ?? 0;

    axios
      .get(`/board/department/${schoolId}`)
      .then(res => {
        callback(res.data);
      })
      .catch(err => {
        console.error(err);
      });
  };
  const getBoardList = (request: RequestType, callback: (data: DataResType) => void) => {
    const { category, sorted, page, size = 12, column, value, departmentId } = request;

    const schoolId = Number(sessionStorage.getItem('schoolId')) ?? 0;
    const memberId = Number(sessionStorage.getItem('memberId')) ?? 0;

    const body = {
      schoolId,
      memberId,
      departmentId,
    };

    axios
      .post(
        `/boards?category=${category}&sorted=${sorted}&page=${page}&size=${size}&column=${column}&value=${value}`,
        body,
      )
      .then(res => {
        callback(res.data);
      })
      .catch(err => {
        console.error(err);
      });
  };

  return { getBoardList, getDepartmentList };
};

export default useBoardData;
