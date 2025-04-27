package krilovs.andrejs.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import krilovs.andrejs.domain.MenuItemDomain;
import krilovs.andrejs.service.MenuItemService;

import java.util.List;

@Path("/menuItems")
@Produces(MediaType.APPLICATION_JSON)
public class MenuItemResource {
  @Inject
  private MenuItemService menuItemService;

  @GET
  public List<MenuItemDomain> getMenuItems() {
    return menuItemService.getMenuItems();
  }

  @POST
  @Path("/add")
  public MenuItemDomain addMenuItem(@Valid MenuItemDomain menuItem) {
    return menuItemService.addMenuItem(menuItem.label());
  }
}
