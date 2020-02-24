<template>
  <div id="repository-form">

    <form v-on:submit.prevent="getfiles">
      <label>Enter the keyword</label>
      <input type="text" v-model="keyword" placeholder="Enter keyword" required />
      <button>Search Repositories</button>
    </form>

    <br>
    <br>

    <label>List of Repositories</label>
    <br>

    <table>
      <thead>
      <tr>
        <th>Repo Name</th>
        <th>Email</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="repo in repos" :key="repo.id">
        <td>{{repo.name}} </td>
        <td>{{repo.fname}}</td>

      </tr>
      </tbody>
    </table>


  </div>
</template>

<script>
  // eslint-disable-next-line no-unused-vars
  import axios from 'axios';
  export default {
    name: 'repository-form',
    data() {
      return {
        repos: [],
        keyword: '',
      }
    },
     methods: {
       getRepo: function () {
         alert('testing handleSubmit')
       },

       getfiles()
       {
         //http://localhost:8080/demo?keyword=tetris
         const path = 'http://127.0.0.1:8080/demo?keyword=tetris';
         console.log(path);
         axios.get(path).then((res) => {
           console.log(res.data);
           this.repos = res.data.repos;
         })
                 .catch((error) => {
                   console.error(error);
                 })
       },

     },

    created() {
      //this.getfiles();
    }
  }
</script>

<style scoped>
  form {
    margin-bottom: 2rem;
  }
</style>
