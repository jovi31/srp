import axios from 'axios'
import { Notify } from 'quasar'

export const baseApiUrl = 'http://localhost:8081'

export const setAuthorizationHeader = function (userCredentials) {
  if (userCredentials) {
    let basicAuthCredentials = `${userCredentials.email}:${userCredentials.senha}`
    basicAuthCredentials = btoa(basicAuthCredentials)
    // axios.defaults.headers.common['Authorization'] = `Basic ${basicAuthCredentials}`
    axios.defaults.headers.common.Authorization = `Basic ${basicAuthCredentials}`
  } else {
    delete axios.defaults.headers.common.Authorization
  }
}

export function showNotification (type, msg) {
  const notification = { type: type }
  if (msg) notification.message = msg
  Notify.create(notification)
}

export function showError (e) {
  let msg
  if (e && e.response && e.response.data) {
    msg = e.response.data.message
  }
  showNotification('default-error', msg)
}

export function showSuccess (msg) {
  showNotification('default-success', msg)
}

export default { baseApiUrl, showError, showSuccess, showNotification }
