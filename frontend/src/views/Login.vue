<template>
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
      <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <div class="field">
        <div class="control has-icons-left">
          <input
            type="text"
            id="username"
            class="form-control input is-small"
            placeholder="Username"
            v-model="user.username"
            required
            autofocus
          />
          <span class="icon is-small is-left">
            <font-awesome-icon icon="user" />
          </span>
        </div>
      </div>

      <div class="field">
        <div class="control has-icons-left">
          <input
            type="password"
            id="password"
            class="form-control input is-small"
            placeholder="Password"
            v-model="user.password"
            required
          />
          <span class="icon is-small is-left">
            <font-awesome-icon icon="lock" />
          </span>
        </div>
      </div>

      <div class="control">
        <button class="button is-small" type="submit">Sign in</button>
      </div>

      <div class="control">
        <router-link :to="{ name: 'register' }">Need an account?</router-link>
      </div>

      <div class="control">
        <router-link :to="{ name: 'forgot-password' }">Forgot your password?</router-link>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";
import auth from "../auth";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/login`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify(this.user)
      })
        .then(response => {
          if (response.ok) {
            return response.text();
          } else {
            this.invalidCredentials = true;
          }
        })
        .then(token => {
          if (token != undefined) {
            if (token.includes('"')) {
              token = token.replace(/"/g, "");
            }
            auth.saveToken(token);
            this.$router.push("/");
          }
        })
        .catch(err => console.error(err));
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
