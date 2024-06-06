<script setup>
import Header from "@/commons/Header.vue";
import Footer from "@/commons/Footer.vue";

import { ref } from "vue";
import { updatePw, fetchImage, updateprofile } from "@/apis/UserApi";
import { useRouter } from "vue-router";
import Swal from "sweetalert2";

const router = useRouter();
const selectedFile = ref(null);
const imageUrl = ref("");

const userInfo = ref(JSON.parse(sessionStorage.getItem("userInfo")));

const updateInfo = ref({
  id: userInfo.value.id,
  name: userInfo.value.name,
  pass: "",
  newPw: "",
  confirmpw: "",
});

fetchImage(
  userInfo.value.id,
  (response) => {
    const url = URL.createObjectURL(response.data);
    imageUrl.value = url;
  },
  (error) => {
    alert.error("에러 발생");
  }
);

const onFileChange = (event) => {
  selectedFile.value = event.target.files[0];
  if (selectedFile.value) {
    const reader = new FileReader();
    reader.onload = (e) => {
      imageUrl.value = e.target.result;
      console.log(imageUrl.value);
    };
    reader.readAsDataURL(selectedFile.value); // 파일을 Data URL로 읽음
  }
};

const updateUser2 = () => {
  console.log(updateInfo);
  updatePw(
    updateInfo.value,
    async (response) => {
      {
        await Swal.fire({
          title: "비밀번호 변경 완료!",
          icon: "success",
        });
        logout();
      }
    },
    (error) => {
      alert("문제발생");
    }
  );
};

const updateUser = () => {
  const formData = new FormData();
  formData.append("file", selectedFile.value);
  formData.append(
    "user",
    new Blob([JSON.stringify(updateInfo.value)], { type: "application/json" })
  );
  updateprofile(
    formData,
    async (response) => {
      {
        await Swal.fire({
          title: "회원 정보 수정 완료!\n 다시 로그인 해주세요.",
          icon: "success",
        });
      }
      logout();
    },
    (error) => {
      alert("문제발생");
    }
  );
};

const logout = () => {
  sessionStorage.removeItem("userInfo");
  userInfo.value = null; // 사용자 정보를 제거하고 로그아웃 상태로 설정
  router.push({ name: "home" });
};
</script>

<template>
  <Header />
  <div class="main-screen">
    <div class="sub-upper">
      <h2>내정보 수정</h2>
    </div>

    <div class="container">
      <div class="show">
        <input type="hidden" name="action" value="modify" />
        <div id="set_profile">
          <img
            :src="imageUrl"
            alt=""
            onerror="this.onerror=null; this.src='/img/noImage.jpg'"
            style="width: 300px"
          /><br /><br />
          <input
            type="file"
            @change.prevent="onFileChange"
            accept="image/*"
          /><br /><br />
        </div>
        <div id="set_pwd">
          <label for="name">이름:</label><br />
          <input
            type="text"
            id="name"
            name="name"
            v-model="updateInfo.name"
            readonly
          /><br /><br />
          <label for="email">아이디:</label><br />
          <input
            type="text"
            id="id"
            v-model="updateInfo.id"
            readonly
          /><br /><br />
          <label for="email">현재 비밀번호:</label><br />
          <input
            type="password"
            id="currentpw"
            v-model="updateInfo.pass"
          /><br /><br />
          <label for="new_password">새 비밀번호:</label><br />
          <input
            type="password"
            id="new_password"
            v-model="updateInfo.newPw"
            required
          /><br /><br />
          <label for="confirm_password">비밀번호 확인:</label><br />
          <input
            type="password"
            id="confirm_password"
            v-model="updateInfo.confirmpw"
            required
          /><br /><br />
        </div>
      </div>
      <div class="button_brother">
        <a href="#" @click.prevent="updateUser" class="btn btn-primary d-block">
          수정
        </a>
        <a href="#" class="btn btn-danger d-block">탈퇴</a>
      </div>
    </div>
  </div>
  <Footer />
</template>

<style scoped>
.show {
  display: flex;
  justify-content: center;
  margin-top: 30px;
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
.res_title {
  max-width: 1280px;
  margin: 300px auto 20px;
  padding: 20px;
  align-items: center;
}
.container {
  border-radius: 30px;
  max-width: 1280px;
  margin: 350px auto 30px;
  background: #fff;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
#set_profile {
  margin: 0 50px auto;
  float: left;
}
#set_pwd {
  float: right;
  margin: 0 50px auto;
}
.container::after {
  content: "";
  display: block;
  clear: both;
}
input[type="text"],
input[type="email"] {
  width: 450px;
  padding: 10px;
  margin: 5px 0;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  background-color: #f0f0f0;
}
input[type="password"] {
  width: 450px;
  padding: 10px;
  margin: 5px 0;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}
input[type="submit"] {
  background-color: #4caf50;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
input[type="submit"]:hover {
  background-color: #45a049;
}
#set_profile {
  margin-top: 100px;
}
@media (max-width: 768px) {
  form {
    flex-direction: column;
  }
}

.button_brother {
  display: flex;
  margin: 20px auto;
  justify-content: center;
}
.btn {
  margin: 0 15px;
}
</style>
