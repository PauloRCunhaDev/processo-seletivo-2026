package br.com.lapes.ecommerce.infrastructure.persistence;

import br.com.lapes.ecommerce.domain.entity.CouponRedemption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CouponRedemptionJpaRepository extends JpaRepository<CouponRedemption, UUID> {

    boolean existsByUserIdAndCouponId(UUID userId, UUID couponId);
}
