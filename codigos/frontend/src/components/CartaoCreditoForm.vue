<template>
  <div class="q-pa-md">
    <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-md">
      <div class="row">
        <q-input
          class="q-px-sm col-4"
          outlined
          v-model="titular"
          label="Titular do cartão"
          lazy-rules
          :rules="[ val => val && val.length > 0 && val.length <= 30 || 'Campo obrigatório']"
        />

        <q-input
          class="q-px-sm col-4"
          outlined
          v-model="numero"
          label="Número do cartão"
          mask="#### #### #### ####"
          lazy-rules
          :rules="[ val => val && val.length === 19 || 'Campo obrigatório']"
        />
      </div>

      <div class="row">
        <q-select
          class="q-px-sm col-3"
          outlined
          v-model="mesExp"
          :options="meses"
          label="Mês Exp."
          lazy-rules
          :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
        />

        <q-select
          class="q-px-sm col-3"
          outlined
          v-model="anoExp"
          :options="anos"
          label="Ano Exp."
          lazy-rules
          :rules="[ val => val || 'Campo obrigatório']"
        />

        <q-input
          class="q-px-sm col-2"
          outlined
          v-model="cvv"
          label="CVV"
          mask="###"
          lazy-rules
          :rules="[ val => val && val.length <= 3 || 'Campo obrigatório']"
        />
      </div>

      <div class="row">
        <div class="q-px-sm q-mt-lg">
          <q-btn label="Confirmar" type="submit" color="primary" />
          <q-btn label="Limpar" type="reset" color="primary" flat class="q-ml-sm" />
        </div>
      </div>
    </q-form>
  </div>
</template>

<script>
export default {
  name: 'CartaoCreditoForm',
  data () {
    return {
      titular: '',
      numero: '',
      mesExp: '',
      anoExp: null,
      cvv: '',
      meses: [
        'Janeiro', 'Fevereiro', 'Março',
        'Abril', 'Maio', 'Junho',
        'Julho', 'Agosto', 'Setembro',
        'Outubro', 'Novembro', 'Dezembro'
      ],
      anos: []
    }
  },
  methods: {
    onSubmit () {
      const numeroArr = this.numero.split(' ')
      const numero = numeroArr.join('')

      let mesExp = (this.meses.indexOf(this.mesExp) + 1).toString()
      if (mesExp.length === 1) mesExp = '0' + mesExp

      const cartao = {
        titular: this.titular,
        numero,
        mesExp: mesExp,
        anoExp: this.anoExp,
        cvv: this.cvv
      }

      if (this.numero) {
        // Verifica se o cartão é Visa ou Mastercard
        switch (this.numero[0]) {
          case '4':
            cartao.bandeira = 0
            break
          case '5':
            cartao.bandeira = 1
            break
        }
      }

      this.$emit('submit', {
        ...cartao
      })
    },
    onReset () {
      this.titular = ''
      this.numero = ''
      this.mesExp = ''
      this.anoExp = null
      this.cvv = ''
    }
  },
  mounted () {
    for (let i = 2020; i <= 2034; i++) {
      this.anos.push(i)
    }
  }
}
</script>

<style>
</style>
