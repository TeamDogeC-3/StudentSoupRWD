import Footer from '../common/footer';
import RestaurantNavbar from '../common/restaurantNavbar';
import RestaurantMain from './restaurantMain';

const Restaurant = () => {
  return (
    <div className='relative'>
      <RestaurantNavbar />
      <div className='min-h-screen'>
        <RestaurantMain />
      </div>
      <div className='static bottom-0 w-screen'>
        <Footer />
      </div>
    </div>
  );
};
export default Restaurant;
