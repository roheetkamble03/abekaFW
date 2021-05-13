package abekaUISuite;

import base.GenericAction;
import constants.*;
import dataProvider.DataProviders;
import elementConstants.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.AbekaHomeScreen;
import pageObjects.DashboardScreen;
import pageObjects.EnrollmentsScreen;
import utility.RetryUtility;

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

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void enrollmentPurchase(String userId, String password) {
        CheckoutCriteria checkoutCriteria = new CheckoutCriteria();
        Product product = new Product();
        product.setProductTitle(ShoppingCart.GRADE_ONE_VIDEO_BOOKS_ENROLLMENT_ACCREDITED);
        product.setItemNumber(ShoppingCart.ITEM_NUMBER);
        product.setPrice(ShoppingCart.PRICE);
        product.setQuantity(ShoppingCart.QUANTITY);
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

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void validateDashBoardWidgets(String userId, String password){
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateDashboardNewTab().validateMyOrdersWidgetLinks().validateVideoManualPdfsLinks().validateNotificationRows();;
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void validateNewStudentCreation(String userId, String password) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = new StudentDetails();
        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup().navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_ONE_ACCREDITED)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails).clickOnNextButton().fillEnrollmentOptionsDetails(enrollmentOptions)
                .clickOnNextButton().validateBeginDate().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().submitEnrollment().validateAllSetMessage();
    }
}