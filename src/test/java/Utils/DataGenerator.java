package Utils;

import java.util.Locale;

import Entities.RegistrationDto;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * @author Siumbel
 */
public class DataGenerator {

  private static final RequestSpecification requestSpec = new RequestSpecBuilder()
      .setBaseUri("http://localhost")
      .setPort(9999)
      .setAccept(ContentType.JSON)
      .setContentType(ContentType.JSON)
      .log(LogDetail.ALL)
      .build();
  private static final Faker faker = new Faker(new Locale("en"));

  private DataGenerator() {
  }

  private static void sendRequest(RegistrationDto user) {
    given()
        .spec(requestSpec)
        .body(user)
        .when()
        .post("/api/system/users")
        .then()
        .statusCode(200);
  }

  public static String getRandomLogin() {
    String login = faker.name().username();
    return login;
  }

  public static String getRandomPassword() {
    String password = faker.internet().password();
    return password;
  }

  public static RegistrationDto getUser(String status) {
    RegistrationDto user = new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
    return user;
  }

  public static class Registration {
    public static RegistrationDto getRegisteredUser(String status) {
      var registeredUser = getUser(status);
      sendRequest(registeredUser);
      return registeredUser;
    }
  }
}
