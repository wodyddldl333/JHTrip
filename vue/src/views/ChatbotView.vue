<script setup>
import { ref } from "vue";
import axios from "axios";

const messages = ref([]);
const prompt = ref("");
const loading = ref(false);

const BASE_URL = "http://localhost/chat-gpt";

// 챗봇에게 질문 전송
const sendQuestion = async () => {
  if (prompt.value.trim() === "") return;
  loading.value = true;
  const userMessage = {
    id: Date.now(),
    text: prompt.value,
    sender: "user",
  };
  messages.value.push(userMessage);

  const data = { question: prompt.value };
  try {
    const response = await axios.post(`${BASE_URL}/question`, data);
    const botMessage = {
      id: Date.now() + 1,
      text: response.data.choices[0].text,
      sender: "bot",
    };
    messages.value.push(botMessage);
    prompt.value = "";
    loading.value = false;
  } catch (error) {
    console.error("Error sending question:", error);
  }
};
</script>

<template>
  <div id="chatbot-header">챗봇과 대화하기</div>
  <div id="chatbot-messages">
    <li class="chatbox message bot">
      <span class="material-symbols-outlined">smart_toy</span>
      <p>안녕하세요 👋<br />여행 관련 어떤 도움을 드릴까요?</p>
    </li>
    <li
      v-for="message in messages"
      :key="message.id"
      :class="['chatbox message', message.sender]"
    >
      <span class="material-symbols-outlined">smart_toy</span>
      <p>{{ message.text }}</p>
    </li>
    <li v-if="loading" class="chatbox message bot">
      <span class="material-symbols-outlined">smart_toy</span>
      <p>입력중...</p>
    </li>
  </div>

  <div class="chat-input">
    <textarea
      v-model="prompt"
      @keyup.enter.prevent="sendQuestion"
      placeholder="Enter a message..."
      spellcheck="false"
      required
    ></textarea>
    <span @click="sendQuestion" id="send-btn" class="material-symbols-rounded"
      >send</span
    >
  </div>
</template>

<style>
#chatbot-header {
  padding: 16px 0;
  position: relative;
  text-align: center;
  color: #fff;
  background: royalblue;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

#chatbot-messages {
  height: 500px;
  overflow-y: auto;
}

#chatbot-input {
  display: flex;
  padding: 10px;
  border-top: 1px solid #ccc;
}

#chatbot-input input {
  flex: 1;
  padding: 5px;
}

#chatbot-input button {
  padding: 5px 10px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}
.chatbox.bot {
  padding: 10px;
  display: flex;
  font-size: 12px;
  justify-content: flex-start;
}

.chatbox.user {
  padding: 0 10px;
  display: flex;
  font-size: 12px;
  justify-content: flex-end;
}
.chatbox.user span {
  display: none;
}
.chatbox span {
  width: 32px;
  height: 32px;
  color: #fff;
  cursor: default;
  text-align: center;
  line-height: 32px;
  align-self: flex-end;
  background: royalblue;
  border-radius: 4px;
  margin-bottom: 20px;
}

.chatbox.bot p {
  max-width: 220px;
  border-radius: 10px;
  padding: 10px;
  background-color: #d7d7d7c4;
  text-align: left;
  margin-left: 10px;
}

.chatbox.user p {
  border-radius: 10px;
  padding: 10px;
  background-color: royalblue;
  color: white;
  text-align: left;
}

input {
  width: calc(100% - 22px);
  padding: 10px;
  margin-right: 2px;
}

button {
  padding: 10px 15px;
}

.chat-input {
  display: flex;
  gap: 5px;
  position: absolute;
  bottom: 0;
  width: 100%;
  background: #fff;
  padding: 3px 20px;
  border-top: 1px solid #ddd;
  border-radius: 10px;
}
</style>
