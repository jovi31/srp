<template>
  <q-page padding>
    <FormCard title="Cadastro de Empresa">
      <div class="row">
        <div class="col-4">
          <q-form @submit="save" @reset="reset" class="q-gutter-md q-mt-lg">
            <div class="row">
              <q-input
                class="col-10"
                outlined
                v-model="empresa.nome"
                label="Nome da empresa"
                lazy-rules
                :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
              />
            </div>

            <div class="row">
              <q-input
                class="col-10"
                outlined
                v-model="empresa.cnpj"
                label="CNPJ"
                lazy-rules
                :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
              />
            </div>

            <div class="row">
              <q-table
                class="col-12"
                title="Cidades"
                :data="cidadesData"
                :columns="cidadeColumns"
                row-key="id"
                :filter="cidadesFilter"
                selection="multiple"
                :selected.sync="empresa.cidade"
                :loading="cidadesLoading"
                hide-pagination
                hide-selected-banner
                no-data-label="Sem resultados"
                :rows-per-page-options="[0]"
                flat
                bordered
              >
                <template v-slot:top-right>
                  <q-input dense debounce="300" v-model="cidadesFilter" placeholder="Buscar">
                    <template v-slot:append>
                      <q-icon name="search" />
                    </template>
                  </q-input>
                </template>
              </q-table>
            </div>

            <div class="row">
              <q-btn label="Salvar" type="submit" color="primary" />
              <q-btn label="Limpar" type="reset" color="primary" flat class="q-ml-sm" />
            </div>
          </q-form>

          <div class="row q-mt-md">
            <q-list class="col-12" bordered separator dense>
              <q-item v-for="cidade in empresa.cidade" :key="cidade.id">
                <q-item-section>{{ cidade.nome + ', ' + cidade.uf.sigla }}</q-item-section>
                <q-item-section side top>
                  <q-btn @click="removerCidade(cidade)" color="negative" rounded dense icon="remove" />
                </q-item-section>
              </q-item>
            </q-list>
          </div>
        </div>

        <q-table
          class="q-mt-lg q-ml-lg col-7"
          title="Lista de Empresas"
          :data="empresasData"
          :columns="empresaColumns"
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
                <q-btn @click="loadEmpresa(props)" size="sm" color="warning" round dense icon="edit" />
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
      </div>
    </FormCard>
  </q-page>
</template>

<script>
import empresaService from '../../services/empresa'
import cidadeService from '../../services/cidade'
import { showError, showSuccess } from '../../global'

import FormCard from '../../components/FormCard'

export default {
  name: 'CadastroEmpresa',
  components: { FormCard },
  data () {
    return {
      update: false,
      empresa: {
        nome: '',
        cnpj: '',
        cidade: []
      },
      cidadesFilter: '',
      cidadesLoading: false,
      cidadeColumns: [
        { name: 'nome', label: 'Nome', field: 'nome', sortable: true },
        { name: 'uf', label: 'UF', field: cidade => cidade.uf.sigla, sortable: true }
      ],
      cidadesData: [],
      empresasFilter: '',
      empresasLoading: false,
      empresaColumns: [
        { name: 'id', label: 'Id', field: 'id', align: 'left', sortable: true },
        { name: 'nome', label: 'Nome', field: 'nome', align: 'left', sortable: true },
        { name: 'cnpj', label: 'CNPJ', field: 'cnpj', align: 'left', sortable: true }
      ],
      empresasData: []
    }
  },
  methods: {
    save () {
      const saveEmpresa = this.update ? empresaService.update : empresaService.insert
      const msg = this.update ? 'alterada' : 'inserida'

      saveEmpresa(this.empresa)
        .then(res => {
          showSuccess(`Empresa ${msg} com sucesso!`)
          this.loadEmpresas()
        })
        .catch(err => {
          showError(err)
        })
        .finally(() => this.reset())
    },
    remove (tableItem) {
      const id = tableItem.cols[0].value
      empresaService.remove(id)
        .then(res => {
          showSuccess('Empresa deletada com sucesso!')
          this.loadEmpresas()
        })
        .catch(err => {
          showError(err)
        })
    },
    loadEmpresa (tableItem) {
      const id = tableItem.cols[0].value
      const empresa = this.empresasData.find(empresa => empresa.id === id)
      this.empresa = { ...empresa }
      this.update = true
    },
    loadEmpresas () {
      this.empresasLoading = true
      empresaService.findAll()
        .then(res => {
          this.empresasData = res.data
        })
        .catch(err => {
          showError(err)
        })
        .finally(() => { this.empresasLoading = false })
    },
    loadCidades () {
      this.cidadesLoading = true
      cidadeService.findAll()
        .then(res => {
          this.cidadesData = res.data
        })
        .catch(err => {
          showError(err)
        })
        .finally(() => { this.cidadesLoading = false })
    },
    reset () {
      this.empresa.nome = ''
      this.empresa.cnpj = ''
      this.empresa.cidade = []

      this.empresasFilter = ''
      this.cidadesFilter = ''

      this.update = false
    },
    removerCidade (cidade) {
      const index = this.empresa.cidade.indexOf(cidade)
      if (index > -1) {
        this.empresa.cidade.splice(index, 1)
      }
    }
  },
  mounted () {
    this.loadEmpresas()
    this.loadCidades()
  }
}
</script>

<style>
</style>
