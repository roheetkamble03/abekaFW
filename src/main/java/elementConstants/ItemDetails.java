package elementConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ItemDetails {
    GRADE_ONE("Grade 1 Video & Books Enrollment – Accredited","381748",1074.00,1),
    GRADE_FOUR("Grade 4 Tuition and Books Enrollment","295507",900.00,1),
    GRADE_NINE("Grade 9 Tuition and Books Enrollment","295574",1145.00,1);

    @Getter String itemName;
    @Getter String itemNumber;
    @Getter double price;
    @Getter int quantity;
}
