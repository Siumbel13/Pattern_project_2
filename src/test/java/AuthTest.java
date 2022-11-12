import Utils.DataGenerator;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * @author Siumbel
 */
public class AuthTest {

  private static final String LOGIN = "[data-test-id='login'] input";
  private static final String PASSWORD = "[data-test-id='password'] input";
  private static final String BUTTON = ".button.button";

  @BeforeEach
  void setup() {
    Configuration.holdBrowserOpen = true;
    open("http://localhost:9999");
  }

  @Test
  void shouldAddUserAndAuthTest() {
    var user = DataGenerator.Registration.getRegisteredUser("active");

    login(user.getLogin(), user.getPassword());

    $(".heading_theme_alfa-on-white").shouldHave(Condition.text("Личный кабинет"));
  }

  @Test
  void shouldAddBlockedUserAndAuthTest() {
    var user = DataGenerator.Registration.getRegisteredUser("blocked");

    login(user.getLogin(), user.getPassword());

    $(".notification .notification__content")
        .shouldHave(Condition.text("Пользователь заблокирован"))
        .shouldBe(Condition.visible);
  }

  @Test
  void shouldAddNoValidateUserAndAuthTest() {
    var user = DataGenerator.Registration.getRegisteredUser("active");

    login("noValidateName", user.getPassword());

    $(".notification .notification__content")
        .shouldHave(Condition.text("Неверно указан логин или пароль"))
        .shouldBe(Condition.visible);
  }

  @Test
  void shouldAddNoValidatePasswordAndAuthTest() {
    var registeredUser = DataGenerator.Registration.getRegisteredUser("active");

    login(registeredUser.getLogin(), "noValidatePassword");

    $(".notification .notification__content")
        .shouldHave(Condition.text("Неверно указан логин или пароль"))
        .shouldBe(Condition.visible);
  }

  @Test
  void shouldNotRegisteredUserAuthTest() {
    var notRegisteredUser = DataGenerator.getUser("active");

    login(notRegisteredUser.getLogin(), notRegisteredUser.getPassword());

    $(".notification .notification__content")
        .shouldHave(Condition.text("Неверно указан логин или пароль"))
        .shouldBe(Condition.visible);
  }

  private void login(String login, String password) {
    $(LOGIN).setValue(login);
    $(PASSWORD).setValue(password);
    $(BUTTON).click();
  }
}
