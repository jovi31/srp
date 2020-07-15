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
              v-model="email"
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
              v-model="senha"
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
import FormCard from '../components/FormCard'

export default {
  name: 'Login',
  components: { FormCard },
  data () {
    return {
      email: '',
      senha: ''
    }
  },
  methods: {
    onSubmit () {
      let path = '/'

      if (this.email === 'admin@mail.com') {
        path = '/admin'
      }

      this.$router.push({ path: path })
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
