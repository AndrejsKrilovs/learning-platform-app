package krilovs.andrejs.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import krilovs.andrejs.domain.MenuItemDomain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

@QuarkusTest
class MenuItemServiceTest {
  @Inject
  MenuItemService menuItemService;

  @Test
  void getMenuItems() {
    Assertions.assertFalse(menuItemService.getMenuItems().isEmpty());
  }

  @Test
  void addMenuItem() {
    MenuItemDomain result = menuItemService.addMenuItem(Mockito.anyString());
    Assertions.assertNotNull(result.getId());
  }
}