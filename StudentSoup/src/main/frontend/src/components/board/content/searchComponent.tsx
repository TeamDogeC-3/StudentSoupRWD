import _ from 'lodash';
import { useEffect, useRef, useState } from 'react';
import cn from 'clsx';
import Arrow from '../../../img/board/icon_selectbox_arrow.png';
import { RANGE } from './titleComponent';
import useBoardData, { DepartmentType } from '../data/useBoardData';

interface PropsType {
  range: RANGE;
  searchValue: string;
  setSearchValue: React.Dispatch<React.SetStateAction<string>>;
  sort: number;
  setSort: React.Dispatch<React.SetStateAction<number>>;
  column: string;
  setColumn: React.Dispatch<React.SetStateAction<string>>;
  department: any;
  setDepartment: any;
  handleSearchButton: () => void;
  page: number;
  setPage: React.Dispatch<React.SetStateAction<number>>;
}

const keywordList = [
  { label: '제목', value: 'title' },
  { label: '글쓴이', value: 'nickname' },
  { label: '내용', value: 'content' },
];

const sortList = [
  { label: '추천순', value: 1 },
  { label: '최신순', value: 2 },
  { label: '조회순', value: 3 },
  { label: '댓글순', value: 4 },
];

const SearchComponent = (props: PropsType) => {
  const {
    range,
    searchValue,
    setSearchValue,
    sort,
    setSort,
    column,
    setColumn,
    department,
    setDepartment,
    handleSearchButton,
    page,
    setPage,
  } = props;

  const [showKeywords, setShowKeywords] = useState(false);
  const [showSorts, setShowSorts] = useState(false);
  const [showSubjects, setShowSubjects] = useState(false);

  const [subjectList, setSubjectList] = useState<DepartmentType[]>([]);

  const keywordRef: any = useRef(null);
  const sortRef: any = useRef(null);
  const subjectRef: any = useRef(null);

  const { getDepartmentList } = useBoardData();

  /**
   * 컴포넌트 특정 영역 외 클릭 감지를 위해 사용
   * */
  useEffect(() => {
    const handleOutside = (e: any) => {
      if (keywordRef.current && !keywordRef.current.contains(e.target)) setShowKeywords(false);
    };
    document.addEventListener('mousedown', handleOutside);
    return () => {
      document.removeEventListener('mousedown', handleOutside);
    };
  }, [keywordRef]);

  useEffect(() => {
    const handleOutside = (e: any) => {
      if (sortRef.current && !sortRef.current.contains(e.target)) setShowSorts(false);
    };
    document.addEventListener('mousedown', handleOutside);
    return () => {
      document.removeEventListener('mousedown', handleOutside);
    };
  }, [sortRef]);

  useEffect(() => {
    const handleOutside = (e: any) => {
      if (subjectRef.current && !subjectRef.current.contains(e.target)) setShowSubjects(false);
    };
    document.addEventListener('mousedown', handleOutside);
    return () => {
      document.removeEventListener('mousedown', handleOutside);
    };
  }, [subjectRef]);

  return (
    <div className="flex justify-between mt-[26px]">
      <div className="flex">
        {/* Keyword */}
        <div ref={keywordRef} className="relative">
          <div
            onClick={() => {
              setShowKeywords(prev => !prev);
            }}
            className="cursor-pointer flex items-center justify-between bg-white text-[#A4A4A4] p-[10px] w-[84px] h-[46px] rounded-l-[5px] border border-solid border-[#BCBCBC]"
          >
            {
              _.find(keywordList, item => {
                return item.value === column;
              })?.label
            }
            <span>
              <img src={Arrow} alt="selectbox" />
            </span>
          </div>
          {showKeywords && (
            <div className="flex flex-col absolute border border-solid border-[#BCBCBC] rounded-[5px] w-[84px] bg-[#E9E9E9] text-[#7B7B7B] top-[52px]">
              {_.map(keywordList, (item, index) => {
                return (
                  <div
                    onClick={() => {
                      setColumn(item.value);
                      setShowKeywords(false);
                    }}
                    key={index}
                    className={cn(
                      'py-[7px] px-[12px] cursor-pointer hover:bg-[#dddddd] first-of-type:rounded-t-[5px] last-of-type:rounded-b-[5px]',
                      {
                        ['border-t-[1px] border-solid border-[#BCBCBC]']: index !== 0,
                      },
                    )}
                  >
                    {item.label}
                  </div>
                );
              })}
            </div>
          )}
        </div>

        {/* Search */}
        <input
          value={searchValue}
          onChange={e => {
            setSearchValue(e.target.value);
          }}
          onKeyDown={e => {
            if (e.key === 'Enter') {
              if (page !== 1) setPage(1);
              else handleSearchButton();
            }
          }}
          placeholder="글 제목, 내용, 해시태그를 적어주세요"
          className="bg-white border-r border-y border-solid border-[#BCBCBC] rounded-r-[5px] mr-[5px] w-[280px] h-[46px] p-[13px] text-[16px] focus:outline-none"
        />
        <div
          onClick={() => {
            if (page !== 1) setPage(1);
            else handleSearchButton();
          }}
          className="cursor-pointer bg-orange text-white w-[68px] h-[46px] text-center rounded-[5px] leading-[46px]"
        >
          검색
        </div>
      </div>

      <div className="flex justify-end gap-x-[5px]">
        {/* Sort */}
        <div ref={sortRef} className="relative">
          <div
            onClick={() => {
              setShowSorts(prev => !prev);
            }}
            className="cursor-pointer flex items-center justify-between bg-white text-[#A4A4A4] p-[10px] w-[137px] h-[46px] rounded-[5px] border border-solid border-[#BCBCBC]"
          >
            {sort === 0
              ? '정렬'
              : _.find(sortList, item => {
                  return item.value === sort;
                })?.label}
            <span>
              <img src={Arrow} alt="selectbox" />
            </span>
          </div>
          {showSorts && (
            <div className="flex flex-col absolute border border-solid border-[#BCBCBC] rounded-[5px] w-[137px] bg-[#E9E9E9] text-[#7B7B7B] top-[52px]">
              {_.map(sortList, (item, index) => {
                return (
                  <div
                    onClick={() => {
                      setSort(item.value);
                      setShowSorts(false);
                    }}
                    key={index}
                    className={cn(
                      'py-[7px] px-[12px] cursor-pointer hover:bg-[#dddddd] first-of-type:rounded-t-[5px] last-of-type:rounded-b-[5px]',
                      {
                        ['border-t-[1px] border-solid border-[#BCBCBC]']: index !== 0,
                      },
                    )}
                  >
                    {item.label}
                  </div>
                );
              })}
            </div>
          )}
        </div>

        {/* Subject */}
        {range === RANGE.SUBJECT && (
          <div ref={subjectRef} className="relative">
            <div
              onClick={() => {
                setShowSubjects(prev => !prev);
                if (!showSubjects) {
                  getDepartmentList(data => {
                    setSubjectList(data);
                  });
                }
              }}
              className="cursor-pointer flex items-center justify-between bg-white text-[#A4A4A4] p-[10px] w-[165px] h-[46px] rounded-[5px] border border-solid border-[#BCBCBC]"
            >
              {department.id === undefined ? '학과' : department.name}
              <span>
                <img src={Arrow} alt="selectbox" />
              </span>
            </div>
            {showSubjects && (
              <div className="flex flex-col absolute border border-solid border-[#BCBCBC] rounded-[5px] w-[165px] bg-[#E9E9E9] text-[#7B7B7B] top-[52px]">
                {_.map(subjectList, (item, index) => {
                  return (
                    <div
                      onClick={() => {
                        setShowSubjects(false);
                        setDepartment({ id: String(item.departmentId), name: item.departmentName });
                      }}
                      key={index}
                      className={cn(
                        'py-[7px] px-[12px] cursor-pointer hover:bg-[#dddddd] first-of-type:rounded-t-[5px] last-of-type:rounded-b-[5px]',
                        {
                          ['border-t-[1px] border-solid border-[#BCBCBC]']: index !== 0,
                        },
                      )}
                    >
                      {item.departmentName}
                    </div>
                  );
                })}
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default SearchComponent;
