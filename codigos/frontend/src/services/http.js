import axios from 'axios'

const client = axios.create({
  baseURL: 'http://localhost:8081/',
  timeout: 1000,
  headers: { // Teste
    Authorization: 'Basic dXN1YXJpbzAxQG1haWwuY29tOjAxMjM0NTY3'
    // Cookie: 'JSESSIONID=7B859CCC80083BFF777DAB0A7C6139FA'
  }
})

export default client
