<template>
  <q-page padding>
    <div class="row">
      <q-stepper class="col-8 offset-2" v-model="step" ref="stepper" color="primary" flat bordered>
        <q-step
          :name="1"
          title="Selecionar cartão de passe"
          :done="step > 1"
        >
          <TabelaCartoes @confirmar="registrarCartaoPasse" />
        </q-step>

        <q-step
          :name="2"
          title="Selecionar conta"
          :done="step > 2"
        >
          <TabelaContas @confirmar="registrarConta" />
        </q-step>

        <q-step
          :name="3"
          title="Escolher valor"
          :done="step > 3"
        >
          <Valor @confirmar="registrarValor" />
        </q-step>

        <template v-slot:navigation>
          <q-stepper-navigation class="q-mt-lg">
            <q-btn
              v-if="step > 1"
              flat
              color="primary"
              @click="$refs.stepper.previous()"
              label="Voltar"
              class="q-ml-sm"
            />
          </q-stepper-navigation>
        </template>
      </q-stepper>
    </div>
  </q-page>
</template>

<script>
import TabelaCartoes from '../../components/TabelaCartoes'
import TabelaContas from '../../components/TabelaContas'
import Valor from '../../components/Valor'

import service from '../../services/saque'
import { showError, showSuccess } from '../../global'

export default {
  name: 'Saque',
  components: { TabelaCartoes, TabelaContas, Valor },
  data () {
    return {
      step: 1,
      cartaoPasse: {},
      conta: {},
      valor: 0
    }
  },
  methods: {
    registrarCartaoPasse (dados) {
      this.cartaoPasse = dados
      this.$refs.stepper.next()
    },
    registrarConta (dados) {
      this.conta = dados
      this.$refs.stepper.next()
    },
    registrarValor (dados) {
      this.valor = dados.valor
      this.realizarSaque()
    },
    realizarSaque () {
      const saque = {
        valor: this.valor,
        cartaoInteligente: this.cartaoPasse,
        conta: this.conta
      }

      service.insert(saque)
        .then(res => {
          showSuccess('Saque realizado com sucesso!')
        })
        .catch(showError)
        .finally(() => this.$router.push({ path: '/' }))
    }
  }
}
</script>

<style>
</style>
