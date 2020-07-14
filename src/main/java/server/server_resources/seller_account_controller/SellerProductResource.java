package server.server_resources.seller_account_controller;

import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.*;
import server.controller.accounts.SellerAccountController;
import server.model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerProductResource extends ServerResource {
    @Get
    public Product getProductDetails() throws  AuthorizationException {
        try {
            return SellerAccountController.getInstance().getProductDetails(getQueryValue("username"), getQueryValue("productID"));
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    @Post
    public Product createProduct(String username) throws  AuthorizationException {
        String name = getQueryValue("name");
        String company = getQueryValue("company");
        double price = Double.parseDouble(getQueryValue("price"));
        int quantity = Integer.parseInt(getQueryValue("quantity"));
        String category = getQueryValue("category");
        String description = getQueryValue("description");
        try {
            return SellerAccountController.getInstance().createNewProduct(username, name, company, price, quantity, category, description);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    //properties argument contains two hashmaps to edit the product
    @Put
    public void editProduct(ArrayList<HashMap> properties) throws  AuthorizationException {
        String productId = getQueryValue("productID");
        String field = getQueryValue("field");
        String newField = getQueryValue("newField");
        HashMap extraValueProperties = properties.get(0);
        HashMap extraStringProperties = properties.get(1);
        try {
            SellerAccountController.getInstance().editProduct(getQueryValue("username"), productId, field, newField, extraValueProperties, extraStringProperties);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    @Delete
    public void removeProduct() throws  AuthorizationException {
        try {
            SellerAccountController.getInstance().removeProductFromSeller(getQueryValue("username"), getQueryValue("productID"));
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }
}
