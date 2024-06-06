<script setup>
import { getNotice, deleteNotice } from "@/apis/Notice";
import { useRouter, useRoute } from "vue-router";
import { ref } from "vue";

const router = useRouter();
const route = useRoute();

const userInfo = ref(JSON.parse(sessionStorage.getItem("userInfo")));
const userId = !userInfo.value ? "" : userInfo.value.id;
const notice = ref({});
const no = route.params.no;

const goModify = () => {
  router.push({
    name: "NoticeModify",
    params: { no },
  });
};

const delNotice = () => {
  if (confirm("정말로 삭제하시겠습니까?")) {
    deleteNotice(
      no,
      (response) => {
        if (response.status == 200) {
          alert("삭제가 완료되었습니다");
          goList();
        }
      },
      (error) => {
        alert("문제가 발생했습니다" + error);
      }
    );
  }
};

const goList = () => {
  router.push({
    name: "NoticeList",
  });
};

const success = (response) => {
  notice.value = response.data;
};

const fail = (error) => {
  alert("문제가 발생했습니다");
};

getNotice(no, success, fail);
</script>

<template>
  <div class="sub-upper">
    <h2>자유게시판</h2>
  </div>
  <main>
    <div class="post-container">
      <h1 class="post-title">{{ notice.title }}</h1>
      <div class="post-info">
        <p><strong>작성자:</strong> {{ notice.userId }}</p>
        <p><strong>작성일:</strong> {{ notice.createdAt }}</p>
        <p><strong>조회수:</strong> {{ notice.hit }}</p>
        <p>
          <strong>
            <span class="icon">
              <img
                src="/public/img/thumbs-up.png"
                style="height: 30px"
                alt="" /> </span
          ></strong>
          좋아요 수
        </p>
      </div>
      <div class="post-content">
        <p class="fs-5">
          {{ notice.content }}
        </p>
      </div>
      <div class="btn_brother">
        <button
          v-if="userId == 'admin'"
          class="btn btn-primary me-3"
          @click="goModify">
          수정
        </button>
        <a
          v-if="userId == 'admin'"
          @click="delNotice"
          class="btn btn-danger me-3"
          >삭제</a
        >
      </div>
    </div>
  </main>
</template>

<style scoped>
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
  width: 100%;
  background-color: #fff;
}
.icon {
  position: relative;
  top: 1px;
  display: inline-block;
  font-style: normal;
  line-height: 1;
  font-weight: 400;
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
}
main {
  position: relative;
  width: 100%;
  background-color: #fff;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}
.post-container {
  max-width: 1000px;
  margin: 150px auto 0;
  background-color: #fff;
  padding: 20px;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
  border-radius: 8px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}
.post-title {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}
.post-info {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  margin-bottom: 20px;
}
.post-info p {
  margin-right: 20px;
  margin-bottom: 5px;
  color: #666;
}
.post-info strong {
  margin-right: 5px;
  color: #333;
}
.post-content {
  font-size: 14px;
  line-height: 1.6;
  color: #666;
}
.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
}
.btn-primary:hover {
  background-color: #0056b3;
  border-color: #0056b3;
}
.btn_brother {
  text-align: right;
}
</style>
