package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.E2ETests;
import br.com.restassuredapitesting.suites.SchemaTests;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.utils.Utils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.util.HashMap;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.*;

@Feature("Feature - Consultas de Reservas")
public class GetBookingTest extends BaseTest {

    GetBookingRequest getBookingRequest = new GetBookingRequest();
    @Test
    @Category({AllTests.class, AcceptanceTests.class})
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Listar Ids de reservas")
    public void validateBookingReturnIds(){
        getBookingRequest.returnBookingIds()
                .then()
                .statusCode(200)
                .body("size()",greaterThan(0));
    }

    @Test
    @Category({AllTests.class,SchemaTests.class})
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Garantir o Schema do retorno da listagem de reservas")
    public void validateSchemaFromBookingReturnIds(){
        getBookingRequest.returnBookingIds()
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath("booking","bookings"))));
    }

    @Test
    @Category({AllTests.class,SchemaTests.class})
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Garantir o Schema do retorno de uma reserva específica")
    public void validateSchemaFromSingleBooking(){

        getBookingRequest.returnBookingFromId(getBookingRequest.returnFirstIdFromBookingIds())
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath("booking","singleBooking"))));
    }

    @Test
    @Category({AllTests.class,AcceptanceTests.class})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Validar o retorno de Reservas por filtro de primeiro nome")
    public void validateBookingFilterByFirstName(){
        int idToTest = getBookingRequest.returnFirstIdFromBookingIds();
        String firstName = getBookingRequest.returnBookingFirstName(idToTest);
        getBookingRequest.returnBookingFilterFirstName(firstName)
                .then()
                .statusCode(200)
                .body(containsString(String.valueOf(idToTest)));
    }

    @Test
    @Category({AllTests.class,AcceptanceTests.class})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Validar o retorno de Reservas por filtro de ultimo nome")
    public void validateBookingFilterByLastName(){
        int idToTest = getBookingRequest.returnFirstIdFromBookingIds();
        String lastName = getBookingRequest.returnBookingLastName(idToTest);
        getBookingRequest.returnBookingFilterLastName(lastName)
                .then()
                .statusCode(200)
                .body(containsString(String.valueOf(idToTest)));
    }

    @Test
    @Category({AllTests.class,AcceptanceTests.class})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Validar o retorno de Reservas por filtro de data de entrada")
    public void validateBookingFilterByCheckIn(){
        int idToTest = getBookingRequest.returnFirstIdFromBookingIds();
        HashMap bookingDates = getBookingRequest.returnBookingDates(idToTest);
        getBookingRequest.returnBookingFilterByBookingDates("checkin", bookingDates)
                .then()
                .statusCode(200)
                .body(containsString(String.valueOf(idToTest)));
    }

    @Test
    @Category({AllTests.class,AcceptanceTests.class})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Validar o retorno de Reservas por filtro de data de saída")
    public void validateBookingFilterByCheckOut(){
        int idToTest = getBookingRequest.returnFirstIdFromBookingIds();
        HashMap<String, String> bookingDates = getBookingRequest.returnBookingDates(idToTest);
        getBookingRequest.returnBookingFilterByBookingDates("checkout", bookingDates)
                .then()
                .statusCode(200)
                .body(containsString(String.valueOf(idToTest)));
    }

    @Test
    @Category({AllTests.class,AcceptanceTests.class})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Validar o retorno de Reservas por filtro de data de entrada e saída simultaneos")
    public void validateBookingFilterByCheckInAndCheckOut(){
        int idToTest = getBookingRequest.returnFirstIdFromBookingIds();
        HashMap<String, String> bookingDates = getBookingRequest.returnBookingDates(idToTest);
        getBookingRequest.returnBookingFilterByBookingDates("both", bookingDates)
                .then()
                .statusCode(200)
                .body(containsString(String.valueOf(idToTest)));
    }

    @Test
    @Category({AllTests.class,AcceptanceTests.class})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Validar o retorno de Reservas por um filtro de nome, sobrenome, data de entrada e saída")
    public void validateBookingFilter(){
        int idToTest = getBookingRequest.returnFirstIdFromBookingIds();
        String firstName = getBookingRequest.returnBookingFirstName(idToTest);
        String lastName = getBookingRequest.returnBookingLastName(idToTest);
        HashMap<String, String> bookingDates = getBookingRequest.returnBookingDates(idToTest);
        getBookingRequest.returnBookingFilter(firstName,lastName,bookingDates)
                .then()
                .statusCode(200)
                .body(containsString(String.valueOf(idToTest)));
    }

    @Test
    @Category({AllTests.class, E2ETests.class})
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Validar o retorno de erro de servidor 500 ao enviar um filtro mal formatado")
    public void validateServerErrorOnBadBookingFilter(){
        getBookingRequest.returnResponseOnBadBookingFilter()
                .then()
                .statusCode(500);
    }
}
