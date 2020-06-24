package view.account_menus.seller_view.sellers_products_view;

import controller.accounts.SellerAccountController;
import exceptions.AccountsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;
import view.MenuManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductView extends MenuManager {
    public Label nameLabel;
    public Label iDLabel;
    public Label priceLabel;
    public Label companyLabel;
    public Label categoryLabel;
    public Label basePriceLabel;
    public Label quantityLabel;
    public Label statusLabel;
    public Label hasOffLabel;
    public Label explanationLabel;

    public Label nameError;
    public Label companyError;
    public Label priceError;
    public Label quantityError;

    public Button editName;
    public Button editCompany;
    public Button editPrice;
    public Button editQuantityAdd;
    private Button editQuantitySubtract;
    public Button editStatus;
    private ToggleGroup statusOptions;

    private TextField nameField;
    private TextField companyField;
    private TextField priceField;
    private TextField quantityField;

    public GridPane informationTable;
    public VBox propertyList;
    private Product product;
    private SellerAccountController sellerAccountController;

    //TODO: ADD EDIT CATEGORY OPTION IF POSSIBLE

    public void setProduct(Product product) {
        sellerAccountController = SellerAccountController.getInstance();
        this.product = product;
        nameLabel.setText(product.getName());
        iDLabel.setText(product.getProductId());
        priceLabel.setText(Double.toString(product.getPrice()));
        basePriceLabel.setText(Double.toString(product.getBasePrice()));
        quantityLabel.setText(Integer.toString(product.getCount()));
        statusLabel.setText(String.valueOf(product.getStatus()));
        hasOffLabel.setText(String.valueOf(product.isInOff()));
        explanationLabel.setText(product.getExplanation());
        categoryLabel.setText(product.getCategory());
        PropertyManager.setProduct(product);

        addPropertiesToVBox();
    }

    private void addPropertiesToVBox() {
        HashMap<String, Double> extraValueProperties = product.getExtraValueProperties();
        HashMap<String, String> extraStringProperties = product.getExtraStringProperties();
        for(Map.Entry<String, Double> entry : extraValueProperties.entrySet()) {
            AnchorPane item = null;
            try {
                item = (AnchorPane) FXMLLoader.load(getClass().
                        getResource("/layouts/seller_menus/manage_product_menus/property_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(entry, hBox);
                propertyList.getChildren().add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setLabelsContent(Map.Entry<String, Double> entry, HBox hBox) {
        Label propertyNameLabel =(Label) hBox.getChildren().get(0);
        Label propertyValueLabel = (Label) hBox.getChildren().get(1);
        propertyNameLabel.setText(entry.getKey());
        propertyValueLabel.setText(Double.toString(entry.getValue()));
    }

    public void handleCloseWindow(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    public void changeName() {
        editName.setText("save");
        editName.setOnMouseClicked(e -> saveName());
        if(nameField == null)
            nameField = new TextField();
        informationTable.add(nameField, 2, 0);
        nameField.setText(product.getName());
    }

    private void saveName() {
        if(!nameField.getText().isBlank()) {
            editName.setText("edit");
            try {
                sellerAccountController.editProduct(product.getProductId(), "name", nameField.getText(),
                        product.getExtraValueProperties(), product.getExtraStringProperties());
                informationTable.getChildren().remove(nameField);
                nameLabel.setText(nameField.getText());
                nameField.setText("");
                editName.setOnMouseClicked(e -> changeName());
                nameError.setText("Wait for manager's acceptance!");
            } catch (AccountsException e) {
                nameError.setText(e.getMessage());
            }
        } else {
            nameError.setText("Fill this field!");
        }
    }

    public void changeCompany() {
        editCompany.setText("save");
        editCompany.setOnMouseClicked(e -> saveCompany());
        if(companyField == null)
            companyField = new TextField();
        informationTable.add(companyField, 2, 2);
        nameField.setText(product.getCompany());
    }

    private void saveCompany() {
        if(!companyField.getText().isBlank()) {
            editCompany.setText("edit");
            try {
                sellerAccountController.editProduct(product.getProductId(), "company", companyField.getText(),
                        product.getExtraValueProperties(), product.getExtraStringProperties());
                informationTable.getChildren().remove(companyField);
                companyLabel.setText(companyField.getText());
                companyField.setText("");
                editCompany.setOnMouseClicked(e -> changeCompany());
                nameError.setText("Wait for manager's acceptance!");
            } catch (AccountsException e) {
                companyError.setText(e.getMessage());
            }
        } else {
            companyError.setText("Fill this field!");
        }
    }

    public void changePrice() {
        editPrice.setText("save");
        editPrice.setOnMouseClicked(e -> savePrice());
        if(priceField == null)
            priceField = new TextField();
        informationTable.add(priceField, 2, 4);
        priceField.setText(Double.toString(product.getPrice()));
    }

    private void savePrice() {

    }

    public void changeQuantity() {
    }

    public void changeStatus() {
    }
}
