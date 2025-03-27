package krilovs.andrejs.domain;

import jakarta.validation.constraints.NotBlank;

public record MenuItemDomain(
  Long id,
  @NotBlank(message = "Menu item should be defined") String label
) {}