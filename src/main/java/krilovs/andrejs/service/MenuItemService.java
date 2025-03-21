package krilovs.andrejs.service;

import jakarta.inject.Singleton;
import krilovs.andrejs.domain.MenuItemDomain;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class MenuItemService {
  private List<MenuItemDomain> menuItems;

  public List<MenuItemDomain> getMenuItems() {
    return menuItems;
  }

  public void setMenuItems(List<MenuItemDomain> menuItems) {
    this.menuItems = menuItems;
  }

  public MenuItemService() {
    menuItems = new CopyOnWriteArrayList<>();
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
