<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { postNotice } from "@/apis/Notice";
import Swal from "sweetalert2";
import NoticeForm from "@/items/BoardFormItem.vue";

const userInfo = ref(JSON.parse(sessionStorage.getItem("userInfo")));
const router = useRouter();
const notice = ref({
  title: "",
  userId: userInfo.value.id,
  content: "",
});

const registNotice = () => {
  const success = async (response) => {
    {
      await Swal.fire({
        title: "작성 완료!",
        icon: "success",
      });
      goList();
    }
  };
  const fail = async (error) => {
    {
      await Swal.fire({
        title: "문제가 발생했습니다",
        icon: "error",
      });
    }
  };
  postNotice(notice.value, success, fail);
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
    <NoticeForm type="create" :board="notice" @event="registNotice" />
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
