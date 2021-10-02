package pageObjects;

import base.GenericAction;
import com.codeborne.selenide.Condition;
import constants.CheckoutCriteria;
import elementConstants.Checkout;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class CheckoutScreen extends GenericAction {
    @Override
    public void setUp(String browserName, String platform) {

    }

    public CheckoutScreen selectCheckoutCriteria(CheckoutCriteria checkoutCriteria){
        selectPaymentTerm(checkoutCriteria.getPaymentTerm());
        selectShippingAddress(checkoutCriteria.getShippingAddress());
        selectShippingMethod(checkoutCriteria.getShippingMethod());
        selectBillingAddress(checkoutCriteria.isBillingAndShippingAddressSame());
        selectPaymentMethod(checkoutCriteria.getPaymentInformation());
        selectTermsAndCondition(checkoutCriteria.isAcceptTermsAndCondition());
        return this;
    }

    public void selectPaymentTerm(String paymentTerm){
        bringElementIntoView(String.format(Checkout.paymentTermRadio, paymentTerm));
        click(String.format(Checkout.paymentTermRadio, paymentTerm));
        waitForAbekaBGProcessLogoDisappear();
        waitForOrderProcessingMsgDisappear();
    }

    public void selectShippingAddress(String shippingAddress){
        if(shippingAddress.equalsIgnoreCase(Checkout.DEFAULT_SHIPPING_ADDRESS)) {
            bringElementIntoView(Checkout.shippingAddressMainRadio);
            click(Checkout.shippingAddressMainRadio);
            waitForAbekaBGProcessLogoDisappear();
            waitForOrderProcessingMsgDisappear();
        }
        if(isElementExists(Checkout.suggestedShipAddresBtn)){
            click(Checkout.suggestedShipAddresBtn);
        }
    }

    public void selectShippingMethod(String shippingMethod){
        bringElementIntoView(shippingMethod);
        click(shippingMethod);
        waitForAbekaBGProcessLogoDisappear();
        waitForOrderProcessingMsgDisappear();
    }

    public void selectBillingAddress(boolean isBillingAndShippingAddressSame){
        if(isBillingAndShippingAddressSame){
            if(!getElement(Checkout.shippingBillingSameChkBox).is(Condition.checked)){
                bringElementIntoView(Checkout.shippingBillingSameChkBox);
                click(Checkout.shippingBillingSameChkBox);
                waitForAbekaBGProcessLogoDisappear();
                waitForOrderProcessingMsgDisappear();
            }
        }
    }

    public void selectPaymentMethod(String paymentMethod){
        if(paymentMethod.equalsIgnoreCase(Checkout.DEFAULT)){
            click(Checkout.newCreditCardRadio);
            type(Checkout.ccNumber, Checkout.CC_NUMBER);
            clickByJavaScript(Checkout.ccExpiryMonthTextBox);
            clickByJavaScript(Checkout.ccExpiryMonth);
            clickByJavaScript(Checkout.getCcExpiryYearTextBox);
            clickByJavaScript(String.format(Checkout.ccExpiryYear, Integer.toString(LocalDateTime.now().getYear()+3).substring(2,4)));
            type(Checkout.ccSecurityCode,RandomStringUtils.randomNumeric(3));
        }else {
            bringElementIntoView(Checkout.savedCreditCard);
            click(Checkout.savedCreditCard);
            waitForAbekaBGProcessLogoDisappear();
            waitForOrderProcessingMsgDisappear();
        }
    }

    public void selectTermsAndCondition(boolean isAcceptTermsAndCondition){
        if(isAcceptTermsAndCondition){
            if(!getElement(Checkout.termsAgreedChkBox).is(Condition.checked)){
                bringElementIntoView(Checkout.termsAgreedChkBox);
                click(Checkout.termsAgreedChkBox);
                waitForAbekaBGProcessLogoDisappear();
            }
        }
    }

    public OrderConfirmationScreen clickOnPlaceOrder(){
        bringElementIntoView(Checkout.placeOrder);
        click(Checkout.placeOrder);
        waitForAbekaBGProcessLogoDisappear();
        return new OrderConfirmationScreen();
    }

    public CheckoutScreen validateProductInCart(){
        return this;
    }
}
