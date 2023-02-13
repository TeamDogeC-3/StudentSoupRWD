import _ from 'lodash';
import HeartIcon from '../../../img/board/icon_heart.png';
import { BoardListType } from './boardListComponent';
import { useHistory } from 'react-router-dom';

interface PropsType {
  hotList: BoardListType[];
}

const HotListComponent = (props: PropsType) => {
  const history = useHistory();
  const { hotList } = props;

  return (
    <div className="my-[15px] w-[48%]">
      <div className="bg-white p-[11px] rounded-[5px] border border-solid border-[#bcbcbc]">
        <table className="w-full text-center">
          <colgroup>
            <col style={{ width: '65%' }} />
            <col style={{ width: '15%' }} />
            <col style={{ width: '20%' }} />
          </colgroup>
          <thead>
            <tr>
              <td colSpan={3}>
                <div className="text-[20px] text-left py-[13px] px-[13px] font-[600]">
                  <span className="text-orange">HOT</span> 게시글 🔥
                </div>
              </td>
            </tr>
          </thead>
          <tbody>
            {_.map(hotList, (item, index) => {
              return (
                <tr
                  key={index}
                  className="py-[15px] px-[13px] border-t-[1px] border-solid border-[#BCBCBC]"
                >
                  <td
                    onClick={() => history.push('/board/detail', item.boardId)}
                    className="text-start py-[15px] px-[13px] cursor-pointer hover:underline underline-offset-[1px]"
                  >
                    [{item.tag}] {item.title}{' '}
                    <span className="text-orange">{item.reviewCount}</span>
                  </td>
                  <td className="flex py-[15px] px-[13px] gap-x-[5px]">
                    <img src={HeartIcon} alt="heart" className="self-center" />
                    {item.view}
                  </td>
                  <td className="py-[15px] px-[13px]">{item.writeDate}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default HotListComponent;
