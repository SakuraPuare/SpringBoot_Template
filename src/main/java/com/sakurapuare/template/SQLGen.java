package com.sakurapuare.template;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.dialect.JdbcTypeMapping;
import com.sakurapuare.template.pojo.entity.BaseEntity;
import com.zaxxer.hikari.HikariDataSource;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SQLGen {

    public static void main(String[] args) {
        // 配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(
                "jdbc:mariadb://127.0.0.1:3306/template?useInformationSchema=true&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        GlobalConfig globalConfig = createGlobalConfigUseStyle();
        // 通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        // JdbcTypeMapping
        JdbcTypeMapping.registerMapping(BigInteger.class, Long.class);
        JdbcTypeMapping.registerMapping(Timestamp.class, LocalDateTime.class);

        // 生成代码
        generator.generate();
    }

    public static GlobalConfig createGlobalConfigUseStyle() {
        // 创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        globalConfig.enableEntity();
//        globalConfig.enableMapper();
//        globalConfig.enableService();
        globalConfig.enableServiceImpl();
//        globalConfig.enableController();
//        globalConfig.enableTableDef();
//        globalConfig.enableMapperXml();

        // 设置根包
        globalConfig.setBasePackage("com.sakurapuare.template");
        globalConfig.getPackageConfig()
                .setControllerPackage("com.sakurapuare.template.controller.superadmin")
                .setServicePackage("com.sakurapuare.template.service.base")
                .setServiceImplPackage("com.sakurapuare.template.service.base")
                .setEntityPackage("com.sakurapuare.template.pojo.entity");

        globalConfig.getEntityConfig()
                .setSuperClassFactory(table -> {
                    return BaseEntity.class;
                })
                .setOverwriteEnable(true)
                .setWithLombok(true)
                .setWithSwagger(true)
                .setJdkVersion(21);

        globalConfig.getMapperConfig()
                .setOverwriteEnable(true);

        globalConfig.getServiceConfig()
                .setClassPrefix("Base")
                .setOverwriteEnable(true);

        globalConfig.getServiceImplConfig()
                .setClassPrefix("Base")
                .setClassSuffix("Service")
                .setCacheExample(true)
                .setOverwriteEnable(true);

        globalConfig.getControllerConfig()
                .setClassPrefix("SuperAdmin")
                .setOverwriteEnable(true);

        globalConfig.getTableDefConfig()
                .setOverwriteEnable(true);

        globalConfig.getMapperXmlConfig()
                .setOverwriteEnable(true);

        return globalConfig;
    }
}