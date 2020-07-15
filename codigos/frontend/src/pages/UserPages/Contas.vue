<template>
  <q-page padding>
    <q-table
      class="q-mt-lg"
      title="Contas cadastradas"
      :data="contas"
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
          <q-th v-for="col in props.cols" :key="col.name" :props="props">{{ col.label }}</q-th>
        </q-tr>
      </template>

      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td auto-width>
            <q-btn @click="loadConta(props)" size="sm" color="warning" round dense icon="edit" />
            <q-btn
              @click="remove(props)"
              class="q-ml-sm"
              size="sm"
              color="negative"
              round
              dense
              icon="delete"
            />
          </q-td>
          <q-td v-for="col in props.cols" :key="col.name" :props="props">{{ col.value }}</q-td>
        </q-tr>
      </template>
    </q-table>
  </q-page>
</template>

<script>
import service from '../../services/conta'
import { showError, showSuccess } from '../../global'

export default {
  name: 'Contas',
  data () {
    return {
      cliente: null,
      contas: [],
      columns: [
        { name: 'nome', label: 'Nome', align: 'left', field: 'nome', sortable: true },
        { name: 'cpf', label: 'CPF', align: 'left', field: 'cpf', sortable: true },
        { name: 'banco', label: 'Banco', align: 'left', field: 'banco', sortable: true },
        { name: 'agencia', label: 'Agência', align: 'left', field: 'agencia', sortable: true },
        { name: 'conta', label: 'Conta', align: 'left', field: 'conta', sortable: true },
        { name: 'tipo', label: 'Tipo', align: 'left', field: 'tipo', sortable: true }
      ]
    }
  },
  methods: {
    remove (tableItem) {
      const id = tableItem.key
      service.remove(id)
        .then(res => {
          showSuccess('Conta deletada com sucesso!')
          this.loadContas()
        })
        .catch(showError)
    },
    loadConta (tableItem) {
      const id = tableItem.key
      const conta = this.contas.find(conta => conta.id === id)

      delete conta.cliente
      switch (conta.tipo) {
        case 'CORRENTE':
          conta.tipo = 0
          break
        case 'POUPANCA':
          conta.tipo = 1
          break
      }

      this.$router.push({
        name: 'cadastroConta',
        params: {
          contaInicial: { ...conta }
        }
      })
    },
    loadContas () {
      service.findByCliente(this.cliente)
        .then(res => {
          this.contas = res.data
        })
        .catch(showError)
    }
  },
  mounted () {
    this.cliente = { id: 2 } // Teste
    this.loadContas()
  }
}
</script>

<style>
</style>
