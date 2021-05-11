package abekaUISuite;

import base.GenericAction;
import constants.CheckoutCriteria;
import constants.CommonTexts;
import constants.Product;
import dataProvider.DataProviders;
import elementConstants.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.AbekaHomeScreen;
import pageObjects.DashboardScreen;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginTestSuite extends GenericAction {
    AbekaHomeScreen abekaHomeScreen;
    DashboardScreen dashboardScreen;

    @Parameters({"browser", "platform"})
    @BeforeMethod
    public void setUp(String browserName, String platform) {
        super.setUp(browserName, platform);
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void enrollmentPurchase(String userId, String password) {
        Product product = Product.builder().build();
        product.setProductTitle(ShoppingCart.gradeOneVideoBookAccredited);
        product.setItemNumber(ShoppingCart.itemNumber);
        product.setPrice(ShoppingCart.price);
        product.setQuantity(ShoppingCart.quantity);
        product.setSubtotal(getCalculatedSubTotal(product.getQuantity(), product.getPrice()));


        ArrayList productList = new ArrayList(Arrays.asList(product));


        abekaHomeScreen = loginToAbeka(userId, password);
        abekaHomeScreen.navigateToShopByGrade().searchProduct(Search.gradeOneEnrollment).selectProduct(Search.gradeOneEnrollment).
                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, BookDescription.accredited, CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut().
                selectCheckoutCriteria(CheckoutCriteria.builder().build()).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(CommonTexts.gradeOneAccredited);
        abekaHomeScreen.logoutFromAbeka();
    }

    @Test(dataProvider = "credentials", dataProviderClass = DataProviders.class)
    public void validateAccessControlForParent(String userId, String password) {
        dashboardScreen = new DashboardScreen();
        abekaHomeScreen = loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.dashboard);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateDashboardNewTab();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardMyOrderWidgetsLinks(String userId, String password) {
        dashboardScreen = new DashboardScreen();
        abekaHomeScreen = loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.dashboard);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateMyOrdersWidgetLinks();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardVideoManualPdfsWidgetLinks(String userId, String password) {
        dashboardScreen = new DashboardScreen();
        abekaHomeScreen = loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.dashboard);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateVideoManualPdfsLinks();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardNotificationWidgetLinks(String userId, String password) {
        dashboardScreen = new DashboardScreen();
        abekaHomeScreen = loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.dashboard);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateNotificationRows();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardMyStudentsWidgetLinks(String userId, String password) {
        dashboardScreen = new DashboardScreen();
        abekaHomeScreen = loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.dashboard);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateDashboardMyStudentLink();
    }
}