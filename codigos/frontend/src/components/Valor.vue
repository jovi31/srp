<template>
  <div class="q-pa-md">
    <div class="row">
      <q-btn-toggle
        v-model="valor"
        class="my-custom-toggle"
        size="lg"
        push
        toggle-color="positive"
        color="white"
        text-color="positive"
        :options="[
          {label: 'R$10', value: 10},
          {label: 'R$15', value: 15},
          {label: 'R$20', value: 20},
          {label: 'R$30', value: 30},
          {label: 'R$45', value: 45},
          {label: 'R$50', value: 50}
        ]"
      />
    </div>
    <div class="row q-mt-lg">
      <q-input
        type="number"
        prefix="R$"
        dense
        outlined
        v-model="valor"
        lazy-rules
        :rules="[ val => val && val > 0 && val <= 200 || 'Campo obrigatório']"
      />
    </div>
    <div class="row q-mt-sm q-mt-lg">
      <q-btn @click="confirmar" label="Confirmar" color="primary" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'Valor',
  data () {
    return {
      valor: 0
    }
  },
  methods: {
    confirmar () {
      if (this.valor > 0) {
        this.$emit('confirmar', {
          valor: this.valor
        })
      }
    }
  }
}
</script>

<style>
</style>
