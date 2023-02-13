import { useEffect } from 'react';
import Navbar from '../common/navbar';
import MainSearch from './mainSearch';

const Home = () => {
  return (
    <div className='w-full h-[100vh] bg-[url("./img/mainlogo.jpg")] bg-cover z-50'>
      <div className="w-full h-[100vh] bg-gradient-to-t from-[rgba(0,0,0,0.45)] to-[rgba(0,0,0,0.1)] z-[51]">
        <Navbar />
        <MainSearch />
      </div>
    </div>
  );
};

export default Home;
