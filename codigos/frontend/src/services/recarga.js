import Http from './http'

export const insert = recarga => Http.post('/recargas', recarga)

export const update = recarga => Http.put(`/recargas/${recarga.id}`, recarga)

export const find = id => Http.get(`/recargas/${id}`)

export const findByCliente = cliente => Http.get(`/recargas/findByCliente/${cliente.id}`)

export const findAll = () => Http.get('/recargas')

export const remove = id => Http.delete(`/recargas/${id}`)

export default { insert, update, find, findByCliente, findAll, remove }
