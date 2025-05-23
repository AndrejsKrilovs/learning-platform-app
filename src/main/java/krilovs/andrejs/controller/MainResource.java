package krilovs.andrejs.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import krilovs.andrejs.repo.LoadDataRepository;

@Path("/main")
public class MainResource {

  @Inject
  LoadDataRepository loadDataRepository;

  @POST
  @Path("/loadCourses")
  public String initCourseData() {
    loadDataRepository.initFakeCourses();
    return "Courses initialized";
  }

  @POST
  @Path("/loadUsers")
  public String initUsers() {
    loadDataRepository.initFakeUsers();
    return "Users loaded";
  }

  @POST
  @Path("/loadLessons")
  public String initLessons() {
    loadDataRepository.initFakeLessons();
    return "Lessons loaded";
  }
}
