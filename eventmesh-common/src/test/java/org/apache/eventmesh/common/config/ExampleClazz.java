package org.apache.eventmesh.common.config;

@Config(field = "exampleConfiguration" , prefix = "")
public class ExampleClazz {

    @ConfigFiled(field = "eventMesh.server.env")
    String exampleConfiguration;

    public ExampleClazz() {
    }

    public ExampleClazz(String exampleConfiguration) {
        this.exampleConfiguration = exampleConfiguration;
    }

    public String getExampleConfiguration() {
        return exampleConfiguration;
    }

    public void setExampleConfiguration(String exampleConfiguration) {
        this.exampleConfiguration = exampleConfiguration;
    }
}
