import React from 'react';
import './App.css';
import Routes from './Routes';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

const App = () => {
  return (
    <RecoilRoot>
      <BrowserRouter>
        <Routes />
      </BrowserRouter>
    </RecoilRoot>
  );
};

export default App;
