package pageObjects;

import base.GenericAction;
import elementConstants.BookDescription;

public class BookDescriptionScreen extends GenericAction {
    @Override
    public void setUp(String browserName, String platform) {

    }

    public void navigateToNewVersionOfProduct(){
        waitForElementTobeExist(BookDescription.newVersionOfProductAvailable, veryLongWait);
        click(BookDescription.newVersionOfProductAvailable, false);
    }

    public BookDescriptionScreen selectBookingCriteria(String enrollmentPeriod, String materials, String programOption, String quantity){
        navigateToNewVersionOfProduct();
        click(enrollmentPeriod, false);
        click(materials, false);
        if(programOption.length()>0) {
            click(programOption, false);
        }
        type(BookDescription.quantityTxtBox, quantity);
        return this;
    }

    public void clickOnAddToCart(){
        click(BookDescription.addToCart, false);
        waitForElementTobeExist(BookDescription.productAddedLink, veryLongWait);
    }
}
