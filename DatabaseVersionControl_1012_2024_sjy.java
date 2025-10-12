// 代码生成时间: 2025-10-12 20:24:34
import io.dropwizard.Application;
import io.dropwizard.configuration.Environment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Configuration;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
# 改进用户体验
import liquibase.Liquibase;
# NOTE: 重要实现细节
import liquibase.database.Database;
import liquibase.resource.ClassLoaderResourceAccessor;
# 添加错误处理
import liquibase.resource.ResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class DatabaseVersionControl extends Application<Configuration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseVersionControl.class);

    public static void main(String[] args) throws Exception {
# TODO: 优化性能
        new DatabaseVersionControl().run(args);
# 增强安全性
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        // 注册HibernateBundle
        HibernateBundle<Configuration> hibernateBundle = new HibernateBundle<Configuration>(MigrationEntity.class) {
            @Override
            public DataSource getDataSource(Configuration configuration) {
                return configuration.getDataSourceFactory().build(environment.metrics());
            }
# 增强安全性
        };
        bootstrap.addBundle(hibernateBundle);

        // 注册MigrationsBundle
        bootstrap.addBundle(new MigrationsBundle<Configuration>() {
            @Override
            public DataSource getDataSource(Configuration configuration) {
                return configuration.getDataSourceFactory().build(environment.metrics());
            }
        });
# 扩展功能模块
    }
# 添加错误处理

    @Override
    public void run(Configuration configuration, Environment environment) {
        // 获取DataSource
# 扩展功能模块
        DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics());

        // 获取HibernateBundle
        HibernateBundle<Configuration> hibernateBundle = new HibernateBundle<Configuration>(MigrationEntity.class) {
            @Override
            public DataSource getDataSource(Configuration configuration) {
                return configuration.getDataSourceFactory().build(environment.metrics());
            }
        };

        // 初始化Liquibase
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
        Liquibase liquibase = new Liquibase("migration.xml", resourceAccessor, dataSource.getConnection());

        // 执行数据库迁移
        try {
            liquibase.update("versionControl");
            LOGGER.info("Database migration executed successfully");
        } catch (Exception e) {
            LOGGER.error("Error executing database migration", e);
        }
    }
}
