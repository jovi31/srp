import axios from 'axios'

const client = axios.create({
  baseURL: 'http://localhost:8081/',
  timeout: 1000
  // headers: { 'X-Custom-Header' : 'foobar' }
})

export default client
