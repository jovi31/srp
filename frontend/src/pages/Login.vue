<template>
  <div class="fullscreen text-center q-pa-md flex flex-center">
    <div class="login-form">
      <FormCard title="SRP Login">
        <q-form @submit="onSubmit" class="q-gutter-md q-mt-lg">
          <div class="row q-mt-sm">
            <q-input
              class="col-12"
              type="email"
              outlined
              dense
              v-model="user.email"
              label="E-mail"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
            />
          </div>

          <div class="row q-mt-sm">
            <q-input
              class="col-12"
              type="password"
              outlined
              dense
              v-model="user.senha"
              label="Senha"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
            />
          </div>

          <div class="row">
            <q-btn label="Entrar" type="submit" color="primary" />

            <q-space />

            <router-link
              class="no-text-decoration text-primary text-weight-medium"
              to="/cadastro"
            >Criar conta</router-link>
          </div>
        </q-form>
      </FormCard>
    </div>
  </div>
</template>

<script>
import { signIn } from '../services/auth'
import { setAuthorizationHeader, showNotification, showSuccess } from '../global'

import FormCard from '../components/FormCard'

export default {
  name: 'Login',
  components: { FormCard },
  data () {
    return {
      user: {
        email: '',
        senha: ''
      }
    }
  },
  methods: {
    onSubmit () {
      const userCredentials = { ...this.user }
      setAuthorizationHeader(userCredentials)

      signIn()
        .then(res => {
          const userData = res.data
          this.$store.commit('setUser', userData)

          if (userData.admin) {
            this.$router.push({ path: '/admin' })
          } else {
            this.$router.push({ path: '/' })
          }

          showSuccess('Usuário logado com sucesso!')
        })
        .catch(() => {
          showNotification('default-error', 'Falha na autenticação.')
          this.reset()
        })
    },
    reset () {
      this.user.email = ''
      this.user.senha = ''
    }
  }
}
</script>

<style lang="sass" scoped>
.login-form
  width: 300px;

.no-text-decoration
  text-decoration: none;
</style>
