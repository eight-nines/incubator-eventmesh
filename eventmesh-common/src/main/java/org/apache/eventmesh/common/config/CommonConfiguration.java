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

import java.util.List;

@Config(prefix = "eventMesh")
public class CommonConfiguration {
    @ConfigFiled(field = "sysid")
    public String sysID = "5477";

    @ConfigFiled(field = "server.env")
    public String eventMeshEnv = "P";

    @ConfigFiled(field = "server.idc")
    public String eventMeshIDC = "FT";

    @ConfigFiled(field = "server.name")
    public String eventMeshName = "";

    @ConfigFiled(field = "server.cluster")
    public String eventMeshCluster = "LS";

    @ConfigFiled(field = "server.hostIp")
    public String eventMeshServerIp = null;

    @ConfigFiled(field = "registry.plugin.server-addr")
    public String namesrvAddr = "";


    @ConfigFiled(field = "trace.plugin")
    public String eventMeshTracePluginType;

    @ConfigFiled(field = "metrics.plugin")
    public List<String> eventMeshMetricsPluginType;

    @ConfigFiled(field = "registry.plugin.type")
    public String eventMeshRegistryPluginType = "namesrv";

    @ConfigFiled(field = "security.plugin.type")
    public String eventMeshSecurityPluginType = "security";

    @ConfigFiled(field = "connector.plugin.type")
    public String eventMeshConnectorPluginType = "rocketmq";


    @ConfigFiled(field = "registry.plugin.username")
    public String eventMeshRegistryPluginUsername = "";

    @ConfigFiled(field = "registry.plugin.password")
    public String eventMeshRegistryPluginPassword = "";

    @ConfigFiled(field = "server.registry.registerIntervalInMills")
    public Integer eventMeshRegisterIntervalInMills = 10 * 1000;

    @ConfigFiled(field = "server.registry.fetchRegistryAddrIntervalInMills")
    public Integer eventMeshFetchRegistryAddrInterval = 10 * 1000;


    @ConfigFiled(field = "server.trace.enabled")
    public boolean eventMeshServerTraceEnable = false;

    @ConfigFiled(field = "server.security.enabled")
    public boolean eventMeshServerSecurityEnable = false;

    @ConfigFiled(field = "server.registry.enabled")
    public boolean eventMeshServerRegistryEnable = false;


    @ConfigFiled(field = "server.provide.protocols")
    public List<String> eventMeshProvideServerProtocols;


    @ConfigFiled(reload = true)
    public String eventMeshWebhookOrigin = "eventmesh." + eventMeshIDC;

    public CommonConfiguration() {
    }

    public void reload() {
        this.eventMeshWebhookOrigin = "eventmesh." + eventMeshIDC;
    }

    // todo Adapt the previous code
    public ConfigurationWrapper getConfigurationWrapper() {
        return new ConfigurationWrapper(null, null, true);
    }

    // todo Adapt the previous code
    protected void init() {
    }
}