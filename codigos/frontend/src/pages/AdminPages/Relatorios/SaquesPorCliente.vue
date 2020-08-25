<template>
  <q-page padding>
    <PorCliente
      :service="listagemService"
      :conta="true"
      titulo="Saques por Cliente"
    />
  </q-page>
</template>

<script>
import { saquesPorCliente } from '../../../services/listagens'

import PorCliente from './PorCliente'

export default {
  name: 'SaquesPorCliente',
  components: { PorCliente },
  data () {
    return {
      listagemService: saquesPorCliente
    }
  }
}
</script>

<style>

</style>
