import { Switch, Route } from 'react-router-dom';
import Home from './components/home/homeComponent';
import Register1 from './components/register/register1';
import Register2 from './components/register/register2';
import Register3 from './components/register/register3';
import Err404 from './components/err404/err404';
import Login from './components/login/login';
import FindAccount from './components/login/findAccount';
import MypageHome from './components/mypage/mypageHome';
import MypageModify from './components/mypage/mypageModify';
import Mypage from './components/mypage/mypage';
import Restaurant from './components/restaurant/restaurant';
import BoardDetail from './components/board/boardDetail';
import RestaurantDetail from './components/restaurant/restaurantDetail';
import MypageBoardReview from './components/mypage/mypageBoardReview';
import BoardWrite from './components/board/boardWrite';
import BoardEditWrite from './components/board/boardEditWrite';
import BoardAll from './components/board/boardAll';
import BoardFree from './components/board/boardFree';
import BoardConsulting from './components/board/boardConsulting';
import BoardTip from './components/board/boardTip';
import BoardNotice from './components/board/boardNotice';

const Routes = () => {
  return (
    <Switch>
      <Route exact path="/" component={Home} />
      <Route exact path="/register/1" component={Register1} />
      <Route exact path="/register/2" component={Register2} />
      <Route exact path="/register/3" component={Register3} />
      <Route exact path="/login" component={Login} />
      <Route exact path="/login/findAccount" component={FindAccount} />
      <Route exact path="/mypage" component={Mypage} />
      <Route exact path="/mypageHome" component={MypageHome} />
      <Route exact path="/mypagemodify" component={MypageModify} />
      <Route exact path="/restaurant" component={Restaurant} />
      <Route exact path="/restaurant/detail" component={RestaurantDetail} />
      <Route exact path="/mypageBoardReview" component={MypageBoardReview} />
      <Route exact path="/board/all" component={BoardAll} />
      <Route exact path="/board/free" component={BoardFree} />
      <Route exact path="/board/consulting" component={BoardConsulting} />
      <Route exact path="/board/tip" component={BoardTip} />
      <Route exact path="/board/notice" component={BoardNotice} />
      <Route exact path="/board/detail" component={BoardDetail} />
      <Route exact path="/board/write" component={BoardWrite} />
      <Route exact path="/board/edit" component={BoardEditWrite} />
      <Route component={Err404} />
    </Switch>
  );
};

export default Routes;
