<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { getBoard } from "@/apis/BoardApi";
import { getNotice } from "@/apis/Notice";
import Swal from "sweetalert2";

const router = useRouter();
const route = useRoute();

const no = route.params.no;

const props = defineProps({
  type: String,
  board: Object,
});

const emit = defineEmits(["event"]);

const refTitle = ref(null);
const refContent = ref(null);

const validate = async () => {
  let isValid = true;
  let errMsg = "";

  !props.board.title
    ? ((isValid = false),
      (errMsg = "제목을 입력해주세요."),
      refTitle.value.focus())
    : !props.board.content
    ? ((isValid = false),
      (errMsg = "내용을 입력해주세요."),
      refContent.value.focus())
    : (isValid = true);

  if (!isValid) {
    await Swal.fire({
      title: errMsg,
      icon: "error",
    });
  } else {
    emit("event");
  }
};

const goList = () => {
  router.push({
    name: "BoardList",
  });
};

if (props.type === "modify") {
  const title = route.params.title;

  const success = (response) => {
    const board = response.data;
    props.board.title = board.title;
    props.board.content = board.content;
    props.board.userId = board.userId;
  };
  const fail = (error) => {
    alert("문제가 발생했습니다", error);
  };

  getBoard(no, success, fail);
  getNotice(no, success, fail);
}
</script>

<template>
  <main>
    <div class="container">
      <h2 class="fs-2 fw-bold" style="margin-bottom: 30px"></h2>
      <label for="title">제목:</label>
      <input
        v-model="board.title"
        ref="refTitle"
        type="text"
        id="title"
        name="title"
        required />

      <label for="userId">작성자 ID:</label>
      <input
        v-model="board.userId"
        type="text"
        id="userId"
        name="userId"
        readonly />

      <label for="content">내용:</label>
      <textarea
        v-model="board.content"
        ref="refContent"
        id="content"
        name="content"
        rows="6"
        required></textarea>

      <div class="buttons">
        <button class="btn btn-primary me-3" @click="validate">
          {{ type === "create" ? "글작성" : "수정" }}
        </button>
        <button class="btn btn-secondary" @click="goList">목록</button>
      </div>
    </div>
  </main>
</template>

<style scoped>
main {
  margin: 120px auto;
  max-width: 1200px;
  min-height: 950px;
}

.container {
  max-width: 100%;
  margin: 30px auto;
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}

.buttons {
  align-self: flex-end;
}
h2 {
  text-align: center;
  color: #333;
}
label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}
input[type="text"],
textarea {
  width: 100%;
  padding: 10px;
  margin-top: 5px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}
textarea {
  resize: vertical;
}
input[type="submit"] {
  background-color: #27a7f1;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  display: block;
  margin: 0 auto;
}
input[type="submit"]:hover {
  background-color: #45a049;
}

input[readonly] {
  background-color: rgb(235, 235, 235);
}

#content {
  height: 300px;
}
</style>
