import Http from './http'
import { showNotification } from '../global'

const baseUris = {
  recargasEmpresas: '/recargas/findTotaisAndQuantidadesRecargasOfEmpresasByPeriodo',
  saquesEmpresas: '/saques/findTotaisAndQuantidadesSaquesOfEmpresasByPeriodo',
  recargasPorCliente: '/recargas/findRecargasByClienteAndPeriodo',
  saquesPorCliente: '/saques/findSaquesByClienteAndPeriodo',
  empresasCidades: '/empresas/findQuantidadesEmpresasOfCidadesByUf'
}

export const recargasEmpresas = (inicio, termino) => {
  return totaisEQuantidades(inicio, termino,
    baseUris.recargasEmpresas)
}
export const saquesEmpresas = (inicio, termino) => {
  return totaisEQuantidades(inicio, termino,
    baseUris.saquesEmpresas)
}

export const recargasPorCliente = (clienteId, inicio, termino) => {
  return porCliente(clienteId, inicio, termino,
    baseUris.recargasPorCliente)
}
export const saquesPorCliente = (clienteId, inicio, termino) => {
  return porCliente(clienteId, inicio, termino,
    baseUris.saquesPorCliente)
}

export const empresasCidades = async function (siglaUf) {
  const uri = `${baseUris.empresasCidades}/${siglaUf}`
  return relatorio(uri, item => {
    return {
      cidadeId: item[0],
      cidade: item[1],
      quantidade: item[2]
    }
  })
}

const relatorio = async function (uri, callback) {
  try {
    const response = await Http.get(uri)
    const lista = response.data.map(callback)
    return lista
  } catch (err) {
    showNotification('default-error')
  }
}

const totaisEQuantidades = (inicio, termino, baseUri) => {
  const uri = `${baseUri}/${inicio}/${termino}`
  return relatorio(uri, item => {
    return {
      empresaId: item[0],
      empresa: item[1],
      total: item[2],
      quantidade: item[3]
    }
  })
}

const porCliente = (clienteId, inicio, termino, baseUri) => {
  const uri = `${baseUri}/${clienteId}/${inicio}/${termino}`
  return relatorio(uri, item => {
    let data = new Date(item[1])
    data = data.toLocaleDateString('pt-BR')

    const obj = {
      id: item[0],
      data,
      status: item[2],
      valor: item[3],
      empresa: item[4],
      cartao: item[5]
    }
    if (item[6]) obj.conta = item[6]
    return obj
  })
}
