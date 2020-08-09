import Http from './http'
import Service from './service'

const config = {
  baseUri: '/recargas',
  idAttributes: [
    'numero',
    'empresa.id'
  ]
}

const recargaService = Service(config)
recargaService.findByCliente = clienteId => Http.get(`/recargas/findByCliente/${clienteId}`)

export default recargaService
