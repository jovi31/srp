<template>
  <q-layout view="hHh Lpr lff">
    <q-header>
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="leftDrawerOpen = !leftDrawerOpen"
        />

        <div class="text-h6 text-white q-ml-lg">
          Administração do Sistema
        </div>
      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      :show-if-above="false"
      bordered
      content-class="bg-grey-1"
    >
      <q-list>
        <q-expansion-item label="CRUDs" :content-inset-level="1" icon="fa fa-database">
          <EssentialLink
            v-for="link in links.cruds"
            :key="link.title"
            v-bind="link"
          />
        </q-expansion-item>

        <q-expansion-item label="Relatórios" :content-inset-level="1" icon="far fa-list-alt">
          <EssentialLink
            v-for="link in links.relatorios"
            :key="link.title"
            v-bind="link"
          />
        </q-expansion-item>

        <EssentialLink v-bind="links.logout"/>
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import EssentialLink from 'components/EssentialLink.vue'

export default {
  name: 'MainLayout',

  components: {
    EssentialLink
  },

  data () {
    return {
      leftDrawerOpen: false,
      links: {
        cruds: [
          {
            title: 'Usuário',
            link: '/admin/cadastroUsuario'
          },
          {
            title: 'Empresa',
            link: '/admin/cadastroEmpresa'
          },
          {
            title: 'Cidade',
            link: '/admin/cadastroCidade'
          },
          {
            title: 'UF',
            link: '/admin/cadastroUf'
          }
        ],
        relatorios: [],
        logout: {
          title: 'Sair',
          icon: 'power_settings_new',
          link: '/auth'
        }
      }
    }
  }
}
</script>
