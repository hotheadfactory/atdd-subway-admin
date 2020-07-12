<template>
  <div id="wrapper">
    <Nav />
    <router-view />
  </div>
</template>

<script>
import Nav from "./components/Nav.vue";

export default {
  name: "App",
  components: {
    Nav
  }
};
</script>

<style>
@import "../public/css/admin-app.css";
</style>
