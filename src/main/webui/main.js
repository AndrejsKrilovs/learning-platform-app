import {
  setupLogin,
  renderLoginForm
} from './parts/user/user.js'

document.querySelector('#app').innerHTML = renderLoginForm()
setupLogin()
