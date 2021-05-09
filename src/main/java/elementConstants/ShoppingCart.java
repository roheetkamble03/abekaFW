package elementConstants;

import lombok.Getter;

public class ShoppingCart {
    public static String checkOutBtn = "id=lbnFinishOrder";
    @Getter
    public static String gradeOneVideoBookAccredited = "Grade 1 Video & Books Enrollment – Accredited";
    public static String shoppingCartTitleText = "You have %s items in your cart.";
    public static String productRow = "xpath=//div[@data-sbn='%s']";
    public static final String productTitle = "xpath=./descendant::div[position()=3]";
    public static final String productPrice = "xpath=./descendant::div[position()=5]";
    public static final String quantityTextBox = "xpath=./descendant::input[@class='txtQuantity shop-item-qty']";
    public static final String subTotal = "xpath=./descendant::div[position()=7]";
    public static String shoppingCartTitle = "id=cartItems";
    public static String itemNumber = "381748";
    @Getter
    public static double price = 1074.00;
    public static int quantity = 1;
}
