import Vue from "vue";
import VueRouter from "vue-router";
import Index from "../components/Index.vue";

Vue.use(VueRouter);

const NotFound = { template: "<div>Not Found</div>" };

export const router = new VueRouter({
  mode: "history",
  routes: [
    { path: "/", component: Index },
    { path: "*", component: NotFound },
  ],
});
