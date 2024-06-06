<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const userInfo = ref(JSON.parse(sessionStorage.getItem("userInfo")));
const isScrolled = ref(false);

const handleScroll = () => {
  isScrolled.value = window.scrollY > 70;
};

const logout = () => {
  sessionStorage.removeItem("userInfo");
  active.value = !active.value;
  userInfo.value = null; // 사용자 정보를 제거하고 로그아웃 상태로 설정
  router.push({ name: "home" });
};

const active = ref(false);

// 세션 스토리지에 저장된 사용자 정보가 변경될 때마다 자동으로 업데이트
window.addEventListener("storage", () => {
  userInfo.value = JSON.parse(sessionStorage.getItem("userInfo"));
});

// 페이지가 로드될 때 세션 스토리지에서 사용자 정보를 가져와 초기화
onMounted(() => {
  userInfo.value = JSON.parse(sessionStorage.getItem("userInfo"));
  active.value = !active.value;
  window.addEventListener("scroll", handleScroll);
});
</script>

<template>
  <header id="header" :class="['header', { 'navbar-scrolled': isScrolled }]">
    <div class="inner">
      <div class="util-wrap">
        <ul class="util" v-if="userInfo" :class="{ isLogedIn: active }">
          <li>
            <a>{{ userInfo.name }}({{ userInfo.id }})님, 반갑습니다.</a>
          </li>
          <li>
            <a href="#"
              ><RouterLink :to="{ name: 'Settings' }">설정</RouterLink></a
            >
          </li>
          <li>
            <a href="#" @click.prevent="logout">로그아웃</a>
          </li>
        </ul>
        <ul class="util" v-else>
          <li>
            <a href="#"
              ><RouterLink :to="{ name: 'LoginView' }">로그인</RouterLink></a
            >
          </li>
          <li>
            <a href="#">
              <RouterLink :to="{ name: 'LoginSignUp' }">회원가입</RouterLink></a
            >
          </li>
        </ul>
      </div>
      <div class="nav-content">
        <RouterLink :to="{ name: 'home' }"
          ><img class="logo" src="/src/assets/img/newlogo.png" alt=""
        /></RouterLink>
        <nav class="content">
          <ul>
            <li>
              <RouterLink :to="{ name: 'NoticeView' }">공지사항</RouterLink>
            </li>
            <li>
              <RouterLink :to="{ name: 'SearchAttractionView' }"
                >관광지검색</RouterLink
              >
            </li>
            <li>
              <RouterLink :to="{ name: 'BoardList' }">자유게시판</RouterLink>
            </li>
            <li>
              <RouterLink :to="{ name: 'TripBoardView' }">여행갈까?</RouterLink>
            </li>
            <li>
              <RouterLink :to="{ name: 'MypageView' }">마이페이지</RouterLink>
            </li>
            <li>
              <RouterLink :to="{ name: 'aiplanSetup' }"
                >Ai 추천 여행 코스</RouterLink
              >
            </li>
          </ul>
        </nav>
      </div>
    </div>
  </header>
</template>

<style scoped>
.cart-img {
  width: 50px;
  height: 50px;
}
.header {
  position: fixed;
  z-index: 999;
  width: 100%;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}
.navbar-scrolled {
  background-color: #fff;
  box-shadow: 0 1px 0.3rem hsl(0deg 0% 80% / 80%);
}

.header.navbar-scrolled .util-wrap .util li a {
  color: black;
}

.header.navbar-scrolled .nav-content .content a {
  color: black;
}

.header.navbar-scrolled .util-wrap {
  border-bottom: 1px solid rgba(0, 0, 0, 0.2);
}

.inner {
  border-bottom: 1px solid rgba(247, 247, 247, 0.2);
}

.inner:hover {
  border-bottom: 1px solid rgba(0, 0, 0, 0.2);
}

.header.navbar-scrolled .inner {
  border-bottom: 1px solid rgba(0, 0, 0, 0.2);
}

.header:hover {
  background-color: #fff;
}

.header:hover .util-wrap .util li a {
  color: black;
}

.header:hover .nav-content .content a {
  color: black;
}

.header:hover .util-wrap {
  border-bottom: 1px solid rgba(0, 0, 0, 0.2);
}

.header .util-wrap {
  position: relative;
  display: flex;
  width: 100%;
  margin: 0 auto;
  padding: 12px 0;
  border-bottom: 1px solid rgba(247, 247, 247, 0.2);
}

.header .util-wrap .util {
  display: flex;
  justify-content: right;
  width: 1280px;
  margin: 0 auto;
}

ol,
ul {
  list-style: none;
}
.header .util-wrap .util li {
  position: relative;
  display: inline-block;
}
.header .util-wrap .util li a {
  display: block;
  margin: 0 8px;
  color: #fff;
}

.header .util-wrap .util li:nth-child(1):after,
.header .util-wrap .util.isLogedIn li:nth-child(2):after {
  position: absolute;
  top: 4px;
  right: 0;
  display: block;
  width: 1px;
  height: 15px;
  content: "";
  background-color: #fff;
}

.header:hover .util-wrap .util li:nth-child(1):after,
.header:hover .util-wrap .util.isLogedIn li:nth-child(2):after {
  position: absolute;
  top: 4px;
  right: 0;
  display: block;
  width: 1px;
  height: 15px;
  content: "";
  background-color: #000;
}

.header.navbar-scrolled .util-wrap .util li:nth-child(1):after,
.header.navbar-scrolled .util-wrap .util.isLogedIn li:nth-child(2):after {
  position: absolute;
  top: 4px;
  right: 0;
  display: block;
  width: 1px;
  height: 15px;
  content: "";
  background-color: #000;
}

.header .nav-content {
  position: relative;
  width: 1280px;
  margin: 0 auto;
  -webkit-transition: 0.5s;
  -o-transition: 0.5s;
  transition: 0.5s;
}

.header .nav-content .logo {
  position: absolute;
  z-index: 100;
  top: -12px;
  left: 0;
  height: 55px;
}

.header .nav-content .content {
  width: 1280px;
  margin: 30px auto;
}

.header .nav-content .content a {
  color: #fff;
}

a {
  text-decoration: none;
}

.header .nav-content .content > ul {
  position: relative;
  z-index: 90;
  margin: 0 150px auto;
  display: flex;
  flex-flow: row wrap;
  justify-content: space-around;
}

.header .nav-content .content > ul > li:nth-of-type(1) {
  width: 81px;
}

.header .nav-content .content > ul > li {
  display: inline-block;
  margin: 0 30px;
  vertical-align: top;
}

.header .nav-content .content > ul > li:nth-of-type(2) {
  margin-left: 10px;
}
</style>
