<template>
  <q-page padding>
    <q-table
      class="q-mt-lg"
      title="Recargas"
      :data="recargas"
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
import service from '../../services/recarga'
import { showError } from 'src/global'
import { mapState } from 'vuex'

export default {
  name: 'Recargas',
  data () {
    return {
      cliente: null,
      recargas: [],
      columns: [
        {
          name: 'cartaoInteligente',
          label: 'Cartão Inteligente',
          align: 'left',
          field: recarga => recarga.cartaoInteligente.nome,
          sortable: true
        },
        { name: 'valor', label: 'Valor', align: 'left', field: 'valor', sortable: true },
        { name: 'data', label: 'Data', align: 'left', field: 'data', sortable: true },
        { name: 'status', label: 'Status', align: 'left', field: 'status', sortable: true }
      ]
    }
  },
  methods: {
    loadRecargas () {
      service.findByCliente(this.cliente.id)
        .then(res => {
          this.recargas = res.data
        })
        .catch(showError)
    }
  },
  computed: mapState(['user']),
  mounted () {
    if (this.user) {
      this.cliente = { id: this.user.id }
      this.loadRecargas()
    }
  }
}
</script>

<style>

</style>
