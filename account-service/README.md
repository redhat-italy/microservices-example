# Spring Boot and Camel REST / SQL QuickStart

This example demonstrates how to use SQL via JDBC along with Camel's REST DSL to expose a RESTful API.

Generated with archetype:

```mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate   -DarchetypeCatalog=https://maven.repository.redhat.com/ga/io/fabric8/archetypes/archetypes-catalog/2.2.195.redhat-000004/archetypes-catalog-2.2.195.redhat-000004-archetype-catalog.xml   -DarchetypeGroupId=org.jboss.fuse.fis.archetypes   -DarchetypeArtifactId=spring-boot-camel-rest-sql-archetype   -DarchetypeVersion=2.2.195.redhat-000004 -s settings.xml```

# Prerequisite

This microservice uses mysql as Database to store accounts. Before importing create the database in the project


### Building

The example can be built with

    mvn clean install

### Running the example in OpenShift

It is assumed that:
- OpenShift platform is already running, if not you can find details how to [Install OpenShift at your site](https://docs.openshift.com/container-platform/3.3/install_config/index.html).
- Your system is configured for Fabric8 Maven Workflow, if not you can find a [Get Started Guide](https://access.redhat.com/documentation/en/red-hat-jboss-middleware-for-openshift/3/single/red-hat-jboss-fuse-integration-services-20-for-openshift/)
- The Red Hat MySQL xPaaS product should already be installed and running on your OpenShift installation, one simple way to run a MySQL service is following the documentation of the MySQL xPaaS image for OpenShift related to the `mysql-ephemeral` template.

The example can then be built and deployed using a single goal:

    $ mvn fabric8:deploy -Dmysql-service-username=<username> -Dmysql-service-password=<password>

The `username` and `password` system properties correspond to the credentials
used when deploying the MySQL database service.


To list all the running pods:

    oc get pods

Then find the name of the pod that runs this quickstart, and output the logs from the running pods with:

    oc logs <name of pod>

You can also use the OpenShift [web console](https://docs.openshift.com/container-platform/3.3/getting_started/developers_console.html#developers-console-video) to manage the
running pods, and view logs and much more.

# Running via an S2I Application Template

Application templates allow you deploy applications to OpenShift by filling out a form in the OpenShift console that allows you to adjust deployment parameters.  This template uses an S2I source build so that it handle building and deploying the application for you.

First, import the Fuse image streams:

    oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/GA/fis-image-streams.json

Then create the quickstart template:

    oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/GA/quickstarts/spring-boot-camel-rest-sql-template.json

Now when you use "Add to Project" button in the OpenShift console, you should see a template for this quickstart. 

## Database and Data setup

This Microservice uses [Spring jdbc](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html) the relevant files are:

* schema-{platform}.sql
* data-{platform}.sql

For further reading you can follow this link: [Spring Boot database initialization guide](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html)

### Base templates

Reference for mysql templates

https://github.com/openshift/origin/tree/master/examples/db-templates

### Accessing the REST service
                   
###

### Insert a demo account

This the json for an account {"id":1,"name":"Jack","surname":"Black"}

bash test: ~/ext/curl-account-sample-json.sh

### Swagger API

The example provides API documentation of the service using Swagger using the _context-path_ `camel-rest-sql/api-doc`. You can access the API documentation from your Web browser at <http://qs-camel-rest-sql.example.com/camel-rest-sql/api-doc>.

