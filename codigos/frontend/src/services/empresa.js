import Http from './http'

export const insert = empresa => Http.post('/empresas', empresa)

export const update = empresa => Http.put(`/empresas/${empresa.id}`, empresa)

export const find = id => Http.get(`/empresas/${id}`)

export const findAll = () => Http.get('/empresas')

export const remove = id => Http.delete(`/empresas/${id}`)

export default { insert, update, find, findAll, remove }
