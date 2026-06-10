package br.com.lapes.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CouponType type;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal value;

    @Column(nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal minOrderValue = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public BigDecimal calculateDiscount(BigDecimal orderTotal) {
        return switch (type) {
            case PERCENTAGE -> orderTotal.multiply(value).divide(BigDecimal.valueOf(100));
            case FIXED      -> value.min(orderTotal);
        };
    }
}
