package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.PutBookingRequest;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.requests.payloads.BookingPayloads;
import br.com.restassuredapitesting.utils.Utils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@Feature("Feature - Atualização de Reservas")
public class PutBookingTest extends BaseTest {

    PutBookingRequest putBookingRequest = new PutBookingRequest();
    GetBookingRequest getBookingRequest = new GetBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();

    @Test
    @Category({AllTests.class, AcceptanceTests.class})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Validar alterar uma reserva utilizando um token de autenticação")
    public void validateBookingModificationUsingAuthToken(){
        int idToTest = getBookingRequest.returnFirstIdFromBookingIds();
        String tokenToUse = postAuthRequest.getToken();
        JSONObject payloadToUse = BookingPayloads.defaultPayload();
        String randomFirstName = Utils.generateRandomFirstName();
        payloadToUse.put("firstname",randomFirstName);

        putBookingRequest.updateOneBookingWithAuthToken(idToTest,tokenToUse,payloadToUse)
                .then()
                .statusCode(200)
                .body("firstname",equalTo(randomFirstName));
    }

    @Test
    @Category({AllTests.class,AcceptanceTests.class})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Validar alterar uma reserva utilizando autenticação Basic")
    public void validateBookingModificationUsingBasicAuth(){
        int idToTest = getBookingRequest.returnFirstIdFromBookingIds();
        JSONObject payloadToUse = BookingPayloads.defaultPayload();
        String randomFirstName = Utils.generateRandomFirstName();
        payloadToUse.put("firstname",randomFirstName);

        putBookingRequest.updateOneBookingWithBasicAuth(idToTest,payloadToUse)
                .then()
                .statusCode(200)
                .body("firstname",equalTo(randomFirstName));
    }
}
