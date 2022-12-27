package acetoys;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import acetoys.simulation.TestPopulation;

public class AceToysSimulation extends Simulation {

  private static final String DOMAIN = "acetoys.uk";
  private static final String TEST_TYPE = System.getenv("TEST_TYPE");

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://" + DOMAIN)
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7");
  
    {
      if (TEST_TYPE.equals("INSTANT_USERS")) {
          setUp(TestPopulation.instantUsers).protocols(httpProtocol)
          .assertions(
            global().responseTime().mean().lt(3000),
            global().successfulRequests().percent().gt(99.0),
            forAll().responseTime().max().lt(5000)
        );
      } else if (TEST_TYPE.equals("RAMP_USERS")) {
          setUp(TestPopulation.rampUsers).protocols(httpProtocol);
      } else if (TEST_TYPE.equals("COMPLEX_INJECTION")) {
          setUp(TestPopulation.complexInjection).protocols(httpProtocol);
      } else if (TEST_TYPE.equals("CLOSED_MODEL")) {
          setUp(TestPopulation.closedModel).protocols(httpProtocol);
      } else {
          setUp(TestPopulation.instantUsers).protocols(httpProtocol);
      }
  }

  }

