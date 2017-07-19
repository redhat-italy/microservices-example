# Account Service with CXF JAXRS MongoDB

This service is a spring boot application responsible for providing up to date company and ticker/quote information. 

It does this by providing a REST api with 2 calls:

* GET /quotes?q={symbol1,symbol2,etc} 

eg:

{"terms":["Apple","RHT"]}

That needs to be encoded:

%7B%22terms%22%3A%5B%22Apple%22%2C%22RHT%22%5D%7D%0A

Returns as up to date quote for the given symbol(s):

[{"symbol":"AMZN","name":"Amazon","lastTradeValue":1010.0,"id":"596e22d76b1cfeb045a5c88c"},{"symbol":"RHT","name":"Red Hat","lastTradeValue":100.0,"id":"596e22d76b1cfeb045a5c88a"}]
* GET /company/{search} 

Returns a list of companies that have the search parameter in their terms or symbols.

For this implementation a static data set is stored on Mongo DB .

This implementation demonstrates how you can use Apache CXF with Spring Boot and connection to MongoDB.

The quickstart uses Spring Boot to configure a little application that includes a CXF JAXRS endpoint with Swagger enabled.

## Components

[Spring data MongoDB](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-nosql.html#boot-features-mongodb)


## Setup MongoDB

If you start mongo with --rest parameter you can see the database content calling the URL:

http://127.0.0.1:28017/test/quote/

eg:

```
{
  "offset" : 0,
  "rows": [
    { "_id" : { "$oid" : "596e22d76b1cfeb045a5c88a" }, "_class" : "com.redhat.showcase.quote.model.Quote", "symbol" : "RHT", "name" : "Red Hat", "lastTradeValue" : 100 } ,
    { "_id" : { "$oid" : "596e22d76b1cfeb045a5c88b" }, "_class" : "com.redhat.showcase.quote.model.Quote", "symbol" : "AAPL", "name" : "Apple", "lastTradeValue" : 150 } ,
    { "_id" : { "$oid" : "596e22d76b1cfeb045a5c88c" }, "_class" : "com.redhat.showcase.quote.model.Quote", "symbol" : "AMZN", "name" : "Amazon", "lastTradeValue" : 1010 } ,
    { "_id" : { "$oid" : "596e22d76b1cfeb045a5c88d" }, "_class" : "com.redhat.showcase.quote.model.Quote", "symbol" : "GOOGL", "name" : "Google", "lastTradeValue" : 900 }
  ],

  "total_rows" : 4 ,
  "query" : {} ,
  "millis" : 0
}
```

Starting mongo with --auth needs to setup users:

## Admin user

Procedure

Start MongoDB without access control:

```mongod --port 27017 [... other params...]```

Connect to the instance.

```mongo --port 27017```

Create the user administrator.

```
use admin
db.createUser(
  {
    user: "mongouser",
    pwd: "mongouser",
    roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
  }
)
```

Re-start the MongoDB instance with access control.

```mongod --auth --port 27017 [... other params...]```

Authenticate as the user administrator.

```mongo --port 27017 -u "myUserAdmin" -p "abc123" --authenticationDatabase "admin" [... other params...]```


## db user
```
use test
db.createUser(
   {
     user: "mongouser",
     pwd: "mongouser",
     roles: [ "readWrite", "dbAdmin" ]
   }
)
```



### Building

The example can be built with

    mvn clean install
    
### Running locally
    
If you run from the project ```quote-service``` dir:
    
```mvn clean spring-boot:run -s ../settings.xml```
    
You will start with applicaton-dev.yaml environment, you will need an existing MongoDB running.     
    

### Running the example in OpenShift

It is assumed that:
- OpenShift platform is already running, if not you can find details how to [Install OpenShift at your site](https://docs.openshift.com/container-platform/3.3/install_config/index.html).
- Your system is configured for Fabric8 Maven Workflow, if not you can find a [Get Started Guide](https://access.redhat.com/documentation/en/red-hat-jboss-middleware-for-openshift/3/single/red-hat-jboss-fuse-integration-services-20-for-openshift/)

Then the following command will package your app and run it on OpenShift:

    mvn fabric8:deploy

To list all the running pods:

    oc get pods

Then find the symbol of the pod that runs this quickstart, and output the logs from the running pods with:

    oc logs <symbol of pod>

You can also use the OpenShift [web console](https://docs.openshift.com/container-platform/3.3/getting_started/developers_console.html#developers-console-video) to manage the running pods, and view logs and much more.

### Running via an S2I Application Template

Application templates allow you deploy applications to OpenShift by filling out a form in the OpenShift console that allows you to adjust deployment parameters.  This template uses an S2I source build so that it handle building and deploying the application for you.

First, import the Fuse image streams:

    oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/GA/fis-image-streams.json

Then create the quote-service template:

    oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/GA/quickstarts/spring-boot-cxf-jaxrs-template.json

Now when you use "Add to Project" button in the OpenShift console, you should see a template for this quickstart. 


### Endpoints    

