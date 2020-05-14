<template>
    <div id="change-password">
        <h2>Change your password</h2>
        <form @submit.prevent="submitForm">
            <label for="password">Enter your new password:</label>
            <input
                type="text"
                id="password"
                class="form-control"
                placeholder="Password"
                v-model="newPassword"
                required
            />
            <label for="password">Confirm new password:</label>
            <input
                type="text"
                id="confirm-password"
                class="form-control"
                placeholder="Confirm password"
                required
            />
            <button type="submit" @click="submitForm">Submit</button>
        </form>
    </div>
</template>


<script>
import axios from "axios";

export default {
  name: 'change-password',
  data() {
      return {
          newPassword: '',
          confirmNewPassword: '',
          passwordsMatch: false,
      }
  },
  methods: {
      checkPasswordsMatch() {
          if(this.newPassword == this.confirmNewPassword) {
              this.passwordsMatch = true;
          } else {
              this.passwordsMatch = false;
          }
      },
      submitForm() {
        this.checkPasswordsMatch();
        console.log("Passwords dont match");
        if(this.passwordsMatch) {
            console.log("Passwords match");
            axios.post(`${process.env.VUE_APP_REMOTE_API}/change-password`, this.newPassword)
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
        }
      }
  }
}
</script>


<style>
</style>