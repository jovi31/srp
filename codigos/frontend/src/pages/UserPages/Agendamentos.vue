<template>
  <q-page padding>
    <div class="row">
      <q-select
        class="col-4"
        outlined
        v-model="cartao"
        :options="cartoesCliente"
        value="id"
        option-label="nome"
        label="Selecione um cartão"
      />
    </div>

    <q-table
      class="q-mt-lg"
      title="Agendamentos"
      :data="agendamentos"
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
            <q-btn @click="update(props)" size="sm" color="warning" round dense icon="edit" />
            <q-btn @click="remove(props)" class="q-ml-sm" size="sm" color="negative" round dense icon="delete" />
          </q-td>

          <q-td key="tipoPeriodo" :props="props">
            {{ props.row.tipoPeriodo }}
          </q-td>

          <q-td key="periodo" :props="props">
            {{ props.row.periodo }}
          </q-td>

          <q-td key="dataInicio" :props="props">
            {{ props.row.dataInicio }}
          </q-td>

          <q-td key="valor" :props="props">
            <q-badge color="green">
              {{ 'R$ ' + props.row.valor }}
            </q-badge>

            <q-popup-edit
              v-model="props.row.valor"
              title="Atualizar valor"
              buttons label-set="Alterar" label-cancel="Cancelar"
            >
              <q-input type="number" v-model="props.row.valor" dense autofocus hint="Informe o novo valor" />
            </q-popup-edit>
          </q-td>
        </q-tr>
      </template>
    </q-table>
  </q-page>
</template>

<script>
import agendamentoService from '../../services/agendamento'
import cartaoService from '../../services/cartao'
import { showError, showSuccess } from '../../global'
import { mapState } from 'vuex'

export default {
  name: 'Agendamentos',
  data () {
    return {
      cliente: null,
      cartao: null,
      cartoesCliente: [],
      agendamentos: [],
      columns: [
        { name: 'tipoPeriodo', label: 'Tipo do período', align: 'left', field: 'tipoPeriodo', sortable: true },
        { name: 'periodo', label: 'Período', align: 'left', field: 'periodo', sortable: true },
        { name: 'dataInicio', label: 'Data de início', align: 'left', field: 'dataInicio', sortable: true },
        {
          name: 'valor',
          label: 'Valor',
          align: 'left',
          field: agendamento => 'R$ ' + agendamento.valor,
          sortable: true
        }
      ]
    }
  },
  methods: {
    remove (tableItem) {
      const id = tableItem.key

      agendamentoService.remove(id)
        .then(res => {
          showSuccess('Agendamento deletado com sucesso!')
        })
        .catch(showError)
        .finally(() => this.loadAgendamentos())
    },
    update (tableItem) {
      const id = tableItem.key
      const agendamento = this.agendamentos.find(agendamento => agendamento.id === id)

      agendamentoService.update(id, agendamento)
        .then(res => {
          showSuccess('Agendamento alterado com sucesso!')
        })
        .catch(showError)
        .finally(() => this.loadAgendamentos())
    },
    loadAgendamentos () {
      agendamentoService.findByCartao(this.cartao.id)
        .then(res => {
          this.agendamentos = res.data
        })
        .catch(showError)
    },
    loadCartoes () {
      cartaoService.findByCliente(this.cliente.id)
        .then(res => {
          this.cartoesCliente = res.data.map(cartao => {
            return {
              id: {
                numero: cartao.id.numero,
                empresa: {
                  id: cartao.id.empresa.id
                }
              },
              nome: cartao.nome
            }
          })
        })
        .catch(showError)
    }
  },
  computed: mapState(['user']),
  watch: {
    cartao () {
      this.loadAgendamentos()
    }
  },
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
