package org.apache.eventmesh.connector.rocketmq.consumer;

import org.apache.eventmesh.common.config.ConfigService;
import org.apache.eventmesh.connector.rocketmq.config.ClientConfiguration;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RocketMQConsumerImplTest {

    @Test
    public void getConfigWhenSPIInit() throws Exception {
        RocketMQConsumerImpl extensionInstance = RocketMQConsumerImpl.class.newInstance();

        ConfigService configService = ConfigService.getInstance();
        configService.setRootConfig("classPath://rocketmq-client.properties");
        ConfigService.getInstance().getConfig(extensionInstance);

        ClientConfiguration config = extensionInstance.getClientConfiguration();
        Assert.assertEquals(config.namesrvAddr, "namesrvAddr-succeed!!!");
        Assert.assertEquals(config.clientUserName, "username-succeed!!!");
        Assert.assertEquals(config.clientPass, "password-succeed!!!");
        Assert.assertEquals(config.consumeThreadMin, Integer.valueOf(1816));
        Assert.assertEquals(config.consumeThreadMax, Integer.valueOf(2816));
        Assert.assertEquals(config.consumeQueueSize, Integer.valueOf(3816));
        Assert.assertEquals(config.pullBatchSize, Integer.valueOf(4816));
        Assert.assertEquals(config.ackWindow, Integer.valueOf(5816));
        Assert.assertEquals(config.pubWindow, Integer.valueOf(6816));
        Assert.assertEquals(config.consumeTimeout, 7816);
        Assert.assertEquals(config.pollNameServerInterval, Integer.valueOf(8816));
        Assert.assertEquals(config.heartbeatBrokerInterval, Integer.valueOf(9816));
        Assert.assertEquals(config.rebalanceInterval, Integer.valueOf(11816));
        Assert.assertEquals(config.clusterName, "cluster-succeed!!!");
        Assert.assertEquals(config.accessKey, "accessKey-succeed!!!");
        Assert.assertEquals(config.secretKey, "secretKey-succeed!!!");
    }
}