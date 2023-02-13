import React, { useState } from 'react';
import { ReactComponent as BoardWriteReplyHeart } from '../../img/BoardWriteReplyHeart.svg';
import { ReactComponent as BoardReplyIcon } from '../../img/boardReplyIcon.svg';
import { ReactComponent as BardWriteReplyActiveHeart } from '../../img/BoardWriteReplyActiveHeart.svg';
import { useLocation } from 'react-router-dom';
import Circle_human from '../../img/circle_human.png';

import axios from 'axios';

const boardReviewList = (data: any) => {
  const state = useLocation();
  const getBoardId = state.state;
  const [reply, setReply] = useState<number>(0);
  const [findId, setFindId] = useState<number>();
  const saveMemberId = sessionStorage.getItem('memberId');
  const [nestedReply, setNestedReply] = useState<string>('');
  const saveMemberName = sessionStorage.getItem('nickname');
  const [ReplyLikeCount, setReplyLikeCount] = useState<any>();
  const [replyLike, isReplyLike] = useState<boolean>(data.like);
  const [editClick, isEditClick] = useState<boolean>(false);
  const [like, isLike] = useState<boolean>(false);
  const [saveBoardId, setSaveBoardId] = useState<any>();
  const [contented, setContented] = useState<string>('');
  const [edit, isEdit] = useState<number>(0);
  const [replyTextValue, setReplyTextValue] = useState<string>('');
  const handleSetContentValue = (e: any) => {
    setNestedReply(e.target.value);
  };
  const handleReReply = (e: any) => {
    if (!nestedReply) {
      alert('댓글을 입력해주세요');
      return;
    }
    if (nestedReply.length < 2 || replyTextValue.length > 500) {
      alert('댓글은 2자에서 500자까지 입력가능합니다.');
      return;
    }
    axios
      .put('/boardReply', {
        boardId: getBoardId,
        memberId: saveMemberId,
        content: nestedReply,
        level: 1,
        seq: reply,
      })
      .then(res => {
        alert('성공적으로 댓글을 작성하였습니다.');
        location.reload();
      })
      .catch(err => {
        console.error(err);
      });
  };
  const handleReplyLikeCount = (e: any) => {
    const boardReplyId = e.target.id;
    axios
      .post(`/boardReply/${boardReplyId}/${saveMemberId}/like`)
      .then(res => {
        setReplyLikeCount(res.data.data.likeCount);
        isReplyLike(res.data.data.like);
      })
      .catch(err => {
        console.error(err);
      });
    isReplyLike(!replyLike);
    isLike(!like);
  };

  const handleDeleteReply = (e: any) => {
    const boardReplyId = e.target.id;
    if (confirm('정말로 댓글을 삭제하시겟습니까?')) {
      axios
        .delete(`/boardReply/${boardReplyId}/${saveMemberId}`)
        .then(res => {
          alert('댓글이 삭제되었습니다.');
          location.reload();
        })
        .catch(err => {
          console.error(err);
        });
    } else {
      /* empty */
    }
  };
  const handleEditClick = (e: any) => {
    isEditClick(!editClick);
    setSaveBoardId(e.target.id);

    axios
      .get(`/boardReply/${e.target.id}/${saveMemberId}`)
      .then(res => {
        setContented(res.data.content);
      })
      .catch(err => {
        console.error(err);
      });
  };
  const handleReplySetContentValue = (e: any) => {
    setReplyTextValue(e.target.value);
    setContented(e.target.value);
  };
  const handleEditReply = (e: any) => {
    const boardReplyId = e.target.id;
    if (!contented.length) {
      alert('댓글을 입력해주세요.');
      return;
    }
    if (contented.length < 2 || contented.length > 500) {
      alert('댓글은 2자이상 500자 이하입니다.');
      return;
    }
    axios
      .patch(`/boardReply/${saveBoardId}`, {
        boardReplyId,
        boardId: getBoardId,
        memberId: saveMemberId,
        content: contented,
      })
      .then(res => {
        alert('성공적으로 수정하였습니다.');
        location.reload();
      })
      .catch(err => {
        console.error(err);
      });
  };
  const handleImgError = (e: any) => {
    e.target.src = Circle_human;
  };
  return (
    <>
      <div key={data.boardReplyId} className="grid grid-cols-[96px_minmax(720px,_1fr)_100px]">
        {data.seq && data.level === 0 ? (
          <>
            <img
              src={`/image/${data.memberProfileImageName}`}
              onError={handleImgError}
              className="row-span-2 ml-[38px] mt-[20px] w-[40px] h-[40px] border-[1px] rounded-full"
            />
            <div className="flex flex-row mt-[20px]">
              <div className="h-[23px] font-normal text-[16px] leading-[22px] text-[#404040]">
                {data.nickname}
              </div>
              <div className="ml-[8px] font-normal text-[14px] leading-[18px] text-[#919191]">
                {data.writeDate}
              </div>
            </div>
            <div className="flex flex-row ml-[19px] mt-[18px]">
              {saveMemberName === data.nickname ? (
                <>
                  {data.active === 'N' ? (
                    <></>
                  ) : (
                    <>
                      {editClick ? (
                        <>
                          <div
                            id={data.boardReplyId}
                            onClick={handleEditClick}
                            className="text-[14px] text-[#989898 cursor-pointer"
                          >
                            수정취소
                          </div>
                        </>
                      ) : (
                        <>
                          <div
                            id={data.boardReplyId}
                            onClick={handleEditClick}
                            className="text-[14px] text-[#989898] cursor-pointer"
                          >
                            수정
                          </div>
                          <span className="ml-[4px] text-[14px] text-[#989898]">|</span>
                          <div
                            onClick={handleDeleteReply}
                            id={data.boardReplyId}
                            className="ml-[4px] text-[14px] text-[#989898] cursor-pointer"
                          >
                            삭제
                          </div>
                        </>
                      )}
                    </>
                  )}
                </>
              ) : (
                ''
              )}
            </div>
            <div
              id={data.boardReplyId}
              className="col-span-2 mt-[2px] w-[723px] h-auto font-normal text-[16px] leading-[21px] text-[#404040]"
            >
              {editClick ? (
                <>
                  <div className="flex flex-row">
                    <textarea
                      maxLength={500}
                      value={contented}
                      onChange={e => {
                        handleReplySetContentValue(e);
                      }}
                      placeholder="댓글을 입력해주세요."
                      className="w-[534px] h-[50px] resize-y border-[1px] rounded-[5px] border-[#C4C4C4]"
                    ></textarea>
                    <button
                      onClick={handleEditReply}
                      className="ml-[10px] w-[50px] h-[50px] bg-[#FF611D] rounded-[5px] text-[16px] font-normal text-[#FFFFFF]"
                    >
                      등록
                    </button>
                  </div>
                </>
              ) : (
                data.content
              )}
            </div>
            <div className="flex flex-row col-span-3">
              {data.active === 'N' ? (
                <></>
              ) : (
                <>
                  {findId === data.boardReplyId ? (
                    <button
                      id={data.boardReplyId}
                      onClick={() => {
                        setFindId(0);
                        setReply(0);
                        setNestedReply('');
                      }}
                      className="ml-[96px] mt-[10px] font-normal text-[13px] leading-[17px] text-[#404040]"
                    >
                      답글작성 취소
                    </button>
                  ) : (
                    <button
                      id={data.boardReplyId}
                      value={data.seq}
                      onClick={() => {
                        setFindId(data.boardReplyId);
                        setReply(data.seq);
                      }}
                      className="ml-[96px] mt-[10px] font-normal text-[13px] leading-[17px] text-[#404040]"
                    >
                      답글작성
                    </button>
                  )}
                  {findId === data.boardReplyId ? (
                    ''
                  ) : (
                    <>
                      {data.active === 'N' ? (
                        <></>
                      ) : (
                        <>
                          {replyLike ? (
                            <svg
                              id={data.boardReplyId}
                              onClick={handleReplyLikeCount}
                              className="ml-[725px] mt-[2px]"
                              width="16"
                              height="14"
                              viewBox="0 0 16 14"
                              fill="#FF611D"
                              xmlns="http://www.w3.org/2000/svg"
                            >
                              <path
                                id={data.boardReplyId}
                                d="M4.49271 1C2.56374 1 1 2.5481 1 4.45778C1 5.99947 1.61099 9.65822 7.62742 13.3574C7.8453 13.4914 8.12555 13.4914 8.34343 13.3574C14.3598 9.65822 14.9708 5.99947 14.9708 4.45778C14.9708 2.5481 13.4071 1 11.4781 1C9.54918 1 7.98542 3.09563 7.98542 3.09563C7.98542 3.09563 6.42168 1 4.49271 1Z"
                                stroke="#FF661D"
                                strokeLinecap="round"
                                strokeLinejoin="round"
                              />
                            </svg>
                          ) : (
                            <svg
                              id={data.boardReplyId}
                              onClick={handleReplyLikeCount}
                              className="ml-[725px] mt-[2px]"
                              width="16"
                              height="14"
                              viewBox="0 0 16 14"
                              fill="none"
                              xmlns="http://www.w3.org/2000/svg"
                            >
                              <path
                                id={data.boardReplyId}
                                d="M4.49271 1C2.56374 1 1 2.5481 1 4.45778C1 5.99947 1.61099 9.65822 7.62742 13.3574C7.8453 13.4914 8.12555 13.4914 8.34343 13.3574C14.3598 9.65822 14.9708 5.99947 14.9708 4.45778C14.9708 2.5481 13.4071 1 11.4781 1C9.54918 1 7.98542 3.09563 7.98542 3.09563C7.98542 3.09563 6.42168 1 4.49271 1Z"
                                stroke="#898989"
                                strokeLinecap="round"
                                strokeLinejoin="round"
                              />
                            </svg>
                          )}

                          <div className="ml-[6.03px] mt-[1px] font-normal text-[16px] leading-[21px] text-[#898989]">
                            {like ? ReplyLikeCount : data.likeCount}
                          </div>
                        </>
                      )}
                    </>
                  )}
                </>
              )}
            </div>
            {findId === data.boardReplyId && (
              <>
                <textarea
                  onChange={e => {
                    handleSetContentValue(e);
                  }}
                  placeholder="댓글을 입력해주세요."
                  className="ml-[28px] mt-[16px] w-[834px] h-[50px] resize-y border-[1px] rounded-[5px] border-[#C4C4C4]"
                ></textarea>
                <button
                  onClick={handleReReply}
                  className="relative left-[760px] mt-[16px] ml-[10px] w-[50px] h-[50px] bg-[#FF611D] rounded-[5px] text-[16px] font-normal text-[#FFFFFF]"
                >
                  등록
                </button>
              </>
            )}
            <div className="col-span-3 ml-[28px] mt-[10px] w-[884px] border-[1px] border-[#BCBCBC]"></div>
          </>
        ) : (
          ''
        )}
      </div>
      <div className="grid grid-cols-[74px_60px_720px_100px]">
        {data.seq && data.level === 1 ? (
          <>
            <div
              key={data.boardReplyId}
              className="mt-[23px] grid grid-cols-[74px_60px_720px_100px]"
            >
              <BoardReplyIcon className="row-span-2 ml-[38px]" />
              <img
                src={`/image/${data.memberProfileImageName}`}
                onError={handleImgError}
                className="row-span-2 w-[40px] h-[40px] border-[1px] rounded-full"
              />

              <div className="flex flex-row">
                <div className="h-[23px] font-normal text-[16px] leading-[22px] text-[#404040]">
                  {data.nickname}
                </div>
                <div className="ml-[8px] font-normal text-[14px] leading-[18px] text-[#919191]">
                  {data.writeDate}
                </div>
              </div>
              <div className="flex flex-row row-span-2">
                {saveMemberName === data.nickname ? (
                  <>
                    {data.active === 'N' ? (
                      <></>
                    ) : (
                      <>
                        {editClick ? (
                          <>
                            <div
                              id={data.boardReplyId}
                              onClick={handleEditClick}
                              className="text-[14px] text-[#989898 cursor-pointer"
                            >
                              수정취소
                            </div>
                          </>
                        ) : (
                          <>
                            <div
                              id={data.boardReplyId}
                              onClick={handleEditClick}
                              className="text-[14px] text-[#989898] cursor-pointer"
                            >
                              수정
                            </div>
                            <span className="ml-[4px] text-[14px] text-[#989898]">|</span>
                            <div
                              onClick={handleDeleteReply}
                              id={data.boardReplyId}
                              className="ml-[4px] text-[14px] text-[#989898] cursor-pointer"
                            >
                              삭제
                            </div>
                          </>
                        )}
                      </>
                    )}
                  </>
                ) : (
                  ''
                )}
              </div>
              <div className="mt-[2px] w-[723px] h-auto font-normal text-[16px] leading-[21px] text-[#404040]">
                {editClick ? (
                  <>
                    <div className="flex flex-row">
                      <textarea
                        maxLength={500}
                        value={contented}
                        onChange={e => {
                          handleReplySetContentValue(e);
                        }}
                        placeholder="댓글을 입력해주세요."
                        className="w-[534px] h-[50px] resize-y border-[1px] rounded-[5px] border-[#C4C4C4]"
                      ></textarea>
                      <button
                        onClick={handleEditReply}
                        className="ml-[10px] w-[50px] h-[50px] bg-[#FF611D] rounded-[5px] text-[16px] font-normal text-[#FFFFFF]"
                      >
                        등록
                      </button>
                    </div>
                  </>
                ) : (
                  data.content
                )}
              </div>
              <div className="flex flex-row ml-[853px] col-span-4 ">
                {replyLike ? (
                  <svg
                    id={data.boardReplyId}
                    onClick={handleReplyLikeCount}
                    className="mt-[5px] ml-[13px]"
                    width="16"
                    height="14"
                    viewBox="0 0 16 14"
                    fill="#FF611D"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      id={data.boardReplyId}
                      d="M4.49271 1C2.56374 1 1 2.5481 1 4.45778C1 5.99947 1.61099 9.65822 7.62742 13.3574C7.8453 13.4914 8.12555 13.4914 8.34343 13.3574C14.3598 9.65822 14.9708 5.99947 14.9708 4.45778C14.9708 2.5481 13.4071 1 11.4781 1C9.54918 1 7.98542 3.09563 7.98542 3.09563C7.98542 3.09563 6.42168 1 4.49271 1Z"
                      stroke="#FF661D"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                    />
                  </svg>
                ) : (
                  <svg
                    id={data.boardReplyId}
                    onClick={handleReplyLikeCount}
                    className="mt-[5px] ml-[13px]"
                    width="16"
                    height="14"
                    viewBox="0 0 16 14"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      id={data.boardReplyId}
                      d="M4.49271 1C2.56374 1 1 2.5481 1 4.45778C1 5.99947 1.61099 9.65822 7.62742 13.3574C7.8453 13.4914 8.12555 13.4914 8.34343 13.3574C14.3598 9.65822 14.9708 5.99947 14.9708 4.45778C14.9708 2.5481 13.4071 1 11.4781 1C9.54918 1 7.98542 3.09563 7.98542 3.09563C7.98542 3.09563 6.42168 1 4.49271 1Z"
                      stroke="#898989"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                    />
                  </svg>
                )}

                <div className="ml-[6.03px] mt-[3px] font-normal text-[16px] leading-[21px] mb-[10px] text-[#898989]">
                  {like ? ReplyLikeCount : data.likeCount}
                </div>
              </div>
              <div className="col-span-3 ml-[28px] mt-[5px] w-[884px] border-[1px] border-[#BCBCBC]"></div>
            </div>
          </>
        ) : (
          ''
        )}
      </div>
    </>
  );
};

export default boardReviewList;
