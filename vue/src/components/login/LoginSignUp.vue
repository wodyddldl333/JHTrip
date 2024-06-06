<script setup>
import { ref } from "vue";
import { signup } from "@/apis/UserApi";
import { useRouter } from "vue-router";
import Swal from "sweetalert2";

const router = useRouter();

const userInfo = ref({
  id: "",
  pass: "",
  confirmpw: "",
  name: "",
  email: "",
});

const signUp = () => {
  let isValid = true;
  let errMsg = "";
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  userInfo.value.name == ""
    ? ((isValid = false), (errMsg = "이름을 입력해주세요."))
    : userInfo.value.id == ""
    ? ((isValid = false), (errMsg = "아이디를 입력해주세요."))
    : userInfo.value.pass == ""
    ? ((isValid = false), (errMsg = "비밀번호를 입력해주세요."))
    : userInfo.value.confirmpw == ""
    ? ((isValid = false), (errMsg = "비밀번호(확인)을 입력해주세요."))
    : userInfo.value.email == ""
    ? ((isValid = false), (errMsg = "이메일을 입력해주세요."))
    : !emailRegex.test(userInfo.value.email)
    ? ((isValid = false), (errMsg = "유효하지 않은 이메일 형식입니다."))
    : userInfo.value.pass != userInfo.value.confirmpw
    ? ((isValid = false), (errMsg = "비밀번호를 확인하세요."))
    : userInfo.value.pass.length < 4
    ? ((isValid = false), (errMsg = "비밀번호는 4자리 이상이여야 합니다."))
    : (isValid = true);

  if (!isValid) {
    alert(errMsg);
  } else {
    registUser();
  }
};

const registUser = () => {
  signup(
    userInfo.value,
    async (response) => {
      await Swal.fire({
        title: "회원가입 완료!",
        icon: "success",
      });
      goHome();
    },
    (error) => {
      alert("회원가입에 실패하였습니다. 다시 시도해주세요.", error);
    }
  );
};

const goHome = () => {
  router.push({
    name: "home",
  });
};
</script>

<template>
  <div class="main">
    <div class="login-box">
      <div class="login-header">
        <header>회원가입</header>
      </div>

      <div class="input-box">
        <input
          v-model="userInfo.name"
          type="text"
          class="input-field"
          placeholder="이름"
          autocomplete="off"
          required />
      </div>
      <div class="input-box">
        <input
          v-model="userInfo.id"
          type="text"
          class="input-field"
          placeholder="아이디"
          autocomplete="off"
          required />
      </div>
      <div class="input-box">
        <input
          v-model="userInfo.pass"
          type="password"
          class="input-field"
          placeholder="비밀번호"
          autocomplete="off"
          required />
      </div>
      <div class="input-box">
        <input
          v-model="userInfo.confirmpw"
          type="password"
          class="input-field"
          placeholder="비밀번호 확인"
          autocomplete="off"
          required />
      </div>
      <div class="input-box" style="margin-bottom: 20px">
        <input
          v-model="userInfo.email"
          type="email"
          class="input-field"
          placeholder="이메일"
          autocomplete="off"
          required />
      </div>
      <div class="input-submit">
        <button class="submit-btn" id="submit" @click="signUp"></button>
        <label for="submit">회원가입</label>
      </div>
    </div>
  </div>
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
