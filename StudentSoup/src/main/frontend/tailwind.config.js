/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      fontFamily: {
        sans: ['SDGothic', 'Helvetica', 'Arial', 'sans-serif'],
      },
      textColor: {
        orange: '#FF611D',
      },
      backgroundColor: {
        orange: '#FF611D',
      },
    },
  },
  plugins: [],
};
