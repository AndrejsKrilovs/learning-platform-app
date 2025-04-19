package krilovs.andrejs.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Currency;

@Entity
@Table(name = "course_table")
public class CourseItemDomain {
  private final static String COURSE_ITEM_SEQUENCE = "course_item_sequence";

  @Id
  @Column(name = "course_id")
  @SequenceGenerator(name = COURSE_ITEM_SEQUENCE, sequenceName = COURSE_ITEM_SEQUENCE, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = COURSE_ITEM_SEQUENCE)
  private Long id;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Column(name = "course_label", nullable = false, length = 50)
  @NotBlank(message = "Course item should be defined")
  @Size(max = 50, message = "Course name cannot be more than 50 characters")
  private String label;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Column(name = "course_start_date", nullable = false)
  @FutureOrPresent(message = "Start date should me in future")
  private LocalDate startDate;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Column(name = "course_price", nullable = false, precision = 5, scale = 2)
  @PositiveOrZero(message = "Price should not be negative")
  private BigDecimal price;

  @Column(name = "course_currency", nullable = false)
  private final Currency currency = Currency.getInstance("EUR");

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price.setScale(2, RoundingMode.UP);
  }

  public Currency getCurrency() {
    return currency;
  }
}
