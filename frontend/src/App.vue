<template>
  <div id="q-app">
    <router-view />
  </div>
</template>

<script>
// import { mapState } from 'vuex'

export default {
  name: 'App',
  // computed: mapState(['user']),
  mounted () {
    if (this.$router.currentRoute.path !== '/auth') {
      this.$router.push({ path: '/auth' })
    }
  }
}
</script>
