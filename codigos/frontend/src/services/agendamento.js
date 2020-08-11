import Http from './http'
import Service from './service'

const config = {
  baseUri: '/agendamentos'
}

const agendamentoService = Service(config)
agendamentoService.findByCartao = ciId => {
  return Http.get(`/agendamentos/findByCartao/${ciId.empresa.id}/${ciId.numero}`)
}

export default agendamentoService
