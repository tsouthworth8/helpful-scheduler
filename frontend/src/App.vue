<template>
  <div id="app">
    <div id="nav">
      <span><router-link to="/">Home</router-link></span>
      <span style="float:right;" @click.prevent="logout">Logout</span>
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
