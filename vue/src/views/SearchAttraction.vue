<script setup>
import { ref, onMounted, defineProps } from "vue";
import { listSido } from "@/apis/Map";
import { searchAttraction, getInfo } from "@/apis/Map";
import Footer from "@/commons/Footer.vue";
import Header from "@/commons/Header.vue";
import VKakaoMap from "@/commons/VKakaoMap.vue";
import VSelect from "@/commons/VSelect.vue";
import ChatbotView from "./ChatbotView.vue";
import CartView from "./CartView.vue";

const category = ref({
  area: null,
  content: null,
  keyword: null,
});
const selectAttraction = ref({});
const attractions = ref([]);
const sidoList = ref([]);

// cart
const isCartVisible = ref(false);
const cartCount = ref(0);
const props = defineProps([]);
const cartList = ref([]);
const attractionList = ref([]);
const num = ref(0);
const addCart = (attraction) => {
  cartList.value.push({ contentId: attraction.contentId, sequence: 0 });
  attractionList.value.push(attraction);
  cartCount.value++;
};

const removeCart = () => {
  cartCount.value--;
};

const goToCart = () => {
  isCartVisible.value = !isCartVisible.value;
};

// const updateAttractions = (newAttractions) => {
//   attractionList.value = newAttractions;
// };

// chatbot
const isChatbotVisible = ref(false);
const goToChat = () => {
  isChatbotVisible.value = !isChatbotVisible.value;
};

const getSidoList = () => {
  listSido(
    ({ data }) => {
      let options = [];
      options.push({ text: "시도선택", value: "" });
      data.forEach((sido) => {
        options.push({ text: sido.sidoName, value: sido.sidoCode });
      });
      console.log(options);
      sidoList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

const viewAttraction = (attraction) => {
  selectAttraction.value = attraction;
};
const search = () => {
  searchAttraction(
    category.value,
    (response) => {
      if (response.data.length == 0) {
        alert("검색 결과가 없습니다.");
        return;
      }
      attractions.value = response.data;
      console.log(attractions.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

onMounted(() => {
  getSidoList();
});

const detailInfo = ref({
  contentId: 0,
  contentTypeId: 0,
  title: "",
  addr1: "",
  tel: "",
  firstImage: "",
  latitude: 0,
  longitude: 0,
  sidoCode: 0,
  gugunCode: 0,
  overView: "",
});

const getitem = (id) => {
  getInfo(
    id,
    (response) => {
      detailInfo.value = response.data;
      console.log(detailInfo.value);
    },
    (error) => {
      alert("문제 발생");
    }
  );
};
</script>

<template>
  <Header />
  <div class="main-screen">
    <div class="sub-upper">
      <h2>관광지 검색</h2>
    </div>
    <div style="height: 350px"></div>
    <div class="big title">
      <p class="map_title text-center">ENJOY YOUR LIFE, LOVE YOUR TRIP!!</p>
    </div>
    <div>
      <p
        class="map_little_title text-center"
        style="
          font-family: 'Noto Sans KR', sans-serif;
          font-optical-sizing: auto;
          font-weight: 600;
          font-style: normal;
        "
      >
        원하는 지역과 관광지 유형, 관광지명을 입력하세요.
      </p>
    </div>
    <div style="max-width: 1280px; margin: 60px auto 0">
      <div class="alert alert-primary text-center" role="alert">
        INFORMATION
      </div>
    </div>
    <div class="search-container">
      <VSelect :attractionOption="sidoList" :category="category" />
      <select
        v-model="category.content"
        id="search-content-id"
        name="search-content"
        class="form-select me-2"
        style="margin-left: 3px"
      >
        <option value="0" selected>관광지 유형</option>
        <option value="12">관광지</option>
        <option value="14">문화시설</option>
        <option value="15">축제공연행사</option>
        <option value="25">여행코스</option>
        <option value="28">레포츠</option>
        <option value="32">숙박</option>
        <option value="38">쇼핑</option>
        <option value="39">음식점</option>
      </select>
      <input
        v-model="category.keyword"
        id="search-keyword"
        class="form-control me-2"
        name="keyword-search"
        type="search"
        placeholder="검색어"
        aria-label="검색어"
      />
      <button
        id="btn-search"
        class="btn btn-outline-success me-2"
        @click="search"
      >
        search
      </button>

      <button id="btn-reset" class="btn btn-outline-danger" type="submit">
        reset
      </button>
    </div>
    <VKakaoMap :attractions="attractions" :selectAttraction="viewAttraction" />

    <div style="height: 50px"></div>
    <div class="row">
      <p class="detail">찾으신 관광지(지역)의 상세정보</p>
      <table class="table table-striped">
        <thead>
          <tr>
            <th>대표이미지</th>
            <th>관광지명</th>
            <th>주소</th>
            <th>추가</th>
            <th>삭제</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="attraction in attractions" :key="attraction.contentId">
            <td v-if="attraction.firstImage == ''">
              <a
                href="#"
                @click.prevent="getitem(attraction.contentId)"
                data-bs-toggle="modal"
                data-bs-target="#exampleModal"
              >
                <img src="https://via.placeholder.com/150" width="100px" />
              </a>
            </td>
            <td v-else>
              <a
                href="#"
                @click.prevent="getitem(attraction.contentId)"
                data-bs-toggle="modal"
                data-bs-target="#exampleModal"
              >
                <img :src="attraction.firstImage" width="100px" />
              </a>
            </td>
            <td class="marker-title">{{ attraction.title }}</td>
            <td>{{ attraction.addr1 }}</td>
            <td class="marker-add">
              <button
                type="button"
                class="btn btn-primary"
                @click="addCart(attraction)"
              >
                +
              </button>
            </td>
            <td class="marker-remove">
              <button type="button" class="btn btn-danger">-</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Modal -->
    <div
      class="modal fade"
      id="exampleModal"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">
              {{ detailInfo.title }}
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <img
              v-if="!detailInfo.firstImage"
              class="modal-img"
              src="https://via.placeholder.com/150"
              alt=""
            />
            <img
              width="400px"
              v-if="detailInfo.firstImage"
              class="modal-img"
              :src="detailInfo.firstImage"
              alt=""
            />
            <div class="modal-addr">
              <h5 style="font-weight: bold">{{ detailInfo.addr1 }}</h5>
            </div>
            <div class="modal-overview">
              {{ detailInfo.overView }}
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
            <button type="button" class="btn btn-primary">Understood</button>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal end -->

    <!-- cart -->
    <div class="cart">
      <a href="#" @click="goToCart">
        <img src="/public/img/cart.png" class="cart-img" alt="" />
      </a>
      <div class="cc">
        <span style="font-size: 15px">{{ cartCount }}</span>
      </div>
    </div>
    <div v-if="isCartVisible" id="cart-container">
      <CartView :attractions="attractionList" @decrement="removeCart" />
    </div>
    <!-- cart end-->

    <!-- chatbot -->
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
    <!-- chatbot end -->

    <Footer />
  </div>
</template>

<style scoped>
.cc {
  text-align: center;
  color: white;
  background-color: red;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  position: absolute;
  top: -10px;
  right: -3px;
}
.cart-img {
  margin: 0 auto;
  display: flex;
  width: 40px;
  height: 40px;
  display: inline-block;
}

.cart {
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60px;
  width: 50px;
  position: fixed;
  cursor: pointer;
  width: 60px;
  height: 60px;
  z-index: 1000;
  right: 100px;
  bottom: 30px;
  border-radius: 50%;
  background-color: gray;
}
#trip-list {
  vertical-align: middle;
}

.row {
  max-width: 1280px;
  margin: 0 auto 20px;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}

.bot-image {
  width: 35px;
  height: 40px;
}
#cart-container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;

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
</style>
