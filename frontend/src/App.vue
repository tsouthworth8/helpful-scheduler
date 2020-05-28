<template>
  <div id="app">
    <div id="nav">
      <button class="button"><router-link to="/">Home</router-link></button>
      <button class="button" style="float:right;" @click.prevent="logout">Logout</button>
    </div>
    <router-view/>
  </div>
</template>

<script>
import axios from "axios";
import Auth from './auth.js'

export default {
  name: 'App',
  methods: {
   logout() {
    Auth.logout();
    this.$router.push('/login');
   }
  }
}
</script>

<style lang="scss">
.text-center {
  display: flex;
  justify-content: space-evenly;
}
</style>
