package br.com.restassuredapitesting.tests.ping.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.tests.ping.requests.GetPingRequest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.lessThan;

public class GetPingTest extends BaseTest {

    GetPingRequest getPingRequest = new GetPingRequest();
    @Test
    @Category({AllTests.class})
    public void healthCheck(){

        getPingRequest.heathCheck()
                .then()
                .statusCode(201)
                .time(lessThan(3L), TimeUnit.SECONDS);
    }
}