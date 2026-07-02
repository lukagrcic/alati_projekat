package rs.fon.laptopprodaja.dto;

import java.math.BigDecimal;

/**
 * Jedna stavka u zahtevu za kreiranje racuna.
 * prodajnaCena je opciona; ako je null, uzima se trenutna cena laptopa.
 */
public record StavkaZahtev(Long idLaptop, int kolicina, BigDecimal prodajnaCena) {
}
