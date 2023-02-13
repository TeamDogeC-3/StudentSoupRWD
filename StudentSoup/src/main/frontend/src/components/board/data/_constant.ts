import CheckSquareIcon from '../../../img/board/icon_check_square.png';
import UserIcon from '../../../img/board/icon_users.png';
import ChatIcon from '../../../img/board/icon_chat.png';
import DocumentIcon from '../../../img/board/icon_document.png';
import CheckSquareActiveIcon from '../../../img/board/icon_check_square_active.png';
import UserActiveIcon from '../../../img/board/icon_users_active.png';
import ChatActiveIcon from '../../../img/board/icon_chat_active.png';
import DocumentActiveIcon from '../../../img/board/icon_document_active.png';

export const enum BOARD_CATEGORY {
  ALL = '전체게시판',
  FREE = '자유게시판',
  CONSULTING = '취업/상담게시판',
  TIP = 'TIP 게시판',
  NOTICE = '공지사항',
}

export const categoryList = [
  {
    label: BOARD_CATEGORY.ALL,
    imgPath: DocumentIcon,
    activeImgPath: DocumentActiveIcon,
    value: 'ALL',
    link: '/all',
  },
  {
    label: BOARD_CATEGORY.FREE,
    imgPath: ChatIcon,
    activeImgPath: ChatActiveIcon,
    value: 'FREE',
    link: '/free',
  },
  {
    label: BOARD_CATEGORY.CONSULTING,
    imgPath: UserIcon,
    activeImgPath: UserActiveIcon,
    value: 'CONSULTING',
    link: '/consulting',
  },
  {
    label: BOARD_CATEGORY.TIP,
    imgPath: CheckSquareIcon,
    activeImgPath: CheckSquareActiveIcon,
    value: 'TIP',
    link: '/tip',
  },
  {
    label: BOARD_CATEGORY.NOTICE,
    imgPath: DocumentIcon,
    activeImgPath: DocumentActiveIcon,
    value: 'ANNOUNCEMENT',
    link: '/notice',
  },
];
