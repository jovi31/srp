import { Notify } from 'quasar'

export const baseApiUrl = 'http://localhost:8081'

function showNotification (type, msg) {
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

export default { baseApiUrl, showError, showSuccess }
