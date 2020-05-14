<template>
    <div id="change-password">
        <h2>Change your password</h2>

        <p v-if="matchAlert">Your new password entries must match.</p>
        <form @submit.prevent="submitForm">
            <label for="password">Enter your current password:</label>
            <input
                type="text"
                id="old-password"
                class="form-control"
                placeholder="Current password"
                v-model="passwordUpdate.oldPassword"
                required
            />
            <label for="password">Enter your new password:</label>
            <input
                type="text"
                id="new-password"
                class="form-control"
                placeholder="New password"
                v-model="passwordUpdate.newPassword"
                required
            />
            <label for="password">Confirm new password:</label>
            <input
                type="text"
                id="confirm-password"
                class="form-control"
                placeholder="Confirm new password"
                v-model="passwordUpdate.confirmNewPassword"
                required
            />
            <button type="submit">Submit</button>
        </form>

        <p id="result-alert"></p>

    </div>
</template>


<script>
import axios from "axios";

export default {
  name: 'change-password',
  data() {
      return {
          passwordUpdate: {
              oldPassword: '',
              newPassword: '',
              confirmNewPassword: '',
          },
          matchAlert: false,
      }
  },
  methods: {
      submitForm() {
          if(this.passwordUpdate.newPassword == this.passwordUpdate.confirmNewPassword) {
            this.matchAlert = false;

            axios.post(`${process.env.VUE_APP_REMOTE_API}/change-password`, this.passwordUpdate, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("Authorization")
                }
            })
            .then(function (response) {
                console.log(response);
                if(response.data === true) {
                    document.getElementById("result-alert").innerText = "Your password has been successfully updated."
                } else {
                    document.getElementById("result-alert").innerText = "There was an error updating your password."
                }
            })
            .catch(function (error) {
                console.log(error);
            });
          } else {
              this.matchAlert = true;
          }
        }
      }
}
</script>


<style>
</style>