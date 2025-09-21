// 代码生成时间: 2025-09-22 02:09:53
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/crawl")
public class WebContentCrawlerResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response crawlWebsite(@QueryParam("url") String url) {
        try {
            // Fetch and parse the content of the website
            Document document = Jsoup.connect(url).get();
            // Return the HTML content of the website
            return Response.ok(document.toString()).build();
        } catch (IOException e) {
            // Handle exceptions and return an error message
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to fetch website content: " + e.getMessage()).build();
        }
    }
}

public class WebContentCrawlerApplication extends Application<WebContentCrawlerConfiguration> {

    public static void main(String[] args) throws Exception {
        new WebContentCrawlerApplication().run(args);
    }

    @Override
    public String getName() {
        return "WebContentCrawler";
    }

    @Override
    public void initialize(Bootstrap<WebContentCrawlerConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)));
        // Register the ViewBundle which provides a default template renderer
        bootstrap.addBundle(new ViewBundle<WebContentCrawlerConfiguration>()
                .withDefaultRenderingEngine());
        // Register the AssetsBundle to serve static files like CSS, JS, images, etc.
        bootstrap.addBundle(new AssetsBundle("/web", "/", "index.html"));
    }

    @Override
    public void run(WebContentCrawlerConfiguration configuration, Environment environment) {
        // Register the WebContentCrawlerResource resource
        environment.jersey().register(new WebContentCrawlerResource());
        // Register the SSE feature to support Server-Sent Events
        environment.jersey().register(SseFeature.class);
    }
}