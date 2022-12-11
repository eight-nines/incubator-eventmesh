package org.apache.eventmesh.runtime.boot;

import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import junit.framework.TestCase;
import org.apache.eventmesh.common.config.CommonConfiguration;
import org.apache.eventmesh.common.config.ConfigService;
import org.apache.eventmesh.runtime.configuration.EventMeshHTTPConfiguration;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class EventMeshServerTest extends TestCase {

    public void testGetConfig() throws Exception {

        ConfigService configService = ConfigService.getInstance();
        configService.setRootConfig("classPath://newConfiguration-runtime.properties");

        EventMeshHTTPConfiguration config = configService.getConfig(EventMeshHTTPConfiguration.class);

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


        Assert.assertEquals(config.httpServerPort, 1816);
        Assert.assertEquals(config.eventMeshServerBatchMsgBatchEnabled, Boolean.FALSE);
        Assert.assertEquals(config.eventMeshServerBatchMsgThreadNum, 2816);
        Assert.assertEquals(config.eventMeshServerSendMsgThreadNum, 3816);
        Assert.assertEquals(config.eventMeshServerPushMsgThreadNum, 4816);
        Assert.assertEquals(config.eventMeshServerReplyMsgThreadNum, 5816);
        Assert.assertEquals(config.eventMeshServerClientManageThreadNum, 6816);
        Assert.assertEquals(config.eventMeshServerRegistryThreadNum, 7816);
        Assert.assertEquals(config.eventMeshServerAdminThreadNum, 8816);
        Assert.assertEquals(config.eventMeshServerRetryThreadNum, 9816);
        Assert.assertEquals(config.eventMeshServerPullRegistryInterval, 11816);
        Assert.assertEquals(config.eventMeshServerAsyncAccumulationThreshold, 12816);
        Assert.assertEquals(config.eventMeshServerRetryBlockQSize, 13816);
        Assert.assertEquals(config.eventMeshServerBatchBlockQSize, 14816);
        Assert.assertEquals(config.eventMeshServerSendMsgBlockQSize, 15816);
        Assert.assertEquals(config.eventMeshServerPushMsgBlockQSize, 16816);
        Assert.assertEquals(config.eventMeshServerClientManageBlockQSize, 17816);
        Assert.assertEquals(config.eventMeshServerBusyCheckInterval, 18816);
        Assert.assertEquals(config.eventMeshServerConsumerEnabled, Boolean.TRUE);
        Assert.assertEquals(config.eventMeshServerUseTls, Boolean.TRUE);
        Assert.assertEquals(config.eventMeshHttpMsgReqNumPerSecond, 19816);
        Assert.assertEquals(config.eventMeshBatchMsgRequestNumPerSecond, 21816);
        Assert.assertEquals(config.eventMeshEventSize, 22816);
        Assert.assertEquals(config.eventMeshEventBatchSize, 23816);

        List<IPAddress> list4 = new ArrayList<>();
        list4.add(new IPAddressString("127.0.0.1").toAddress());
        list4.add(new IPAddressString("127.0.0.2").toAddress());
        Assert.assertEquals(config.eventMeshIpv4BlackList, list4);
        List<IPAddress> list6 = new ArrayList<>();
        list6.add(new IPAddressString("0:0:0:0:0:0:7f00:01").toAddress());
        list6.add(new IPAddressString("0:0:0:0:0:0:7f00:02").toAddress());
        Assert.assertEquals(config.eventMeshIpv6BlackList, list6);


    }
}