<template>
  <q-page padding>
    <div class="row">
      <q-stepper class="col-8 offset-2" v-model="step" ref="stepper" color="primary" flat bordered>
        <q-step
          :name="1"
          title="Selecionar cartão de passe"
          :done="step > 1"
        >
          <ListaCartoes @confirmar="registrarCartaoPasse" />
        </q-step>

        <q-step :name="2" title="Inserir dados do cartão de crédito" :done="step > 2">
          <CartaoCreditoForm @submit="registrarCartaoCredito" />
        </q-step>

        <q-step :name="3" title="Escolher valor" :done="step > 3">
          <Valor @confirmar="realizarRecarga" />
        </q-step>

        <template v-slot:navigation>
          <q-stepper-navigation class="q-mt-lg">
            <!-- <q-btn
              @click="$refs.stepper.next()"
              color="primary"
              :label="step === 3 ? 'Confirmar' : 'Próximo'"
            /> -->
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
import ListaCartoes from '../../components/ListaCartoes'
import CartaoCreditoForm from '../../components/CartaoCreditoForm'
import Valor from '../../components/Valor'

export default {
  name: 'Recarga',
  components: { ListaCartoes, CartaoCreditoForm, Valor },
  data () {
    return {
      step: 1,
      cartaoPasse: {},
      cartaoCredito: {},
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
    realizarRecarga (dados) {
      this.valor = dados.valor
      this.$router.push({ path: '/' })
    }
  }
}
</script>

<style>
</style>
