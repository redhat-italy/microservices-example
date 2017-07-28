# Creating from archetype

```mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate \
  -DarchetypeCatalog=https://maven.repository.redhat.com/ga/io/fabric8/archetypes/archetypes-catalog/2.2.195.redhat-000004/archetypes-catalog-2.2.195.redhat-000004-archetype-catalog.xml \
  -DarchetypeGroupId=org.jboss.fuse.fis.archetypes \
  -DarchetypeArtifactId=spring-boot-camel-xml-archetype \
  -DarchetypeVersion=2.2.195.redhat-000004
  ```

ref: https://access.redhat.com/documentation/en-us/red_hat_jboss_fuse/6.3/html/fuse_integration_services_2.0_for_openshift/spring-boot-image#create_a_spring_boot_project_using_maven_archetype

### Running via an S2I Application Template


# Spring-Boot and Camel XML QuickStart


### Building

The example can be built with

    mvn clean install

### Running the example in OpenShift

### Running via an S2I Application Template

### Integration Testing


