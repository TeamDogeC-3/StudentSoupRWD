module.exports = {
  content: [
    './projectDogeWeb/src/main/resources/templates/*.{html,js}',
    './projectDogeWeb/src/main/resources/templates/**/*.{html,js}',
  ],
  theme: {
    fontFamily: {
      'sans': ['Kanit', 'sans-serif'],
    },
    extend: {
      keyframes: {
        moveInLeft: {
          '0%': { transform: "translateX(10rem)" },
          '80%': { transform: "translateX(1rem)" },
          '100%': { transform: "translateX(0)" }
        },
        moveInRight: {
          '0%': { transform: "translateX(10)" },
          '80%': { transform: "translateX(-1)" },
          '100%': { transform: "translate(0)" }
        },
        moveInBottom: {
          '0%': { transform: "translateY(3)" },
          '100%': { transform: "translateY(0)" }
        },
        wiggle: {
          '0%, 100%': { transform: 'rotate(-3deg)' },
          '50%': { transform: 'rotate(3deg)' },
        }
      },
      animation: {
        moveInLeft: "moveInLeft 2s",
        moveInRight: "moveInRight 2s",
        moveInBottom: "moveInBottom 2s",
        wiggle: 'wiggle 1s ease-in-out infinite',
      },
    },  
  },
};