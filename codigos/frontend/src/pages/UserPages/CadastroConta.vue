<template>
  <q-page padding>
    <FormCard title="Cadastro de Conta">
      <q-form @submit="save" @reset="reset" class="q-gutter-md q-mt-lg">
        <div class="row">
          <q-input
            class="col-10"
            outlined
            v-model="conta.nome"
            label="Nome"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-input
            class="col-10"
            outlined
            v-model="conta.cpf"
            label="CPF"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-input
            class="col-4"
            outlined
            v-model="conta.banco"
            label="Banco"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />

          <q-input
            class="col-6 q-pl-sm"
            outlined
            v-model="conta.agencia"
            label="Agência"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-input
            class="col-10"
            outlined
            v-model="conta.conta"
            label="Conta"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-select
            class="col-10"
            outlined
            v-model="conta.tipo"
            :options="tiposConta"
            emit-value
            map-options
            label="Tipo da conta"
            lazy-rules
            :rules="[ val => (val != null) || 'Campo obrigatório' ]"
          />
        </div>

        <div class="row">
          <q-btn label="Salvar" type="submit" color="primary" />
          <q-btn label="Limpar" type="reset" color="primary" flat class="q-ml-sm" />
        </div>
      </q-form>
    </FormCard>
  </q-page>
</template>

<script>
import service from '../../services/conta'
import { showError, showSuccess } from '../../global'

import FormCard from '../../components/FormCard'

export default {
  name: 'CadastroConta',
  components: { FormCard },
  props: {
    contaInicial: {
      type: Object
    }
  },
  data () {
    return {
      update: false,
      conta: {
        id: null,
        nome: '',
        cpf: '',
        banco: '',
        agencia: '',
        conta: '',
        tipo: null,
        cliente: null
      },
      tiposConta: [
        { label: 'Corrente', value: 0 },
        { label: 'Poupança', value: 1 }
      ]
    }
  },
  methods: {
    save () {
      const saveConta = this.update ? service.update : service.insert
      const msg = this.update ? 'alterada' : 'inserida'

      saveConta(this.conta)
        .then(res => {
          showSuccess(`Conta ${msg} com sucesso!`)
        })
        .catch(showError)
        .finally(() => this.reset())
    },
    reset () {
      this.conta.id = null
      this.conta.nome = ''
      this.conta.cpf = ''
      this.conta.banco = ''
      this.conta.agencia = ''
      this.conta.conta = ''
      this.conta.tipo = null

      this.update = false
    }
  },
  mounted () {
    if (this.contaInicial) {
      this.update = true
      this.conta = this.contaInicial
    }
    this.conta.cliente = { id: 2 }
  }
}
</script>

<style>
</style>
