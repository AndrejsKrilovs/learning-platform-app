package krilovs.andrejs.service;

import jakarta.inject.Singleton;
import krilovs.andrejs.domain.MenuItemDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Singleton
public class MenuItemService {
  private final List<MenuItemDomain> menuItems = new ArrayList<>();

  public List<MenuItemDomain> getMenuItems() {
    return menuItems;
  }

  public MenuItemService() {
    menuItems.add(new MenuItemDomain(1L, "Available courses"));
    menuItems.add(new MenuItemDomain(2L, "Taken learnings"));
    menuItems.add(new MenuItemDomain(3L, "Homeworks"));
    menuItems.add(new MenuItemDomain(4L, "Progress"));
    menuItems.add(new MenuItemDomain(5L, "Calendar"));
  }

  public MenuItemDomain addMenuItem(String menuItem) {
    int totalItemElements = menuItems.size() + 1;
    MenuItemDomain menuItemToAdd = new MenuItemDomain((long) totalItemElements, menuItem);
    menuItems.add(menuItemToAdd);
    return menuItemToAdd;
  }
}
