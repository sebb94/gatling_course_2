package acetoys;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class AceToysSimulation extends Simulation {

  private static final String DOMAIN = "acetoys.uk";

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://" + DOMAIN)
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7");
  

  private ScenarioBuilder scn = scenario("AceToysSimulation")
    .exec(
      http("Load Home Page")
        .get("/")
        .check(status().is(200))
        .check(status().not(400))
        .check(substring("We are a fictional online"))
        .check(css("#_csrf","content").saveAs("csrfToken"))
    )
    .pause(2)
    .exec(
      http("Load Our Stroy")
        .get("/our-story")
        .check(substring("toy store was founded online in"))
    )
    .pause(25)
    .exec(
      http("Load get in touch")
        .get("/get-in-touch")
    )
    .pause(2)
    .exec(
      http("Load products list page - Category All products")
        .get("/category/all")
    )
    .pause(2)
    .exec(
      http("Load next page of products - page 1")
        .get("/category/all?page=1")
    )
    .pause(21)
    .exec(
      http("Load next page of products - page 2")
        .get("/category/all?page=2")
    )
    .pause(2)
    .exec(
      http("Load products details page - Product: Darts Board")
        .get("/product/darts-board")
    )
    .pause(2)
    .exec(
      http("Add product to cart - product id 19")
        .get("/cart/add/19")
    )
    .pause(2)
    .exec(
      http("Load product page - Babies Toys")
        .get("/category/babies-toys")
    )
    .pause(2)
    .exec(
      http("Add product to cart - id 4")
        .get("/cart/add/11")
    )
    .pause(2)
    .exec(
      http("Add product to cart - id 11")
        .get("/cart/add/11")
    )
    .pause(2)
    .exec(
      http("View Cart")
        .get("/cart/view")
    )
    .pause(2)
    .exec(
      http("Login user")
        .post("/login")
        .formParam("_csrf", "#{csrfToken}")
        .formParam("username", "user1")
        .formParam("password", "pass")
        .check(css("#_csrf","content").saveAs("csrfTokenLoggedIn"))
    )
    .exec( 
        session -> {
          System.out.println(session.getString("#{csrfTokenLoggedIn}"));
          return session;
        }
    )
    .pause(2)
    .exec(
      http("Increase product quantity in cart - product id 19")
        .get("/cart/add/19?cartPage=true")
    )
    .pause(2)
    .exec(
      http("Increase product quantity in cart - product id 19")
        .get("/cart/add/19?cartPage=true")
    )
    .pause(2)
    .exec(
      http("Decrease product quantity in cart - product id 19")
        .get("/cart/subtract/19")
    )
    .pause(2)
    .exec(
      http("Checkout")
        .get("/cart/checkout")
        .check(substring("Your products are on their way to you now!!"))
    )
    .pause(2)
    .exec(
      http("Logout")
        .post("/logout")
        .formParam("_csrf", "#{csrfTokenLoggedIn}")
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
