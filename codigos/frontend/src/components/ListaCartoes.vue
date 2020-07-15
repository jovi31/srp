<template>
  <div class="q-pa-md">
    <q-table
      title="Cartões de passe"
      :data="data"
      :columns="columns"
      row-key="id"
      selection="single"
      :selected.sync="selected"
      hide-pagination
      hide-selected-banner
      :rows-per-page-options="[0]"
    />

    <div class="q-pt-sm q-mt-lg">
      <q-btn @click="confirmar" label="Confirmar" color="primary" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'ListaCartoes',
  data () {
    return {
      selected: [],
      columns: [
        { name: 'nome', label: 'Nome', field: 'nome', sortable: true },
        { name: 'numero', label: 'Número', field: 'numero', sortable: true },
        {
          name: 'empresa',
          label: 'Empresa',
          field: row => row.empresa.nome,
          sortable: true
        }
      ],
      data: [
        {
          id: 0, // identificar o item na lista
          nome: 'Cartão01',
          numero: '00000000001',
          empresa: {
            id: 0,
            nome: 'Empresa01'
          }
        }
        // {
        //   id: 1,
        //   nome: 'Cartão01',
        //   numero: '00000000002',
        //   empresa: {
        //     id: 0,
        //     nome: 'Empresa01'
        //   }
        // },
        // {
        //   id: 3,
        //   nome: 'Cartão02',
        //   numero: '00000000001',
        //   empresa: {
        //     id: 1,
        //     nome: 'Empresa02'
        //   }
        // }
      ]
    }
  },
  methods: {
    confirmar () {
      if (this.selected.length > 0) {
        this.$emit('confirmar', {
          ...this.selected
        })
      }
    }
  }
}
</script>

<style>
</style>
