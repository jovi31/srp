import Http from './http'

export const insert = conta => Http.post('/contas', conta)

export const update = conta => Http.put(`/contas/${conta.id}`, conta)

export const find = id => Http.get(`/contas/${id}`)

export const findByCliente = cliente => Http.get(`/contas/findByCliente/${cliente.id}`)

export const findAll = () => Http.get('/contas')

export const remove = id => Http.delete(`/contas/${id}`)

export default { insert, update, find, findByCliente, findAll, remove }
