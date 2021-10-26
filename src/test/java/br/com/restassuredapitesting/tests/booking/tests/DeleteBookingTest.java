package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.E2ETests;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.requests.DeleteBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;

@Feature("Feature - Remoção de Reservas")
public class DeleteBookingTest extends BaseTest {
    DeleteBookingRequest deleteBookingRequest = new DeleteBookingRequest();
    GetBookingRequest getBookingRequest = new GetBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();

    @Test
    @Category({AllTests.class, AcceptanceTests.class})
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Valida apagar uma reserva existente com um Auth Token")
    public void validateDeletingOneBookingUsingAuthToken(){
        int idToDelete = getBookingRequest.returnFirstIdFromBookingIds();
        deleteBookingRequest.deleteBookingFromIdWithAuthToken(idToDelete,postAuthRequest.getToken())
                .then()
                .statusCode(201)
                        .body(containsString("Created"));
        getBookingRequest.returnBookingFromId(idToDelete)
                .then()
                .statusCode(404)
                .body(containsString("Not Found"));
    }

    @Test
    @Category({AllTests.class, E2ETests.class})
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Valida que a API responderá um código de erro de cliente ao tentar uma reserva inexistente")
    public void validateClientErrorOnDeleteNonExistentBookingWithAuthToken(){
//        Não faço ideia
    }

    @Test
    @Category({AllTests.class,E2ETests.class})
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName(("Valida que a API não deletará reservas sem um Auth Token"))
    public void validateClientErrorAndNoWriteOnDeleteExistingBookingWithoutAuthToken(){
        int idToDelete = getBookingRequest.returnFirstIdFromBookingIds();
        deleteBookingRequest.deleteBookingFromIdWithoutToken(idToDelete)
                .then()
                .statusCode(403);
        getBookingRequest.returnBookingFromId(idToDelete)
                .then()
                .statusCode(200)
                .body("",notNullValue());
    }
}
