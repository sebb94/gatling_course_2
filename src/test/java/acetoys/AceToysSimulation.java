package acetoys;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import session.UserSession;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import acetoys.pageobjects.Cart;
import acetoys.pageobjects.Category;
import acetoys.pageobjects.Customer;
import acetoys.pageobjects.Products;
import acetoys.pageobjects.StaticPages;
public class AceToysSimulation extends Simulation {

  private static final String DOMAIN = "acetoys.uk";

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://" + DOMAIN)
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7");
  

  private ScenarioBuilder scn = scenario("AceToysSimulation")
    .exec(UserSession.initSession)
    .exec(StaticPages.homepage)
    .pause(2)
    .exec(StaticPages.ourStory)
    .pause(25)
    .exec(StaticPages.getInTouch)
    .pause(2)
    .exec(Category.productListByCatgory)
    .pause(2)
    .exec(Category.loadSecondPageOfProducts)
    .pause(21)
    .exec(Category.loadThirdPageOfProducts)
    .pause(2)
    .exec(Products.loadProductDetailsPage)
    .pause(2)
    .exec(Products.addProductToCart)
    .pause(2)
    .exec(Category.productListByCatgory)
    .pause(2)
    .exec(Products.addProductToCart)
    .pause(2)
    .exec(Cart.cartView)
    .pause(2)
    .exec(Cart.increaseQuantityInCart)
    .pause(2)
    .exec(Cart.increaseQuantityInCart)
    .pause(2)
    .exec(Cart.decreaseQuantityInCart)
    .pause(2)
    .exec(Cart.checkout)
    .pause(2)
    .exec(Customer.logout);

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
