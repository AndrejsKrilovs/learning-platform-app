export function setupLogin(element) {
  element.addEventListener('click', () => console.log('Login click'))
}

export function renderLoginForm() {
  return `
    <div class="login_form">
      <h3>Please login before continue</h3>

        <div class="input_box">
          <label for="username">Username</label>
          <input type="text" id="username" placeholder="Username required" required />
        </div>

        <div class="input_box">
          <label for="password">Password</label>
          <input type="password" id="password" placeholder="Password required" required />
        </div>

        <button id="login" type="button">Login</button>
    </div>
  `
}