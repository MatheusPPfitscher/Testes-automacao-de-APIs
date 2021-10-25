package br.com.restassuredapitesting.tests.ping.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.HealthCheckTests;
import br.com.restassuredapitesting.tests.ping.requests.GetPingRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.lessThan;

@Feature("Feature - teste de saúde da API")
public class GetPingTest extends BaseTest {

    GetPingRequest getPingRequest = new GetPingRequest();
    @Test
    @Category({AllTests.class, HealthCheckTests.class})
    @DisplayName("Verificar a saúde da API")
    @Severity(SeverityLevel.BLOCKER)
    public void healthCheck(){
        getPingRequest.doHeathCheck()
                .then()
                .statusCode(201)
                .time(lessThan(3L), TimeUnit.SECONDS);
    }
}