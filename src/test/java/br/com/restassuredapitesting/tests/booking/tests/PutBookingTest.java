package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.E2ETests;
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

    @Test
    @Category({AllTests.class, E2ETests.class})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Validar erro de Cliente ao alterar uma reserva sem enviar token ou autenticação")
    public void validateClientErrorBookingModificationWithoutAnyAuth(){
        int idToTest = getBookingRequest.returnFirstIdFromBookingIds();
        JSONObject payloadToUse = BookingPayloads.defaultPayload();
        String randomFirstName = Utils.generateRandomFirstName();
        payloadToUse.put("firstname",randomFirstName);

        putBookingRequest.updateOneBookingWithoutAuth(idToTest,payloadToUse)
                .then()
                .statusCode(403);
    }

    @Test
    @Category({AllTests.class,E2ETests.class})
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Validar Erro de Cliente ao alterar uma reserva com um token invalido")
    public void validateClientErrorBookingModificationWithIncorrectAuthToken(){
        int idToTest = getBookingRequest.returnFirstIdFromBookingIds();
        JSONObject payloadToUse = BookingPayloads.defaultPayload();
        String randomFirstName = Utils.generateRandomFirstName();
        String randomToken = "These Are Not the Droids You Are Looking For";
        putBookingRequest.updateOneBookingWithAuthToken(idToTest, randomToken,payloadToUse)
                .then()
                .statusCode(403);
    }

    @Test
    @Category({AllTests.class,E2ETests.class})
    @Severity(SeverityLevel.TRIVIAL)
    @DisplayName("Validar Erro de Cliente ao tentar alterar uma reserva inexistente com Auth Token")
    public void validateClientErrorBookingModificationNonExistentBookingWithAuthToken(){
        int idToTest = -1;
        String tokenToUse = postAuthRequest.getToken();
        JSONObject payloadToUse = BookingPayloads.defaultPayload();
        String randomFirstName = Utils.generateRandomFirstName();
        String randomToken = "These Are Not the Droids You Are Looking For";

        putBookingRequest.updateOneBookingWithAuthToken(idToTest,tokenToUse,payloadToUse)
                .then()
                .statusCode(405);
    }
}
