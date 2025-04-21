import './course.css'
import { renderModal, generateContent } from './../modal/modal.js'
import 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'

const getLessonsForCurrentCourse = (event) => {
  const selectedCourseId = event.target.id.split('course')[1]
  fetch(`http://localhost:8080/lessons/take/${event.target.id.split('course')[1]}`)
    .then(response => response.json())
    .then(data => {
      const selectedCourseHeader = data.metadata.courseName
      const selectedCourseLessonList = data
      document.querySelector('#modalArea').innerHTML = renderModal(selectedCourseHeader, selectedCourseLessonList, selectedCourseId)
      generateContent(selectedCourseLessonList)
    })
}

const renderCourseItems = (responseData) => {
  if (responseData.metadata.totalElements === 0) {
    document.querySelector('#content-data').innerHTML = `
      <div class="alert alert-primary" role="alert">
        No items at this moment. Try later.
      </div>
    `
    return
  }

  for (let responseItem of responseData.items) {
    const tableRow = document.createElement('tr')

    const labelElement = document.createElement('td')
    labelElement.innerHTML = `${responseItem.label}`
    tableRow.appendChild(labelElement)

    const startDateElement = document.createElement('td')
    startDateElement.innerHTML = `${responseItem.startDate}`
    tableRow.appendChild(startDateElement)

    const priceElement = document.createElement('td')
    priceElement.innerHTML = `${Number(responseItem.price).toFixed(2)} ${responseItem.currency}`
    tableRow.appendChild(priceElement)

    const showLessonsButton = document.createElement('button')
    showLessonsButton.setAttribute('class', 'btn btn-primary')
    showLessonsButton.setAttribute('id', `course${responseItem.id}`)
    showLessonsButton.setAttribute('data-bs-toggle', 'modal')
    showLessonsButton.setAttribute('data-bs-target', '#exampleModal')
    showLessonsButton.innerText = `Show lessons`
    showLessonsButton.addEventListener('mouseenter', getLessonsForCurrentCourse, true)
    tableRow.appendChild(showLessonsButton)

    document.querySelector('#courseItemList').appendChild(tableRow)
  }
}

const renderCourseHeaders = (responseData) => {
  for (let responseHeader of responseData.headers) {
    const headerItemElement = document.createElement('th')
    headerItemElement.setAttribute('scope', 'col')
    headerItemElement.innerHTML = `${responseHeader}`
    document.querySelector('#courseItemTableHeaders').appendChild(headerItemElement)
  }
}

export const generateUserCourseItems = () => {
  fetch(`http://localhost:8080/courseItems/userCourses`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'username': sessionStorage.getItem('username')
    }
  })
    .then(response => response.json())
    .then(data => {
      renderCourseHeaders(data)
      renderCourseItems(data)
    })
}

export const renderUserCourseTable = () => {
  return `
    <table class="table" id="courseTable">
      <thead>
        <tr id="courseItemTableHeaders" />
      </thead>
      <tbody id="courseItemList" />
    </table>
    <div id="modalArea">
    </div>
  `
}