package server.server_resources.customer_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.model.log.Log;

import java.util.HashMap;

public class OrdersResource extends ServerResource {
    @Get
    public HashMap<String, Log> getOrders() throws AuthorizationException {
        return CustomerAccountController.getInstance().getOrders(getQueryValue("username"));
    }
}
