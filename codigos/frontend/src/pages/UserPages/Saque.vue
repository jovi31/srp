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
          <Valor @confirmar="realizarSaque" />
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
    realizarSaque (dados) {
      this.valor = dados.valor
      this.$router.push({ path: '/' })
    }
  }
}
</script>

<style>
</style>
