<script setup>
import { ref, onMounted } from "vue";
import Footer from "@/commons/Footer.vue";
import Header from "@/commons/Header.vue";
import VKakaoPlanMap from "@/commons/VKaKaoPlanMap.vue";
import ChatbotView from "@/views/ChatbotView.vue";
import {
  getRoute,
  getDetailPlan,
  getlikes,
  likePlan,
  unlikePlan,
  isLikePlan,
} from "@/apis/Plan";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const id = route.params.id;
const router = useRouter();

const userInfo = ref(JSON.parse(sessionStorage.getItem("userInfo")));
const userId = !userInfo.value ? "" : userInfo.value.id;

const isLike = ref(false);

isLikePlan(
  { id: id, userId: userId },
  (response) => {
    if (response.data == 1) {
      isLike.value = true;
    }
  },
  (error) => console.log(error)
);

const likeThis = () => {
  likePlan(
    { id: id, userId: userId },
    (response) => {
      isLike.value = !isLike.value;
      like.value++;
    },
    (error) => console.log(error)
  );
};

const unlikeThis = () => {
  unlikePlan(
    { id: id, userId: userId },
    (response) => {
      isLike.value = !isLike.value;
      like.value--;
    },
    (error) => console.log(error)
  );
};
const category = ref({
  area: null,
  content: null,
  keyword: null,
});

const attractions = ref([]);

const planInfo = ref({
  id: 0,
  userId: "",
  planName: "",
  description: "",
  hit: 0,
  routePlans: null,
  createdAt: "",
});

const like = ref(0);

getlikes(
  id,
  (response) => {
    like.value = response.data;
  },
  (error) => {
    console.log(error);
  }
);
getDetailPlan(
  id,
  (response) => {
    planInfo.value = response.data;
    planInfo.value.createdAt = planInfo.value.createdAt.split(" ")[0];
  },
  (error) => {
    console.log(error);
  }
);
getRoute(
  id,
  (response) => {
    attractions.value = response.data;
  },
  (error) => {
    console.log(error);
  }
);

const isChatbotVisible = ref(false);

const goToChat = () => {
  isChatbotVisible.value = !isChatbotVisible.value;
};

const viewAttraction = (attraction) => {
  selectAttraction.value = attraction;
};

const share = () => {
  router.push({
    name: "TripBoardList",
  });
};
</script>

<template>
  <Header />
  <div class="main-screen">
    <div class="sub-upper">
      <h2>여행갈까?</h2>
    </div>
    <div style="height: 350px"></div>
    <div id="container_plan">
      <div class="big title">
        <h2>{{ planInfo.planName }}</h2>
      </div>
      <span style="margin-top: 10px">작성자 : {{ planInfo.userId }}</span>
      <br />
      <span id="date">작성일 : {{ planInfo.createdAt }}</span>
      <div class="container_good">
        <div class="left-elements">
          <span v-if="isLike" class="material-symbols-outlined good">
            <a
              href="#"
              @click.prevent="unlikeThis"
              style="color: red; font-weight: 1000"
              >heart_check</a
            >
          </span>
          <span v-if="!isLike" class="material-symbols-outlined good">
            <a href="#" @click.prevent="likeThis">favorite</a>
          </span>
          <span class="num_good" style="margin-left: 10px">{{ like }}</span>
          <span
            class="material-symbols-outlined good"
            style="margin-left: 10px">
            visibility
          </span>
          <span class="num_good" style="margin-left: 10px">{{
            planInfo.hit
          }}</span>
        </div>
      </div>
      <div></div>

      <div class="container_map" style="max-width: 1280px; margin: 60px auto 0">
        <VKakaoPlanMap
          style="width: 860px; margin: 0"
          :attractions="attractions"
          :selectAttraction="viewAttraction" />
        <div class="container_list">
          <div class="info_title">여행 경로</div>
          <li
            class="itemList"
            v-for="(attraction, index) in attractions"
            :key="attraction.contentId">
            <div class="fs-4 me-2">{{ index + 1 }}</div>
            <img
              :src="
                attraction.firstImage
                  ? attraction.firstImage
                  : '/img/noImage.jpg'
              "
              width="100px"
              height="100px"
              style="border-radius: 20%" />
            <div class="itemInfo">
              <div class="fs-5">{{ attraction.title }}</div>
              <div>{{ attraction.addr1 }}</div>
            </div>
          </li>
        </div>
      </div>

      <div class="mt-5">
        <div class="fs-4 content">{{ planInfo.description }}</div>
      </div>
    </div>

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
.left-element {
  display: flex;
  align-items: center;
}
.share {
  margin-left: auto;
}
.container_map {
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid #ddd;
  padding-bottom: 30px;
}
#container_plan {
  max-width: 1250px;
  margin: 0 auto;
}
.info_title {
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 20px;
  color: #444;
}
.good {
  margin-top: 10px;
  line-height: 10px;
  height: 20px;
  vertical-align: middle;
}
.good a {
  color: black;
  text-decoration: none;
}
.container_good {
  display: flex;
  margin-top: 10px;
  height: 50px;
  align-items: center;
  border-bottom: 1px solid #ddd;
}
.num_good {
  margin-bottom: 10px;
}

.foot_good > a {
  float: right;
  margin-right: 20px;
  height: 40px;
  line-height: 40px;
  margin-top: auto;
}

.row {
  max-width: 1280px;
  margin: 0 auto;
}
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
.map {
  position: relative;
  margin: 30px auto 300px;
  width: 1280px;
  height: 400px;
}
.sub-upper {
  position: absolute;
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

.main-screen {
  position: relative;
  overflow: hidden;
  width: 100%;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}

.map_title {
  font-weight: bold;
  color: rgb(105, 105, 105);
  font-size: 30px;
}
.map_little_title {
  color: black;
  font-size: 18px;
}
.detail {
  font-weight: bold;
  color: black;
  font-size: 18px;
  height: 40px;
  border-bottom: 3px solid black;
}

.search-container {
  display: flex;
  margin: 20px auto;
  align-items: center;
  max-width: 1280px;
  height: 50px;
  border: 1px solid #ccc;
  border-radius: 5px;
  overflow: hidden;
}

/* 여행경로 Item 관련 스타일 */
.container_list {
  overflow-y: auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  height: 700px;
}
.itemList {
  margin: 15px 0;
  display: flex;
  align-items: center;
  background-color: #ffffff;
  padding: 10px 0;
  border-radius: 10px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
  width: 330px;
}
.itemList:hover {
  transform: translateY(-10px);
  transform: scale(1.1);
}
.itemList img {
  border-radius: 10px;
}
.itemInfo {
  margin-left: 15px;
}
::-webkit-scrollbar {
  width: 12px;
  height: 12px;
}
::-webkit-scrollbar-thumb {
  background: #a8a8a8;
  border-radius: 6px;
}
::-webkit-scrollbar-thumb:hover {
  background: #796bf5;
}
::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 6px;
  box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
}
</style>
