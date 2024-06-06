import axios from "axios";

const { VITE_API_BASE_URL, VITE_ATTRACTION_CODE_URL, VITE_API_GPT_URL } =
  import.meta.env;

const localAxios = () => {
  const instance = axios.create({
    baseURL: VITE_API_BASE_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });

  return instance;
};

const gptAxios = () => {
  const instance = axios.create({
    baseURL: VITE_API_GPT_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
};

const attractionAxios = () => {
  const instance = axios.create({
    baseURL: VITE_ATTRACTION_CODE_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
};

export { localAxios, attractionAxios, gptAxios };
