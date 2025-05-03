package krilovs.andrejs.controller;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class AbstractController<T> {
  protected Map<String, ?> showGetResponse(List<T> items, List<String> headers, Map<String, ?> metadata) {
    return Map.of("items", items,"headers", headers,"metadata", metadata);
  }

  protected abstract T findById(Long id);
  protected abstract T add(@Valid T item);
  protected abstract T modify(@Valid T item);
  protected abstract void remove(Long id);
}
