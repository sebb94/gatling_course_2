package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Customer {

  public static ChainBuilder login = 
  exec(
      http("Login user")
        .post("/login")
        .formParam("_csrf", "#{csrfToken}")
        .formParam("username", "user1")
        .formParam("password", "pass")
        .check(css("#_csrf","content").saveAs("csrfTokenLoggedIn"))
    );

  public static ChainBuilder logout = 
  exec(
      http("Logout")
        .post("/logout")
        .formParam("_csrf", "#{csrfTokenLoggedIn}")
        .check(css("#LoginLink").is("Login"))
    );
  
}
