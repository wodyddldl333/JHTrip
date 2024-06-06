const slider = document.querySelector(".slider");
const slides = document.querySelectorAll(".slide");

let counter = 1;
const slideWidth = slides[0].clientWidth;

function slide() {
  slider.style.transition = "transform 0.5s ease-in-out";
  slider.style.transform = `translateX(${-slideWidth * counter}px)`;
  counter++;

  if (counter === slides.length) {
    setTimeout(() => {
      counter = 1;
      slider.style.transition = "none";
      slider.style.transform = `translateX(0)`;
    }, 500);
  }
}
setInterval(slide, 3000);
