import cn from 'clsx';
import _ from 'lodash';
import { categoryList } from '../data/_constant';

interface PropsType {
  boardCategory: string;
  range: RANGE;
  setRange: React.Dispatch<React.SetStateAction<RANGE>>;
}

export const enum RANGE {
  SCHOOL = 'SCHOOL',
  SUBJECT = 'SUBJECT',
}

const TitleComponent = (props: PropsType) => {
  const { boardCategory, range, setRange } = props;

  const boardCategoryLabel = _.find(categoryList, category => {
    return category.value === boardCategory;
  })?.label;

  return (
    <div className="flex justify-between">
      <div className="text-[24px] font-[700]">{boardCategoryLabel}</div>
      <div className="flex">
        <div
          onClick={() => {
            setRange(RANGE.SCHOOL);
          }}
          className={cn(
            'cursor-pointer rounded-[45px] drop-shadow-sm  w-[110px] h-[36px] text-center border-solid border text-[16px] leading-[33px] mr-[13px]',
            {
              'bg-[#FF611D] text-[#fff] border-[#FF611D]': range === RANGE.SCHOOL,
              'bg-[#fff] border-[#BCBCBC] text-[#FF611D]': range !== RANGE.SCHOOL,
            },
          )}
        >
          {RANGE.SCHOOL}
        </div>
        <div
          onClick={() => {
            setRange(RANGE.SUBJECT);
          }}
          className={cn(
            'cursor-pointer rounded-[45px] drop-shadow-sm w-[110px] h-[36px] text-center border-solid border text-[16px] leading-[30px] mr-[13px]',
            {
              'bg-[#FF611D] text-[#fff] border-[#FF611D]': range === RANGE.SUBJECT,
              'bg-[#fff] border-[#BCBCBC] text-[#FF611D]': range !== RANGE.SUBJECT,
            },
          )}
        >
          {RANGE.SUBJECT}
        </div>
      </div>
    </div>
  );
};

export default TitleComponent;
