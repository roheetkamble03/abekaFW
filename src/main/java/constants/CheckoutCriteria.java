package constants;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class CheckoutCriteria {

    @Builder.Default
    private String paymentTerm = "Pay in Full";

    @Builder.Default
    private String shippingAddress = "Default shipping address";

    @Builder.Default
    private String shippingMethod = "Standard";

    @Builder.Default
    private boolean isBillingAndShippingAddressSame = true;

    @Builder.Default
    private String promoCode = "";

    @Builder.Default
    private String paymentInformation = "Default";

    @Builder.Default
    private boolean isAcceptTermsAndCondition = true;
}
