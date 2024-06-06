import { localAxios } from "@/utils/http-commons.js";

const axios = localAxios();

export const signup = (userInfo, success, fail) => {
  console.log("회원가입");
  axios.post(`/user/regist`, userInfo).then(success).catch(fail);
};

export const signin = (userInfo, success, fail) => {
  console.log("로그인");
  axios.post(`/user/login`, userInfo).then(success).catch(fail);
};

export const ResetPw = (token, success, fail) => {
  console.log("비밀번호 초기화");
  axios.get(`/user/reset-password/${token}`).then(success).catch(fail);
};

export const changePw = (userInfo, token, success, fail) => {
  console.log("비밀번호 변경");
  axios.put(`/user/update/${token}`, userInfo).then(success).catch(fail);
};

export const forget = (userInfo, success, fail) => {
  console.log("비밀번호 찾기");
  axios.post(`/user/reset-password`, userInfo).then(success).catch(fail);
};

export const updatePw = (userInfo, success, fail) => {
  console.log("비밀번호 변경 in my page");
  axios.put(`user/update`, userInfo).then(success).catch(fail);
};

export const updateprofile = (data, success, fail) => {
  axios
    .put(`user/update`, data, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then(success)
    .catch(fail);
};
export const fetchImage = (id, success, fail) => {
  axios
    .get(`/user/uploads/${id}`, { responseType: "blob" })
    .then(success)
    .catch(fail);
};
