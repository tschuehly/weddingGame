/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js}"],
  theme: {
    extend: {
      fontFamily:{
        'dancing-script':['dancing-script' ,"cursive"],
        'sans':['saira', 'ui-sans-serif', 'system-ui', '-apple-system', 'BlinkMacSystemFont', "Segoe UI", 'Roboto', "Helvetica Neue", 'Arial', "Noto Sans", 'sans-serif', "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji" ]
      },
    },
  },
  plugins: [
    require('@tailwindcss/forms'),
    require("daisyui")],
  daisyui:{
    themes:["pastel"]
  }
}
