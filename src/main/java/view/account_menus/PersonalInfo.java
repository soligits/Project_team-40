package view.account_menus;

import controller.accounts.AccountController;
import controller.accounts.CustomerAccountController;
import controller.accounts.SellerAccountController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.users.Customer;
import model.users.Seller;
import model.users.User;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonalInfo extends AccountMenu implements Initializable {
    private User user = AccountController.getInstance().getThisUser();
    private CustomerAccountController customerAccountController = CustomerAccountController.getInstance();
    private SellerAccountController sellerAccountController = SellerAccountController.getInstance();
    public Pane pane;
    public Label creditLabel;
    public Label credit;
    public Label company;
    public Label companyLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(user instanceof Customer) {
            creditLabel.setText("Credit: ");
            credit.setText(Double.toString(customerAccountController.getBalance()));
        }
        if(user instanceof Seller) {
            creditLabel.setText("Credit: ");
            credit.setText(Double.toString(sellerAccountController.getBalance()));
            companyLabel.setText("Company: ");
            company.setText(sellerAccountController.getCompanyInfo());
        }
    }
}
