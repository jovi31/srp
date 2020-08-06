<template>
  <q-page padding>
    <div class="row">
      <q-stepper class="col-8 offset-2" v-model="step" ref="stepper" color="primary" flat bordered>
        <q-step :name="1" title="Selecionar cartão de passe" :done="step > 1">
          <TabelaCartoes @confirmar="registrarCartaoPasse" />
        </q-step>

        <q-step :name="2" title="Inserir dados do cartão de crédito" :done="step > 2">
          <CartaoCreditoForm @submit="registrarCartaoCredito" />
        </q-step>

        <q-step :name="3" title="Configurar agendamento" :done="step > 3">
          <AgendamentoForm @submit="registrarDadosAgendamento" />
        </q-step>

        <q-step :name="4" title="Escolher valor" :done="step > 3">
          <Valor @confirmar="realizarAgendamento" />
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
import CartaoCreditoForm from '../../components/CartaoCreditoForm'
import AgendamentoForm from '../../components/AgendamentoForm'
import Valor from '../../components/Valor'

export default {
  name: 'Agendamento',
  components: { TabelaCartoes, CartaoCreditoForm, AgendamentoForm, Valor },
  data () {
    return {
      step: 1,
      cartaoPasse: {},
      cartaoCredito: {},
      dadosAgendamento: {},
      valor: 0
    }
  },
  methods: {
    registrarCartaoCredito (dados) {
      this.cartaoCredito = dados
      this.$refs.stepper.next()
    },
    registrarCartaoPasse (dados) {
      this.cartaoPasse = dados
      this.$refs.stepper.next()
    },
    registrarDadosAgendamento (dados) {
      this.dadosAgendamento = dados
      this.$refs.stepper.next()
    },
    realizarAgendamento (dados) {
      this.valor = dados.valor
      this.$router.push({ path: '/' })
    }
  }
}
</script>

<style>
</style>
