package krilovs.andrejs.domain;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Currency;

public class CourseItemDomain {
  private Long id;
  @NotBlank(message = "Course item should be defined")
  private String label;
  @FutureOrPresent(message = "Start date should me in future")
  private LocalDate startDate;
  @PositiveOrZero(message = "Price should not be negative")
  private BigDecimal price;
  private final Currency currency;

  public CourseItemDomain() {
    this.currency = Currency.getInstance("EUR");;
  }

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
    return price.setScale(2, RoundingMode.UP);
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Currency getCurrency() {
    return currency;
  }
}
