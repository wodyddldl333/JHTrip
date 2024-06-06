<script setup>
import { defineProps } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const props = defineProps(["attractions"]);

// 선택된 여행지 제거
const removeItem = (contentId) => {
  const idx = props.attractions.findIndex((att) => att.contentId === contentId);
  if (idx !== -1) {
    props.attractions.splice(idx, 1);
  }
};

// Drag & Drop으로 여행지 순서 변경
const handleDragStart = (event, index) => {
  event.dataTransfer.setData("text/plain", index);
};

const handleDrop = (event, toIndex) => {
  const fromIndex = parseInt(event.dataTransfer.getData("text/plain"), 10);

  if (fromIndex >= 0 && fromIndex < props.attractions.length) {
    const draggedItem = props.attractions[fromIndex];
    props.attractions.splice(fromIndex, 1);

    if (toIndex < 0) {
      toIndex = 0;
    } else if (toIndex > props.attractions.length) {
      toIndex = props.attractions.value.length;
    }

    props.attractions.splice(toIndex, 0, draggedItem);
  }
};

const goRegist = () => {
  router.push({
    name: "MyTripRegist",
    query: { data: JSON.stringify(props.attractions) },
  });
};
</script>

<template>
  <div class="cart-header">내가 담은 여행지</div>
  <div class="cart-main">
    <div
      class="item-list"
      v-for="(att, index) in attractions"
      :key="att.contentId"
    >
      <div
        class="item"
        draggable="true"
        @dragstart="handleDragStart($event, index)"
        @dragover.prevent
        @drop.prevent="handleDrop($event, index)"
      >
        <img
          class="img"
          :src="att.firstImage ? att.firstImage : '/img/noImage.jpg'"
          alt=""
        />
        <div class="title">{{ att.title }}</div>
        <div>
          <button
            type="button"
            class="btn btn-danger remove"
            @click="removeItem(att.contentId), $emit('decrement')"
          >
            -
          </button>
        </div>
      </div>
    </div>
  </div>
  <div class="cart-footer">
    <button class="btn btn-primary plan" @click="goRegist">
      여행 계획 세우기
    </button>
  </div>
</template>

<style scoped>
.cart-footer {
  display: flex;
  justify-content: center;
  align-items: center;
}
.plan {
  width: 80%;
  margin-bottom: 5px;
}
.item-list {
  display: flex;
  justify-content: center;
}
.cart-main {
  overflow-y: auto;
}
.title {
  margin-right: 20px;
  width: 150px;
  font-size: 12px;
}
.img {
  width: 100px;
  min-width: 100px;
  height: 100px;
  margin-right: 20px;
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
}
.cart-header {
  padding: 16px 0;
  position: relative;
  text-align: center;
  color: #fff;
  background: royalblue;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}
.item {
  margin: 0 auto;
  align-items: center;
  display: flex;
  width: 250px;
  height: 100px;
  margin: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}
.btn.remove {
  height: 100px;
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
}
</style>
