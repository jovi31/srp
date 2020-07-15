import Http from './http'

export const insert = cartao => Http.post('/cartoes', cartao)

export const update = cartao => Http.put(`/cartoes/${cartao.id.empresa.id}/${cartao.id.numero}`, cartao)

export const find = id => Http.get(`/cartoes/${id.empresa.id}/${id.numero}`)

export const findByCliente = cliente => Http.get(`/cartoes/findByCliente/${cliente.id}`)

export const findAll = () => Http.get('/cartoes')

export const remove = id => Http.delete(`/cartoes/${id.empresa.id}/${id.numero}`)

export default { insert, update, find, findByCliente, findAll, remove }
