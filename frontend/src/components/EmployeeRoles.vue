<template>
  <div>
    <h1>Employee Roles</h1>

    <div class="form-group" v-for="(role,k) in roles" :key="k">
      <input type="text" class="form-control" v-model="role.title" />
      <button @click="add(k)" v-show="k == roles.length-1">Add</button>
      <button @click="remove(k)" v-show="k || ( !k && roles.length > 1)">Remove</button>
    </div>
    <br />
    <button @click="submitRoles" type="submit">Submit Roles</button>
  </div>
</template>

<script>
import axios from "axios";
export default {
  components: {},
  data() {
    return {
      roles: [
        {
          title: ""
        }
      ]
    };
  },
  methods: {
    add(index) {
      this.roles.push({ title: "" });
    },
    remove(index) {
      this.roles.splice(index, 1);
    },
    submitRoles() {
      console.log(this.roles);
      axios
        .post(`${process.env.VUE_APP_REMOTE_API}/api/roles`, this.roles, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("Authorization")
          }
        })
        // eslint-disable-next-line no-unused-vars
        .then(response => {
          this.roles = "";
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