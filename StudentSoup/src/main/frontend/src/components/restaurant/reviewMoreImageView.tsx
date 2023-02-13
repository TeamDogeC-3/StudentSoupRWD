import React, { useState } from 'react';

const reviewMoreImageView = (data: any) => {
  const imgArr = data.imageFileNameList;
  return (
    <>
      {imgArr.map((school: any) => (
        <img
          key={school}
          src={`/image/${school}`}
          className="ml-[3px] w-[146px] h-[135px] border-[1px]"
        />
      ))}
    </>
  );
};

export default reviewMoreImageView;
