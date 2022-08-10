import axios from 'axios'
import { Notify } from 'quasar'

export const baseApiUrl = 'http://localhost:8081'

export const setAuthorizationHeader = function (userCredentials) {
  if (userCredentials) {
    let basicAuthCredentials = `${userCredentials.email}:${userCredentials.senha}`
    basicAuthCredentials = btoa(basicAuthCredentials)
    axios.defaults.headers.common.Authorization = `Basic ${basicAuthCredentials}`
  } else {
    delete axios.defaults.headers.common.Authorization
  }
}

// Notificações
export function showNotification (type, msg) {
  const notification = { type: type }
  if (msg) notification.message = msg
  Notify.create(notification)
}
export function showError (e) {
  let msg
  if (e && e.response && e.response.data) {
    msg = e.response.data.message
  } else if (typeof e === 'string') {
    msg = e
  }
  showNotification('default-error', msg)
}
export function showSuccess (msg) {
  showNotification('default-success', msg)
}

// Formatação
export function formatarValor (valor) {
  if (!valor) {
    return valor
  } else if (!isNaN(valor)) {
    const valorStr = valor.toFixed(2).replace('.', ',')
    return `R$ ${valorStr}`
  } else {
    throw new Error('Valor inválido')
  }
}
export function formatarData (data) {
  if (data && (typeof data === 'string')) {
    const dataFormatada = data.replace('/', '-').replace('/', '-')
    return dataFormatada
  } else {
    throw new Error('Data inválida')
  }
}
export function formatarStatus (status) {
  if (status !== undefined && !isNaN(status)) {
    switch (status) {
      case 0:
        return 'PENDENTE'
      case 1:
        return 'EFETIVADO'
      default:
        throw new Error('Status inválido')
    }
  } else {
    throw new Error('Status inválido')
  }
}

export default { baseApiUrl, showError, showSuccess, showNotification }
