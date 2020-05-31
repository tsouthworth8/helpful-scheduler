<template>
  <div id="register" class="text-center">
    <form v-if="!employee" class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="registrationErrors"
      >There were problems registering this user.</div>
      <div v-if="!userRegister">
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
              type="text"
              id="email"
              class="form-control input is-small"
              placeholder="Email"
              v-model="user.email"
              required
              autofocus
            />
            <span class="icon is-small is-left">
              <font-awesome-icon icon="envelope" />
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
        <div class="field">
          <div class="control has-icons-left">
            <input
              type="password"
              id="confirmPassword"
              class="form-control input is-small"
              placeholder="Confirm Password"
              v-model="user.confirmPassword"
              required
            />
            <span class="icon is-small is-left">
              <font-awesome-icon icon="check" />
            </span>
          </div>
        </div>

        <div class="control">
          <button class="button is-small" v-on:click="switchRegistration">Continue Registration</button>
        </div>
        <router-link :to="{ name: 'login' }">Have an account?</router-link>
      </div>

      <div v-if="userRegister">
        <div class="field">
          <div class="control has-icons-left">
            <input
              type="companyName"
              id="companyName"
              class="form-control input is-small"
              placeholder="Company Name"
              v-model="user.companyName"
              required
            />
            <span class="icon is-small is-left">
              <font-awesome-icon icon="building" />
            </span>
          </div>
        </div>

        <div class="control">
          <button class="button is-small" type="submit">Create Account</button>
        </div>
      </div>
    </form>

    <form v-if="employee" class="form-register" @submit.prevent="employeeregister">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="registrationErrors"
      >There were problems registering this user.</div>

      <div class="field">
        <label for="username" class="sr-only">Username</label>
        <div class="control">
          <input
            type="text"
            id="username"
            class="form-control"
            placeholder="Username"
            v-model="user.username"
            required
            autofocus
          />
        </div>
      </div>

      <div class="field">
        <label for="email" class="sr-only">Email</label>
        <div class="control">
          <input
            type="text"
            id="email"
            class="form-control"
            placeholder="Email"
            v-model="user.email"
            required
            autofocus
          />
        </div>
      </div>

      <div class="field">
        <label for="password" class="sr-only">Password</label>
        <div class="control">
          <input
            type="password"
            id="password"
            class="form-control"
            placeholder="Password"
            v-model="user.password"
            required
          />
        </div>
        <div class="control">
          <input
            type="password"
            id="confirmPassword"
            class="form-control"
            placeholder="Confirm Password"
            v-model="user.confirmPassword"
            required
          />
        </div>
      </div>

      <div class="control">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Create Account</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
        manager: true,
        companyName: "",
        companyId: ""
      },
      registrationErrors: false,
      userRegister: false,
      employee: false
    };
  },
  methods: {
    register() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/register`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify(this.user)
      })
        .then(response => {
          if (response.ok) {
            this.$router.push({
              path: "/login",
              query: { registration: "success" }
            });
          } else {
            this.registrationErrors = true;
            this.userRegister = false;
            this.clearInfo();
          }
        })

        .then(err => console.error(err));
    },
    employeeregister() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/register`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify(this.user)
      })
        .then(response => {
          if (response.ok) {
            this.$router.push({
              path: "/login",
              query: { registration: "success" }
            });
          } else {
            this.registrationErrors = true;
            this.userRegister = false;
            this.clearInfo();
          }
        })

        .then(err => console.error(err));
    },
    switchRegistration() {
      this.userRegister = !this.userRegister;
    },
    clearInfo() {
      this.user.username = "";
      this.user.password = "";
      this.user.confirmPassword = "";
      this.user.companyName = "";
      this.user.email = "";
    }
  },
  created() {
    if (this.$route.query.id != null) {
      this.employee = true;
      this.user.companyId = this.$route.query.id;
      this.user.manager = false;
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
