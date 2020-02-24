<template>
  <div id="app">
    <h1>Repository Search</h1>

    <repository-form />
  </div>
</template>

<script>
  import RepositoryForm from '@/components/RepositoryForm.vue'
  // app Vue instance
  const app = {
    name: 'app',
    components: {
      RepositoryForm
    },
    // app initial state
    data: () => {
      return {
      }
    }
  }

  export default app
</script>

<style>
  [v-cloak] { display: none; }
</style>
