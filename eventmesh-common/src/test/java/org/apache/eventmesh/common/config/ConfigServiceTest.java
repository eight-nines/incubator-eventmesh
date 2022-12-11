package org.apache.eventmesh.common.config;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ConfigServiceTest {


    @Test
    public void testGetConfigForCommonConfiguration() throws Exception {
        ConfigService configService = ConfigService.getInstance();
        configService.setRootConfig("classPath://newConfiguration.properties");

        CommonConfiguration config = configService.getConfig(CommonConfiguration.class);

        Assert.assertEquals(config.eventMeshEnv, "env-succeed!!!");
        Assert.assertEquals(config.eventMeshIDC, "idc-succeed!!!");
        Assert.assertEquals(config.eventMeshCluster, "cluster-succeed!!!");
        Assert.assertEquals(config.eventMeshName, "name-succeed!!!");
        Assert.assertEquals(config.sysID, "sysid-succeed!!!");
        Assert.assertEquals(config.eventMeshConnectorPluginType, "connector-succeed!!!");
        Assert.assertEquals(config.eventMeshSecurityPluginType, "security-succeed!!!");
        Assert.assertEquals(config.eventMeshRegistryPluginType, "registry-succeed!!!");
        Assert.assertEquals(config.eventMeshTracePluginType, "trace-succeed!!!");
        Assert.assertEquals(config.eventMeshServerIp, "hostIp-succeed!!!");

        List<String> list = new ArrayList<>();
        list.add("metrics-succeed1!!!");
        list.add("metrics-succeed2!!!");
        list.add("metrics-succeed3!!!");
        Assert.assertEquals(config.eventMeshMetricsPluginType, list);

        Assert.assertEquals(config.eventMeshServerSecurityEnable, Boolean.TRUE);
        Assert.assertEquals(config.eventMeshServerRegistryEnable, Boolean.TRUE);
        Assert.assertEquals(config.eventMeshServerTraceEnable, Boolean.TRUE);

        Assert.assertEquals(config.eventMeshWebhookOrigin, "eventmesh.idc-succeed!!!");
    }



}