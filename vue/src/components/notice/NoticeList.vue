<script setup>
import { ref, onMounted } from "vue";
import { getNotices, getCount } from "@/apis/Notice";
import { useRouter, useRoute } from "vue-router";
import VPageNavigation from "@/commons/VPageNavigation.vue";

const notices = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const search = ref({
  key: "",
  countPerPage: VITE_ARTICLE_LIST_SIZE,
  word: "",
  currentPage: currentPage.value,
});

const router = useRouter();
const route = useRoute();
const no = route.params.no;
const userInfo = ref(JSON.parse(sessionStorage.getItem("userInfo")));
const userId = !userInfo.value ? "" : userInfo.value.id;

const onPageChange = (val) => {
  currentPage.value = val;
  search.value.currentPage = val;
  getList();
};

const getList = () => {
  getNotices(
    search.value,
    ({ data }) => {
      notices.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};

const goDetail = (no) => {
  router.push({
    name: "NoticeDetail",
    params: {
      no,
    },
  });
};

const goRegist = () => {
  router.push({
    name: "NoticeRegist",
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
  getList();
});
</script>

<template>
  <div class="main-screen">
    <div class="sub-upper">
      <h2>공지사항</h2>
    </div>
    <div v-if="notices.length > 0" class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="notice-table">
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                  <th style="width: 50%; height: 30px">제목</th>
                  <th style="width: 15%; height: 30px">글쓴이</th>
                  <th style="width: 8%; height: 30px">조회수</th>
                  <th style="width: 8%; height: 30px">
                    <span class="icon">
                      <img
                        src="/public/img/thumbs-up.png"
                        style="height: 30px"
                        alt=""
                      />
                    </span>
                  </th>
                  <th style="width: 19%; height: 30px">작성일</th>
                </tr>
              </thead>
              <tbody v-for="notice in notices" :key="notice.no">
                <tr>
                  <td>
                    <a href="#" @click.prevent="goDetail(notice.no)">{{
                      notice.title
                    }}</a>
                  </td>

                  <td>{{ notice.userId }}</td>
                  <td>{{ notice.hit }}</td>
                  <td>{{ Math.floor(Math.random() * 10) }}</td>
                  <td>{{ notice.createdAt }}</td>
                </tr>
              </tbody>
            </table>
            <VPageNavigation
              :current-page="currentPage"
              :total-page="totalPage"
              @pageChange="onPageChange"
            />
            <div v-if="userId == 'admin'" class="btn-write">
              <button class="mt-3 btn btn-primary" @click="goRegist">
                글쓰기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.btn-write {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
a {
  text-decoration: none;
  color: #63b7e4;
}
a:hover {
  color: royalblue;
  font-weight: bold;
}
td {
  height: 30px;
}
.icon {
  position: relative;
  top: 1px;
  display: inline-block;
  font-style: normal;
  line-height: 1;
  font-weight: 400;
}
.table {
  max-width: 100%;
  margin-bottom: 20px;
}
.table-bordered {
  border: 1px solid #ddd;
}
.main-screen .container .row {
  display: flex;
  margin-top: 50px;
  justify-content: center;
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
  height: 950px;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}

.main-screen .main-img {
  top: 0;
  right: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: no-repeat center/cover;
}

.main-screen .container {
  margin-top: 300px;
}

.title-p {
  color: white;
}
</style>
