package farmSystem.closeUp.repository.payment;

import farmSystem.closeUp.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByMerchantId(String merchantId);
}
