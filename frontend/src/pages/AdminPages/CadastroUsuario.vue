<template>
  <q-page padding>
    <FormCard title="Cadastro de Usuário">
      <q-form @submit="save" @reset="reset" class="q-gutter-md q-mt-lg">
        <div class="row">
          <q-input
            class="col-10"
            outlined
            v-model="usuario.nome"
            label="Nome"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-input
            class="col-10"
            outlined
            v-model="usuario.cpf"
            label="CPF"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-input
            class="col-4 q-pr-sm"
            outlined
            v-model="usuario.ddd"
            label="DDD"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />

          <q-input
            class="col-6"
            type="tel"
            outlined
            v-model="usuario.telefone"
            label="Telefone"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-input
            class="col-10"
            type="email"
            outlined
            v-model="usuario.email"
            label="Email"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-input
            class="col-10"
            type="password"
            outlined
            v-model="usuario.senha"
            label="Senha"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-toggle size="md" v-model="usuario.admin" val="md" label="Administrador" />
        </div>

        <div class="row">
          <q-btn label="Salvar" type="submit" color="primary" />
          <q-btn label="Limpar" type="reset" color="primary" flat class="q-ml-sm" />
        </div>
      </q-form>
    </FormCard>

    <q-table
      class="q-mt-lg"
      title="Lista de Usuários"
      :data="usuarios"
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
            <q-btn @click="loadUsuario(props)" size="sm" color="warning" round dense icon="edit" />
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
import service from '../../services/usuario'
import { showError, showSuccess } from '../../global'

import FormCard from '../../components/FormCard'

export default {
  name: 'CadastroUsuario',
  components: { FormCard },
  data () {
    return {
      update: false,
      usuario: {
        nome: '',
        cpf: '',
        ddd: '',
        telefone: '',
        email: '',
        senha: '',
        admin: false
      },
      usuarios: [],
      columns: [
        { name: 'id', label: 'Id', align: 'left', field: 'id', sortable: true },
        { name: 'nome', label: 'Nome', align: 'left', field: 'nome', sortable: true },
        { name: 'cpf', label: 'CPF', align: 'left', field: 'cpf', sortable: true },
        {
          name: 'telefone',
          label: 'Telefone',
          align: 'left',
          field: (usuario) => `(${usuario.ddd}) ${usuario.telefone}`,
          sortable: true
        },
        { name: 'email', label: 'E-mail', align: 'left', field: 'email', sortable: true },
        {
          name: 'admin',
          label: 'Função',
          align: 'left',
          field: (usuario) => usuario.admin ? 'Administrador' : 'Cliente',
          sortable: true
        }
      ]
    }
  },
  methods: {
    save () {
      const saveUsuario = this.update ? service.update : service.insert
      const msg = this.update ? 'alterado' : 'inserido'

      saveUsuario(this.usuario)
        .then(res => {
          showSuccess(`Usuário ${msg} com sucesso`)
          this.loadUsuarios()
        })
        .catch(err => {
          showError(err)
        })
        .finally(() => this.reset())
    },
    remove (tableItem) {
      const id = tableItem.cols[0].value
      service.remove(id)
        .then(res => {
          showSuccess('Usuário deletado com sucesso!')
          this.loadUsuarios()
        })
        .catch(err => {
          showError(err)
        })
    },
    loadUsuario (tableItem) {
      const id = tableItem.cols[0].value
      const usuario = this.usuarios.find(usuario => usuario.id === id)
      this.usuario = { ...usuario }
      this.update = true
    },
    loadUsuarios () {
      service.findAll()
        .then(res => {
          this.usuarios = res.data
        })
        .catch(err => {
          showError(err)
        })
    },
    reset () {
      this.usuario.nome = ''
      this.usuario.cpf = ''
      this.usuario.ddd = ''
      this.usuario.telefone = ''
      this.usuario.email = ''
      this.usuario.senha = ''
      this.usuario.admin = false

      this.update = false
    }
  },
  mounted () {
    this.loadUsuarios()
  }
}
</script>

<style>
</style>
