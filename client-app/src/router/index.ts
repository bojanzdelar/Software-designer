import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "login",
      component: HomeView,
    },
    {
      path: "/:project/",
      name: "project",
      redirect: { name: "requirements" },
      component: HomeView,
      children: [
        {
          path: "requirements",
          name: "requirements",
          component: () => import("../components/RequirementTable.vue"),
        },
        {
          path: "diagrams",
          name: "diagrams",
          component: () => import("../components/DiagramTable.vue"),
        },
        {
          path: "docs/:type",
          name: "docs",
          component: () => import("../components/DocsTables.vue"),
        },
      ],
    },
    {
      path: "/design/:id/:type",
      name: "design",
      component: () => import("../views/DesignView.vue"),
    },
    {
      path: "/:pathMatch(.*)*",
      name: "NotFound",
      component: () => import("../views/NotFound.vue"),
    },
  ],
});

export default router;
