// 代码生成时间: 2025-10-07 00:00:22
 * comments, and follows Java best practices for maintainability and scalability.
 */

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/mock-data")
public class MockDataResource extends ResourceConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(MockDataResource.class);

    public MockDataResource() {
        super();
        register(CORSFilter.class); // Enable CORS if needed
        packages("com.example.resources"); // Register all resources in the package
    }

    @GET
    @Path("/random-number")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(100); // Generate a random number between 0 and 99
        return String.valueOf(randomNumber);
    }

    @GET
    @Path("/random-string")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRandomString() {
        Random random = new Random();
        return "RandomString" + random.nextInt(1000); // Generate a random string
    }
}

public class MockDataGenerator extends Application<MockDataGeneratorConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MockDataGenerator.class);

    public static void main(String[] args) throws Exception {
        new MockDataGenerator().run(args);
    }

    @Override
    public void initialize(Bootstrap<MockDataGeneratorConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<>());
    }

    @Override
    public void run(MockDataGeneratorConfiguration configuration, Environment environment) {
        environment.jersey().register(new MockDataResource()); // Register the mock data resource
    }
}

/**
 * A simple configuration class for Dropwizard application.
 */
public class MockDataGeneratorConfiguration extends Configuration {
    // Configuration properties can be added here
}
