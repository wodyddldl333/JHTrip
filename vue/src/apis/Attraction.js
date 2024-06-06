import { attractionAxios } from "@/utils/http-commons";

const attraction = attractionAxios();

function listAttractions(param, success, fail) {
  attraction.get("", { params: param }).then(success).catch(fail);
}

export { listAttractions };
