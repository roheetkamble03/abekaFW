package constants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserAccountDetails {
    String userId;
    String loginId;
    String accountNumber;
    String studentId;
    String userName;
    String subscriptionNumber;
    String applicationNumber;
}
