<script setup>
import Header from "@/commons/Header.vue";
import Footer from "@/commons/Footer.vue";

import VKakaoPlanMap from "@/commons/VKaKaoPlanMap.vue";
import ChatbotView from "@/views/ChatbotView.vue";
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { localAxios } from "@/utils/http-commons";
import Swal from "sweetalert2";

const axios = localAxios();
const route = useRoute();
const router = useRouter();

const userInfo = ref(JSON.parse(sessionStorage.getItem("userInfo")));
const userId = !userInfo.value ? "" : userInfo.value.id;

const viewAttraction = (attraction) => {
  selectAttraction.value = attraction;
};
const selectAttraction = ref({});

const plan = ref({
  userId: userId,
  planName: "",
  description: "",
  routePlans: [],
});

const tripList = JSON.parse(route.query.data);

const insertdata = () => {
  plan.value.routePlans = tripList.map((value, index) => ({
    contentId: value.contentId,
    sequence: index + 1,
  }));
};

insertdata();

const planId = ref(0);

const sendData = async () => {
  const jsonString = plan.value;

  await Swal.fire({
    title: "저장되었습니다 ! ",
    icon: "success",
  });
  try {
    await axios.post(`/plan/write`, plan.value).then((response) => {
      planId.value = response.data;
    });
    goMyPage();
  } catch (error) {
    console.error("Error", error);
  }
};

const goDetail = (id) => {
  router.push({
    name: "MyPlanView",
    params: { id },
  });
};

const goMyPage = () => {
  router.push({
    name: "MypageView",
  });
};
</script>

<template>
  <Header />
  <div class="main-screen">
    <div class="sub-upper">
      <h2>나의 여행 계획</h2>
    </div>
    <div style="height: 350px"></div>
    <div id="container_plan">
      <div class="plan-title">
        <input
          type="text"
          v-model="plan.planName"
          id="title"
          name="title"
          class="title"
          placeholder="제목을 입력하세요"
          required
        />
      </div>

      <div class="container_good">
        <span v-if="isLike" class="material-symbols-outlined good">
          <a
            href="#"
            @click.prevent="likeThis"
            style="color: red; font-weight: 1000"
            >heart_check</a
          >
        </span>
      </div>
      <div></div>

      <div class="container_map" style="max-width: 1280px; margin: 40px auto 0">
        <VKakaoPlanMap
          style="width: 860px; margin: 0"
          :attractions="tripList"
          :selectAttraction="viewAttraction"
        />
        <div class="container_list">
          <div class="info_title">여행 경로</div>
          <li
            class="itemList"
            v-for="(attraction, index) in tripList"
            :key="attraction.contentId"
          >
            <div class="fs-4 me-2">{{ index + 1 }}</div>
            <img
              :src="
                attraction.firstImage
                  ? attraction.firstImage
                  : '/img/noImage.jpg'
              "
              width="100px"
              height="100px"
              style="border-radius: 20%"
            />
            <div class="itemInfo">
              <div class="fs-5">{{ attraction.title }}</div>
              <div>{{ attraction.addr1 }}</div>
            </div>
          </li>
        </div>
      </div>

      <!-- 여행 계획 설명 -->
      <div class="mt-4" style="margin-bottom: 30px">
        <h2 style="margin-bottom: 20px">상세 설명</h2>
        <div class="fs-4 content">
          <textarea
            v-model="plan.description"
            id="description"
            rows="5"
            title="description"
            style="width: 100%"
          ></textarea>
        </div>
      </div>
      <button class="btn btn-primary save" @click="sendData">저장하기</button>
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
.title {
  border: none;
  font-size: 40px;
}
.save {
  margin-bottom: 20px;
  float: center;
  width: 300px;
  height: 50px;
}
.plan-title .title {
  width: 100%;
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
  margin-top: 10px;
  height: 10px;
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
