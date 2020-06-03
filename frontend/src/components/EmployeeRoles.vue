<template>
  <div>
    <h1>Employee Roles</h1>
    <div> Current Company Roles </div>
    <div v-for="role in currentRoles" v-bind:key="role.id">{{role.title}}</div>  
    <div class="form-group" v-for="(role,k) in roles" :key="k">
      <input type="text" class="form-control" v-model="role.title" placeholder="Company Role"/>
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
      ],
      currentRoles:[
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
    getRoles(){
     axios
      .get(`${process.env.VUE_APP_REMOTE_API}/api/getRoles`, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("Authorization")
        }
      })
      .then(response => {
        this.currentRoles = response.data;
      })
      .catch(error => {
        console.log(error + " there was an error");
      });
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
          this.getRoles();
          this.roles = [{title: "" }];
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created(){
    axios
      .get(`${process.env.VUE_APP_REMOTE_API}/api/getRoles`, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("Authorization")
        }
      })
      .then(response => {
        this.currentRoles = response.data;
      })
      .catch(error => {
        console.log(error + " there was an error");
      });

  }
};
</script>

<style>
</style>