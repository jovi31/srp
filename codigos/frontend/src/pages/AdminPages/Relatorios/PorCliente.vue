<template>
  <div>
    <div class="q-mt-lg row">
      <q-input
        class="col-3"
        v-model="datas.inicio"
        mask="date"
        :rules="['date']"
        label="Data de início"
      >
        <template v-slot:append>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy ref="qDateProxy1">
              <q-date v-model="datas.inicio" @input="() => $refs.qDateProxy1.hide()" />
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>

      <q-input
        class="col-3 q-ml-lg"
        v-model="datas.termino"
        mask="date"
        :rules="['date']"
        label="Data de término"
      >
        <template v-slot:append>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy ref="qDateProxy2">
              <q-date v-model="datas.termino" @input="() => $refs.qDateProxy2.hide()" />
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>
    </div>

    <div class="row q-mt-lg">
      <q-input
        class="col-3"
        outlined
        type="number"
        v-model="clienteId"
        label="ID do cliente"
      />
    </div>

    <div class="row q-mt-lg">
      <q-btn @click="listar" color="primary" label="Listar" />
    </div>

    <div class="row q-mt-lg">
      <q-table
        class="col-8"
        :title="titulo"
        :data="itensLista"
        :columns="columns"
        row-key="empresaId"
        hide-pagination
        no-data-label="Sem resultados"
        :rows-per-page-options="[0]"
        flat
        bordered
      ></q-table>
    </div>
  </div>
</template>

<script>
import { showError, formatarData, formatarValor, formatarStatus } from 'src/global'

export default {
  name: 'PorCliente',
  props: {
    service: { type: Function },
    conta: { type: Boolean },
    titulo: { type: String }
  },
  data () {
    return {
      clienteId: null,
      datas: {},
      itensLista: [],
      columns: [
        { name: 'id', label: 'ID', align: 'left', field: 'id', sortable: true },
        { name: 'data', label: 'Data', align: 'left', field: 'data', sortable: true },
        {
          name: 'status',
          label: 'Status',
          align: 'left',
          field: item => formatarStatus(item.status),
          sortable: true
        },
        {
          name: 'valor',
          label: 'Valor',
          align: 'left',
          field: item => formatarValor(item.valor),
          sortable: true
        },
        { name: 'empresa', label: 'Empresa', align: 'left', field: 'empresa', sortable: true },
        { name: 'cartao', label: 'Cartão', align: 'left', field: 'cartao', sortable: true }
      ]
    }
  },
  methods: {
    async listar () {
      try {
        const inicio = formatarData(this.datas.inicio)
        const termino = formatarData(this.datas.termino)
        const lista = await this.service(this.clienteId, inicio, termino)
        this.itensLista = lista
      } catch (err) {
        showError(err.message)
      }
    }
  },
  created () {
    if (this.conta) {
      this.columns.push({
        name: 'conta',
        label: 'Conta',
        align: 'left',
        field: 'conta',
        sortable: true
      })
    }
  }
}
</script>

<style>
</style>
