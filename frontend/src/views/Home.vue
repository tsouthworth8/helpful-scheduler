<template>
  <div class="home">
    <h1>Landing Page</h1>
    <router-link :to="{ name: 'change-password' }">Change your password</router-link>
    <employee-invite></employee-invite>
  </div>
</template>

<script>
import axios from "axios";
import EmployeeInvite from "../components/EmployeeInvite";

export default {
  name: 'home',
  components: {
    EmployeeInvite
  }
}

</script>
