<script setup>
import Header from "@/commons/Header.vue";
import Footer from "@/commons/Footer.vue";
import Swal from "sweetalert2";

import { getAiRoute } from "@/apis/Chatbot";
import { getAttractions } from "@/apis/Plan";
import { ref } from "vue";
import { useRouter } from "vue-router";

const cities = [
  { value: "1", name: "서울" },
  { value: "6", name: "부산" },
  { value: "4", name: "대구" },
  { value: "2", name: "인천" },
  { value: "5", name: "광주" },
  { value: "3", name: "대전" },
  { value: "7", name: "울산" },
  { value: "8", name: "세종" },
  { value: "31", name: "경기" },
  { value: "32", name: "강원" },
  { value: "33", name: "충북" },
  { value: "34", name: "충남" },
  { value: "37", name: "전북" },
  { value: "38", name: "전남" },
  { value: "35", name: "경북" },
  { value: "36", name: "경남" },
  { value: "39", name: "제주" },
];

const categories = [
  { value: "12", name: "관광지" },
  { value: "14", name: "문화시설" },
  { value: "15", name: "축제공연행사" },
  { value: "25", name: "여행코스" },
  { value: "28", name: "레포츠" },
  { value: "32", name: "숙박" },
  { value: "38", name: "쇼핑" },
  { value: "39", name: "음식점" },
];

const router = useRouter();
const nextStep = ref(true);

const data = ref([]);
const contentList = ref([]);

const attractions = ref({
  description: "",
  contents: [],
});

const keyword = ref({
  sidoCode: 0,
  contentIds: [],
});

const makePlan = async () => {
  let timerInterval;

  Swal.fire({
    title: "AI 여행 코스 생성중...",
    html: "잠시만 기다려 주세요.",
    allowOutsideClick: false,
    didOpen: () => {
      Swal.showLoading();
    },
    willClose: () => {
      clearInterval(timerInterval);
    },
  });

  await getAiRoute(
    keyword.value,
    (response) => {
      const jsonData = response.data;
      data.value = JSON.parse(jsonData.choices[0].text.replace(/\n/g, ""));
      data.value.forEach((item) => {
        if (item.content_id != 0) {
          contentList.value.push(item.content_id);
        }
      });
      attractions.value.description = data.value[data.value.length - 1].title;
      console.log(attractions.value.description);
    },
    (error) => {
      console.log(error);
    }
  );

  if (contentList.value) {
    getAttractions(
      contentList.value,
      (response) => {
        attractions.value.contents = response.data;
        Swal.close();
        goAiPlanView();
      },
      (error) => console.log(error)
    );
  }
};

const goAiPlanView = () => {
  router.push({
    name: "aiplanView",
    query: { data: JSON.stringify(attractions) },
  });
};

const goNext = () => {
  nextStep.value = !nextStep.value;
};
</script>

<template>
  <Header />
  <div class="main-screen">
    <div class="sub-upper">
      <h2>AI 추천 여행 코스</h2>
    </div>
    <div v-if="nextStep" class="container">
      <div class="top">
        <em>이번 여행, 어디로 떠나볼까요?</em>
        <h4><strong>여행을 떠나고 싶은 지역을</strong> 선택해주세요</h4>
      </div>
      <div class="middle">
        <div v-for="(city, index) in cities" :key="index" class="city">
          <input
            type="radio"
            :id="'area_chk' + city.value"
            name="areaChk"
            :value="city.value"
            v-model="keyword.sidoCode"
          />
          <label :for="'area_chk' + city.value"
            ><span>{{ city.name }}</span></label
          >
        </div>
      </div>
      <div class="bottom">
        <button class="btn btn-primary next" @click="goNext">다음으로</button>
      </div>
    </div>
    <div v-else class="container-second">
      <div class="top">
        <em>가고싶은 관광지를 선택해볼까요?</em>
        <h4>
          <strong>원하는 카테고리를 2개 이상</strong> 선택해주세요(최대 4개)
        </h4>
      </div>
      <div class="middle-second">
        <div
          v-for="(category, index) in categories"
          :key="index"
          class="category"
        >
          <input
            type="checkbox"
            :id="'area_chk' + category.value"
            :value="category.value"
            v-model="keyword.contentIds"
          />
          <label :for="'area_chk' + category.value"
            ><span>{{ category.name }}</span></label
          >
        </div>
      </div>
      <div class="bottom">
        <button class="btn btn-primary next" @click="makePlan">다음으로</button>
      </div>
    </div>
  </div>
  <Footer />
</template>

<style scoped>
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
.container-second {
  display: flex;
  flex-direction: column;
  height: calc(100%-200px);
  padding: 20px;
  width: 700px;
  margin: 320px auto 50px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1);
  border-radius: 20px;
}
.container {
  display: flex;
  flex-direction: column;
  height: calc(100%-200px);
  padding: 20px;
  width: 700px;
  margin: 320px auto 50px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1);
  border-radius: 20px;
}

.top {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}

.top em {
  margin-bottom: 20px;
}

.top h4 {
  font-size: 34px;
  line-height: 38px;
  letter-spacing: -2.5px;
  font-weight: 400;
  color: #333;
  margin: 0;
}

.top h4 strong {
  display: block;
  font-weight: 800;
}

.middle {
  margin-top: 50px;
  flex: 2;
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 10px;
  align-items: center;
  justify-items: center;
  margin-bottom: 50px;
}
.middle-second {
  margin-top: 50px;
  flex: 2;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  justify-content: center;
  align-content: center;
  width: 80%;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 50px;
}
.city {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100px;
  height: 100px;
  background-color: lightblue;
  border-radius: 50%;
  position: relative;
}

.city input[type="radio"] {
  display: none;
}

.city label {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  cursor: pointer;
}

.city input[type="radio"]:checked + label {
  background-color: deepskyblue;
  color: white;
  border-radius: 50%;
}

.city label span {
  display: block;
  text-align: center;
}

.category {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100px;
  height: 100px;
  background-color: lightblue;
  border-radius: 50%;
  position: relative;
}

.category input[type="checkbox"] {
  display: none;
}

.category label {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  cursor: pointer;
}

.category input[type="checkbox"]:checked + label {
  background-color: deepskyblue;
  color: white;
  border-radius: 50%;
}

.category label span {
  display: block;
  text-align: center;
}

.bottom {
  flex: 1;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.next {
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
}
</style>
