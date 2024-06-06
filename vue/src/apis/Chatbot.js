import { gptAxios } from "@/utils/http-commons";

const local = gptAxios();

const getChatResponse = async (prompt) => {
  try {
    const response = await local.post(`/question`, { prompt });
    return response.data.choices[0].text;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

const getAiRoute = async (keyword, success, fail) => {
  await local.post(`/planai`, keyword).then(success).catch(fail);
};

export { getChatResponse, getAiRoute };
