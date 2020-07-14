package server.server_resources.accounts;

import exceptions.AccountsException;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.AccountController;
import server.model.users.User;

public class AccountResource extends ServerResource {

    @Post
    public void login(String username)  {
        String password = getQueryValue("password");
        try {
            AccountController.getInstance().login(username, password);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    @Get
    public boolean doesUserExist(){
        return User.doesUserExist(getQueryValue("username"));
    }

}
