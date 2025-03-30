export const generateContent = (responseContent) => {
  for (let lesson of responseContent) {
    const lessonItem = document.createElement('p')
    lessonItem.innerHTML = `
      <span>${lesson.startsAt}&nbsp;</span>
      <b>${lesson.name}</b>
    `
    document.querySelector('#modalBody').appendChild(lessonItem)
  }
}

export const renderModal = (header, content) => {
  return `
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">${header}</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body" id="modalBody" />
        </div>
      </div>
    </div>
  `
}