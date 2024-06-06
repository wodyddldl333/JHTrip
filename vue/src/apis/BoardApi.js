import { localAxios } from "@/utils/http-commons.js";

const axios = localAxios();

export const searchBoard = (condition, success, fail) => {
  console.log("게시글 검색하기");
  axios.get(`/board/board`, { params: condition }).then(success).catch(fail);
};

export const getBoards = (success, fail) => {
  // 디버깅용
  console.log("게시글 불러오기");
  axios.get(`/board/board`).then(success).catch(fail);
};

export const getBoard = (no, success, fail) => {
  // 디버깅용
  console.log("게시글 상세보기");
  axios.get(`/board/board/${no}`).then(success).catch(fail);
};

export const postBoard = (board, success, fail) => {
  // 디버깅용
  console.log("게시글 작성", board);
  axios.post(`/board/board`, board).then(success).catch(fail);
};

export const putBoard = (board, success, fail) => {
  // 디버깅용
  console.log("게시글 수정", board);
  axios.put(`/board/board`, board).then(success).catch(fail);
};

export const deleteBoard = (no, success, fail) => {
  // 디버깅용
  console.log("게시글 삭제");
  axios.delete(`/board/board/${no}`).then(success).catch(fail);
};
export const postComment = (comments, success, fail) => {
  console.log("댓글 작성", comments);
  axios.post(`/board/comment`, comments).then(success).catch(fail);
};

export const getComments = (no, success, fail) => {
  console.log("댓글 불러오기", no);
  axios.get(`/board/comment/${no}`).then(success).catch(fail);
};

export const putComment = (comments, success, fail) => {
  console.log("댓글 수정", comments);
  axios.put(`/board/comment`, comments).then(success).catch(fail);
};

export const deleteComment = (no, success, fail) => {
  console.log("댓글 삭제", no);
  axios.delete(`/board/comment/${no}`).then(success).catch(fail);
};

export const getCount = (success, fail) => {
  axios.get(`/board/count`).then(success).catch(fail);
};
