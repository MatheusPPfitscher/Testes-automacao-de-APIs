package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GetBookingRequest {

    @Step("Retorna os Ids da Listagem de Reservas")
    public Response returnBookingIds(){
        return given()
                    .when()
                    .get("booking");
    }

    @Step("Retorna o id da primeira Reserva listada")
    public int returnFirstIdFromBookingIds(){
        return this.returnBookingIds()
                .then()
                .statusCode(200)
                .extract()
                .path("[0].bookingid");
    }

    @Step("Retorna a Reserva de um ID específico")
    public Response returnBookingFromId(int id){
        return given()
                .pathParam("id",String.valueOf(id))
                .when()
                .get("booking/{id}");
    }

    @Step("Retorna o primeiro nome de uma Reserva por id")
    public String returnBookingFirstName(int id){
        return this.returnBookingFromId(id)
                .then()
                .extract()
                .path("firstname");
    }

    @Step("Retorna o sobrenome de uma Reserva por id")
    public String returnBookingLastName(int id){
        return this.returnBookingFromId(id)
                .then()
                .extract()
                .path("lastname");
    }

    @Step("Retorna o bookingDates de uma Reserva por id")
    public HashMap<String, String> returnBookingDates(int id){
        return this.returnBookingFromId(id)
                .then()
                .extract()
                .path("bookingdates");
    }

    @Step("Retorna os IDs de Reservas filtradas por primeiro nome, sobrenome, data de entrada e data de saida")
    public Response returnBookingFilter(String firstName, String lastName, HashMap<String, String> bookingDates){
        return given()
                .queryParam("firstname",firstName)
                .queryParam("lastname",lastName)
                .queryParam("checkin",bookingDates.get("checkin"))
                .queryParam("checkout",bookingDates.get("checkout"))
                .when()
                .get("booking");
    }

    @Step("Retorna os IDs de Reservas filtradas pelo primeiro nome")
    public Response returnBookingFilterFirstName(String firstName){
    return given()
            .queryParam("firstname",firstName)
            .when()
            .get("booking");
    }

    @Step("Retorna os IDs de Reservas filtradas pelo sobrenome")
    public Response returnBookingFilterLastName(String lastName){
    return given()
            .queryParam("lastname", lastName)
            .when()
            .get("booking");
    }

    @Step("Retorna os IDs de Reservas filtradas pela data de entrada")
    public Response returnBookingFilterCheckIn(String checkIn){
    return given()
            .queryParam("checkin",checkIn)
            .when()
            .get("booking");
    }


    @Step("Retorna os IDs de Reservas filtrada por data de entrada, data de saída ou ambas")
    public Response returnBookingFilterByBookingDates(String mode, HashMap bookingDates){
        if (mode.contentEquals("checkin")) return given()
                .queryParam("checkin",bookingDates.get("checkin").toString())
                .when()
                .get("booking");
        else if (mode.contentEquals("checkout")) return given()
                .queryParam("checkout",bookingDates.get("checkout").toString())
                .when()
                .get("booking");
        else return given()
                    .queryParam("checkin",bookingDates.get("checkin").toString())
                    .queryParam("checkout",bookingDates.get("checkout").toString())
                    .when()
                    .get("booking");
    }

    @Step("Retorna a resposta ao realizar uma requisição com um Filtro mal formatado")
    public Response returnResponseOnBadBookingFilter(){
        return given()
                .queryParam("firstname",true)
                .queryParam("someGarbage", "UnsLixosQueChegaramAqui")
                .when()
                .log().all()
                .get("booking");
    }
}


