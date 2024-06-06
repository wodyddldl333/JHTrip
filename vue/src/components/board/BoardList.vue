<script setup>
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import { getBoards, searchBoard, getCount } from "@/apis/BoardApi";
import VPageNavigation from "@/commons/VPageNavigation.vue";

const router = useRouter();
const boards = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const search = ref({
  key: "",
  countPerPage: VITE_ARTICLE_LIST_SIZE,
  word: "",
  currentPage: currentPage.value,
});

const goCreate = () => {
  router.push({
    name: "BoardRegist",
  });
};
const getArticleList = () => {
  searchBoard(
    search.value,
    ({ data }) => {
      boards.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};

const onPageChange = (val) => {
  currentPage.value = val;
  search.value.currentPage = val;
  getArticleList();
};

const goDetail = (no) => {
  router.push({
    name: "BoardDetail",
    params: {
      no,
    },
  });
};

onMounted(() => {
  getCount(
    (response) => {
      totalPage.value = Math.ceil(response.data / 10);
    },
    (error) => {
      console.log("error");
    }
  );
  getArticleList();
});
</script>

<template>
  <div class="sub-upper">
    <h2>자유게시판</h2>
  </div>
  <section class="bulletin-board">
    <div class="fs-3 mb-3" style="color: rgb(177, 177, 177)">
      궁금한 여행지를 게시글을 통해 알아보기
    </div>
    <form
      class="selecting mb-3"
      @submit.prevent="sortPosts"
      style="text-align: right"
    >
      <input type="hidden" name="action" value="sort" />
      <select
        v-model="search.key"
        id="sort-cate"
        name="sort-cate"
        @change="sortPosts"
        style="
          font-family: 'Noto Sans KR', sans-serif;
          font-optical-sizing: auto;
          font-weight: 700;
          font-style: normal;
        "
      >
        <option value="" disabled>검색조건</option>
        <option value="title">제목</option>
        <option value="user_id">작성자</option>
      </select>
      <input v-model="search.word" type="text" class="ms-2" />
      <button class="btn btn-secondary ms-2 mb-1" @click="searchKeyword">
        검색
      </button>
    </form>
    <div v-if="boards.length > 0" class="bulletin-table">
      <div class="bulletin-row header">
        <div class="bulletin-cell">제목</div>
        <div class="bulletin-cell">작성자</div>
        <div class="bulletin-cell">조회수</div>
        <div class="bulletin-cell">작성일</div>
      </div>

      <div class="bulletin-row" v-for="item in boards" :key="item.no">
        <div class="bulletin-cell">
          <a href="#" @click.prevent="goDetail(item.no)"> {{ item.title }}</a>
        </div>
        <div class="bulletin-cell">{{ item.userId }}</div>
        <div class="bulletin-cell">{{ item.hit }}</div>
        <div class="bulletin-cell">{{ item.createdAt }}</div>
      </div>
      <div class="bulletin-row empty"></div>
    </div>
    <div v-else class="bulletin-cell" colspan="4">게시물이 없습니다.</div>
  </section>
  <div class="bottom">
    <VPageNavigation
      :current-page="currentPage"
      :total-page="totalPage"
      @pageChange="onPageChange"
    />
    <div class="btn-write">
      <button class="mt-3 btn btn-primary" @click="goCreate">글쓰기</button>
    </div>
  </div>
</template>

<style scoped>
.btn {
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 700;
  font-style: normal;
}
.bottom {
  margin-top: 20px;
  margin: 20px auto;
  max-width: 1200px;
}
.btn-write {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.selecting {
  display: flex;
  height: 40px;
  justify-content: flex-end;
}

input {
  width: 200px;
}
.sub-upper {
  position: relative;
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
main {
  position: relative;
  overflow: hidden;
  width: 100%;
  height: 910px;
  background-color: #fff;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}
.bulletin-board {
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  margin: 100px auto 0;
  max-width: 1200px;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}

.bulletin-board h2 {
  font-size: 24px;
  margin-bottom: 20px;
}

.bulletin-table {
  width: 100%;
}

.bulletin-row {
  display: flex;
  border-bottom: 1px solid #ccc;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}
a {
  text-decoration: none;
  color: #63b7e4;
}
a:hover {
  color: royalblue;
  font-weight: bold;
}

.bulletin-row.header {
  font-weight: bold;
  background-color: #f3f3f3;
}

.bulletin-cell {
  flex: 1;
  padding: 10px;
}

.bulletin-cell:first-child {
  flex: 6;
}

.bulletin-cell:last-child {
  flex: 2;
}

.bulletin-row.empty {
  text-align: center;
}

.bulletin-cell:not(:first-child) {
  text-align: center;
}

.bulletin-row:first-child {
  text-align: center;
}
</style>
