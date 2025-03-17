import './user.css'
import { renderMainTemplate, addMenuItems } from './../template/template.js'

let loginValue = ``
let passwordValue = ``
const loginFormInputComponents = [
  {
    id : 'username',
    label: 'Username',
    type: 'text',
    placeholder : 'Username required'
  },
  {
    id : 'password',
    label: 'Password',
    type: 'password',
    placeholder : 'Password required'
  }
]

const isEmptyString = (stringElement) => (!stringElement?.trim().length)

const renderSingleInput = (singleItem, parentElement) => {
  const newElement = document.createElement('div')
  newElement.setAttribute('class', 'input_box')
  newElement.innerHTML = `
    <label for="${singleItem.id}">${singleItem.label}</label>
    <input type="${singleItem.type}" id="${singleItem.id}" placeholder="${singleItem.placeholder}" />
  `
  parentElement.appendChild(newElement)
}

const generateInputFields = (componentItemList) => {
  const inputItems = document.querySelector('#inputItems')
  componentItemList.forEach(value => renderSingleInput(value, inputItems))
}

const hidingLoginButton = () => {
  document.querySelector("#incorrectCredentials").innerText = ``
  const loginBtn = document.querySelector('#login')
  if (loginValue && passwordValue) {
    loginBtn.removeAttribute('disabled')
  }
  else {
    loginBtn.setAttribute('disabled', 'disabled')
  }
}

const getLogin = () => {
  document.querySelector('#username').addEventListener('input', (event) => {
    loginValue = !isEmptyString(event.target.value) ? event.target.value : ``
    hidingLoginButton()
  })
}

const getPassword = () => {
  document.querySelector('#password').addEventListener('input', (event) => {
    passwordValue = !isEmptyString(event.target.value) ? event.target.value : ``
    hidingLoginButton()
  })
}

const clearFormData = () => {
  loginValue = ``
  passwordValue = ``
  document.querySelector('#username').value = ``
  document.querySelector('#password').value = ``
  hidingLoginButton()
}

const loginRequest = async (lg, pwd) => {
  return await fetch('http://localhost:8080/users/login', {
    method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },

      body: JSON.stringify({
        login: lg,
        password: pwd
      })
  }).then(resp => resp.json())
}

const renderingAfterLogin = (data) => {
  clearFormData()
  if(data.login) {
    document.querySelector("#app").innerHTML = renderMainTemplate(data.login)
    addMenuItems()
  }
  else {
    document.querySelector("#incorrectCredentials").innerText = data.violations[0].message
  }
}

export const setupLogin = () => {
  generateInputFields(loginFormInputComponents)
  getLogin()
  getPassword()

  document.querySelector('#login')
    .addEventListener('click', () => {
      loginRequest(loginValue, passwordValue).then(data => renderingAfterLogin(data))
    })
}

export const renderLoginForm = () => {
  return `
    <div id="loginPage">
    <div class="login_form">
      <h3>Please login before continue</h3>
      <span id="incorrectCredentials"></span>
      <div id="inputItems"></div>
      <button id="login" type="button" disabled>Login</button>
    </div>
    </div>
  `
}