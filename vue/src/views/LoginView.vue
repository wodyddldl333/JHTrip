<script setup>
import { ref } from "vue";
import { signin } from "@/apis/UserApi";
import { useRouter } from "vue-router";

const router = useRouter();

const savedId =
  sessionStorage.getItem("savedId") == null
    ? ""
    : sessionStorage.getItem("savedId");

const user = ref({
  id: savedId,
  pass: "",
  name: "",
  emailId: "",
  domain: "",
});

const rememberId = ref(savedId !== "");

const validate = () => {
  let isValid = false;
  let errMsg = "";

  console.log(user.value);

  user.value.id == ""
    ? ((isValid = false), (errMsg = "아이디를 입력해주세요."))
    : user.value.pass == ""
    ? ((isValid = false), (errMsg = "비밀번호를 입력해주세요."))
    : (isValid = true);

  if (!isValid) {
    alert(errMsg);
  } else {
    login();
  }
};

const login = () => {
  signin(
    user.value,
    (response) => {
      console.log(response.status);
      if (response.status == 200) {
        user.value = response.data;
        sessionStorage.setItem("userInfo", JSON.stringify(user.value));
        if (rememberId.value) {
          sessionStorage.setItem("savedId", user.value.id);
        } else {
          sessionStorage.removeItem("savedId");
        }
        const previousPage = sessionStorage.getItem("previousPage");
        if (previousPage) {
          router.push(previousPage);
        } else {
          router.push({ name: "home" });
        }
      } else {
        alert("로그인 정보가 잘못되었습니다.");
      }
    },
    (error) => {
      alert(error);
    }
  );
};
</script>

<template>
  <div class="main">
    <div class="login-box">
      <div class="login-header">
        <header>로그인</header>
      </div>
      <div class="input-box">
        <input
          v-model="user.id"
          type="text"
          class="input-field"
          placeholder="아이디"
          autocomplete="off"
          required
        />
      </div>
      <div class="input-box">
        <input
          v-model="user.pass"
          type="password"
          class="input-field"
          placeholder="비밀번호"
          autocomplete="off"
          required
        />
      </div>
      <div class="forgot">
        <section>
          <input v-model="rememberId" type="checkbox" id="check" />
          <label for="check">아이디 기억하기</label>
        </section>
        <section>
          <a href="#"
            ><RouterLink :to="{ name: 'LoginForgotPassword' }"
              >비밀번호 찾기</RouterLink
            ></a
          >
        </section>
      </div>
      <div class="input-submit">
        <button class="submit-btn" id="submit" @click="validate"></button>
        <label for="submit">로그인</label>
      </div>
      <div class="sign-up-link">
        <p>
          아이디가 존재하지 않습니까?
          <a href="#"
            ><RouterLink :to="{ name: 'LoginSignUp' }">회원가입</RouterLink></a
          >
        </p>
      </div>
    </div>
  </div>
  <RouterView />
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Noto Sans KR", sans-serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}
.main {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: #dfdfdf;
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
.forgot {
  display: flex;
  justify-content: space-between;
  margin-bottom: 40px;
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
.submit-btn:hover {
  background: #000;
  transform: scale(1.05, 1);
}
.sign-up-link {
  text-align: center;
  font-size: 15px;
  margin-top: 20px;
}
.sign-up-link a {
  color: #000;
  font-weight: 600;
}
</style>
