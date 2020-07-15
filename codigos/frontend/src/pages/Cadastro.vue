<template>
  <div class="fullscreen text-center q-pa-md flex flex-center">
    <div class="login-form">
      <FormCard title="SRP Cadastro">
        <q-form @submit="onSubmit" class="q-gutter-md">
          <div class="row q-mt-none">
            <div class="col-5 q-mt-lg">
              <div class="row">
                <q-input
                  class="col-12"
                  outlined dense
                  v-model="nome"
                  label="Nome"
                  lazy-rules
                  :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
                />
              </div>

              <div class="row">
                <q-input
                  class="col-12"
                  outlined dense
                  v-model="cpf"
                  label="CPF"
                  lazy-rules
                  :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
                />
              </div>

              <div class="row">
                <q-input
                  class="col-5 q-pr-sm"
                  outlined dense
                  v-model="ddd"
                  label="DDD"
                  lazy-rules
                  :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
                />

                <q-input
                  class="col-7"
                  type="tel"
                  outlined dense
                  v-model="telefone"
                  label="Telefone"
                  lazy-rules
                  :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
                />
              </div>
            </div>

            <q-separator vertical inset class="q-mx-auto" />

            <div class="col-5 q-mt-lg">
              <div class="row">
                <q-input
                  class="col-12"
                  type="email"
                  outlined dense
                  v-model="email"
                  label="E-mail"
                  lazy-rules
                  :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
                />
              </div>

              <div class="row">
                <q-input
                  class="col-12"
                  type="email"
                  outlined dense
                  v-model="email2"
                  label="Confirmar E-mail"
                  lazy-rules
                  :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
                />
              </div>

              <div class="row">
                <q-input
                  class="col-12"
                  type="password"
                  outlined dense
                  v-model="senha"
                  label="Senha"
                  lazy-rules
                  :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
                />
              </div>

              <div class="row">
                <q-input
                  class="col-12"
                  type="password"
                  outlined dense
                  v-model="senha2"
                  label="Confirmar Senha"
                  lazy-rules
                  :rules="[ val => val && val.length > 0 || 'Campo obrigatório']"
                />
              </div>
            </div>
          </div>

          <div class="row">
            <q-btn label="Confirmar" type="submit" color="primary" />

            <q-space />

            <router-link
              class="no-text-decoration text-primary text-weight-medium"
              to="/auth"
            >Fazer login</router-link>
          </div>
        </q-form>
      </FormCard>
    </div>
  </div>
</template>

<script>
import FormCard from '../components/FormCard'

export default {
  name: 'Cadastro',
  components: { FormCard },
  data () {
    return {
      nome: '',
      cpf: '',
      ddd: '',
      telefone: '',
      email: '',
      email2: '',
      senha: '',
      senha2: ''
    }
  },
  methods: {
    onSubmit () {
      this.$router.push({ path: '/auth' })
    }
  }
}
</script>

<style lang="sass" scoped>
.login-form
  width: 500px

.no-text-decoration
  text-decoration: none
</style>
