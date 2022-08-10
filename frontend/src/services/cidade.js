import Http from './http'

export const insert = cidade => Http.post('/cidades', cidade)

export const update = cidade => Http.put(`/cidades/${cidade.id}`, cidade)

export const find = id => Http.get(`/cidades/${id}`)

export const findByUf = uf => Http.get(`/cidades/findByUf/${uf.sigla}`)

export const findAll = () => Http.get('/cidades')

export const remove = id => Http.delete(`/cidades/${id}`)

export default { insert, update, find, findByUf, findAll, remove }
