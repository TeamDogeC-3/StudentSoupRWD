import { atom } from 'recoil';
import { RANGE } from '../content/titleComponent';
export const departmentState = atom({
  key: 'departmentState',
  default: { id: undefined, name: '' },
});

export const rangeState = atom({
  key: 'rangeState',
  default: RANGE.SCHOOL,
});
