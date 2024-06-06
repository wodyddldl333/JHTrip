import { createApp } from "vue";

import App from "./App.vue";
import router from "./router";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { useKakao } from "vue3-kakao-maps/@utils";

useKakao("daf0b6417df18a2427d93bea9d254efa");
const app = createApp(App);

// app.use(createPinia())
app.use(router);

app.mount("#app");
