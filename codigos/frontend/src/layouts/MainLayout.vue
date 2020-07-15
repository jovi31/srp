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

        <q-tabs class="q-pl-lg">
          <q-route-tab
            v-for="(tab, index) in tabs"
            :label="tab.title"
            :to="tab.link"
            :key="index"
            exact
          />
        </q-tabs>
      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      :show-if-above="false"
      bordered
      content-class="bg-grey-1"
    >
      <q-list>
        <EssentialLink
          v-for="link in essentialLinks"
          :key="link.title"
          v-bind="link"
        />
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
      tabs: [
        {
          title: 'Recarga',
          link: '/recarga'
        },
        {
          title: 'Saque',
          link: '/saque'
        },
        {
          title: 'Agendamento',
          link: '/agendamento'
        },
        {
          title: 'Cadastrar Cartão',
          link: '/cadastroCartao'
        },
        {
          title: 'Cadastrar Conta',
          link: '/cadastroConta'
        }
      ],
      essentialLinks: [
        {
          title: 'Recargas',
          icon: 'monetization_on',
          link: '/recargas'
        },
        {
          title: 'Saques',
          icon: 'fas fa-hand-holding-usd',
          link: '/saques'
        },
        {
          title: 'Agendamentos',
          icon: 'calendar_today',
          link: '/agendamentos'
        },
        {
          title: 'Cartões',
          icon: 'credit_card',
          link: '/cartoes'
        },
        {
          title: 'Contas',
          icon: 'fas fa-piggy-bank',
          link: '/contas'
        },
        {
          title: 'FAQ',
          icon: 'help',
          link: '/faq'
        },
        {
          title: 'Sair',
          icon: 'power_settings_new',
          link: '/auth'
        }
      ]
    }
  }
}
</script>
