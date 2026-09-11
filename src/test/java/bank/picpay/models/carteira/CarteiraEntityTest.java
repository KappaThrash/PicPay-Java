package bank.picpay.models.carteira;

import bank.picpay.dominio.models.carteira.CarteiraEntity;
import bank.picpay.api.exceptions.custom_exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CarteiraEntityTest {


    private CarteiraEntity carteiraTest;

    @BeforeEach
    void setup(){
        carteiraTest = new CarteiraEntity();
        carteiraTest.setBalance(new BigDecimal(1000));
    }

    @Test
    void debit() {
        carteiraTest.debit(new BigDecimal(100));
        assertEquals(new BigDecimal(900), carteiraTest.getBalance());
    }

    @Test
    void credit() {
        carteiraTest.credit(new BigDecimal(100));
        assertEquals(new BigDecimal(1100), carteiraTest.getBalance());
    }

    @Test
    void debitTooMuchShouldThrowBusinessException(){
        assertThrows(BusinessException.class, () -> carteiraTest.debit(new BigDecimal(1100)));
    }

    @Test
    void creditNegativeValueShouldThrowBusinessException(){
        assertThrows(BusinessException.class, () -> carteiraTest.credit(new BigDecimal(-100)));
    }
}