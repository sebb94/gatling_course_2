package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class StaticPages {

    public static ChainBuilder homepage = 
    exec(
      http("Load Home Page")
        .get("/")
        .check(status().is(200))
        .check(status().not(400))
        .check(substring("We are a fictional online"))
        .check(css("#_csrf","content").saveAs("csrfToken"))
    );

    public static ChainBuilder ourStory = 
    exec(
      http("Load Our Stroy")
        .get("/our-story")
        .check(substring("toy store was founded online in"))
    );

    public static ChainBuilder getInTouch = 
    exec(
      http("Load get in touch")
        .get("/get-in-touch")
        .check(substring("we are not actually a real store!"))
    );
  
}
