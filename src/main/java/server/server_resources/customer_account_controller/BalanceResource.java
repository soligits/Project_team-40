package server.server_resources.customer_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;

public class BalanceResource extends ServerResource {
    @Get
    public Double getBalance() throws AuthorizationException {
        return CustomerAccountController.getInstance().getBalance(getQueryValue("username"));
    }
}
