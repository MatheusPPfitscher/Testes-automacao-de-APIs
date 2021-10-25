package br.com.restassuredapitesting.tests.booking.requests.payloads;

import org.json.JSONObject;

public class BookingPayloads {

    public static JSONObject payloadValidBooking(){
        JSONObject payload = new JSONObject();
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin","2021-10-28");
        bookingDates.put("checkout","2022-01-01");

        payload.put("firstname","Joselito");
        payload.put("lastname","Silveira");
        payload.put("totalprice", 98765);
        payload.put("depositpaid", true);
        payload.put("bookingdates", bookingDates);
        payload.put("additionalneeds", "Breakfast");
        return payload;
    }
}
