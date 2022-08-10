<template>
  <q-page padding>
    <FormCard title="Cadastro de UF">
      <q-form @submit="save" @reset="reset" class="q-gutter-md q-mt-lg">
        <div class="row">
          <q-input
            class="col-4"
            outlined
            v-model="uf.sigla"
            :readonly="update"
            label="Sigla da UF"
            lazy-rules
            :rules="[ val => val && val.length == 2 || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-input
            class="col-10"
            outlined
            v-model="uf.nome"
            label="Nome da UF"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
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
      title="Lista de UFs"
      :data="ufs"
      :columns="columns"
      row-key="sigla"
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
            <q-btn @click="loadUf(props)" size="sm" color="warning" round dense icon="edit" />
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
import service from '../../services/uf'
import { showError, showSuccess } from '../../global'

import FormCard from '../../components/FormCard'

export default {
  name: 'CadastroUF',
  components: { FormCard },
  data () {
    return {
      update: false,
      uf: {
        sigla: '',
        nome: ''
      },
      ufs: [],
      columns: [
        { name: 'sigla', label: 'Sigla', align: 'left', field: 'sigla', sortable: true },
        { name: 'nome', label: 'Nome', align: 'left', field: 'nome', sortable: true }
      ]
    }
  },
  methods: {
    save () {
      const saveUf = this.update ? service.update : service.insert
      const msg = this.update ? 'alterada' : 'inserida'

      saveUf(this.uf)
        .then(res => {
          showSuccess(`UF ${msg} com sucesso!`)
          this.loadUfs()
        })
        .catch(err => {
          showError(err)
        })
        .finally(() => this.reset())
    },
    remove (tableItem) {
      const sigla = tableItem.cols[0].value

      service.remove(sigla)
        .then(res => {
          showSuccess('UF deletada com sucesso!')
          this.loadUfs()
        })
        .catch(err => {
          showError(err)
        })
    },
    loadUf (tableItem) {
      this.uf.sigla = tableItem.cols[0].value
      this.uf.nome = tableItem.cols[1].value
      this.update = true
    },
    loadUfs () {
      service.findAll()
        .then(res => {
          this.ufs = res.data
        })
    },
    reset () {
      this.update = false
      this.uf.sigla = ''
      this.uf.nome = ''
    }
  },
  mounted () {
    this.loadUfs()
  }
}
</script>

<style lang="sass" scoped>
</style>
