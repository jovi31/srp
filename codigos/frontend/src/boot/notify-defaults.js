import { Notify } from 'quasar'

Notify.setDefaults({
  position: 'bottom',
  timeout: 2500,
  textColor: 'white',
  actions: [{ icon: 'close', color: 'white' }]
})

Notify.registerType('default-success', {
  icon: 'check_circle',
  color: 'positive',
  message: 'Operação realizada com sucesso!'
})

Notify.registerType('default-error', {
  icon: 'warning',
  color: 'negative',
  message: 'Erro inesperado.'
})
