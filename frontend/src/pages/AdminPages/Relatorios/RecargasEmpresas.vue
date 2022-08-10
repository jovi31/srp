<template>
  <q-page padding>
    <TotaisEQuantidades
      :service="listagemService"
      titulo="Totais e Quantidades de Recargas nas Empresas"
    />
  </q-page>
</template>

<script>
import { recargasEmpresas } from '../../../services/listagens'

import TotaisEQuantidades from './TotaisEQuantidades'

export default {
  name: 'RecargasEmpresas',
  components: { TotaisEQuantidades },
  data () {
    return {
      listagemService: recargasEmpresas
    }
  }
}
</script>

<style>
</style>
