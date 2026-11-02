package pe.upeu.agrosierra;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class AgroApiApplicationTests {

    @Test
    void generarHashSeguro() {
        String hash = new BCryptPasswordEncoder().encode("Agro2026*");
        System.out.println("=================================================");
        System.out.println("HASH_BCRYPT_REAL: " + hash);
        System.out.println("=================================================");
    }
}