import './course.css'

let userCourseItemPage = 0

const removeChildren = (elementId) => {
  const parent = document.querySelector(elementId)
  let child = parent.lastElementChild

  while (child) {
    parent.removeChild(child)
    child = parent.lastElementChild
  }
}

const appendCourseForCurrentUser = (event) => {
  const courseId = event.target.id.split('course')[1]
  fetch(`http://localhost:8080/courseItems/take/${courseId}`)
    .then(response => {
      removeChildren('#courseItemTableHeaders')
      removeChildren('#courseItemList')
      generateCourseItems()
    })
}

const renderCourseItems = (responseData) => {
  for (let responseItem of responseData) {
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

    document.querySelector('#courseItemList').appendChild(tableRow)
  }
}

const renderCourseHeaders = (responseData) => {
  if(responseData.totalElements === 0) {
    document.querySelector('#content-data').innerHTML = `
      <div class="alert alert-primary" role="alert">
        No items at this moment. Try later.
      </div>
    `
    return
  }

  for (let responseHeader of responseData) {
    const headerItemElement = document.createElement('th')
    headerItemElement.setAttribute('scope', 'col')
    headerItemElement.innerHTML = `${responseHeader}`
    document.querySelector('#courseItemTableHeaders').appendChild(headerItemElement)
  }
}

export const generateUserCourseItems = () => {
  fetch(`http://localhost:8080/courseItems/userCourses`)
    .then(response => response.json())
    .then(data => {
      renderCourseHeaders(data.headers)
      renderCourseItems(data.items)
    })
}

export const renderUserCourseTable = () => {
  return `
    <table class="table">
      <thead>
        <tr id="courseItemTableHeaders" />
      </thead>
      <tbody id="courseItemList" />
    </table>
  `
}