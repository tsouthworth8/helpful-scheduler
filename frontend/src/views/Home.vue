<template>
  <div class="home">
    <h1>Landing Page</h1>
    <router-link :to="{ name: 'change-password' }">Change your password</router-link>
    <employee-invite></employee-invite>
    <employee-roles></employee-roles>
  </div>
</template>

<script>
import axios from "axios";
import EmployeeInvite from "../components/EmployeeInvite";
import EmployeeRoles from "../components/EmployeeRoles";
export default {
  name: 'home',
  components: {
    EmployeeInvite,
    EmployeeRoles
  }
}

</script>
