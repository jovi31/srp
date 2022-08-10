<template>
  <q-page padding>
    <TotaisEQuantidades
      :service="listagemService"
      titulo="Totais e Quantidades de Saques nas Empresas"
    />
  </q-page>
</template>

<script>
import { saquesEmpresas } from '../../../services/listagens'

import TotaisEQuantidades from './TotaisEQuantidades'

export default {
  name: 'SaquesEmpresas',
  components: { TotaisEQuantidades },
  data () {
    return {
      listagemService: saquesEmpresas
    }
  }
}
</script>

<style>
</style>
