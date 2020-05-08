<template>
    <div id="forgot-password">
        <h1>Hey welcome to forgot password how may i take ur order?</h1>

        <form class="form-forgot" @submit.prevent="submitForm">
            <div role="alert" v-if="invalidEmail">
                Sorry, there is no account associated with that email.
            </div>

            <label for="email" class="sr-only">Please enter the email associated with your account.</label>
            <input
                type="text"
                id="email"
                class="form-control"
                placeholder="Email"
                v-model="email"
                required
                autofocus
            />
            <button type="submit">Submit</button>
        </form>
    </div>
</template>


<script>
export default {
  name: 'forgot-password',
  data() {
    return {
        email: '',
        invalidEmail: false,
      }
    },
    methods: {
        submitForm() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/forgot-password`, {
                method: 'POST',
                headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                },
                body: this.email,
            })
                .then((response) => {
                if (response.ok) {
                    return response.text();
                } else {
                    this.invalidCredentials = true;
                }
                })
                .then((result) => {
                if (result != undefined) {
                    console.log(result);
                    //this.$router.push('/');
                }
                })
                .catch((err) => console.error(err));
            },
    }
  }
</script>


<style>
</style>