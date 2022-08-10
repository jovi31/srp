import Http from './http'
import Service from './service'

const config = {
  baseUri: '/recargas'
}

const recargaService = Service(config)
recargaService.findByCliente = clienteId => Http.get(`/recargas/findByCliente/${clienteId}`)

export default recargaService
