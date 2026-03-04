import  java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;

//classe de teste para a entidade User, utilizando o framework JUnit 5
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUser {

    //variáveis estáticas para o tipo de conteúdo e a URI da API de pets    
    static String contentType = "application/json";
    static String uriPet = "https://petstore.swagger.io/v2/pet";
    static String petId = "123456"; //ID do pet a ser criado e verificado nos testes
    static String petName = "Snooby";
    static String categoryName = "cachorro";
    static String tagName = "vacinado";  
    static String[] status = {"available","sold"};
    //método para ler o conteúdo de um arquivo JSON e retornar como String
    public static String lerArquivoJson(String arquivoJson) {
        try {
            return new String(Files.readAllBytes(Path.of(arquivoJson)));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o arquivo JSON: " + arquivoJson, e);
        }
    }

    //método de teste para a operação de criação de um pet, atualmente vazio
    @Test @Order(1)
    public void testPostPet() {
        try {
            String jsonBody  = lerArquivoJson("src/test/resource/json/pet1.json");
            //comença  o teste via REST Assured para verificar se o pet foi criado corretamente
            given() //pré-condições
                .contentType(contentType) //tipo de conteúdo
                .log().all() //log de todas as informações da requisição
                .body(jsonBody)   //corpo da requisição com os dados do pet  

            .when() //ação a ser testada
                .post(uriPet) //envia a requisição POST para a URI da API de pets;

            .then() //verificações pós-condição
                .log().all() //log de todas as informações da resposta
                .statusCode(200) //verifica se o status code da resposta é 200 (OK)
                .body("name", is(petName)) //verifica se o nome do pet na resposta é "Snooby"; 
                .body("id", is(Integer.parseInt(petId))) //verifica se o ID do pet na resposta é igual ao ID do pet criado
                .body("category.name", is(categoryName)) //verifica se a categoria do pet na resposta é "cachorro"
                .body("tags[0].id", is(9))
                .body("tags[0].name", is(tagName)) //verifica se o nome da tag na resposta é "vacinado"
                .body("status", is(status[0])) //verifica se o status do pet na resposta é "available"
            ;


        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar o teste de criação de pet: " + e.getMessage(), e);
        }
    }

    @Test @Order(2)
    public void testGetPet(){
       

       given()
            .contentType(contentType)
            .log().all()
        .when()
            .get(uriPet + "/" + Integer.parseInt(petId))
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is(petName))
            .body("category.name", is(categoryName))
            .body("tags[0].name", is(tagName))
            .body("status", is(status[0]))
        ;
    }

    @Test @Order(3)
    public void testPutPet(){
        try {
            String JsonBody = lerArquivoJson("src/test/resource/json/pet2.json");
            given()
                .contentType(contentType)
                .log().all()
                .body(JsonBody)
            .when()
                .put(uriPet)
            .then()
                .log().all()
                .statusCode(200)
                .body("name", is(petName))
                .body("category.name", is(categoryName))
                .body("tags[0].name", is(tagName))
                .body("status", is(status[1]))
        ;
            
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException("Erro ao executar o teste de atualização de pet: " + e.getMessage(), e);
        }
    }

    @Test @Order(4)
    public void testDeletePet(){
        given()
            .contentType(contentType)
            .log().all()
        .when()
            .delete(uriPet + "/" + Integer.parseInt(petId))
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))    
            .body("message", is(String.valueOf(petId)))
        ;
    }
}
