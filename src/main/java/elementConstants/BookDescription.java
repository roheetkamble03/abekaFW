package elementConstants;

public @interface BookDescription {

    /**
     * Element xpath
     */
    String fullYear = "text=Full Year";
    String oneSemester = "text=One Semester";
    String videoAndBooks = "text=Video & Books";
    String video = "text=Video";
    String accredited = "text=Accredited";
    String independentStudy = "text=Independent Study";
    String addToCart = "id=lbnAddToCart";
    String quantityTxtBox = "id=txtQuantity";
    String newVersionOfProductAvailable = "xpath=//div[@id='NewProductBox']/descendant::a";
    String productAddedLink = "xpath=//p/descendant::a[@href='/ABekaOnline/ViewCart.aspx']";
}
