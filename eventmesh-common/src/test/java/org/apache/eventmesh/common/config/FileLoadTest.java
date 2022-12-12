package org.apache.eventmesh.common.config;

import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.util.Objects;
import java.util.Properties;

import static org.junit.Assert.*;

public class FileLoadTest {

    String filePath = "classPath://newConfiguration-common.properties";

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void getFileLoad() {
        Assert.assertTrue(FileLoad.getFileLoad("properties") instanceof FileLoad.PropertiesFileLoad);
        Assert.assertTrue(FileLoad.getFileLoad("yaml") instanceof FileLoad.YamlFileLoad);
        Assert.assertTrue(FileLoad.getFileLoad("other") instanceof FileLoad.PropertiesFileLoad);
        Assert.assertNotNull(FileLoad.getYamlFileLoad());
    }

    @Test
    public void usePropertiesFileLoad() {
        ConfigInfo configInfo = new ConfigInfo();

        String path = Objects.requireNonNull(ConfigService.class.getResource("/" + filePath.substring(12))).getPath();
        configInfo.setFilePath(path);

        Properties properties0 = new Properties();
        properties0.put("eventMesh.server.env","env-succeed!!!");
        Properties properties1 = new Properties();
        properties1.put("eventMesh.server.env","env-succeed!!!");

        ExampleClazz exampleClazz0 = new ExampleClazz();
        ExampleClazz exampleClazz1 = new ExampleClazz();
        try {
            properties0 = FileLoad.PROPERTIES_FILE_LOAD.getConfig(configInfo);
            configInfo.setClazz(ExampleClazz.class);
            exampleClazz1 = FileLoad.PROPERTIES_FILE_LOAD.getConfig(configInfo);
            exampleClazz0 = FileLoad.PROPERTIES_FILE_LOAD.getConfig(properties1,configInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        Assert.assertEquals(properties0.get("eventMesh.server.env"),"env-succeed!!!");
        Assert.assertEquals(exampleClazz0.exampleConfiguration,"env-succeed!!!");
        Assert.assertEquals(exampleClazz1.exampleConfiguration,"env-succeed!!!");
    }

    @Test
    public void useYamlFileLoad() {
        filePath = "classPath://newConfiguration-common.yaml";

        ConfigInfo configInfo = new ConfigInfo();
        String path = Objects.requireNonNull(ConfigService.class.getResource("/" + filePath.substring(12))).getPath();
        configInfo.setFilePath(path);
        configInfo.setClazz(ExampleClazz.class);

        ExampleClazz exampleClazz0 = new ExampleClazz();
        try {
            exampleClazz0 = FileLoad.YAML_FILE_LOAD.getConfig(configInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        Assert.assertEquals(exampleClazz0.exampleConfiguration,"env-succeed!!!");
    }
}