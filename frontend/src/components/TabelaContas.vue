<template>
  <div class="q-pa-md">
    <q-table
      title="Contas"
      :data="contas"
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
import service from '../services/conta'
import { showError } from '../global'
import { mapState } from 'vuex'

export default {
  name: 'TabelaContas',
  data () {
    return {
      cliente: null,
      selected: [],
      columns: [
        { name: 'banco', label: 'Banco', field: 'banco', sortable: true },
        { name: 'agencia', label: 'Agência', field: 'agencia', sortable: true },
        { name: 'conta', label: 'Número da conta', field: 'conta', sortable: true },
        { name: 'tipo', label: 'Tipo da conta', field: 'tipo', sortable: true }
      ],
      contas: []
    }
  },
  methods: {
    confirmar () {
      if (this.selected.length > 0) {
        this.$emit('confirmar', {
          ...this.selected[0]
        })
      }
    },
    loadContas () {
      service.findByCliente(this.cliente)
        .then(res => {
          this.contas = res.data
        })
        .catch(showError)
    }
  },
  computed: mapState(['user']),
  mounted () {
    if (this.user) {
      this.cliente = { id: this.user.id }
      this.loadContas()
    }
  }
}
</script>

<style>
</style>
