package krilovs.andrejs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Getter
@Setter
@Entity
@Table(name = "course_table")
@EqualsAndHashCode(of = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseItemDomain {
  private final static String COURSE_ITEM_SEQUENCE = "course_item_sequence";

  @Id
  @Column(name = "course_id")
  @SequenceGenerator(name = COURSE_ITEM_SEQUENCE, sequenceName = COURSE_ITEM_SEQUENCE, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = COURSE_ITEM_SEQUENCE)
  Long id;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Column(name = "course_label", nullable = false, length = 50)
  @NotBlank(message = "Course item should be defined")
  @Size(max = 50, message = "Course name cannot be more than 50 characters")
  String label;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Column(name = "course_start_date", nullable = false)
  @FutureOrPresent(message = "Start date should me in future")
  LocalDate startDate;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Column(name = "course_price", nullable = false, precision = 5, scale = 2)
  @PositiveOrZero(message = "Price should not be negative")
  BigDecimal price;

  @Column(name = "course_currency", nullable = false)
  final Currency currency = Currency.getInstance("EUR");

  @Version
  @JsonIgnore
  int version;
}
