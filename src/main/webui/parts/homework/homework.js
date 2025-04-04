import './homework.css'

export const renderHomeworkItems = () => {
  return `
    <div class="row">

      <div class="col-md-6 col-lg-4 col-xl-3 mb-3 mb-sm-3">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Lecture name</h5>
            <p class="card-text">
              Last uploaded: <b>2025-04-01</b><br/>
              Status: <b>CHECKED</b><br/>
              <button class="btn btn-link">Review</button>
              <button class="btn btn-link">Lecture description</button>
              <button class="btn btn-link">Previous homework</button>
            </p>

            <div class="mb-1">
              <input class="form-control" type="file" id="formFile">
              <button class="btn btn-link" id="formFileBtn">Attach new homework</button>
            </div>
          </div>
        </div>
      </div>

    </div>
  `
}