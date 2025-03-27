import './template.css'
import { renderHomeworkItems } from './../homework/homework.js'
import { renderProgressBar } from './../progress/progress.js'
import { renderCalendar } from './../calendar/calendar.js'
import {
  setupLogin,
  renderLoginForm
} from './../user/user.js'

import {
  renderCourseTable,
  generateCourseItems
} from './../course/course.js'

import {
  renderUserCourseTable,
  generateUserCourseItems
} from './../course/user_course.js'

const itemClick = (event) => {
  switch (event.target.innerHTML) {
    case 'Available courses':
      document.querySelector('#content-data').innerHTML = renderCourseTable()
      generateCourseItems()
      break
    case 'Homeworks':
      document.querySelector('#content-data').innerHTML = renderHomeworkItems()
      break
    case 'Progress':
      document.querySelector('#content-data').innerHTML = renderProgressBar()
      break
      case 'Calendar':
        document.querySelector('#content-data').innerHTML = renderCalendar()
        break
    default:
      document.querySelector('#content-data').innerHTML = renderUserCourseTable()
      generateUserCourseItems()
  }
}

const logoutAction = () => {
  document.querySelector('#logout')
    .addEventListener('click', () => {
      document.querySelector('#app').innerHTML = renderLoginForm()
      setupLogin()
    })
}

const renderMenuItems = (responseData) => {
  for (let responseItem of responseData) {
    const menuLink = document.createElement('h3')
    menuLink.setAttribute('class', 'nav-link')
    menuLink.setAttribute('id', `${responseItem.id}`)
    menuLink.innerText = `${responseItem.label}`
    menuLink.addEventListener('click', itemClick, false)

    const menuItem = document.createElement('li')
    menuItem.setAttribute('class', 'nav-item')
    menuItem.appendChild(menuLink)
    document.querySelector('#menus').appendChild(menuItem)
  }
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
      <div class="container px-4 py-5">
        <h2 class="pb-2 border-bottom">
          <span>Welcome ${user}</span>
          <button id="logout" class="btn btn-primary">Logout</button>
        </h2>

        <ul id="menus" class="nav nav-pills row g-4 py-3 row-cols-1 row-cols-md-3 row-cols-lg-6 border-bottom" />
      </div>

      <div class="container" id="content-data" />
    </div>
  `
}