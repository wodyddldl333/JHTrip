<script setup>
import { ref } from "vue";
defineProps({ attractionOption: Array, category: Object });
const emit = defineEmits(["onKeySelect"]);

const key = ref("");

const onSelect = () => {
  emit("onKeySelect", key.value);
};
</script>

<template>
  <select
    v-model="category.area"
    class="form-select me-2"
    style="margin-left: 3px"
    @change="onSelect"
  >
    <option
      v-for="option in attractionOption"
      :key="option.value"
      :value="option.value"
      :disabled="option.value === '' ? true : false"
    >
      {{ option.text }}
    </option>
  </select>
</template>

<style scoped></style>
