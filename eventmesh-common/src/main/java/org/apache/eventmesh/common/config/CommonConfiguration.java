/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.eventmesh.common.config;

import org.apache.eventmesh.common.utils.IPUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

public class CommonConfiguration {
    @ConfigFiled(field = "eventMesh.server.env")
    public String eventMeshEnv                 = "P";

    @ConfigFiled(field = "eventMesh.server.idc")
    public String eventMeshIDC                 = "FT";

    @ConfigFiled(field = "eventMesh.server.cluster")
    public String eventMeshCluster             = "LS";

    @ConfigFiled(field = "eventMesh.server.name")
    public String eventMeshName                = "";

    @ConfigFiled(field = "eventMesh.sysid")
    public String sysID                        = "5477";

    @ConfigFiled(field = "eventMesh.connector.plugin.type")
    public String eventMeshConnectorPluginType = "rocketmq";

    @ConfigFiled(field = "eventMesh.security.plugin.type")
    public String eventMeshSecurityPluginType  = "security";

    @ConfigFiled(field = "eventMesh.registry.plugin.type")
    public String eventMeshRegistryPluginType  = "namesrv";

    @ConfigFiled(field = "eventMesh.metrics.plugin")
    public List<String> eventMeshMetricsPluginType;

    @ConfigFiled(field = "eventMesh.trace.plugin")
    public String       eventMeshTracePluginType;

    @ConfigFiled(field = "")
    public    String               namesrvAddr                        = "";

    @ConfigFiled(field = "eventMesh.server.registry.registerIntervalInMills")
    public    Integer              eventMeshRegisterIntervalInMills   = 10 * 1000;

    @ConfigFiled(field = "eventMesh.server.registry.fetchRegistryAddrIntervalInMills")
    public    Integer              eventMeshFetchRegistryAddrInterval = 10 * 1000;

    @ConfigFiled(field = "eventMesh.server.hostIp")
    public    String               eventMeshServerIp                  = null;

    @ConfigFiled(field = "eventMesh.server.security.enabled")
    public    boolean              eventMeshServerSecurityEnable      = false;

    @ConfigFiled(field = "eventMesh.server.registry.enabled")
    public    boolean              eventMeshServerRegistryEnable      = false;

    @ConfigFiled(field = "eventMesh.server.trace.enabled")
    public    boolean              eventMeshServerTraceEnable         = false;

    @ConfigFiled(reload = true)
    public String eventMeshWebhookOrigin = "eventmesh." + eventMeshIDC;

    protected ConfigurationWrapper configurationWrapper;

    public CommonConfiguration(ConfigurationWrapper configurationWrapper) {
        this.configurationWrapper = configurationWrapper;
    }

    public CommonConfiguration() {
    }

    public void reload() {
        this.eventMeshWebhookOrigin = "eventmesh." + eventMeshIDC;
    }

    public void init() {

        if (configurationWrapper != null) {
            eventMeshEnv = checkNotEmpty(ConfKeys.KEYS_EVENTMESH_ENV);

            sysID = checkNumeric(ConfKeys.KEYS_EVENTMESH_SYSID);

            eventMeshCluster = checkNotEmpty(ConfKeys.KEYS_EVENTMESH_SERVER_CLUSTER);

            eventMeshName = checkNotEmpty(ConfKeys.KEYS_EVENTMESH_SERVER_NAME);

            eventMeshIDC = checkNotEmpty(ConfKeys.KEYS_EVENTMESH_IDC);

            eventMeshServerIp = get(ConfKeys.KEYS_EVENTMESH_SERVER_HOST_IP, IPUtils::getLocalAddress);

            eventMeshConnectorPluginType = checkNotEmpty(ConfKeys.KEYS_ENENTMESH_CONNECTOR_PLUGIN_TYPE);

            eventMeshServerSecurityEnable = Boolean.parseBoolean(get(ConfKeys.KEYS_EVENTMESH_SECURITY_ENABLED, () -> "false"));

            eventMeshSecurityPluginType = checkNotEmpty(ConfKeys.KEYS_ENENTMESH_SECURITY_PLUGIN_TYPE);

            eventMeshServerRegistryEnable = Boolean.parseBoolean(get(ConfKeys.KEYS_EVENTMESH_REGISTRY_ENABLED, () -> "false"));

            eventMeshRegistryPluginType = checkNotEmpty(ConfKeys.KEYS_ENENTMESH_REGISTRY_PLUGIN_TYPE);

            String metricsPluginType = configurationWrapper.getProp(ConfKeys.KEYS_EVENTMESH_METRICS_PLUGIN_TYPE);
            if (StringUtils.isNotEmpty(metricsPluginType)) {
                eventMeshMetricsPluginType = Arrays.stream(metricsPluginType.split(","))
                    .filter(StringUtils::isNotBlank)
                    .map(String::trim)
                    .collect(Collectors.toList());
            }

            eventMeshServerTraceEnable = Boolean.parseBoolean(get(ConfKeys.KEYS_EVENTMESH_TRACE_ENABLED, () -> "false"));
            eventMeshTracePluginType = checkNotEmpty(ConfKeys.KEYS_EVENTMESH_TRACE_PLUGIN_TYPE);
        }
    }

    private String checkNotEmpty(String key) {
        String value = configurationWrapper.getProp(key);
        if (value != null) {
            value = StringUtils.deleteWhitespace(value);
        }
        Preconditions.checkState(StringUtils.isNotEmpty(value), key + " is invalidated");
        return value;
    }

    private String checkNumeric(String key) {
        String value = configurationWrapper.getProp(key);
        if (value != null) {
            value = StringUtils.deleteWhitespace(value);
        }
        Preconditions.checkState(StringUtils.isNotEmpty(value) && StringUtils.isNumeric(value), key + " is invalidated");
        return value;
    }

    private String get(String key, Supplier<String> defaultValueSupplier) {
        String value = configurationWrapper.getProp(key);
        if (value != null) {
            value = StringUtils.deleteWhitespace(value);
        }
        return StringUtils.isEmpty(value) ? defaultValueSupplier.get() : value;
    }

    static class ConfKeys {
        public static String KEYS_EVENTMESH_ENV = "eventMesh.server.env"; //eventMeshServerEnv

        public static String KEYS_EVENTMESH_IDC = "eventMesh.server.idc"; //eventMeshServerIDC

        public static String KEYS_EVENTMESH_SYSID = "eventMesh.sysid"; //eventMeshSysid

        public static String KEYS_EVENTMESH_SERVER_CLUSTER = "eventMesh.server.cluster"; //eventMeshServerCluster

        public static String KEYS_EVENTMESH_SERVER_NAME = "eventMesh.server.name"; //eventMeshServerName

        public static String KEYS_EVENTMESH_SERVER_HOST_IP = "eventMesh.server.hostIp"; //eventMeshServerHostip

        public static String KEYS_EVENTMESH_SERVER_REGISTER_INTERVAL =
                "eventMesh.server.registry.registerIntervalInMills"; //eventMeshServerRegistryRegisterintervalinmills

        public static String KEYS_EVENTMESH_SERVER_FETCH_REGISTRY_ADDR_INTERVAL =
                "eventMesh.server.registry.fetchRegistryAddrIntervalInMills";

        public static String KEYS_ENENTMESH_CONNECTOR_PLUGIN_TYPE = "eventMesh.connector.plugin.type";

        public static String KEYS_EVENTMESH_SECURITY_ENABLED = "eventMesh.server.security.enabled";

        public static String KEYS_ENENTMESH_SECURITY_PLUGIN_TYPE = "eventMesh.security.plugin.type";

        public static String KEYS_EVENTMESH_REGISTRY_ENABLED = "eventMesh.server.registry.enabled";

        public static String KEYS_ENENTMESH_REGISTRY_PLUGIN_TYPE = "eventMesh.registry.plugin.type";

        public static String KEYS_EVENTMESH_METRICS_PLUGIN_TYPE = "eventMesh.metrics.plugin";

        public static String KEYS_EVENTMESH_TRACE_ENABLED = "eventMesh.server.trace.enabled";

        public static String KEYS_EVENTMESH_TRACE_PLUGIN_TYPE = "eventMesh.trace.plugin";
    }
}