import Http from './http'

export const insert = uf => Http.post('/ufs', uf)

export const update = uf => Http.put(`/ufs/${uf.sigla}`, uf)

export const find = sigla => Http.get(`/ufs/${sigla}`)

export const findAll = () => Http.get('/ufs')

export const remove = sigla => Http.delete(`/ufs/${sigla}`)

export default { insert, update, find, findAll, remove }
