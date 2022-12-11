package org.apache.eventmesh.runtime.boot;

import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import junit.framework.TestCase;
import org.apache.eventmesh.common.config.CommonConfiguration;
import org.apache.eventmesh.common.config.ConfigService;
import org.apache.eventmesh.runtime.configuration.EventMeshGrpcConfiguration;
import org.apache.eventmesh.runtime.configuration.EventMeshHTTPConfiguration;
import org.apache.eventmesh.runtime.configuration.EventMeshTCPConfiguration;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class EventMeshServerTest extends TestCase {

    public void testGetConfigForEventMeshHTTPConfiguration() throws Exception {

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

    public void testGetConfigForEventMeshGrpcConfiguration() throws Exception {

        ConfigService configService = ConfigService.getInstance();
        configService.setRootConfig("classPath://newConfiguration-runtime.properties");

        EventMeshGrpcConfiguration config = configService.getConfig(EventMeshGrpcConfiguration.class);

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


        Assert.assertEquals(config.grpcServerPort, 816);
        Assert.assertEquals(config.eventMeshSessionExpiredInMills, 1816);
        Assert.assertEquals(config.eventMeshServerBatchMsgBatchEnabled, Boolean.FALSE);
        Assert.assertEquals(config.eventMeshServerBatchMsgThreadNum, 2816);
        Assert.assertEquals(config.eventMeshServerSendMsgThreadNum, 3816);
        Assert.assertEquals(config.eventMeshServerPushMsgThreadNum, 4816);
        Assert.assertEquals(config.eventMeshServerReplyMsgThreadNum, 5816);
        Assert.assertEquals(config.eventMeshServerSubscribeMsgThreadNum, 6816);
        Assert.assertEquals(config.eventMeshServerRegistryThreadNum, 7816);
        Assert.assertEquals(config.eventMeshServerAdminThreadNum, 8816);
        Assert.assertEquals(config.eventMeshServerRetryThreadNum, 9816);
        Assert.assertEquals(config.eventMeshServerPullRegistryInterval, 11816);
        Assert.assertEquals(config.eventMeshServerAsyncAccumulationThreshold, 12816);
        Assert.assertEquals(config.eventMeshServerRetryBlockQueueSize, 13816);
        Assert.assertEquals(config.eventMeshServerBatchBlockQueueSize, 14816);
        Assert.assertEquals(config.eventMeshServerSendMsgBlockQueueSize, 15816);
        Assert.assertEquals(config.eventMeshServerPushMsgBlockQueueSize, 16816);
        Assert.assertEquals(config.eventMeshServerSubscribeMsgBlockQueueSize, 17816);
        Assert.assertEquals(config.eventMeshServerBusyCheckInterval, 18816);
        Assert.assertEquals(config.eventMeshServerConsumerEnabled, Boolean.TRUE);
        Assert.assertEquals(config.eventMeshServerUseTls, Boolean.TRUE);
        Assert.assertEquals(config.eventMeshBatchMsgRequestNumPerSecond, 21816);
        Assert.assertEquals(config.eventMeshMsgReqNumPerSecond, 19816);
    }

    public void testGetConfigForEventMeshTCPConfiguration() throws Exception {

        ConfigService configService = ConfigService.getInstance();
        configService.setRootConfig("classPath://newConfiguration-runtime.properties");

        EventMeshTCPConfiguration config = configService.getConfig(EventMeshTCPConfiguration.class);

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


        Assert.assertEquals(config.eventMeshTcpServerPort, 816);
        Assert.assertEquals(config.eventMeshTcpIdleAllSeconds, 1816);
        Assert.assertEquals(config.eventMeshTcpIdleWriteSeconds, 2816);
        Assert.assertEquals(config.eventMeshTcpIdleReadSeconds, 3816);
        Assert.assertEquals(config.eventMeshTcpMsgReqnumPerSecond, Integer.valueOf(4816));
        Assert.assertEquals(config.eventMeshTcpClientMaxNum, 5816);
        Assert.assertEquals(config.eventMeshTcpServerEnabled, Boolean.TRUE);
        Assert.assertEquals(config.eventMeshTcpGlobalScheduler, 6816);
        Assert.assertEquals(config.eventMeshTcpTaskHandleExecutorPoolSize, 7816);
        Assert.assertEquals(config.eventMeshTcpMsgDownStreamExecutorPoolSize, 8816);
        Assert.assertEquals(config.eventMeshTcpSessionExpiredInMills, 1816);
        Assert.assertEquals(config.eventMeshTcpSessionUpstreamBufferSize, 11816);
        Assert.assertEquals(config.eventMeshTcpMsgAsyncRetryTimes, 12816);
        Assert.assertEquals(config.eventMeshTcpMsgSyncRetryTimes, 13816);
        Assert.assertEquals(config.eventMeshTcpMsgRetrySyncDelayInMills, 14816);
        Assert.assertEquals(config.eventMeshTcpMsgRetryAsyncDelayInMills, 15816);
        Assert.assertEquals(config.eventMeshTcpMsgRetryQueueSize, 16816);
        Assert.assertEquals(config.eventMeshTcpRebalanceIntervalInMills, Integer.valueOf(17816));
        Assert.assertEquals(config.eventMeshServerAdminPort, 18816);
        Assert.assertEquals(config.eventMeshTcpSendBackEnabled, Boolean.TRUE);
        Assert.assertEquals(config.eventMeshTcpSendBackMaxTimes, 3);//默认
        Assert.assertEquals(config.eventMeshTcpPushFailIsolateTimeInMills, 21816);
        Assert.assertEquals(config.gracefulShutdownSleepIntervalInMills, 22816);
        Assert.assertEquals(config.sleepIntervalInRebalanceRedirectMills, 23816);
        Assert.assertEquals(config.eventMeshEventSize, 22816);
        Assert.assertEquals(config.eventMeshEventBatchSize, 23816);
    }
}