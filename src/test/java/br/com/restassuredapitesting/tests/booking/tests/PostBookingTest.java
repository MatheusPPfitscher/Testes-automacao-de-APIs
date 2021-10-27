package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.E2ETests;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
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
import static org.hamcrest.CoreMatchers.notNullValue;

@Feature("Feature - Criação de Reservas")
public class PostBookingTest extends BaseTest {

    PostBookingRequest postBookingRequest = new PostBookingRequest();

    @Test
    @Category({AllTests.class, AcceptanceTests.class})
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Validar a postagem de uma nova reserva")
    public void validatePostNewBooking(){
        JSONObject validPayload = BookingPayloads.defaultPayload();
//        Contribuições do Mentor Maximiliano
        String randomFirstName = Utils.generateRandomFirstName();
        validPayload.put("firstname",randomFirstName);

        postBookingRequest.postNewBooking(validPayload)
                .then()
                .statusCode(200)
                .body("bookingid",notNullValue())
                .body("booking.firstname",equalTo(randomFirstName));
    }

    @Test
    @Category({AllTests.class, E2ETests.class})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Validar erro 500 ao cadastrar uma reserva com informações faltando")
    public void validateServerErrorOnBadPostNewBooking(){
        JSONObject validPayload = BookingPayloads.defaultPayload();
        String randomFirstName = Utils.generateRandomFirstName();
        validPayload.put("firstname",randomFirstName);

        postBookingRequest.postMissingDataNewBooking(validPayload)
                .then()
                .statusCode(500);
    }

    @Test
    @Category({AllTests.class, E2ETests.class})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Validar a criação de duas reservas em sequencia")
    public void validatePostTwoNewBookingSequentially(){
        JSONObject validPayload = BookingPayloads.defaultPayload();
        String randomFirstName = Utils.generateRandomFirstName();
        validPayload.put("firstname",randomFirstName);

        postBookingRequest.postNewBooking(validPayload)
                .then()
                .statusCode(200)
                .body("bookingid",notNullValue())
                .body("booking.firstname",equalTo(randomFirstName));

        randomFirstName = Utils.generateRandomFirstName();
        validPayload.put("firstname",randomFirstName);

        postBookingRequest.postNewBooking(validPayload)
                .then()
                .statusCode(200)
                .body("bookingid",notNullValue())
                .body("booking.firstname",equalTo(randomFirstName));
    }

    @Test
    @Category({AllTests.class,E2ETests.class})
    @Severity(SeverityLevel.TRIVIAL)
    @DisplayName("Validar que em nova reserva com parametros extras, elas são ignoradas")
    public void validatePostNewBookingExtraData(){
        JSONObject validPayload = BookingPayloads.defaultPayload();
        String randomFirstName = Utils.generateRandomFirstName();
        validPayload.put("firstname",randomFirstName);

        postBookingRequest.postExtraDataNewBooking(validPayload)
                .then()
                .statusCode(200)
                .body("bookingid",notNullValue())
                .body("booking.firstname",equalTo(randomFirstName));
    }

    @Test
    @Category({AllTests.class,E2ETests.class})
    @Severity(SeverityLevel.TRIVIAL)
    @DisplayName("Validar retorno de erro de cliente 418 ao enviar reserva com header incorreto")
    public void validatePostNewBookingWrongHeader(){
        JSONObject validPayload = BookingPayloads.defaultPayload();

        postBookingRequest.postWrongHeaderNewBooking(validPayload)
                .then()
                .statusCode(418);
    }
}
