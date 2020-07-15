<template>
  <div class="q-pa-md">
    <q-table
      title="Contas"
      :data="data"
      :columns="columns"
      row-key="id"
      selection="single"
      :selected.sync="selected"
      hide-pagination
      hide-selected-banner
      :rows-per-page-options="[0]"
    />

    <div class="q-pt-sm q-mt-lg">
      <q-btn @click="confirmar" label="Confirmar" color="primary" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'ListaContas',
  data () {
    return {
      selected: [],
      columns: [
        { name: 'banco', label: 'Banco', field: 'banco', sortable: true },
        { name: 'agencia', label: 'Agência', field: 'agencia', sortable: true },
        { name: 'conta', label: 'Número da conta', field: 'conta', sortable: true },
        { name: 'tipoConta', label: 'Tipo da conta', field: 'tipoConta', sortable: true }
      ],
      data: [
        {
          id: 0,
          banco: '001',
          agencia: '0001',
          conta: '123456789',
          tipoConta: 'corrente'
        }
      ]
    }
  },
  methods: {
    confirmar () {
      if (this.selected.length > 0) {
        this.$emit('confirmar', {
          ...this.selected
        })
      }
    }
  }
}
</script>

<style>
</style>
