package abekaUISuite;

import base.GenericAction;
import constants.CheckoutCriteria;
import constants.CommonTexts;
import constants.Product;
import dataProvider.DataProviders;
import elementConstants.BookDescription;
import elementConstants.Search;
import elementConstants.ShoppingCart;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.AbekaHomeScreen;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginTestSuite extends GenericAction {
    AbekaHomeScreen abekaHomeScreen;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browserName) {
        super.setUp(browserName);
    }

    @Test(dataProvider = "credentials", dataProviderClass = DataProviders.class)
    public void enrollmentPurchase(String userId, String password){
        Product product = Product.builder().build();
        product.setProductTitle(ShoppingCart.gradeOneVideoBookAccredited);
        product.setItemNumber(ShoppingCart.itemNumber);
        product.setPrice(ShoppingCart.price);
        product.setQuantity(ShoppingCart.quantity);;
        ArrayList productList = new ArrayList(Arrays.asList(product));
        abekaHomeScreen = loginToAbeka(userId,password);
        abekaHomeScreen.navigateToShopByGrade().selectProduct(Search.gradeOneEnrollment).
                selectBookingCriteria(BookDescription.fullYear,BookDescription.videoAndBooks,BookDescription.accredited,CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut().
                selectCheckoutCriteria(CheckoutCriteria.builder().build()).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(CommonTexts.gradeOneAccredited);
        abekaHomeScreen.logoutFromAbeka();
    }
}
