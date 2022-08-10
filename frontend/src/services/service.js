import Http from './http'

export default function (config) {
  const baseUri = config.baseUri
  const idAttributes = config.idAttributes

  const idUri = function (id) {
    if (typeof id === 'object') {
      let uri = ''
      idAttributes.forEach(attr => {
        let value = id
        const attrArray = attr.split('.')
        for (let i = 0; i < attrArray.length; i++) {
          value = value[attrArray[i]]
        }
        uri += '/' + value
      })

      return uri
    } else {
      return '/' + id
    }
  }

  const insert = obj => Http.post(baseUri, obj)
  const update = (id, obj) => Http.put(baseUri + idUri(id), obj)
  const find = id => Http.get(baseUri + idUri(id))
  const findAll = () => Http.get(baseUri)
  const remove = id => Http.delete(baseUri + idUri(id))

  return {
    insert,
    update,
    find,
    findAll,
    remove
  }
}
