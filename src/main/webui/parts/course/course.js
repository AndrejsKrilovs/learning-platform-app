import './course.css'

let courseItemPage = 0

const removeChildren = (elementId) => {
  const parent = document.querySelector(elementId)
  let child = parent.lastElementChild

  while (child) {
    parent.removeChild(child)
    child = parent.lastElementChild
  }
}

const selectPageNumber = (event) => {
  const selectNumber = event.target.id.split('page')[1] - 1
  courseItemPage = selectNumber
  removeChildren('#courseItemTableHeaders')
  removeChildren('#courseItemList')
  removeChildren('#pagination')
  generateCourseItems()
}

const appendCourseForCurrentUser = (event) => {
  const courseId = event.target.id.split('course')[1]
  fetch(`http://localhost:8080/courseItems/take/${courseId}`)
    .then(response => {
      if (response.status === 400) {
        response.json().then(data => {
          document.querySelector('#courseError').innerHTML = `
            <div class="alert alert-primary" role="alert">
              Sorry, ${data.violations[0].message}
            </div>
          `
        })
      }
      else {
        removeChildren('#courseItemTableHeaders')
        removeChildren('#courseItemList')
        removeChildren('#pagination')
        generateCourseItems()
      }
    })
}

const renderPaginationItems = (responseData) => {
  if(responseData.totalElements === 0) {
    document.querySelector('#content-data').innerHTML = `
      <div class="alert alert-primary" role="alert">
        No items at this moment. Try later.
      </div>
    `
    return
  }

  let pageNumber = 1

  while (pageNumber <= responseData.totalPages) {
    const paginationButtonElement = document.createElement('button')
    paginationButtonElement.setAttribute('class', 'page-link')
    paginationButtonElement.setAttribute('id', `page${pageNumber}`)
    paginationButtonElement.innerHTML = `${pageNumber}`
    paginationButtonElement.addEventListener('click', selectPageNumber, false)

    const paginationListElement = document.createElement('li')
    paginationListElement.setAttribute('class', 'page-item')
    paginationListElement.appendChild(paginationButtonElement)

    document.querySelector('#pagination').appendChild(paginationListElement)
    pageNumber++
  }
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

    const addCourseButton = document.createElement('button')
    addCourseButton.setAttribute('class', 'btn btn-primary')
    addCourseButton.setAttribute('id', `course${responseItem.id}`)
    addCourseButton.innerText = `Apply to course`
    addCourseButton.addEventListener('click', appendCourseForCurrentUser, false)
    tableRow.appendChild(addCourseButton)

    document.querySelector('#courseItemList').appendChild(tableRow)
  }
}

const renderCourseHeaders = (responseData) => {
  for (let responseHeader of responseData) {
    const headerItemElement = document.createElement('th')
    headerItemElement.setAttribute('scope', 'col')
    headerItemElement.innerHTML = `${responseHeader}`
    document.querySelector('#courseItemTableHeaders').appendChild(headerItemElement)
  }
}

export const generateCourseItems = () => {
  fetch(`http://localhost:8080/courseItems?page=${courseItemPage}`)
    .then(response => response.json())
    .then(data => {
      document.querySelector('#courseError').innerHTML = ``
      renderCourseHeaders(data.headers)
      renderCourseItems(data.items)
      renderPaginationItems(data.metadata)
    })
}

export const renderCourseTable = () => {
  return `
    <span id="courseError">
    </span>
    <table class="table" id="courseTable">
      <thead>
        <tr id="courseItemTableHeaders" />
      </thead>
      <tbody id="courseItemList" />
    </table>
    <nav aria-label="Page navigation example">
      <ul class="pagination" id="pagination" />
    </nav>
  `
}