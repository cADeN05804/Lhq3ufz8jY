// 代码生成时间: 2025-10-09 02:00:21
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.viewsViewsBundle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicInteger;

// DocumentCollaborationPlatformApp is the main application class for the document collaboration platform.
@Path("/collaboration")
public class DocumentCollaborationPlatformApp extends Application<DocumentCollaborationPlatformConfiguration> {
    private static final AtomicInteger counter = new AtomicInteger();

    // REST API for the document collaboration platform.
    @Path("/hello-world")
    public static class HelloWorldResource {

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String sayHello() {
            int value = counter.incrementAndGet();
            return "Hello World! This is counter number " + value;
        }
    }

    @Override
    public void run(DocumentCollaborationPlatformConfiguration configuration, Environment environment) throws Exception {
        // Register the REST API resource.
        environment.jersey().register(new HelloWorldResource());
    }

    // Main entry point for the application.
    public static void main(String[] args) throws Exception {
        new DocumentCollaborationPlatformApp().run(args);
    }
}

// DocumentCollaborationPlatformConfiguration is the configuration class for the document collaboration platform.
public class DocumentCollaborationPlatformConfiguration extends Configuration {
    // Add configuration properties here.
}

// ViewsBundleConfig is the configuration class for Dropwizard Views.
public class ViewsBundleConfig extends ViewsBundleConfiguration {
    // Add view configurations here if needed.
}
