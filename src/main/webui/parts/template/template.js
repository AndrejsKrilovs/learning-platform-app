import './template.css'
import {
  setupLogin,
  renderLoginForm
} from './../user/user.js'

const renderSingleItem = (singleItem, parentElement) => {
  const newElement = document.createElement('div')
  newElement.setAttribute('class', 'feature col')
  newElement.innerHTML = singleItem
  parentElement.appendChild(newElement)
}

const renderMenuItems = (responseItems) => {
  const itemListElement = document.querySelector('#menus')
  responseItems.map(element => `<h5 class="fs-12">${element.label}</h5>`)
    .forEach(value => renderSingleItem(value, itemListElement))
}

const logoutAction = () => {
  document.querySelector('#logout')
    .addEventListener('click', () => {
      document.querySelector('#app').innerHTML = renderLoginForm()
      setupLogin()
    })
}

export const addMenuItems = () => {
  fetch('http://localhost:8080/menuItems')
    .then(response => response.json())
    .then(data => renderMenuItems(data))
    logoutAction()
}

export const renderMainTemplate = (user) => {
  return `
    <div id="mainTemplate">
      <div class="container px-4 py-5" id="featured-3">
        <h2 class="pb-2 border-bottom">
          <span>Welcome ${user}</span>
          <button id="logout" class="btn btn-primary">Logout</button>
        </h2>

        <div id="menus" class="row g-4 py-5 row-cols-1 row-cols-md-3 row-cols-lg-6"/>
      </div>
    </div>
  `
}