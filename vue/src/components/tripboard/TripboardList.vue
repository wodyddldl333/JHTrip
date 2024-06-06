<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { localAxios } from "@/utils/http-commons";
import VPageNavigation from "@/commons/VPageNavigation.vue";

const axios = localAxios();
const router = useRouter();
const planList = ref([]);

const currentPage = ref(1);
const totalPage = ref(0);

const search = ref({
  countPerPage: 8,
  currentPage: currentPage.value,
});

const getList = () => {
  axios
    .get(`/plan/planlist`, { params: search.value })
    .then((response) => {
      planList.value = response.data;
      console.log(search.value);
    })
    .catch((error) => console.log(error));
};
const goDetail = (id) => {
  router.push({
    name: "plandetail",
    params: { id },
  });
};

const onPageChange = (val) => {
  currentPage.value = val;
  search.value.currentPage = val;
  getList();
};

const likeimageURL = [
  `/public/img/myplan_imgs/img1.jpg`,
  `/public/img/myplan_imgs/img2.jpg`,
  `/public/img/myplan_imgs/img3.jpg`,
  `/public/img/myplan_imgs/img4.jpg`,
  `/public/img/myplan_imgs/img5.jpg`,
  `/public/img/myplan_imgs/img6.jpg`,
  `/public/img/myplan_imgs/img7.jpg`,
  `/public/img/myplan_imgs/img8.jpg`,
  `/public/img/myplan_imgs/img9.jpg`,
  `/public/img/myplan_imgs/img10.jpg`,
];

const geturl = () => {
  return likeimageURL[Math.floor(Math.random() * 10)];
};

onMounted(() => {
  getList();
  axios
    .get(`/plan/countlist`)
    .then((response) => (totalPage.value = Math.ceil(response.data / 8)))
    .catch((error) => console.log(error));
});
</script>

<template>
  <div class="main-screen">
    <div class="sub-upper">
      <h2>여행갈까?</h2>
    </div>
    <div style="height: 100px"></div>
    <section class="triplist">
      <div class="container">
        <div class="row card-slider">
          <div
            v-for="item in planList"
            :key="item.id"
            class="col-md-3 card-col"
          >
            <div class="card">
              <img
                :src="geturl()"
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
    <div class="bottom">
      <VPageNavigation
        :current-page="currentPage"
        :total-page="totalPage"
        @pageChange="onPageChange"
      />
    </div>
  </div>
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
  margin-top: 250px;
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
.triplist-title {
  font-size: 30px;
  margin-top: 15px;
  margin-bottom: 30px;
  color: #b3b3b3;
}
</style>
