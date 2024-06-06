import { localAxios } from "@/utils/http-commons";

const local = localAxios();

const searchAttraction = (searchKeyword, success, fail) => {
  console.log("관광지 검색");
  local.post(`/map/search`, searchKeyword).then(success).catch(fail);
};

function listSido(success, fail) {
  local.get(`/map/sido`).then(success).catch(fail);
}

const recommandImg = (success, fail) => {
  console.log("추천 여행지");
  local.get(`/map/main`).then(success).catch(fail);
};

const popularImg = (success, fail) => {
  console.log("추천 여행지");
  local.get(`/map/mainpopular`).then(success).catch(fail);
};

const getInfo = (id, success, fail) => {
  console.log("여행지 정보");
  local.get(`/map/getInfo/${id}`).then(success).catch(fail);
};
export { recommandImg, searchAttraction, listSido, popularImg, getInfo };
