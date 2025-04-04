let selectedCourseIdentifier = 0

const removeChildren = (elementId) => {
  const parent = document.querySelector(elementId)
  let child = parent.lastElementChild

  while (child) {
    parent.removeChild(child)
    child = parent.lastElementChild
  }
}

const selectLessonPageNumber = (event) => {
  const requestPageNumber = event.target.id.split('lesson')[1] - 1
  fetch(`http://localhost:8080/lessons/take/${selectedCourseIdentifier}?page=${requestPageNumber}`)
    .then(response => response.json())
    .then(data => {
      removeChildren('#modalBody')
      removeChildren('#lessonPagination')
      generateContent(data)
    })
}

export const generateContent = (responseContent) => {
  for (let lesson of responseContent.lessons) {
    const lessonItem = document.createElement('div')
    lessonItem.setAttribute('class', 'row')
    lessonItem.innerHTML = `
      <div class="col-auto">
        <span>${lesson.startsAt}&nbsp;</span>
        <b>${lesson.name}</b>&nbsp;
      </div>
      <div class="col-auto" style="margin-top:-3px">
        <span>Lecturer:&nbsp;</span>
        <b>${lesson.lecturer}</b>&nbsp;
        <button type="button" class="btn btn-link" style="margin-top:-5px">Lesson notes</button>
      </div>
    `
    document.querySelector('#modalBody').appendChild(lessonItem)
  }

  let pageNumber = 1

  while (pageNumber <= responseContent.metadata.totalPages) {
    const paginationButtonElement = document.createElement('button')
    paginationButtonElement.setAttribute('class', 'page-link')
    paginationButtonElement.setAttribute('id', `lesson${pageNumber}`)
    paginationButtonElement.innerHTML = `${pageNumber}`
    paginationButtonElement.addEventListener('click', selectLessonPageNumber, false)

    const paginationListElement = document.createElement('li')
    paginationListElement.setAttribute('class', 'page-item')
    paginationListElement.appendChild(paginationButtonElement)

    document.querySelector('#lessonPagination').appendChild(paginationListElement)
    pageNumber++
  }
}

export const renderModal = (header, content, courseId) => {
  selectedCourseIdentifier = courseId
  return `
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">${header}</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body" id="modalBody" />
        </div>
        <div class="modal-footer justify-content-center">
          <nav aria-label="Page navigation example">
            <ul class="pagination" id="lessonPagination" />
          </nav>
        </div>
      </div>
    </div>
  `
}