import './course.css'

const renderCourseItems = (responseData) => {
  if(responseData.metadata.totalElements === 0) {
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
    showLessonsButton.innerText = `Show lessons`
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
  fetch(`http://localhost:8080/courseItems/userCourses`)
    .then(response => response.json())
    .then(data => {
      renderCourseHeaders(data)
      renderCourseItems(data)
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