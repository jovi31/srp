<template>
  <q-page padding>
    <PorCliente
      :service="listagemService"
      :conta="false"
      titulo="Recargas por Cliente"
    />
  </q-page>
</template>

<script>
import { recargasPorCliente } from '../../../services/listagens'

import PorCliente from './PorCliente'

export default {
  name: 'RecargasPorCliente',
  components: { PorCliente },
  data () {
    return {
      listagemService: recargasPorCliente
    }
  }
}
</script>

<style>

</style>
