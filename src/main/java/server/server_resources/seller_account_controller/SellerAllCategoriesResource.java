package server.server_resources.seller_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;
import server.model.Category;

import java.util.HashMap;

public class SellerAllCategoriesResource extends ServerResource {

    @Get
    public HashMap<String, Category> getAllCategories() throws AuthorizationException {
        return SellerAccountController.getInstance().getAllCategories();
    }
}
