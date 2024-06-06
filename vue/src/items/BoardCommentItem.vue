<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import { getComments, postComment, deleteComment } from "@/apis/BoardApi";

const route = useRoute();
const userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
const no = route.params.no;
const comments = ref([]);

const newComment = ref({
  boardNo: no,
  userId: !userInfo ? null : userInfo.id,
  comment: "",
});

const delComment = (id) => {
  if (confirm("정말로 삭제하시겠습니까?")) {
    deleteComment(
      id,
      (response) => {
        if (response.status == 200) {
          callMethod();
        }
      },
      (error) => {
        alert("문제가 발생했습니다." + error);
      }
    );
  }
};

const callMethod = () => {
  const success = (response) => {
    console.log(response);
    if (response.status == 200 || response.status == 204) {
      comments.value = response.data;
    }
  };

  const fail = (error) => {
    alert("문제가 발생했습니다", error);
  };

  getComments(no, success, fail);
};

callMethod();

const validate = () => {
  if (!userInfo) {
    alert("로그인 후 이용하세요.");
    return;
  }
  let isValid = true;
  let errMsg = "";

  !newComment.value.comment
    ? ((isValid = false), (errMsg = "내용을 입력해주세요."))
    : (isValid = true);

  if (!isValid) {
    alert(errMsg);
  } else {
    postComment(
      newComment.value,
      (response) => {
        if (response.status == 200 || response.status == 204) {
          callMethod();
          newComment.value.comment = "";
        }
      },
      (error) => {
        alert("문제가 발생했습니다", error);
      }
    );
  }
};
</script>

<template>
  <div class="container">
    <div style="margin-bottom: 10px">
      총 <span style="color: royalblue">{{ comments.length }}</span
      >개의 댓글이 있습니다.
    </div>
    <div class="reply-write-area">
      <div class="rw-inner">
        <div class="textarea">
          <textarea
            v-model="newComment.comment"
            class="form-control noresize"
            placeholder="댓글을 입력하세요."
            id="answerCont"
            maxlength="4000"
          ></textarea>
        </div>

        <div class="btns tar">
          <button
            type="button"
            class="btn btn-sm sz2 btn-primary"
            @click="validate"
          >
            등록
          </button>
        </div>
      </div>
    </div>
  </div>

  <div v-for="comment in comments" :key="comment.id" class="reply-list-area">
    <ul>
      <li>
        <div class="status">
          <div class="user-info">
            <span class="name">{{ comment.userId }}</span>
          </div>
          <div class="date">
            <span>{{ comment.createdAt }}</span>
          </div>
        </div>
        <div class="reply-content">{{ comment.comment }}</div>
        <div class="changeGroup">
          <a
            v-if="newComment.userId == comment.userId"
            class="changeCom"
            href="#"
            @click.prevent="delComment(comment.id)"
            >삭제</a
          >
        </div>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.container {
  max-width: 1000px;
  margin: 50px auto 0;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}

.changeGroup {
  display: flex;
  align-items: flex-end;
  justify-content: flex-end;
}

a:hover {
  color: royalblue;
}
.changeCom {
  margin-right: 5px;
  color: gray;
}

* {
  box-sizing: border-box;
}
.textarea {
  border: 1px solid #e3e7eb;
  padding: 16px 14px;
  background: #fff;
  font-size: 0;
}
.textarea textarea {
  width: 100%;
  min-height: 120px;
  border: 0;
  font-size: 14px;
  resize: none;
}
.tar {
  margin-top: 10px;
  text-align: right;
}
.btn-sm.sz2 {
  min-width: 100px;
}
body {
  font-family: Arial, sans-serif;
  background-color: #f4f4f4;
  margin: 0;
  padding: 0;
}

.reply-list-area {
  max-width: 1000px;
  margin: 20px auto;
}

.reply-list-area ul {
  list-style: none;
  padding: 0;
}

.reply-list-area li {
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #e0e0e0;
  margin-top: 30px;
}

.reply-list-area .status {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.reply-list-area .user-info {
  display: flex;
  align-items: center;
}

.reply-list-area .name {
  font-weight: bold;
  color: #333;
  margin-right: 10px;
}

.reply-list-area .date {
  color: #666;
}

.reply-list-area .reply-content {
  color: #444;
  line-height: 1.6;
}

.separator {
  border-top: 1px solid #e0e0e0;
  width: 100%;
  margin: 20px 0;
}
</style>
