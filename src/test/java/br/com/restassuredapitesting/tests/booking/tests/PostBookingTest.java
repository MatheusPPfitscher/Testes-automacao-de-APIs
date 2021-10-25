package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.payloads.BookingPayloads;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PostBookingTest extends BaseTest {

    PostBookingRequest postBookingRequest = new PostBookingRequest();
    BookingPayloads bookingPayloads = new BookingPayloads();

    @Test
    @Category({AllTests.class, AcceptanceTests.class})
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Validar a postagem de uma nova reserva")
    public void validatePostNewBooking(){
        postBookingRequest.postNewBooking()
                .then()
                .statusCode(200);
    }
}
