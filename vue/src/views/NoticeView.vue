<script setup>
import { ref } from "vue";
import Footer from "@/commons/Footer.vue";

import Header from "@/commons/Header.vue";
import ChatbotView from "./ChatbotView.vue";

const isChatbotVisible = ref(false);

const goToChat = () => {
  isChatbotVisible.value = !isChatbotVisible.value;
};
</script>

<template>
  <Header />
  <RouterView />

  <button @click="goToChat" class="chatbot-toggler">
    <span
      class="material-symbols-outlined"
      :style="{ opacity: isChatbotVisible ? 1 : 0 }"
      >mode_comment</span
    >
    <span
      class="material-symbols-rounded"
      :style="{ opacity: isChatbotVisible ? 0 : 1 }"
      >mode_comment</span
    >
  </button>
  <transition name="slide-up-down">
    <div v-if="isChatbotVisible" id="chatbot-container">
      <ChatbotView />
    </div>
  </transition>
  <Footer />
</template>

<style scoped>
#chatbot-container {
  position: fixed;
  z-index: 999;
  bottom: 90px;
  right: 5px;
  width: 300px;
  height: 600px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.slide-up-down-enter-active,
.slide-up-down-leave-active {
  transition: transform 0.3s ease;
}

.slide-up-down-enter-from {
  transform: translateY(100%);
}

.slide-up-down-leave-to {
  transform: translateY(100%);
}
</style>
