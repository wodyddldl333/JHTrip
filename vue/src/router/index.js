import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/settings",
      name: "Settings",
      component: () => import("@/components/myPage/myPageSetting.vue"),
    },
    {
      path: "/searchAttraction",
      name: "SearchAttractionView",
      component: () => import("@/views/SearchAttraction.vue"),
    },
    {
      path: "/tripboard",
      name: "TripBoardView",
      component: () => import("@/views/TripBoardView.vue"),
      redirect: "/tripboard/list",
      children: [
        {
          path: "detail",
          name: "TripBoardDetail",
          component: () => import("@/components/tripboard/TripboardDetail.vue"),
        },
        {
          path: "list",
          name: "TripBoardList",
          component: () => import("@/components/tripboard/TripboardList.vue"),
        },
      ],
    },
    {
      path: "/notice",
      name: "NoticeView",
      component: () => import("@/views/NoticeView.vue"),
      redirect: "/notice/list",
      children: [
        {
          path: "write",
          name: "NoticeRegist",
          component: () => import("@/components/notice/NoticeRegist.vue"),
        },
        {
          path: "list",
          name: "NoticeList",
          component: () => import("@/components/notice/NoticeList.vue"),
        },
        {
          path: "modify/:no",
          name: "NoticeModify",
          component: () => import("@/components/notice/NoticeModify.vue"),
        },
        {
          path: "detail/:no",
          name: "NoticeDetail",
          component: () => import("@/components/notice/NoticeDetail.vue"),
        },
      ],
    },
    {
      path: "/board",
      name: "BoardView",
      component: () => import("@/views/BoardView.vue"),
      redirect: "/board/list",
      children: [
        {
          path: "write",
          name: "BoardRegist",
          component: () => import("@/components/board/BoardRegist.vue"),
        },
        {
          path: "detail/:no",
          name: "BoardDetail",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "modify/:no",
          name: "BoardModify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
        {
          path: "list",
          name: "BoardList",
          component: () => import("@/components/board/BoardList.vue"),
        },
      ],
    },
    {
      path: "/mypage",
      name: "MypageView",
      component: () => import("@/views/MypageView.vue"),
    },
    {
      path: "/login",
      name: "LoginView",
      component: () => import("@/views/LoginView.vue"),
    },
    {
      path: "/signup",
      name: "LoginSignUp",
      component: () => import("@/components/login/LoginSignUp.vue"),
    },
    {
      path: "/find-password",
      name: "LoginForgotPassword",
      component: () => import("@/components/login/LoginForgotPassword.vue"),
    },
    {
      path: "/reset-password/:token",
      name: "ResetPassword",
      component: () => import("@/components/login/ResetPassword.vue"),
    },
    {
      path: "/myplan/:id",
      name: "MyPlanView",
      component: () => import("@/views/MyPlanView.vue"),
    },
    {
      path: "/myplan/regist",
      name: "MyTripRegist",
      component: () => import("@/components/myTrip/MytripRegist.vue"),
    },
    {
      path: "/aiplansetup",
      name: "aiplanSetup",
      component: () => import("@/views/AiPlanSetup.vue"),
    },
    {
      path: "/aiplanview",
      name: "aiplanView",
      component: () => import("@/views/AiPlanView.vue"),
    },
    {
      path: "/tripdetail/:id",
      name: "plandetail",
      component: () => import("@/components/tripboard/TripboardDetail.vue"),
    },
  ],
  scrollBehavior(to, from, savedPosition) {
    return { left: 0, top: 0 };
  },
});

router.beforeEach((to, from, next) => {
  sessionStorage.setItem("previousPage", from.fullPath);
  next();
});

router.afterEach(() => {
  window.scrollTo({ top: 0, behavior: "smooth" });
});

export default router;
