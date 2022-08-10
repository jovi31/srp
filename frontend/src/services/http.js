import axios from 'axios'

const client = axios.create({
  baseURL: 'https://srp-backend-spring.herokuapp.com/',
  timeout: 6000
})

export default client
