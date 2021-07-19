package pageObjects;

import base.GenericAction;
import elementConstants.BookDescription;

public class BookDescriptionScreen extends GenericAction {
    @Override
    protected void setUp(String browserName, String platform) {

    }

    public BookDescriptionScreen selectBookingCriteria(String enrollmentPeriod, String materials, String programOption, String quantity){
        click(enrollmentPeriod);
        click(materials);
        if(programOption.length()>0) {
            click(programOption);
        }
        type(BookDescription.quantityTxtBox, quantity);
        return this;
    }

    public void clickOnAddToCart(){
        click(BookDescription.addToCart);
    }
}
