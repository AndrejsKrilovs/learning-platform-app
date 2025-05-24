package krilovs.andrejs.app.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import krilovs.andrejs.app.domain.LessonDomain;

@ApplicationScoped
public class LessonRepository implements PanacheRepository<LessonDomain> {
}
