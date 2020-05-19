<template>
  <div>
    <h1>Employee Roles</h1>
    <div class="form-group" v-for="(input,k) in roles" :key="k">
    <input type="text" class="form-control" v-model="input.name">
    <span>
        <i class="fas fa-minus-circle" @click="remove(k)" v-show="k || ( !k && roles.length > 1)"></i>
        <i class="fas fa-plus-circle" @click="add(k)" v-show="k == roles.length-1"></i>
    </span>
</div>
    
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      roles: [
        {
        name: ''
        }
      ]
    };
  },
  methods: {
    add(index) {
        this.inputs.push({ name: '' });
        },
    remove(index) {
      this.inputs.splice(index, 1);
        }
    
  }
};
</script>

<style>
</style>