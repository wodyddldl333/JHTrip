<script setup>
import { ResetPw, changePw } from "@/apis/UserApi";
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import Swal from "sweetalert2";
const route = useRoute();
const router = useRouter();

const token = route.params.token;
console.log(token);

const resetToken = ref({
  id: 0,
  userId: "",
  token: "",
  expiry: "",
});

const userInfo = ref({
  id: "",
  pass: "",
  confirmpw: "",
});

ResetPw(
  token,
  (response) => {
    resetToken.value = response.data;
    userInfo.value.id = resetToken.value.userId;
  },
  (error) => {
    console.log("문제발생");
  }
);

const reset = () => {
  let isValid = true;
  let errMsg = "";
  userInfo.value.pass == ""
    ? ((isValid = false), (errMsg = "비밀번호를 입력해주세요."))
    : userInfo.value.confirmpw == ""
    ? ((isValid = false), (errMsg = "비밀번호(확인)을 입력해주세요."))
    : userInfo.value.pass != userInfo.value.confirmpw
    ? ((isValid = false), (errMsg = "비밀번호를 확인하세요."))
    : (isValid = true);
  if (!isValid) {
    alert(errMsg);
  } else {
    changePassword();
  }
};

const changePassword = () => {
  console.log(userInfo.value);
  changePw(
    userInfo.value,
    resetToken.value.token,
    async (response) => {
      await Swal.fire({
        title: "비밀번호 초기화 완료",
        icon: "success",
      });
      router.push({ name: "home" });
    },
    (error) => {
      alert("비밀번호 변경에 실패하였습니다. 다시 시도해주세요.", error);
    }
  );
};
</script>

<template>
  <body>
    <div class="main" v-if="resetToken.id">
      <div class="login-box">
        <div class="login-header">
          <header>비밀번호 초기화</header>
          <h2>(@{{ resetToken.userId }})</h2>
        </div>

        <div class="input-box" style="margin-bottom: 20px">
          <input
            v-model="userInfo.pass"
            type="password"
            class="input-field"
            placeholder="비밀번호
            "
            autocomplete="off"
            required />
        </div>

        <div class="input-box" style="margin-bottom: 20px">
          <input
            v-model="userInfo.confirmpw"
            type="password"
            class="input-field"
            placeholder="비밀번호 확인
            "
            autocomplete="off"
            required />
        </div>

        <div class="input-submit">
          <button class="submit-btn" id="submit" @click="reset"></button>
          <label for="submit">비밀번호 재설정</label>
        </div>
      </div>
    </div>
    <div class="main" v-else>
      <div class="login-box">
        <div class="login-header">
          <header>유효한 링크가 아닙니다.</header>
        </div>

        <div class="input-submit">
          <button class="submit-btn" id="submit"></button>
          <label for="submit">홈페이지로 이동</label>
        </div>
      </div>
    </div>
  </body>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Poppins", sans-serif;
}
.main {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: #dfdfdf;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}
.login-box {
  display: flex;
  justify-content: center;
  flex-direction: column;
  width: 440px;
  height: 480px;
  padding: 30px;
}
.login-header {
  text-align: center;
  margin: 20px 0 40px 0;
}
.login-header header {
  color: #333;
  font-size: 30px;
  font-weight: 600;
}
.input-box .input-field {
  width: 100%;
  height: 60px;
  font-size: 17px;
  padding: 0 25px;
  margin-bottom: 15px;
  border-radius: 30px;
  border: none;
  box-shadow: 0px 5px 10px 1px rgba(0, 0, 0, 0.05);
  outline: none;
  transition: 0.3s;
}

::placeholder {
  font-weight: 500;
  color: #222;
}
.input-field:focus {
  width: 105%;
}

section {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #555;
}
#check {
  margin-right: 10px;
}
a {
  text-decoration: none;
}
a:hover {
  text-decoration: underline;
}
section a {
  color: #555;
}
.input-submit {
  position: relative;
}
.submit-btn {
  width: 100%;
  height: 60px;
  background: #222;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  transition: 0.3s;
}
.input-submit label {
  position: absolute;
  top: 45%;
  left: 50%;
  color: #fff;
  -webkit-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  cursor: pointer;
}
</style>
