package server.server_resources.customer_account_controller;

import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.model.log.Log;

public class OrderResource extends ServerResource {
    @Get
    public Log getOrder() throws  AuthorizationException {
        try {
            return CustomerAccountController.getInstance().getOrder(getQueryValue("orderID"), getQueryValue("username"));
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }
}
