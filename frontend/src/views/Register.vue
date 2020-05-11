<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        There were problems registering this user.
      </div>
      <div v-if="!userRegister">
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="email" class="sr-only">Email</label>
      <input
        type="text"
        id="email"
        class="form-control"
        placeholder="Email"
        v-model="user.email"
        required
        autofocus
      />
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <router-link :to="{ name: 'login' }">
        Have an account?
      </router-link>
      <button v-on:click="switchRegistration">
        Continue Registration
      </button>
      </div>

    <div v-if="userRegister">
      <input
        type="companyName"
        id="companyName"
        class="form-control"
        placeholder="Company Name"
        v-model="user.companyName"
        required
      />
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
    </div>
    </form>    

  </div>
</template>

<script>
export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
        manager: true,
        companyName: '',
      },
      registrationErrors: false,
      userRegister: false,
    };
  },
  methods: {
    register() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/register`, {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.user),
      })
        .then((response) => {
          if (response.ok) {
            this.$router.push({ path: '/login', query: { registration: 'success' } });
          } else {
            this.registrationErrors = true;
            this.userRegister = false;
            this.clearInfo();
          }
        })

        .then((err) => console.error(err));
    },
    switchRegistration(){
      this.userRegister = !this.userRegister;
    },
    clearInfo(){
      this.user.username = '';
      this.user.password = '';
      this.user.confirmPassword = '';
      this.user.companyName = '';
    }
  },
};
</script>

<style>
</style>
