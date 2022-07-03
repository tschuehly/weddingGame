/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js}"],
  theme: {
    fontFamily:{
      'title':['Dancing Script']
    },
    extend: {},
  },
  plugins: [
    require('@tailwindcss/forms'),
    require("daisyui")],
  daisyui:{
    themes:["pastel"]
  }
}
