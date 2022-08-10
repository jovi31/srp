import Http from './http'
import Service from './service'

const config = {
  baseUri: '/saques'
}

const saqueService = Service(config)
saqueService.findByCliente = clienteId => Http.get(`/saques/findByCliente/${clienteId}`)

export default saqueService
