<template>
  <q-page padding>
    <q-table
      class="q-mt-lg"
      title="Saques"
      :data="saques"
      :columns="columns"
      row-key="id"
      hide-pagination
      no-data-label="Sem resultados"
      :rows-per-page-options="[0]"
      flat
      bordered
    >
    </q-table>
  </q-page>
</template>

<script>
import service from '../../services/saque'
import { showError } from '../../global'

export default {
  name: 'Saques',
  data () {
    return {
      cliente: null,
      saques: [],
      columns: [
        {
          name: 'conta',
          label: 'Conta',
          align: 'left',
          field: saque => saque.conta.conta,
          sortable: true
        },
        {
          name: 'cartaoInteligente',
          label: 'Cartão Inteligente',
          align: 'left',
          field: saque => saque.cartaoInteligente.nome,
          sortable: true
        },
        { name: 'valor', label: 'Valor', align: 'left', field: 'valor', sortable: true },
        { name: 'data', label: 'Data', align: 'left', field: 'data', sortable: true },
        { name: 'status', label: 'Status', align: 'left', field: 'status', sortable: true }
      ]
    }
  },
  methods: {
    loadSaques () {
      service.findByCliente(this.cliente.id)
        .then(res => {
          this.saques = res.data
        })
        .catch(showError)
    }
  },
  mounted () {
    this.cliente = { id: 2 }
    this.loadSaques()
  }
}
</script>

<style>

</style>
