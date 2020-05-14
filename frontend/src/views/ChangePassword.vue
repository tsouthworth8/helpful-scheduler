<template>
    <div id="change-password">
        <h2>Change your password</h2>
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
          }
      }
  },
  methods: {
    //   checkPasswordsMatch() {
    //       if(this.newPassword == this.confirmNewPassword) {
    //           this.passwordsMatch = true;
    //       } else {
    //           this.passwordsMatch = false;
    //       }
    //   },
      submitForm() {
            axios.post(`${process.env.VUE_APP_REMOTE_API}/change-password`, this.passwordUpdate)
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
        }
      }
}
</script>


<style>
</style>