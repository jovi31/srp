<template>
  <q-page padding>
    <div class="row q-mt-lg">
      <q-select
        class="col-3"
        outlined
        v-model="uf"
        :options="ufs"
        option-label="nome"
        option-value="sigla"
        label="UF"
      />
    </div>

    <div class="row q-mt-lg">
      <q-btn @click="listar" color="primary" label="Listar" />
    </div>

    <div class="row q-mt-lg">
      <q-table
        class="col-8"
        title="Quantidades de Empresas nas Cidades"
        :data="itensLista"
        :columns="columns"
        row-key="cidadeId"
        hide-pagination
        no-data-label="Sem resultados"
        :rows-per-page-options="[0]"
        flat
        bordered
      ></q-table>
    </div>
  </q-page>
</template>

<script>
import { empresasCidades } from '../../../services/listagens'
import ufService from '../../../services/uf'
import { showError } from '../../../global'

export default {
  name: 'EmpresasCidades',
  data () {
    return {
      uf: null,
      ufs: [],
      itensLista: [],
      columns: [
        { name: 'cidadeId', label: 'ID da Cidade', align: 'left', field: 'cidadeId', sortable: true },
        { name: 'cidade', label: 'Cidade', align: 'left', field: 'cidade', sortable: true },
        { name: 'quantidade', label: 'Quantidade', align: 'left', field: 'quantidade', sortable: true }
      ]
    }
  },
  methods: {
    loadUfs () {
      ufService.findAll()
        .then(res => {
          this.ufs = res.data
        })
        .catch(err => {
          showError(err)
        })
    },
    async listar () {
      try {
        const lista = await empresasCidades(this.uf.sigla)
        this.itensLista = lista
      } catch (err) {
        showError(err.message)
      }
    }
  },
  mounted () {
    this.loadUfs()
  }
}
</script>

<style>
</style>
