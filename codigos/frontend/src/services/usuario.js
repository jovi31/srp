import Http from './http'

export const insert = usuario => Http.post('/usuarios', usuario)

export const update = usuario => Http.put(`/usuarios/${usuario.id}`, usuario)

export const find = id => Http.get(`/usuarios/${id}`)

export const findAll = () => Http.get('/usuarios')

export const remove = id => Http.delete(`/usuarios/${id}`)

export default { insert, update, find, findAll, remove }
