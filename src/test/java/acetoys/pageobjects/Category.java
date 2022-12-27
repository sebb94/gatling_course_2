package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Category {

  public static final FeederBuilder<String> categoryFeader = csv("data/categoryDetails.csv").circular();

  public static ChainBuilder productListByCatgory = 
  exec(
    http("Load products list page - Category: #{categoryName}")
      .get("/category/#{categorySlug}")
      .check(css("#CategoryName").isEL("#{categoryName}"))
  );

  public static ChainBuilder loadSecondPageOfProducts = 
  exec(
    http("Load 2nd page of products")
      .get("/category/all?page=1")
      .check(css(".page-item.active").is("2"))
  );

  public static ChainBuilder loadThirdPageOfProducts = 
  exec(
    http("Load 3rd page of products")
      .get("/category/all?page=2")
      .check(css(".page-item.active").is("3"))
  );

  
}
