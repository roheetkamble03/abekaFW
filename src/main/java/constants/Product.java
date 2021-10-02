package constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class Product {

    private String productTitle = "";
    private String itemNumber = "";
    private double price = 0;
    private int quantity=1;
    private double subtotal = 0;
}
