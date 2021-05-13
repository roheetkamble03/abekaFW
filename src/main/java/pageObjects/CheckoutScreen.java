package pageObjects;

import base.GenericAction;
import com.codeborne.selenide.Condition;
import constants.CheckoutCriteria;
import elementConstants.Checkout;

public class CheckoutScreen extends GenericAction {
    @Override
    protected void setUp(String browserName, String platform) {

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
    }

    public void selectShippingAddress(String shippingAddress){
        if(shippingAddress.equalsIgnoreCase(Checkout.DEFAULT_SHIPPING_ADDRESS)) {
            bringElementIntoView(Checkout.shippingAddressMainRadio);
            click(Checkout.shippingAddressMainRadio);
            waitForAbekaBGProcessLogoDisappear();
        }
    }

    public void selectShippingMethod(String shippingMethod){
        bringElementIntoView(shippingMethod);
        click(shippingMethod);
        waitForAbekaBGProcessLogoDisappear();
    }

    public void selectBillingAddress(boolean isBillingAndShippingAddressSame){
        if(isBillingAndShippingAddressSame){
            if(!getElement(Checkout.shippingBillingSameChkBox).is(Condition.checked)){
                bringElementIntoView(Checkout.shippingBillingSameChkBox);
                click(Checkout.shippingBillingSameChkBox);
                waitForAbekaBGProcessLogoDisappear();
            }
        }
    }

    public void selectPaymentMethod(String paymentMethod){
        if(paymentMethod.equalsIgnoreCase(Checkout.DEFAULT)){
            bringElementIntoView(Checkout.savedCreditCard);
            click(Checkout.savedCreditCard);
            waitForAbekaBGProcessLogoDisappear();
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
