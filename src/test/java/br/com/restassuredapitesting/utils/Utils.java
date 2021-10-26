package br.com.restassuredapitesting.utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class Utils {

    public static String getSchemaBasePath(String pack, String nameSchema){
        return System.getProperty("user.dir")
                + "/src/test/java/br/com/restassuredapitesting/tests/"
                + pack
                +"/schema/"
                + nameSchema
                + ".json";
    }

    //        Contribuições do Mentor Maximiliano
    public static String generateRandomFirstName(){
        Faker geradorDeFakes = new Faker(Locale.GERMANY);
        return geradorDeFakes.name().firstName();
    }
}
