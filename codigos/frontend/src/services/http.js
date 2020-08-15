import axios from 'axios'

const client = axios.create({
  baseURL: 'http://localhost:8081/',
  timeout: 3000
})

export default client
