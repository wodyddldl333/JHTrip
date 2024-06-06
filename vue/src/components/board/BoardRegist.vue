<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import BoardFormItem from "@/items/BoardFormItem.vue";
import { postBoard } from "@/apis/BoardApi";
import Swal from "sweetalert2";
const userInfo = ref(JSON.parse(sessionStorage.getItem("userInfo")));
console.log(userInfo);
const router = useRouter();
const board = ref({
  title: "",
  userId: userInfo.value.id,
  content: "",
});
console.log(board.value);
const registBoard = () => {
  const success = async (response) => {
    {
      await Swal.fire({
        title: "글 작성 완료!",
        icon: "success",
      });
      goList();
    }
  };

  const fail = async (error) => {
    await Swal.fire({
      title: "문제가 발생했습니다",
      icon: "error",
    });
  };

  postBoard(board.value, success, fail);
};

const goList = () => {
  router.push({
    name: "BoardList",
  });
};
</script>

<template>
  <div class="sub-upper">
    <h2>자유게시판</h2>
  </div>
  <main>
    <BoardFormItem type="create" :board="board" @event="registBoard" />
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
