package model.users;

import model.DiscountCode;
import model.Product;
import model.log.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends User{
    private ArrayList<String> discountCodes;
    private double credit;
    private ArrayList<String> logsId;
    private ArrayList<String> cart;
    //Key Of products is ID

    public Customer(String username, String password, String firstName, String lastName,
                    String email, String phoneNo, double credit) {
        super(username, password, firstName, lastName, email, phoneNo);
        this.credit = credit;
        logsId = new ArrayList<>();
        cart = new ArrayList<>();
        discountCodes = new ArrayList<>();
    }

    public ArrayList<String> getDiscountCodes() {
        return discountCodes;
    }

    public double getCredit() {
        return credit;
    }

    public ArrayList<String> getLogsId() {
        return logsId;
    }

    public ArrayList<String> getCart() {
        return cart;
    }

    public void addDiscountCode(DiscountCode discountCode){
        discountCodes.add(discountCode.getCode());
    }

    public void addLog(Log purchaseLog){
        logsId.add(purchaseLog.getId());
    }

    public void addProduct(Product product){
        cart.add(product.getProductId());
    }

    public void deleteProduct(Product product){
        cart.remove(product.getProductId());
    }

    public Log getLogById(String id){
        if(logsId.contains(id)) {
            return Log.getLogById(id);
        } else {
            return null;
        }
    }

    public void removeAllProducts(){
        cart.removeAll(cart);
    }

    public double getTotalPriceOfCart() {
        double totalPriceOfCart = 0;
        HashMap<String, Product> allProducts = Product.getAllProducts();
        for(String productId: cart){
            totalPriceOfCart += allProducts.get(productId).getPrice();
        }
        return totalPriceOfCart;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
                "Username: " + username + "\n" +
                "Email Address: " + email + "\n" +
                "Phone Number: " + phoneNo + "\n";
    }
}