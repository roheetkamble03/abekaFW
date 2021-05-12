package elementConstants;

public class Checkout {
    //String constants
    public static String payInFull = "Pay in Full";
    public static String tenMonthsPlan = "10-Month Pay Plan";
    public static String defaultShippingAddress = "Default shipping address";
    public static String newShippingAddress = "New Shipping Address";
    public static String shippingMethodStandard = "Standard";
    public static String shippingMethodPriority = "Priority";
    public static String defaultPaymentMethod = "Default";


    public static String iHaveAnAccountBtn = "id=lbnLogin";
    public static String paymentTermRadio = "xpath=//label[contains(normalize-space(text()),'%s')]/parent::*/preceding-sibling::div//input[@type='radio']";
    public static String shippingAddressMainRadio = "id=rbnShippingAddress_MAIN";
    public static String freeShippingRadio = "id=rbnDefaultShipping";
    public static String shippingBillingSameChkBox = "id=chkSameAsShippingAddress";
    public static String savedCreditCard = "xpath=//input[contains(@id,'rptSavedCards') and @name='PaymentChoice']";
    public static String termsAgreedChkBox = "id=ckbAgree";
    public static String placeOrder = "id=lbnPlaceOrder";
}
