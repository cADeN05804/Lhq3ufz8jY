// 代码生成时间: 2025-09-30 02:17:26
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.views.mustache.MustacheFactory;
import io.dropwizard.views.mustache.MustacheViewRenderer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.nio.charset.StandardCharsets;

public class ComplianceMonitoringService extends Application<ComplianceMonitoringConfiguration> {

    public static void main(String[] args) throws Exception {
        new ComplianceMonitoringService().run(args);
    }

    @Override
    public void initialize(Bootstrap<ComplianceMonitoringConfiguration> bootstrap) {
        // 初始化配置文件替换和环境变量
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        // 设置自定义的Jackson模块，如Hibernate支持
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Hibernate5Module());
        bootstrap.setObjectMapper(objectMapper);

        // 配置验证器
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        bootstrap.addValidator(validator);
    }

    @Override
    public void run(ComplianceMonitoringConfiguration configuration, Environment environment) {
        // 设置视图渲染器和视图渲染工厂
        final MustacheFactory mustacheFactory = new MustacheFactory();
        environment.getObjectMapper().registerModule(new Hibernate5Module());
        environment.jersey().register(new MustacheViewRenderer(mustacheFactory));
        environment.jersey().register(new ViewBundle());

        // 创建并注册REST资源
        environment.jersey().register(new ComplianceResource(configuration));
    }
}
