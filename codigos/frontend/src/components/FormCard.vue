<template>
  <q-card flat bordered class="form-card">
    <q-card-section v-if="title">
      <div class="text-h5 text-center text-primary">{{ title }}</div>
    </q-card-section>

    <q-separator v-if="title" inset />

    <q-card-section>
      <slot></slot>
    </q-card-section>
  </q-card>
</template>

<script>
export default {
  name: 'FormCard',
  props: ['title']
}

/*
.form-card
  max-width: 500px
*/
</script>

<style lang="sass" scoped>
</style>
