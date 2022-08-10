import Http from './http'
import Service from './service'

const config = {
  baseUri: '/cartoes',
  idAttributes: [
    'empresa.id',
    'numero'
  ]
}

const cartaoService = Service(config)
cartaoService.findByCliente = clienteId => Http.get(`/cartoes/findByCliente/${clienteId}`)

export default cartaoService
