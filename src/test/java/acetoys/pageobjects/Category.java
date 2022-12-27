package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Category {

  public static ChainBuilder productListByCatgory_AllProducts = 
  exec(
    http("Load products list page - Category: All products")
      .get("/category/all")
      .check(css("#CategoryName").is("All Products"))
  );

  public static ChainBuilder productListByCatgory_BabiesToys = 
  exec(
    http("Load products list page - Category: Babies Toys ")
      .get("/category/babies-toys")
      .check(css("#CategoryName").is("Babies Toys"))
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
