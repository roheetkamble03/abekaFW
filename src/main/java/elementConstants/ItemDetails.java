package elementConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ItemDetails {
    GRADE_ONE("Grade 1 Video & Books Enrollment – Accredited","381748",1031.00,1),
    GRADE_FOUR("Grade 4 Video & Books Enrollment – Accredited","382043",1074.00,1),
    GRADE_NINE("Grade 9 Video & Books Enrollment – Accredited","382531",1365.00,1),
    GRADE_TWELVE("Grade 12 Video & Books Enrollment – Accredited","382833",1365.00,1);

    @Getter String itemName;
    @Getter String itemNumber;
    @Getter double price;
    @Getter int quantity;
}
