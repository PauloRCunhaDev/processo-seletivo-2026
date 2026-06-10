package br.com.lapes.ecommerce.infrastructure.persistence;

import br.com.lapes.ecommerce.domain.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CouponJpaRepository extends JpaRepository<Coupon, UUID> {

    Optional<Coupon> findByCodeAndActiveTrue(String code);

    boolean existsByCode(String code);
}
