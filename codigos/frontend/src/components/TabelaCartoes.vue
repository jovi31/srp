<template>
  <div class="q-pa-md">
    <q-table
      title="Cartões de passe"
      :data="cartoes"
      :columns="columns"
      row-key="rowId"
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
import service from '../services/cartao'
import showError from '../global'
import { mapState } from 'vuex'

export default {
  name: 'TabelaCartoes',
  data () {
    return {
      cliente: null,
      selected: [],
      columns: [
        { name: 'nome', label: 'Nome', field: 'nome', sortable: true },
        {
          name: 'numero',
          label: 'Número',
          field: cartao => cartao.id.numero,
          sortable: true
        },
        {
          name: 'empresa',
          label: 'Empresa',
          field: cartao => cartao.id.empresa.nome,
          sortable: true
        }
      ],
      cartoes: []
    }
  },
  methods: {
    confirmar () {
      if (this.selected.length > 0) {
        this.$emit('confirmar', {
          id: {
            numero: this.selected[0].id.numero,
            empresa: {
              id: this.selected[0].id.empresa.id
            }
          }
        })
      }
    },
    loadCartoes () {
      service.findByCliente(this.cliente.id)
        .then(res => {
          this.cartoes = res.data
          this.cartoes.forEach((cartao, index) => {
            cartao.rowId = index
          })
        })
        .catch(showError)
    }
  },
  computed: mapState(['user']),
  mounted () {
    if (this.user) {
      this.cliente = { id: this.user.id }
      this.loadCartoes()
    }
  }
}
</script>

<style>
</style>
