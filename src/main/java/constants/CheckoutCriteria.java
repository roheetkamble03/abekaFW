package constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class CheckoutCriteria {

    private String paymentTerm = "Pay in Full";

    private String shippingAddress = "Default shipping address";

    private String shippingMethod = "Standard";

    private boolean isBillingAndShippingAddressSame = true;

    private String promoCode = "";

    private String paymentInformation = "Default";

    private boolean isAcceptTermsAndCondition = true;
}
