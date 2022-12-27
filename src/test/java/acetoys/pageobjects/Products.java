package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Products {

  public static final FeederBuilder<Object> productFeeder = jsonFile("data/productDetails.json");

  public static ChainBuilder loadProductDetailsPage = 
  feed(productFeeder).
  exec(
    http("Load products details page - Product: #{name}")
      .get("/product/#{slug}")
      .check(css("#ProductDescription").isEL("#{description}"))
  );

  public static ChainBuilder addProductToCart = 
  exec(
    http("Add product to cart - Product name #{name}")
      .get("/cart/add/#{id}")
      .check(substring("You have <span>1</span> products in your Basket"))
  );

}
