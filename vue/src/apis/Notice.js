import { localAxios } from "@/utils/http-commons.js";

const axios = localAxios();

const getNotice = (no, success, fail) => {
  console.log("공지사항 상세보기");
  axios.get(`/notice/notice/${no}`).then(success).catch(fail);
};

const postNotice = (notice, success, fail) => {
  console.log("공지사항 작성");
  axios.post(`/notice/notice`, notice).then(success).catch(fail);
};

const getNotices = (condition, success, fail) => {
  console.log("공지사항 불러오기");
  axios.get(`/notice/notice`, { params: condition }).then(success).catch(fail);
};

const putNotice = (notice, success, fail) => {
  console.log("공지사항 수정");
  axios.put(`/notice/notice`, notice).then(success).catch(fail);
};

const deleteNotice = (no, success, fail) => {
  console.log("공지사항 삭제");
  axios.delete(`/notice/notice/${no}`).then(success).catch(fail);
};

const getCount = (success, fail) => {
  axios.get(`/notice/count`).then(success).catch(fail);
};

export { getCount, getNotice, postNotice, getNotices, putNotice, deleteNotice };
