package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Cart {

  public static ChainBuilder cartView = 
  exec(
    http("View Cart")
      .get("/cart/view")
      .check(css("#CategoryHeader").is("Cart Overview"))
  );

  public static ChainBuilder increaseQuantityInCart = 
  exec(
    http("Increase product quantity in cart - product id 19")
      .get("/cart/add/19?cartPage=true")
  );

  public static ChainBuilder decreaseQuantityInCart = 
  exec(
      http("Decrease product quantity in cart - product id 19")
        .get("/cart/subtract/19")
  );

}
