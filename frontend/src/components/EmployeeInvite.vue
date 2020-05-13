<template>
  <div>
    <h1>Employee Invite</h1>
    <form @submit.prevent="employeeInvite">
      <span>
        <label for="email">Employee email:</label>
        <input
          type="text"
          id="email"
          placeholder="Employee email"
          v-model="email"
          autofocus
        />
      </span>

      <button class="button is-info is-small" type="submit">Send invitation</button>
     
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  components: {},
  data() {
    return {
      email: ''
    };
  },
  methods: {
    employeeInvite() {
      axios
        .get(`${process.env.VUE_APP_REMOTE_API}/api/invite/${this.email}`, 
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("Authorization")
          }
        })
        // eslint-disable-next-line no-unused-vars
        .then(response => {
          this.email = "";
          
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>

<style>
</style>