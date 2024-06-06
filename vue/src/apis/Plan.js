import { localAxios } from "@/utils/http-commons";

const local = localAxios();

const getRoute = (id, success, fail) => {
  local.get(`/plan/view/${id}`).then(success).catch(fail);
};
const getDetailPlan = (id, success, fail) => {
  local.get(`/plan/detail/${id}`).then(success).catch(fail);
};

const getAttractions = (ids, success, fail) => {
  local.post(`/plan/aiplan`, ids).then(success).catch(fail);
};

const getlikes = (planId, success, fail) => {
  local.get(`/plan/getlikes/${planId}`).then(success).catch(fail);
};
const likePlan = (info, success, fail) => {
  local.post(`/plan/like`, info).then(success).catch(fail);
};
const unlikePlan = (info, success, fail) => {
  local.post(`/plan/unlike`, info).then(success).catch(fail);
};

const isLikePlan = (info, success, fail) => {
  local.post(`/plan/islike`, info).then(success).catch(fail);
};

const share = (id, success, fail) => {
  local.get(`/plan/share/${id}`).then(success).catch(fail);
};

const unshare = (id, success, fail) => {
  local.get(`/plan/unshare/${id}`).then(success).catch(fail);
};
export {
  getRoute,
  getDetailPlan,
  getAttractions,
  getlikes,
  likePlan,
  unlikePlan,
  isLikePlan,
  share,
  unshare,
};
