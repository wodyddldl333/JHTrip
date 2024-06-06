<script setup>
import { recommandImg, popularImg } from "@/apis/Map";
import { ref, reactive } from "vue";

import Footer from "@/commons/Footer.vue";
import Header from "@/commons/Header.vue";
import ChatbotView from "./ChatbotView.vue";

// 배경화면 이미지 랜덤으로 호출
let randomImage = ref("");
const homeImages = reactive(["garden", "jeju", "pusan", "korea"]);

function getImageUrl(name) {
  // 동적으로 이미지 호출하기 위한 함수
  return new URL(`/public/img/main_screen_imgs/${name}.jpg`, import.meta.url)
    .href;
}

function getRandomImage() {
  // 랜덤 이미지 가져오는 함수
  const randomIdx = Math.floor(Math.random() * homeImages.length);
  console.log(homeImages[randomIdx]);
  return homeImages[randomIdx];
}

randomImage = getRandomImage();

// 추천 여행지
const recommandImage = ref([]);
recommandImg(
  (response) => {
    recommandImage.value = response.data;
  },
  (error) => {
    alert("추천 여행지 불러오기 실패");
  }
);

// 실시간 인기 여행지
const popularImage = ref([]);
popularImg(
  (response) => {
    popularImage.value = response.data;
  },
  (error) => {
    alert("실시간 인기 여행지 불러오기 실패");
  }
);

// 챗봇
const isChatbotVisible = ref(false);
const goToChat = () => {
  isChatbotVisible.value = !isChatbotVisible.value;
};
</script>

<template>
  <Header />
  <div class="main-screen1">
    <img :src="getImageUrl(randomImage)" class="main-img" alt="Random Image" />
    <article class="main-title">
      <h2>우리나라의 곳곳의 여행지를 알아보고</h2>
      <h2>나만의 여행계획을 세워보는 건 어떨까요</h2>
    </article>
  </div>
  <div class="main-screen2 container-fluid">
    <div class="main-section">
      <h3>이런 여행지는 어떨까요 !</h3>
      <section>
        <button @click="goToChat" class="chatbot-toggler">
          <span
            class="material-symbols-outlined"
            :style="{ opacity: isChatbotVisible ? 1 : 0 }"
            >mode_comment</span
          >
          <span
            class="material-symbols-rounded"
            :style="{ opacity: isChatbotVisible ? 0 : 1 }"
            >mode_comment</span
          >
        </button>
        <transition name="slide-up-down">
          <div v-if="isChatbotVisible" id="chatbot-container">
            <ChatbotView />
          </div>
        </transition>
        <div
          class="row row-cols-3 row-cols-md-6 g-4 justify-content-center mt-5"
        >
          <div
            class="col"
            v-for="item in recommandImage"
            :key="item.contenetId"
          >
            <div class="card" style="width: 18rem">
              <img
                :src="item.firstImage"
                class="card-img-top"
                alt="..."
                width="286px"
                height="191px"
                loading="lazy"
              />
              <div class="card-body">
                <p class="card-text">
                  {{ item.title }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>

  <div class="main-screen3 container-fluid">
    <div class="main-section">
      <h3>사람들이 가장 많이 찾은 지역 !</h3>
      <section>
        <div
          class="row row-cols-3 row-cols-md-6 g-4 justify-content-center mt-5"
        >
          <div class="col" v-for="item in popularImage" :key="item.contenetId">
            <div class="card" style="width: 18rem">
              <img
                :src="item.firstImage"
                class="card-img-top"
                alt="..."
                width="286px"
                height="191px"
                loading="lazy"
              />
              <div class="card-body">
                <p class="card-text">
                  {{ item.title }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
    <Footer />
  </div>
</template>

<style scoped>
.chatbot-btn {
  cursor: pointer;
  position: fixed;
  right: 50px;
  bottom: 40px;
  z-index: 1000;
  background-color: black;
  border: 10px solid black;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  text-align: center;
}
.bot-image {
  width: 35px;
  height: 40px;
}
.card {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s, box-shadow 0.2s;
}

.card:hover {
  transform: translateY(-10px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2), 0 12px 40px rgba(0, 0, 0, 0.2);
}
.main-screen1 {
  position: relative;
  overflow: hidden;
  width: 100%;
  height: 960px;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}
.main-screen1 .main-img {
  position: fixed;
  top: 0;
  right: 0;
  left: 0;
  width: 100%;
  height: 100%;
  animation: zoomIn 20s ease 0.4s infinite;
  background: no-repeat center/cover;
}

.main-screen1 .main-title {
  position: relative;
  text-align: left;
  color: #000000;
}

.main-screen1 .main-title h2:nth-of-type(1) {
  margin-top: 260px;
}

.main-screen1 .main-title h2:nth-of-type(2) {
  margin-top: 10px;
}

.main-screen2 {
  padding-top: 100px;
  position: relative;
  width: 100%;
  background-color: #fff;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}

.main-screen3 {
  padding-top: 50px;
  position: relative;
  width: 100%;
  background-color: #fff;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}

.main-section {
  position: relative;
  padding-bottom: 80px;
}

h1 {
  margin-top: 30px;
}

h3 {
  text-align: center;
}

article {
  width: 1280px;
  margin: 0 auto;
}

@keyframes zoomIn {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
}
#chatbot-container {
  position: fixed;
  z-index: 999;
  bottom: 100px;
  right: 5px;
  width: 300px;
  height: 600px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.slide-up-down-enter-active,
.slide-up-down-leave-active {
  transition: transform 0.3s ease;
}

.slide-up-down-enter-from {
  transform: translateY(100%);
}

.slide-up-down-leave-to {
  transform: translateY(100%);
}
</style>
