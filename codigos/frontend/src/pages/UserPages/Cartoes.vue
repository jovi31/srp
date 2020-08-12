<template>
  <q-page padding>
    <q-table
      class="q-mt-lg"
      title="Cartões cadastrados"
      :data="cartoes"
      :columns="columns"
      row-key="id"
      hide-pagination
      no-data-label="Sem resultados"
      :rows-per-page-options="[0]"
      flat
      bordered
    >
      <template v-slot:header="props">
        <q-tr :props="props">
          <q-th auto-width>Operações</q-th>
          <q-th
            v-for="col in props.cols"
            :key="col.name"
            :props="props"
          >
            {{ col.label }}
          </q-th>
        </q-tr>
      </template>

      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td auto-width>
            <q-btn @click="loadCartao(props)" size="sm" color="warning" round dense icon="edit" />
            <q-btn @click="remove(props)" class="q-ml-sm" size="sm" color="negative" round dense icon="delete" />
          </q-td>
          <q-td
            v-for="col in props.cols"
            :key="col.name"
            :props="props"
          >
            {{ col.value }}
          </q-td>
        </q-tr>
      </template>
    </q-table>
  </q-page>
</template>

<script>
import service from '../../services/cartao'
import { showError, showSuccess } from '../../global'

export default {
  name: 'Cartoes',
  data () {
    return {
      cliente: null,
      cartoes: [],
      columns: [
        { name: 'numero', label: 'Número', align: 'left', field: cartao => cartao.id.numero, sortable: true },
        { name: 'empresa', label: 'Empresa', align: 'left', field: cartao => cartao.id.empresa.nome, sortable: true },
        { name: 'nome', label: 'Nome', align: 'left', field: 'nome', sortable: true },
        { name: 'carteira', label: 'Carteira', align: 'left', field: 'carteira', sortable: true }
      ]
    }
  },
  methods: {
    remove (tableItem) {
      const id = {
        numero: tableItem.key.numero,
        empresa: {
          id: tableItem.key.empresa.id
        }
      }
      service.remove(id)
        .then(res => {
          showSuccess('Cartão inteligente deletado com sucesso')
          this.loadCartoes()
        })
        .catch(showError)
    },
    loadCartao (tableItem) {
      const id = tableItem.key
      const cartao = this.cartoes.find(cartao => {
        return cartao.id.numero === id.numero &&
          cartao.id.empresa.id === id.empresa.id
      })

      this.$router.push({
        name: 'cadastroCartao',
        params: {
          cartaoInicial: {
            id: {
              numero: cartao.id.numero,
              empresa: {
                id: cartao.id.empresa.id,
                nome: cartao.id.empresa.nome
              }
            },
            nome: cartao.nome
          }
        }
      })
    },
    loadCartoes () {
      service.findByCliente(this.cliente.id)
        .then(res => {
          this.cartoes = res.data
        })
        .catch(showError)
    }
  },
  mounted () {
    this.cliente = { id: 2 } // Teste
    this.loadCartoes()
  }
}
</script>

<style>

</style>
