<template>
  <div class="q-pa-md">
    <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-md">
      <div class="row">
        <q-input
          class="col-4"
          type="number"
          outlined
          v-model="periodo"
          label="Período"
          lazy-rules
          :rules="[ val => val && val > 0 || 'Campo obrigatório']"
        />
      </div>

      <div class="row">
        <q-select
          class="col-4"
          outlined
          v-model="tipoPeriodo"
          :options="tipoPeriodoOptions"
          emit-value
          map-options
          label="Tipo do periodo"
          lazy-rules
          :rules="[ val => (val != null) || 'Campo obrigatório' ]"
        />
      </div>

      <div class="row">
        <q-input class="col-4" filled v-model="dataInicio" mask="date" :rules="['date']" label="Data de início">
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy ref="qDateProxy">
                <q-date v-model="dataInicio" @input="() => $refs.qDateProxy.hide()" />
              </q-popup-proxy>
            </q-icon>
          </template>
        </q-input>
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
  name: 'AgendamentoForm',
  data () {
    return {
      dataInicio: '',
      periodo: null,
      tipoPeriodo: null,
      tipoPeriodoOptions: [
        { label: 'Mensal', value: 0 },
        { label: 'Semanal', value: 1 }
      ]
    }
  },
  methods: {
    onSubmit () {
      const formatedDate = this.dataInicio.replaceAll('/', '-')

      this.$emit('submit', {
        dataInicio: formatedDate,
        periodo: this.periodo,
        tipoPeriodo: this.tipoPeriodo
      })
    },
    onReset () {
      this.dataInicio = ''
      this.periodo = null
      this.tipoPeriodo = null
    }
  }
}
</script>

<style>
</style>
