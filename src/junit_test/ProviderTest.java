package junit_test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import application.model.Message;
import application.model.PrepaidSmsHandy;
import application.model.Provider;
import application.model.TariffPlanSmsHandy;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProviderTest {

    private static Provider provider;

    @BeforeAll
    private static void beforeAllTests(){
        provider = new Provider("T-Mobile");
    }

    @AfterEach
    private void afterEachTest(){
        provider.setCredits(new HashMap<>());
    }

    @Test
    public void sendNullMessageTest(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            provider.send(null);
        });

        String expectedMessage = "Die Nachricht muss eingestellt werden";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void sendTest(){
        Message m = new Message("Test Nachrcht","aaaaaa","bbbbbb",new Date());
        assertTrue(provider.send(m));
    }
    @Test
    public void getCreditForSmsHandyTest(){
        PrepaidSmsHandy handy = new PrepaidSmsHandy("aaaaaa", provider);
        assertEquals(provider.getCreditForSmsHandy(handy.getNumber()), 100);
    }
    @Test
    public void registerTest(){
        TariffPlanSmsHandy handy = new TariffPlanSmsHandy("aaaaaa", provider);
        assertTrue(provider.getSubscriber().containsValue(handy));
    }

    @Test
    public void depositTest(){
        TariffPlanSmsHandy handy = new TariffPlanSmsHandy("aaaaaa", provider);
        provider.deposit(handy.getNumber(),100);
        assertEquals(provider.getCredits().get(handy.getNumber()), 100);
    }

    
}
