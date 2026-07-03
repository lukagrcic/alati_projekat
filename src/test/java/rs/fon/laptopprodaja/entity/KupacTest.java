package rs.fon.laptopprodaja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class KupacTest {

    protected abstract Kupac getInstance();

    protected Kupac kupac;

    @BeforeEach
    void setUp() {
        kupac = getInstance();
    }

    @AfterEach
    void tearDown() {
        kupac = null;
    }

    @Test
    @DisplayName("setEmail treba da postavi validan email koji sadrzi @")
    void setEmailValidnaVrednost() {
        kupac.setEmail("novi@test.com");
        assertEquals("novi@test.com", kupac.getEmail(), "getEmail ne vraca vrednost postavljenu preko setEmail");
    }

    @Test
    @DisplayName("setEmail sa null vrednoscu baca NullPointerException")
    void setEmailNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> kupac.setEmail(null),
                "Ocekivan je NullPointerException kada je email null");
        assertEquals("Email ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setEmail bez @ baca IllegalArgumentException")
    void setEmailBezAtBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> kupac.setEmail("nevalidanemail.com"),
                "Ocekivan je IllegalArgumentException kada email ne sadrzi @");
        assertEquals("Email mora sadrzati @", ex.getMessage());
    }

    @Test
    @DisplayName("setTelefon treba da postavi vrednost bez validacije")
    void setTelefonValidnaVrednost() {
        kupac.setTelefon("0641234567");
        assertEquals("0641234567", kupac.getTelefon(), "getTelefon ne vraca vrednost postavljenu preko setTelefon");
    }

    @Test
    @DisplayName("setTelefon prihvata null jer nije obavezno polje")
    void setTelefonPrihvataNull() {
        kupac.setTelefon(null);
        assertNull(kupac.getTelefon(), "telefon treba da moze biti null jer polje nije obavezno");
    }

    @Test
    @DisplayName("setGrad treba da postavi validan grad")
    void setGradValidnaVrednost() {
        Grad noviGrad = new Grad("Novi Sad", "21000");
        kupac.setGrad(noviGrad);
        assertEquals(noviGrad, kupac.getGrad(), "getGrad ne vraca vrednost postavljenu preko setGrad");
    }

    @Test
    @DisplayName("setGrad sa null vrednoscu baca NullPointerException")
    void setGradNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> kupac.setGrad(null),
                "Ocekivan je NullPointerException kada je grad null");
        assertEquals("Grad ne sme biti null", ex.getMessage());
    }
}
