package br.com.lapes.ecommerce.infrastructure.seed;

import br.com.lapes.ecommerce.domain.entity.*;
import br.com.lapes.ecommerce.infrastructure.persistence.CouponJpaRepository;
import br.com.lapes.ecommerce.infrastructure.persistence.ProductJpaRepository;
import br.com.lapes.ecommerce.infrastructure.persistence.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final UserJpaRepository userRepository;
    private final ProductJpaRepository productRepository;
    private final CouponJpaRepository couponRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            log.info("Seed ignorado: banco ja possui dados");
            return;
        }

        log.info("Executando seed de dados...");
        seedUsers();
        seedProducts();
        seedCoupons();
        log.info("Seed concluido");
    }

    private void seedUsers() {
        userRepository.saveAll(List.of(
            User.builder()
                .name("Admin LAPES")
                .email("admin@lapes.com")
                .passwordHash(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build(),
            User.builder()
                .name("Alice Silva")
                .email("alice@example.com")
                .passwordHash(passwordEncoder.encode("alice123"))
                .role(Role.CUSTOMER)
                .build(),
            User.builder()
                .name("Bob Santos")
                .email("bob@example.com")
                .passwordHash(passwordEncoder.encode("bob123"))
                .role(Role.CUSTOMER)
                .build(),
            User.builder()
                .name("Carol Souza")
                .email("carol@example.com")
                .passwordHash(passwordEncoder.encode("carol123"))
                .role(Role.CUSTOMER)
                .build()
        ));
    }

    private void seedProducts() {
        productRepository.saveAll(List.of(
            product("Camiseta Branca Basica",     "Camiseta de algodao 100%, corte classico",  new BigDecimal("49.90"),  50, "Camisetas"),
            product("Camiseta Preta Slim",        "Camiseta slim fit para uso diario",          new BigDecimal("59.90"),  40, "Camisetas"),
            product("Camiseta Estampada Tropical","Camiseta com estampa floral vibrante",       new BigDecimal("69.90"),  30, "Camisetas"),
            product("Camiseta Polo Azul",         "Polo de algodao com botoes",                 new BigDecimal("89.90"),  25, "Camisetas"),
            product("Calca Jeans Skinny",         "Jeans skinny cintura alta, stretch",         new BigDecimal("149.90"), 20, "Calcas"),
            product("Calca Cargo Bege",           "Cargo com bolsos laterais, confortavel",     new BigDecimal("129.90"), 15, "Calcas"),
            product("Calca Social Preta",         "Calca social slim para ocasioes formais",    new BigDecimal("179.90"), 10, "Calcas"),
            product("Bone Aba Curva",             "Bone snapback ajustavel, bordado frontal",   new BigDecimal("49.90"),  60, "Acessorios"),
            product("Cinto de Couro",             "Cinto legitimo em couro marrom",             new BigDecimal("79.90"),  35, "Acessorios"),
            product("Meia Cano Alto 3-pack",      "Kit com 3 pares de meias cano alto",         new BigDecimal("29.90"),  80, "Acessorios")
        ));
    }

    private Product product(String name, String desc, BigDecimal price, int stock, String category) {
        return Product.builder()
            .name(name)
            .description(desc)
            .price(price)
            .stock(stock)
            .category(category)
            .build();
    }

    private void seedCoupons() {
        couponRepository.saveAll(List.of(
            Coupon.builder()
                .code("DESCONTO10")
                .type(CouponType.PERCENTAGE)
                .value(new BigDecimal("10"))
                .minOrderValue(new BigDecimal("100.00"))
                .expiresAt(LocalDateTime.now().plusYears(1))
                .build(),
            Coupon.builder()
                .code("FRETE20")
                .type(CouponType.FIXED)
                .value(new BigDecimal("20.00"))
                .minOrderValue(new BigDecimal("50.00"))
                .expiresAt(LocalDateTime.now().plusMonths(6))
                .build(),
            Coupon.builder()
                .code("EXPIRADO")
                .type(CouponType.PERCENTAGE)
                .value(new BigDecimal("15"))
                .minOrderValue(BigDecimal.ZERO)
                .expiresAt(LocalDateTime.now().minusDays(1))
                .active(false)
                .build()
        ));
    }
}
