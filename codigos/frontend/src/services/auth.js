import Http from './http'

export const signIn = () => Http.get('/signIn')

export const signUp = user => Http.post('/signUp', user)
