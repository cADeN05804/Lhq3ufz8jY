// 代码生成时间: 2025-09-29 20:08:06
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
# TODO: 优化性能
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
# NOTE: 重要实现细节
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import java.util.EnumSet;

/*
 * IotCarPlatform is a Dropwizard application that implements a basic car IoT platform.
 */
# 优化算法效率
public class IotCarPlatform extends Application<IotCarPlatformConfiguration> {

    @Override
    public String getName() {
        return "IotCarPlatform";
    }
# TODO: 优化性能

    @Override
    public void initialize(Bootstrap<IotCarPlatformConfiguration> bootstrap) {
        // Initialize any necessary configurations here
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)));
        // Enable Jackson to convert JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        bootstrap.getObjectMapper().setConfig(mapper);
        // Initialize ViewBundle
        bootstrap.addBundle(new ViewBundle<IotCarPlatformConfiguration>());
    }

    @Override
    public void run(IotCarPlatformConfiguration configuration, Environment environment) {
        // Enable CORS
        enableCORS(environment);
        // Register resources
        environment.jersey().register(new CarResource(configuration));
    }

    private void enableCORS(Environment environment) {
# NOTE: 重要实现细节
        // Register a CORS filter
# 改进用户体验
        Dynamic filter = environment.servlets().addFilter(CrossOriginFilter.class, "/*");
# FIXME: 处理边界情况
        filter.setInitParameter("allowedOrigins", "*");
        filter.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        filter.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        filter.setInitParameter("preflightMaxAge", "1728000");
# 添加错误处理
        filter.setAsyncSupported(true);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
# 优化算法效率

    /*
     * Main method for starting the Dropwizard application
# FIXME: 处理边界情况
     */
    public static void main(String[] args) throws Exception {
        new IotCarPlatform().run(args);
    }
# 改进用户体验
}
