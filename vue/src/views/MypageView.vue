<script setup>
import { ref, onMounted, computed, reactive } from "vue";
import ChatbotView from "./ChatbotView.vue";
import Footer from "@/commons/Footer.vue";
import Header from "@/commons/Header.vue";
import { useRouter, useRoute } from "vue-router";
import { fetchImage } from "@/apis/UserApi";

import { localAxios } from "@/utils/http-commons";

const axios = localAxios();
const userInfo = ref(JSON.parse(sessionStorage.getItem("userInfo")));
const userId = !userInfo.value ? "" : userInfo.value.id;
const router = useRouter();
const route = useRoute();

const cnt = ref(0);
let randomImage = "";
let randomLikeImage = "";

const myPageImg = reactive([
  "img1",
  "img2",
  "img3",
  "img4",
  "img5",
  "img6",
  "img7",
  "img8",
  "img9",
  "img10",
]);

const eachPlan = ref([]);
const eachLikePlan = ref([]);
const myheart = ref(0);

const id = route.params.id;

const goDetail = (id) => {
  router.push({
    name: "MyPlanView",
    params: { id },
  });
};
const imageUrl = ref("");

fetchImage(
  userInfo.value.id,
  (response) => {
    const url = URL.createObjectURL(response.data);
    imageUrl.value = url;
  },
  (error) => {
    alert.error("에러 발생");
  }
);

function getImageUrl(name) {
  // 동적으로 이미지 호출하기 위한 함수
  return new URL(`/public/img/myplan_imgs/${name}.jpg`, import.meta.url).href;
}

function getRandomImage() {
  // 랜덤 이미지 가져오는 함수
  const randomIdx = Math.floor(Math.random() * myPageImg.length);
  console.log(myPageImg[randomIdx]);
  return myPageImg[randomIdx];
}

const rImage = () => {
  return (randomImage = getRandomImage());
};

const lImgae = () => {
  return (randomLikeImage = getRandomImage());
};

const getData = async () => {
  try {
    const response = await axios.get(`plan/views/${userId}`, userId);
    eachPlan.value = response.data;
    console.log("Success", response.data);
  } catch (error) {
    console.error("Error", error);
  }
};

const getLikeData = async () => {
  try {
    const response = await axios.get(`plan/getlikeplans/${userId}`, userId);
    eachLikePlan.value = response.data;
    console.log("Success", response.data);
  } catch (error) {
    console.error("Error", error);
  }
};

const getMyHeart = async () => {
  try {
    const response = await axios.get(`plan/countheart/${userId}`, userId);
    myheart.value = response.data;
  } catch (error) {
    console.error("Error", error);
  }
};

const isChatbotVisible = ref(false);

const goToChat = () => {
  console.log(isChatbotVisible.value);
  isChatbotVisible.value = !isChatbotVisible.value;
};

const currentIndex = ref(0);
const containerWidth = ref(0);

const totalSlides = computed(() => Math.ceil(eachPlan.value.length / 4));

const slideRight = () => {
  if (currentIndex.value < totalSlides.value - 1) {
    currentIndex.value++;
  }
};
const displayedCards = computed(() => {
  const start = currentIndex.value * 4;
  return eachPlan.value.slice(start, start + 4);
});

const slideLeft = () => {
  if (currentIndex.value > 0) {
    currentIndex.value--;
  }
};

const updateSlider = () => {
  const container = document.querySelector(".container-custom");
  if (container) {
    containerWidth.value = container.offsetWidth;
  }
};

const currentIndex1 = ref(0);
const containerWidth1 = ref(0);

const displayedLikeCards = computed(() => {
  const start = currentIndex1.value * 4;
  return eachLikePlan.value.slice(start, start + 4);
});

const totalSlides1 = computed(() => Math.ceil(eachLikePlan.value.length / 4));

const slideRight1 = () => {
  if (currentIndex1.value < totalSlides1.value - 1) {
    currentIndex1.value++;
  }
};

const slideLeft1 = () => {
  if (currentIndex1.value > 0) {
    currentIndex1.value--;
  }
};

const updateSlider1 = () => {
  const container = document.querySelector(".container-custom");
  if (container) {
    containerWidth1.value = container.offsetWidth;
  }
};

const plandetail = (id) => {
  router.push({
    name: "plandetail",
    params: { id },
  });
};

onMounted(() => {
  window.addEventListener("resize", updateSlider);
  updateSlider();
  updateSlider1();
  getData();
  getLikeData();
  getMyHeart();
});
</script>

<template>
  <Header />
  <div class="main-screen">
    <div class="sub-upper">
      <h2>마이페이지</h2>
    </div>
    <div style="height: 100px"></div>
    <section class="info">
      <div class="profile">
        <img
          :src="imageUrl"
          alt=""
          style="margin-top: 20px; border-radius: 20px"
          height="150px"
        />
      </div>
      <div class="category">
        <div class="compo">
          <p class="title"><strong>내가 만든 리스트</strong></p>
          <div class="ele">{{ eachPlan.length }}</div>
        </div>
        <div class="vertical-line"></div>
        <div class="compo">
          <p class="title"><strong>등급</strong></p>
          <div class="ele">새싹</div>
        </div>
        <div class="vertical-line"></div>
        <div class="compo">
          <p class="title"><strong>받은 하트</strong></p>
          <div class="ele">{{ myheart }}</div>
        </div>
      </div>
    </section>

    <!-- 내가 만든 여행 계획 -->
    <section class="triplist">
      <div class="trip-title">
        <h2
          style="
            font-family: 'Noto Sans KR', sans-serif;
            font-optical-sizing: auto;
            font-weight: 600;
            font-style: normal;
          "
        >
          내가 만든 나만의 여행리스트
        </h2>
      </div>
      <div class="container">
        <button
          class="slider-btn btn-prev"
          @click="slideLeft"
          v-if="currentIndex > 0"
        >
          <img src="/public/img/arrow_left.png" alt="" class="slide-img-left" />
        </button>
        <button
          class="slider-btn btn-next"
          @click="slideRight"
          v-if="currentIndex < totalSlides - 1"
        >
          <img
            src="/public/img/arrow_right.png"
            alt=""
            class="slide-img-right"
          />
        </button>
        <div
          class="row card-slider"
          :style="{
            transform: `translateX(-${currentIndex * containerWidth}px)`,
          }"
        >
          <div
            class="col-md-3 card-col"
            v-for="(item, index) in displayedCards"
            :key="index"
          >
            <div class="card">
              <img
                :src="getImageUrl(rImage())"
                class="card-img-top"
                style="height: 400px; cursor: pointer"
                alt="..."
                @click="goDetail(item.id)"
              />
              <div class="card-body">
                <h5 class="card-title">{{ item.planName }}</h5>
                <p class="card-text">{{ item.description }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 내가 좋아요 한 게시물 -->
    <section class="triplist">
      <div class="trip-title">
        <h2
          style="
            font-family: 'Noto Sans KR', sans-serif;
            font-optical-sizing: auto;
            font-weight: 700;
            font-style: normal;
          "
        >
          내가 관심있는 여행리스트
        </h2>
      </div>
      <div class="container">
        <button
          class="slider-btn btn-prev"
          @click="slideLeft1"
          v-if="currentIndex1 > 0"
        >
          <img src="/public/img/arrow_left.png" alt="" class="slide-img-left" />
        </button>
        <button
          class="slider-btn btn-next"
          @click="slideRight1"
          v-if="currentIndex1 < totalSlides1 - 1"
        >
          <img
            src="/public/img/arrow_right.png"
            alt=""
            class="slide-img-right"
          />
        </button>
        <div
          class="row card-slider"
          :style="{
            transform: `translateX(-${currentIndex1 * containerWidth1}px)`,
          }"
        >
          <div
            class="col-md-3 card-col"
            v-for="(item, index) in displayedLikeCards"
            :key="index"
          >
            <div class="card">
              <img
                :src="getImageUrl(lImgae())"
                class="card-img-top"
                style="height: 400px; cursor: pointer"
                alt="..."
                @click="plandetail(item.id)"
              />
              <div class="card-body">
                <h5 class="card-title">{{ item.planName }}</h5>
                <p class="card-text">{{ item.description }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
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
  </div>
  <Footer />
</template>

<style scoped>
.card-slider {
  display: flex;
  transition: transform 0.5s ease-in-out;
}
.slider-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  z-index: 1000;
}
.btn-prev {
  transition: 0.2s;
  position: absolute;
  width: 40px;
  height: 50px;
  border-radius: 50%;
  left: 0px;
}
.slide-img-right {
  width: 25px;
  height: 35px;
  position: absolute;
  right: 3px;
  bottom: 5px;
}
.slide-img-left {
  width: 25px;
  height: 35px;
  position: absolute;
  left: 3px;
  bottom: 5px;
}
.btn-next {
  transition: 0.2s;
  position: absolute;
  width: 40px;
  height: 50px;
  border-radius: 50%;
  right: 0;
}

.card-col:hover {
  transform: translateY(-10px);
}
.card-col {
  margin-top: 20px;
  margin-bottom: 20px;
  transition: transform 0.2s, box-shadow 0.2s;
}

.card {
  border: 1px solid black;
}
.container {
  width: 1280px;
  overflow: hidden;
  position: relative;
  margin-bottom: 50px;
  vertical-align: center;
  border-radius: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1);
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}

.trip-title {
  width: 1280px;
  margin: 0 auto 30px;
}

.compo {
  padding: 0 20px;
  text-align: center;
}

.vertical-line {
  width: 1px;
  height: 50px;
  background-color: black;
  margin: 0 20px;
}

.title,
.ele {
  margin-bottom: 0.5rem;
}

.category {
  align-items: center;
  display: flex;
  justify-content: center;
  width: 500px;
  padding-top: 10px;
  margin: 30px auto 0;
  border-radius: 20px;
  border: 1px solid black;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1);
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}
.profile {
  text-align: center;
}
.info {
  width: 1280px;
  height: 350px;
  margin: 0 auto;
}
.main-screen {
  position: relative;
  overflow: hidden;
  width: 100%;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}
.sub-upper {
  width: 100%;
  height: 250px;
  background-color: #575454;
}
.sub-upper h2 {
  padding-top: 170px;
  text-align: center;
  word-break: keep-all;
  color: #fff;
  font-size: 2rem;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}

#chatbot-container {
  position: fixed;
  z-index: 999;
  bottom: 90px;
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

.card-text {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.card-title {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
