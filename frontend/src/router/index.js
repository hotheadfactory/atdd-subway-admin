import Vue from "vue";
import VueRouter from "vue-router";
import Index from "../components/Index.vue";
import AdminStation from "../components/AdminStation.vue";
import AdminLine from "../components/AdminLine.vue";
import AdminEdge from "../components/AdminEdge.vue";

Vue.use(VueRouter);

export const router = new VueRouter({
  mode: "history",
  routes: [
    { path: "/", component: Index },
    { path: "/admin-station", component: AdminStation },
    { path: "/admin-line", component: AdminLine },
    { path: "/admin-edge", component: AdminEdge },
  ],
});
