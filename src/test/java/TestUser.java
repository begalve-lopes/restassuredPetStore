import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUser {

    static String ct = "application/json";
    static String uriUser = "https://petstore.swagger.io/v2/user";

    static int id = 123456;
    static String username = "maria";
    static String firstName = "Maria";
    static String lastName = "Silva";
    static String email = "maria@gmail.com";
    static String password = "123456";
    static String phone = "999999999";
    static int userStatus = 100;

    public static String lerArquivoJson(String arquivoJson){
        try {
            return new String(Files.readAllBytes(Path.of(arquivoJson)));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o arquivo JSON: " + arquivoJson, e);
        }
    }

    @Test
    @Order(1)
    public void TestPostUser(){

        String jsonBody = lerArquivoJson("src/test/resources/json/user1.json");

        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)
        .when()
            .post(uriUser)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", is(String.valueOf(id)))
        ;
    }

    @Test
    @Order(2)
    public void testUserGet(){

        given()
            .contentType(ct)
            .log().all()
        .when()
            .get(uriUser + "/" + username)
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(id))
            .body("username", is(username))
            .body("firstName", is(firstName))
            .body("lastName", is(lastName))
            .body("email", is(email))
            .body("password", is(password))
            .body("phone", is(phone))
            .body("userStatus", is(userStatus))
        ;
    }

    @Test
    @Order(3)
    public void testPutUser(){
        String JsonBody = lerArquivoJson("src/test/resources/json/user2.json");
        given()
            .contentType(ct)
            .log().all()
            .body(JsonBody)
        .when()
            .put(uriUser + "/" + username)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", is(String.valueOf(id)))
        ;
    }

    @Test
    @Order(4)
    public void testDeleteUser(){
        given()
            .contentType(ct)
            .log().all()
        .when()
            .delete(uriUser + "/" + username)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", is(username))
        ;
    }
}