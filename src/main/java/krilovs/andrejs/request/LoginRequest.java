package krilovs.andrejs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(@NotBlank(message = "Username should be defined")
                           @Size(max = 20, message = "Maximal length for username is 20 characters")
                           String login,

                           @NotBlank(message = "Password should be defined")
                           String password) {
}
