<template>
  <q-page padding>
    <FormCard title="Cadastro de Cidade">
      <q-form @submit="save" @reset="reset" class="q-gutter-md q-mt-lg">
        <div class="row">
          <q-input
            class="col-10"
            outlined
            v-model="cidade.nome"
            label="Nome da cidade"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-select
            class="col-10"
            outlined
            v-model="cidade.uf"
            :options="ufs"
            option-label="nome"
            option-value="sigla"
            label="UF"
            lazy-rules
            :rules="[ val => val || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-btn label="Salvar" type="submit" color="primary" />
          <q-btn label="Limpar" type="reset" color="primary" flat class="q-ml-sm" />
        </div>
      </q-form>
    </FormCard>

    <q-table
      class="q-mt-lg"
      title="Lista de Cidades"
      :data="cidades"
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
            <q-btn @click="loadCidade(props)" size="sm" color="warning" round dense icon="edit" />
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
import cidadeService from '../../services/cidade'
import ufService from '../../services/uf'
import { showError, showSuccess } from '../../global'

import FormCard from '../../components/FormCard'

export default {
  name: 'CadastroCidade',
  components: { FormCard },
  data () {
    return {
      update: false,
      cidade: {
        nome: '',
        uf: null
      },
      ufs: [],
      cidades: [],
      columns: [
        { name: 'id', label: 'Id', align: 'left', field: 'id', sortable: true },
        { name: 'nome', label: 'Nome', align: 'left', field: 'nome', sortable: true },
        { name: 'uf', label: 'UF', align: 'left', field: (cidade) => cidade.uf.sigla, sortable: true }
      ]
    }
  },
  methods: {
    save () {
      const saveCidade = this.update ? cidadeService.update : cidadeService.insert
      const msg = this.update ? 'alterada' : 'inserida'

      saveCidade(this.cidade)
        .then(res => {
          showSuccess(`Cidade ${msg} com sucesso!`)
          this.loadCidades()
        })
        .catch(err => {
          showError(err)
        })
        .finally(() => this.reset())
    },
    remove (tableItem) {
      const id = tableItem.cols[0].value
      cidadeService.remove(id)
        .then(res => {
          showSuccess('Cidade deletada com sucesso!')
          this.loadCidades()
        })
        .catch(err => {
          showError(err)
        })
    },
    loadCidade (tableItem) {
      const id = tableItem.cols[0].value
      const cidade = this.cidades.find(cidade => cidade.id === id)
      this.cidade = { ...cidade }
      this.update = true
    },
    loadCidades () {
      cidadeService.findAll()
        .then(res => {
          this.cidades = res.data
        })
        .catch(err => {
          showError(err)
        })
    },
    loadUfs () {
      ufService.findAll()
        .then(res => {
          this.ufs = res.data
        })
        .catch(err => {
          showError(err)
        })
    },
    reset () {
      this.cidade.nome = ''
      this.cidade.uf = null

      this.update = false
    }
  },
  mounted () {
    this.loadCidades()
    this.loadUfs()
  }
}
</script>

<style>
</style>
