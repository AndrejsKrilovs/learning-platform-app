let loginValue = ``
let passwordValue = ``

const isEmptyString = (stringElement) => (!stringElement?.trim().length)

const getLogin = () => {
  document.querySelector('#username').addEventListener('input', (event) => {
    loginValue = !isEmptyString(event.target.value) ? event.target.value : ``

    const loginBtn = document.querySelector('#login')
    if (loginValue && passwordValue) {
      loginBtn.removeAttribute('disabled')
    }
    else {
      loginBtn.setAttribute('disabled', 'disabled')
    }
  })
}

const getPassword = () => {
  document.querySelector('#password').addEventListener('input', (event) => {
    passwordValue = !isEmptyString(event.target.value) ? event.target.value : ``

    const loginBtn = document.querySelector('#login')
    if (loginValue && passwordValue) {
      loginBtn.removeAttribute('disabled')
    }
    else {
      loginBtn.setAttribute('disabled', 'disabled')
    }
  })
}

export const setupLogin = () => {
  getLogin()
  getPassword()

  const loginBtn = document.querySelector('#login')
  loginBtn.addEventListener('click', () => {
    console.log('Logging....')
  })
}

export const renderLoginForm = () => {
  return `
    <div class="login_form">
      <h3>Please login before continue</h3>

        <div class="input_box">
          <label for="username">Username</label>
          <span id="incorrectLogin" />
          <input type="text" id="username" placeholder="Username required" required />
        </div>

        <div class="input_box">
          <label for="password">Password</label>
          <span id="incorrectPassword" />
          <input type="password" id="password" placeholder="Password required" required />
        </div>

        <button id="login" type="button" disabled>Login</button>
    </div>
  `
}