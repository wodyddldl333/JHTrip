<script setup>
import { ref } from "vue";
import { putNotice } from "@/apis/Notice";
import { useRoute, useRouter } from "vue-router";
import NoticeForm from "@/items/BoardFormItem.vue";
import Swal from "sweetalert2";

const router = useRouter();
const route = useRoute();
const userInfo = ref(JSON.parse(sessionStorage.getItem("userInfo")));
const no = route.params.no;

const notice = ref({
  no,
  title: "",
  userId: userInfo.value.id,
  content: "",
});

const modifyNotice = () => {
  const success = async (response) => {
    await Swal.fire({
      title: "수정 완료!",
      icon: "success",
    });
    goList();
  };
  const fail = (error) => {
    alert("문제가 발생하였습니다", error);
  };

  putNotice(notice.value, success, fail);
};

const goList = () => {
  router.push({
    name: "NoticeList",
  });
};
</script>

<template>
  <div class="sub-upper">
    <h2>자유게시판</h2>
  </div>
  <main>
    <NoticeForm type="modify" :board="notice" @event="modifyNotice" />
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
</style>
