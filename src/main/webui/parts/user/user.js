let loginValue = ``
let passwordValue = ``

const isEmptyString = (stringElement) => (!stringElement?.trim().length)

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

export const setupLogin = () => {
  getLogin()
  getPassword()

  const loginBtn = document.querySelector('#login')
  loginBtn.addEventListener('click', () => {
    fetch('http://localhost:8080/users/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        login: loginValue,
        password: passwordValue
      })
    })
    .then(res => res.json())
    .then(data => {
      clearFormData()
      if(data.login) {
        console.log(data.login)
      }
      else {
        document.querySelector("#incorrectCredentials").innerText = data.errorMessage
      }
    })
  })
}

export const renderLoginForm = () => {
  return `
    <div class="login_form">
      <h3>Please login before continue</h3>
      <span id="incorrectCredentials"></span>

      <div class="input_box">
        <label for="username">Username</label>
        <input type="text" id="username" placeholder="Username required" required />
      </div>

      <div class="input_box">
        <label for="password">Password</label>
        <input type="password" id="password" placeholder="Password required" required />
      </div>

      <button id="login" type="button" disabled>Login</button>
    </div>
  `
}