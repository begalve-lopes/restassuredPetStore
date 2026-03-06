import  static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class TesteUser {
   static String ct = "application/json";
    static String uriUser = "https://petstore.swagger.io/v2/user/";
    
    static String userName = "maria";
    static String password ="123456";
    static String message = "logged in user session:";
    static String token;

    @Test 
    public String TestLogin(){
        Response resposta = (Response) given()
            .contentType(ct)
            .log().all()
        .when()
            .get(uriUser + "login?username=" + userName + "&password=" + password)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))   
            .body("message", containsString(message)) 
            .body("message",hasLength(36))
        .extract()
        ;

        token = resposta.jsonPath().getString("message").substring(23);
        System.out.println("Conteudo do Token: " + token);
        return token;
    } 
}
