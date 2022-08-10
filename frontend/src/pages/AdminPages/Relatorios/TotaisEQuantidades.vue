<template>
  <div>
    <div class="row q-mt-lg">
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
import { showError, formatarData, formatarValor } from 'src/global'

export default {
  name: 'TotaisEQuantidades',
  props: {
    service: { type: Function },
    titulo: { type: String }
  },
  data () {
    return {
      datas: {},
      itensLista: [],
      columns: [
        { name: 'empresaId', label: 'ID da Empresa', align: 'left', field: 'empresaId', sortable: true },
        { name: 'empresa', label: 'Empresa', align: 'left', field: 'empresa', sortable: true },
        {
          name: 'total',
          label: 'Total',
          align: 'left',
          field: item => formatarValor(item.total) || 0,
          sortable: true
        },
        { name: 'quantidade', label: 'Quantidade', align: 'left', field: 'quantidade', sortable: true }
      ]
    }
  },
  methods: {
    async listar () {
      try {
        const inicio = formatarData(this.datas.inicio)
        const termino = formatarData(this.datas.termino)
        const lista = await this.service(inicio, termino)
        this.itensLista = lista
      } catch (err) {
        showError(err.message)
      }
    }
  }
}
</script>

<style>
</style>
