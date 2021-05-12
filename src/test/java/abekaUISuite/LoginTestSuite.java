package abekaUISuite;

import base.GenericAction;
import constants.*;
import dataProvider.DataProviders;
import elementConstants.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.AbekaHomeScreen;
import pageObjects.DashboardScreen;
import pageObjects.EnrollmentsScreen;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginTestSuite extends GenericAction {
    AbekaHomeScreen abekaHomeScreen;
    DashboardScreen dashboardScreen;
    EnrollmentsScreen enrollmentsScreen;

    @Parameters({"browser", "platform"})
    @BeforeMethod
    public void setUp(String browserName, String platform) {
        super.setUp(browserName, platform);
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void enrollmentPurchase(String userId, String password) {
        CheckoutCriteria checkoutCriteria = new CheckoutCriteria();
        Product product = new Product();
        product.setProductTitle(ShoppingCart.gradeOneVideoBookAccredited);
        product.setItemNumber(ShoppingCart.itemNumber);
        product.setPrice(ShoppingCart.price);
        product.setQuantity(ShoppingCart.quantity);
        product.setSubtotal(getCalculatedSubTotal(product.getQuantity(), product.getPrice()));

        ArrayList productList = new ArrayList(Arrays.asList(product));

        abekaHomeScreen = loginToAbeka(userId, password);
        abekaHomeScreen.navigateToShopByGrade().searchProduct(Enrollments.GRADE_ONE_ENROLLMENT).selectProduct(Enrollments.GRADE_ONE_ENROLLMENT).
                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, BookDescription.accredited, CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut().
                selectCheckoutCriteria(checkoutCriteria).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_ONE_ACCREDITED);
        abekaHomeScreen.logoutFromAbeka();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateAccessControlForParent(String userId, String password) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.dashboard);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateDashboardNewTab();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardMyOrderWidgetsLinks(String userId, String password) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.dashboard);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateMyOrdersWidgetLinks();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardVideoManualPdfsWidgetLinks(String userId, String password) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.dashboard);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateVideoManualPdfsLinks();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardNotificationWidgetLinks(String userId, String password) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.dashboard);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateNotificationRows();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardMyStudentsWidgetLinks(String userId, String password) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.dashboard);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateDashboardMyStudentLink();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateNewStudentCreation(String userId, String password) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = new StudentDetails();
        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.dashboard);
        dashboardScreen.waitAndCloseWidgetTourPopup().navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_ONE_ACCREDITED)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails).clickOnNextButton().fillEnrollmentOptionsDetails(enrollmentOptions)
                .clickOnNextButton().validateBeginDate().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().submitEnrollment().validateAllSetMessage();
    }
}