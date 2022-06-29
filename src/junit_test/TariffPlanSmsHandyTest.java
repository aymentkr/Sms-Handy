package junit_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.model.Provider;
import application.model.TariffPlanSmsHandy;

import static org.junit.jupiter.api.Assertions.*;

public class TariffPlanSmsHandyTest {
    private TariffPlanSmsHandy handy;
    private Provider provider;

    @BeforeEach
    private void beforeAllTests(){
        provider = new Provider("T-Mobile");
        handy = new TariffPlanSmsHandy("qqqqqq", provider);
    }

    @Test
    public void canSendSmsTest(){
        assertFalse(handy.canSendSms());
    }

    @Test
    public void payForSmsTest(){
        handy.payForSms();
        assertEquals(99, handy.getRemainingFreeSms());
    }
}
