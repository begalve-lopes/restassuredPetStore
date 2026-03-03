import  java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

//classe de teste para a entidade User, utilizando o framework JUnit 5
public class TestUser {

    //variáveis estáticas para o tipo de conteúdo e a URI da API de pets    
    static String contentType = "application/json";
    static String uriPet = "https://petstore.swagger.io/v2/pet";

    //método para ler o conteúdo de um arquivo JSON e retornar como String
    public static String lerArquivoJson(String arquivoJson) {
        try {
            return new String(Files.readAllBytes(Path.of(arquivoJson)));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o arquivo JSON: " + arquivoJson, e);
        }
    }

    //método de teste para a operação de criação de um pet, atualmente vazio
    @Test
    public void testPostPet() {
        try {
            String jsonBody  = lerArquivoJson("src/test/resource/json/pet1.json");

            //aqui você pode adicionar a lógica para enviar uma requisição POST para criar um pet
            String petId = "123456"; //substitua pelo ID do pet criado

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
                .body("name", is("Snooby")) //verifica se o nome do pet na resposta é "Snooby"; 
                .body("id", is(Integer.parseInt(petId))) //verifica se o ID do pet na resposta é igual ao ID do pet criado
                .body("category.id", is(1)) //verifica se a categoria do pet na resposta é "cachorro"
                .body("category.name", is("cachorro")) //verifica se a categoria do pet na resposta é "cachorro"
                .body("tags[0].id", is(9))
                .body("tags[0].name", is("vacinado")) //verifica se o nome da tag na resposta é "vacinado"
                .body("status", is("available")) //verifica se o status do pet na resposta é "available"
            ;


        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar o teste de criação de pet: " + e.getMessage(), e);
        }
    }
}
