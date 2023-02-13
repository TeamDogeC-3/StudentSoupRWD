import { useMediaQuery } from 'react-responsive';

export const Desktop = ({ children }: { children: JSX.Element }): JSX.Element | null => {
  const isDesktop = useMediaQuery({ minWidth: 769 });
  return isDesktop ? children : null;
};

export const Mobile = ({ children }: { children: JSX.Element }): JSX.Element | null => {
  const isMobile = useMediaQuery({ maxWidth: 768 });
  return isMobile ? children : null;
};
