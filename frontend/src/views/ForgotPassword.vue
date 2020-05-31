<template>
  <div id="forgot-password" class="text-center">
    <form class="form-forgot" @submit.prevent="submitForm" v-if="this.submitted==false">
      <h1>Please enter your account email</h1>
      <div class="field">
        <div class="control has-icons-left">
          <input
            type="text"
            id="email"
            class="form-control input is-small"
            placeholder="Email"
            v-model="email"
            required
            autofocus
          />
          <span class="icon is-small is-left">
            <font-awesome-icon icon="envelope" />
          </span>
        </div>
      </div>
      <div class="control">
        <button class="button is-small" type="submit">Submit</button>
      </div>
    </form>

    <div id="post-submission-form" v-if="this.submitted">
      <p>If there is an account associated with that email, a message has been sent with a new password.</p>
      <router-link id="login-button" :to="{name: 'login'}" tag="button">Log in</router-link>
      <p>No message? Make sure to check your spam or junk folder. Then click here:</p>
      <button id="email-failed" @click="submitted=false">Try again</button>
    </div>
  </div>
</template>


<script>
export default {
  name: "forgot-password",
  data() {
    return {
      email: "",
      submitted: false
    };
  },
  methods: {
    submitForm() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/forgot-password`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("Authorization")
        },
        body: this.email
      })
        .then(response => {
          if (response.ok) {
            return response.text();
          }
        })
        .then(result => {
          if (result != undefined) {
            console.log(result);
            this.submitted = true;
            this.clearInfo();
          }
        })
        .catch(err => console.error(err));
    },
    clearInfo() {
      this.email = "";
    }
  }
};
</script>


<style>
</style>