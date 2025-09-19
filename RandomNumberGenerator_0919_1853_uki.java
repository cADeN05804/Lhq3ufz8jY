// 代码生成时间: 2025-09-19 18:53:35
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewMessageBody;
import io.dropwizard.views.ViewBundle;
import java.util.Random;

// 定义随机数生成器的资源类
public class RandomNumberGenerator extends Application<RandomNumberGeneratorConfiguration> {

    // 定义资源类，用于生成随机数
    public static class RandomNumberResource {

        // 使用Random类生成随机数
        public int generateRandomNumber(int min, int max) {
            if (min > max) {
                // 异常处理，确保min小于等于max
                throw new IllegalArgumentException("min cannot be greater than max");
            }
            Random random = new Random();
            return random.nextInt(max - min + 1) + min;
        }
    }

    // 定义配置类
    public static class RandomNumberGeneratorConfiguration extends Configuration {
        // 可以添加配置属性
    }

    // 主方法，启动应用程序
    public static void main(String[] args) throws Exception {
        new RandomNumberGenerator().run(args);
    }

    @Override
    public void initialize(Bootstrap<RandomNumberGeneratorConfiguration> bootstrap) {
        // 初始化配置
        bootstrap.addBundle(new ViewBundle<>());
    }

    @Override
    public void run(RandomNumberGeneratorConfiguration configuration, Environment environment) {
        // 环境设置
        RandomNumberResource randomNumberResource = new RandomNumberResource();
        environment.jersey().register(randomNumberResource);
    }
}
