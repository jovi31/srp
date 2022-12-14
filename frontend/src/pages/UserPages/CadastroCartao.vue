<template>
  <q-page padding>
    <FormCard title="Cadastro de Cartão de Passe">
      <q-form @submit="save" @reset="reset" class="q-gutter-md q-mt-lg">
        <div class="row">
          <q-input
            class="col-10"
            outlined
            v-model="cartao.id.numero"
            label="Número do cartão"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-input
            class="col-10"
            outlined
            v-model="cartao.nome"
            label="Nome"
            lazy-rules
            :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
          />
        </div>

        <div class="row">
          <q-select
            class="col-10"
            outlined
            v-model="cartao.id.empresa"
            :options="empresas"
            value="id"
            option-label="nome"
            label="Empresa"
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
  </q-page>
</template>

<script>
import cartaoService from '../../services/cartao'
import empresaService from '../../services/empresa'
import { showError, showSuccess } from '../../global'
import { mapState } from 'vuex'

import FormCard from '../../components/FormCard'

export default {
  name: 'CadastroCartao',
  components: { FormCard },
  props: {
    cartaoInicial: {
      type: Object
    }
  },
  data () {
    return {
      update: false,
      cartao: {
        id: {
          numero: '',
          empresa: null
        },
        nome: '',
        cliente: null
      },
      empresas: []
    }
  },
  methods: {
    insertOrUpdate () {
      if (this.update) {
        return cartaoService.update(this.cartao.id, this.cartao)
      } else {
        return cartaoService.insert(this.cartao)
      }
    },
    save () {
      const msg = this.update ? 'alterado' : 'inserido'

      this.insertOrUpdate()
        .then(res => {
          showSuccess(`Cartão Inteligente ${msg} com sucesso!`)
        })
        .catch(err => {
          showError(err)
        })
        .finally(() => this.reset())
    },
    loadEmpresas () {
      empresaService.findAll()
        .then(res => {
          this.empresas = res.data.map(empresa => {
            return { id: empresa.id, nome: empresa.nome }
          })
        })
    },
    reset () {
      this.cartao.id.numero = ''
      this.cartao.id.empresa = null
      this.cartao.nome = ''

      this.update = false
    }
  },
  computed: mapState(['user']),
  mounted () {
    this.loadEmpresas()
    if (this.cartaoInicial) {
      this.update = true
      this.cartao = this.cartaoInicial
    }
    if (this.user) {
      this.cartao.cliente = { id: this.user.id }
    }
  }
}
</script>

<style>
</style>
