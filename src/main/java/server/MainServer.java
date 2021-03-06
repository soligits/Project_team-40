package server;

import exceptions.DataException;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.service.StatusService;
import server.controller.menus.AuctionController;
import server.model.Loader;
import server.server_resources.accounts.AccountResource;
import server.server_resources.accounts.UserResource;
import server.server_resources.bank.*;
import server.server_resources.chat.*;
import server.server_resources.customer_account_controller.*;
import server.server_resources.manager_account_controller.*;
import server.server_resources.seller_account_controller.*;
import server.server_resources.seller_customer_common.AuctionResource;
import server.server_resources.seller_customer_common.AuctionsProductResource;
import server.server_resources.seller_customer_common.AuctionsResource;
import server.server_resources.seller_customer_common.WalletResource;
import server.server_resources.shop.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MainServer extends Component {
    private static int port;
    private static final String PATH = "src/main/resources";
    public static void main(String[] args) throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.err.println("Saving data ...");
                try {
                    Loader.getLoader().saveData();
                } catch (DataException e) {
                    e.printStackTrace();
                }
            }
        });
        setConfigurations();
        initializeLoading();
        ServerAuthenticator.getInstance().initVerifier();
        AuctionController.checkDeadlines();
        new MainServer().start();
    }

    private static void setConfigurations() throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream("src/main/java/config.properties");
            prop = new Properties();
            prop.load(fis);
            port = Integer.parseInt(prop.getProperty("shop-port"));
            BankInformation.BANK_PORT = Integer.parseInt(prop.getProperty("bank-port"));
            BankInformation.IP = prop.getProperty("bank-ip");
        } catch(IOException fnfe) {
            fnfe.printStackTrace();
        }
        if (fis != null)
            fis.close();
    }


    public MainServer() {
        setStatusService(new StatusService(){
            @Override
            public Representation getRepresentation(Status status, Request request, Response response) {
                if (status.getThrowable() != null)
                    return new StringRepresentation(status.getThrowable().getMessage());
                return null;
            }
        });
        getServers().add(Protocol.HTTP, port);
        ServerAuthenticator authenticator = ServerAuthenticator.getInstance();
        getDefaultHost().attach("/accounts/manager_account_controller/manager/", ManagerResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/support/", SupportResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/user/", authenticator.getNewGuard(ManagerUserResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/users/", authenticator.getNewGuard(ManagerUsersResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/product/", authenticator.getNewGuard(ManagerProductResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/products/", authenticator.getNewGuard(ManagerProductsResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/all_categories/", authenticator.getNewGuard(ManagerAllCategoriesResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/category/", CategoryResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/request/", authenticator.getNewGuard(RequestResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/requests/", authenticator.getNewGuard(RequestsResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/accept_request/",authenticator.getNewGuard( AcceptRequestResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/decline_request/", authenticator.getNewGuard(DeclineRequestResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/discount/", ManagerDiscountResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/all_discounts/", authenticator.getNewGuard(ManagerAllDiscountsResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/store_bank_account/", StoreBankAccountResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/wage/", authenticator.getNewGuard(WageResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/min_wallet_credit/", MinimumWalletCreditResource.class);

        getDefaultHost().attach("/accounts/customer_account_controller/customer/", CustomerResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/all_discounts/", authenticator.getNewGuard(CustomerAllDiscountsResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/balance/", authenticator.getNewGuard(BalanceResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/cart/", authenticator.getNewGuard(CartResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/cart_total_price/", authenticator.getNewGuard(CartTotalPriceResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/discount/", authenticator.getNewGuard(CustomerDiscountResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/product/", authenticator.getNewGuard(CustomerProductResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/product/purchase_status/", CustomerPurchaseStatusResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/order/", authenticator.getNewGuard(OrderResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/orders/", authenticator.getNewGuard(OrdersResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/payment/", authenticator.getNewGuard(PaymentResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/all_customers/", authenticator.getNewGuard(AllCustomersResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/customer_account_controller/bank_account/", authenticator.getNewGuard(StoreBankAccountResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/customer_account_controller/wallet/", authenticator.getNewGuard(CustomerWalletResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/pay_by_bank/", authenticator.getNewGuard(PayByBankResource.class, RoleAccessibility.CUSTOMER));

        getDefaultHost().attach("/accounts/seller_account_controller/seller/", SellerResource.class);
        getDefaultHost().attach("/accounts/seller_account_controller/seller/offs", SellerOffsResource.class);
        getDefaultHost().attach("/accounts/seller_account_controller/all_offs/", authenticator.getNewGuard(AllOffsResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/company_info/", authenticator.getNewGuard(CompanyInfoResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/off/", authenticator.getNewGuard(OffResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/product_count/increase/", authenticator.getNewGuard(ProductCountIncrementResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/product_count/decrease/", authenticator.getNewGuard(ProductCountDecrementResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/product/", authenticator.getNewGuard(SellerProductResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/products/", authenticator.getNewGuard(SellerProductsResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/sales_history/", authenticator.getNewGuard(SalesHistoryResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/all_categories/", authenticator.getNewGuard(SellerAllCategoriesResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/category_properties/", authenticator.getNewGuard(SellerCategoryPropertiesResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/balance/", authenticator.getNewGuard(SellerBalanceResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/manager_permission/", authenticator.getNewGuard(SellingPermissionResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/add_auction/", AddAuctionResource.class);
        getDefaultHost().attach("/accounts/seller_account_controller/wallet/", authenticator.getNewGuard(SellerWalletResource.class, RoleAccessibility.SELLER));

        getDefaultHost().attach("/accounts/seller_customer_common/auction/", AuctionResource.class);
        getDefaultHost().attach("/accounts/seller_customer_common/wallet/", WalletResource.class);
        getDefaultHost().attach("/accounts/seller_customer_common/auctions/", AuctionsResource.class);
        getDefaultHost().attach("/accounts/seller_customer_common/auctions_product/", AuctionsProductResource.class);

        getDefaultHost().attach("/bank/register/", BankRegisterResource.class);
        getDefaultHost().attach("/bank/login/", BankLoginResource.class);
        getDefaultHost().attach("/bank/balance/", BankBalance.class);
        getDefaultHost().attach("/bank/receipts/", ReceiptsResources.class);
        getDefaultHost().attach("/bank/transactions/", TransactionsResources.class);
        getDefaultHost().attach("/bank/create_receipt_resources/", CreateReceiptResources.class);

        getDefaultHost().attach("/accounts/account/", AccountResource.class);
        getDefaultHost().attach("/accounts/user/", UserResource.class);

        getDefaultHost().attach("/shop/all_categories/", AllCategoriesResource.class);
        getDefaultHost().attach("/shop/all_sub_categories/", AllSubCategoriesResource.class);
        getDefaultHost().attach("/shop/comment/", CommentResource.class);
        getDefaultHost().attach("/shop/all_comments/", CommentsResource.class);
        getDefaultHost().attach("/shop/product/", ProductResource.class);
        getDefaultHost().attach("/shop/product/seller", ProductSellerResource.class);
        getDefaultHost().attach("/shop/product/attributes/", ProductAttributesResource.class);
        getDefaultHost().attach("/shop/product/sellers/", ProductSellersResource.class);
        getDefaultHost().attach("/shop/all_products/", ProductsResource.class);
        getDefaultHost().attach("/shop/cart/", ShoppingCartResource.class);
        getDefaultHost().attach("/shop/all_offs/", OffsResource.class);
        getDefaultHost().attach("/shop/all_offs/products/", ProductsInOffResource.class);
        getDefaultHost().attach("/shop/log/", LogResource.class);

        getDefaultHost().attach("/chat/chat/", ChatResource.class);
        getDefaultHost().attach("/chat/message/", MessageResource.class);
        getDefaultHost().attach("/chat/members/", MembersResource.class);
        getDefaultHost().attach("/chat/supports/", SupportsResource.class);
        getDefaultHost().attach("/chat/support_chat/", SupportChatResource.class);
        getDefaultHost().attach("/chat/support_customers/", SupportCustomersResource.class);
        getDefaultHost().attach("/chat/chat_id/", ChatIdResource.class);

        getDefaultHost().attach("/shop/file/", FileResource.class);

        getDefaultHost().attach("/accounts/manager_account_controller/manager_sales_history/", ManagerSalesHistoryResource.class);
    }

    private static void resourcesInitialization() throws DataException {
        File resourcesDirectory = new File(PATH);
        if (!resourcesDirectory.exists())
            if (!resourcesDirectory.mkdir())
                throw new DataException("System loading failed.");
    }

    private static void initializeLoading(){
        try {
            resourcesInitialization();
        } catch (DataException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        try {
            Loader.getLoader().loadData();
        } catch (DataException e) {
            System.err.println(e.getMessage());
        }
    }
}
