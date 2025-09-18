// 代码生成时间: 2025-09-18 10:12:27
import com.dropwizard.Application;
import com.dropwizard.setup.Bootstrap;
import com.dropwizard.setup.Environment;
import com.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

/**
 * This Dropwizard Application demonstrates basic XSS protection.
 */
@Path("/xss")
public class XssProtectionApp extends Application<XssProtectionAppConfiguration> {

    /**
     * Entry point for the application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) throws Exception {
        new XssProtectionApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<XssProtectionAppConfiguration> bootstrap) {
        // Nothing to do here for this example
    }

    @Override
    public void run(XssProtectionAppConfiguration configuration, Environment environment) {
        // Registering a view bundle to handle views
        environment.jersey().register(new ViewMessageBodyWriter());
        environment.views().register(XssView.class);
    }
}

/**
 * A simple resource class demonstrating XSS protection.
 */
@Path("/example")
public class XssResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getUnsafeExample() {
        // Unsafe example, do not use in production!
        return "<html><body><script>alert(1);</script></body></html>";
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public View getSafeExample() {
        // Safe example, using View class to escape HTML
        XssView view = new XssView("<script>alert(1);</script>");
        return view;
    }
}

/**
 * A view class that escapes HTML content.
 */
public class XssView extends View {
    private final String unsafeContent;

    public XssView(String unsafeContent) {
        super("xssTemplate.mustache");
        this.unsafeContent = Objects.requireNonNull(unsafeContent);
    }

    public String getUnsafeContent() {
        return escapeHtml(unsafeContent);
    }

    private String escapeHtml(String unsafeContent) {
        // Simple HTML escaping - for production, consider using a library
        return unsafeContent
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace(""", "&quot;")
                .replace("'", "&#39;");
    }
}

// Configuration class (not shown for brevity)
// XssProtectionAppConfiguration.java
// Message body writer (not shown for brevity)
// ViewMessageBodyWriter.java