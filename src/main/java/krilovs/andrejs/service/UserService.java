package krilovs.andrejs.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import krilovs.andrejs.domain.UserDomain;
import krilovs.andrejs.exception.UserException;
import krilovs.andrejs.repo.UserRepository;
import krilovs.andrejs.request.LoginRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

  @Inject
  UserRepository userRepository;

  @Transactional
  public UserDomain authenticateUser(@Valid LoginRequest credentials) {
    UserDomain result = userRepository.findByLoginAndPassword(credentials.login(), credentials.password())
      .orElseThrow(() -> new UserException("UserResource.login", "Incorrect credentials, try again"));

    result.setLastVisitedDate(LocalDateTime.now());
    return result;
  }

  @Transactional
  public UserDomain registerUser(@Valid UserDomain userToRegister) {
    Optional<UserDomain> userFromDatabase = userRepository.findByIdOptional(userToRegister.getLogin(), LockModeType.OPTIMISTIC);
    if (userFromDatabase.isPresent()) {
      throw new UserException("UserService.registerUser", "User '%s' already exists in system".formatted(userToRegister.getLogin()));
    }

    userRepository.persist(userToRegister);
    return userToRegister;
  }

  public List<UserDomain> findAllUsers() {
    return userRepository.getUserList();
  }
}
