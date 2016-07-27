# Dynatrace Server SDK

This library provides an easy to use Java implementation of Dynatrace Server REST API.

### Table of Contents
- [Installation](#installation)
    - [Maven](#maven)
    - [Gradle](#gradle)
- [Services](#services)
    - [Test Automation](#testautomation)
    - [Sessions](#sessions)
- [Building](#building) 
    - [Running tests](#tests)

## <a name="installation"></a>Installation
Manual installation is currently required, maven repository might be available in the future.
The wrapper comes with Apache's HttpComponents shaded inside, thus you don't have to worry about library dependencies.

### Maven
- Download the latest artifact from [Releases](/releases)
- Put the artifact under */repo/com/dynatrace/sdk*
- Add the following code to the *&lt;repositories&gt;* section:

```xml
<repository>
    <id>local-repo</id>
    <releases>
        <enabled>true</enabled>
        <checksumPolicy>ignore</checksumPolicy>
    </releases>
    <snapshots>
        <enabled>false</enabled>
    </snapshots>
    <url>file://${project.basedir}/repo</url>
</repository>
```

- Add the following code to the *&lt;dependencies&gt;* section:

```xml
<dependency>
    <groupId>com.dynatrace.sdk</groupId>
    <artifactId>server-rest-sdk</artifactId>
    <version>0.0.1</version>
</dependency>
```

### Gradle
- Download the latest artifact from [Releases](/releases)
- Put the artifact under */lib*
- Put the following code in your *dependencies* block:

```groovy
compile fileTree(dir: 'lib', include: '*.jar')
```

## Services
The SDK is divided into small modules called `services`. Each `service` corresponds to the appropriate wiki entry under *[REST Interfaces](https://community.dynatrace.com/community/display/DOCDT99/REST+Interfaces)* section.
The SDK currently supports a small part of the available interfaces, therefore Pull Requests are highly appreciated.

Each `service` takes a [DynatraceClient](src/main/java/com/dynatrace/sdk/server/DynatraceClient.java) as the only parameter in the constructor.

### Dynatrace Client
The [DynatraceClient](src/main/java/com/dynatrace/sdk/server/DynatraceClient.java) holds the Apache HTTPClient under the hood and therefore it is recommended to pass thesame [DynatraceClient](src/main/java/com/dynatrace/sdk/server/DynatraceClient.java) to all `services` to avoid multiple client creation.

#### Constructing
The [DynatraceClient](src/main/java/com/dynatrace/sdk/server/DynatraceClient.java) can be constructed using two constructors:

```java
public DynatraceClient(ServerConfiguration configuration) {
//...
}
``` 

```java
public DynatraceClient(ServerConfiguration configuration, CloseableHttpClient httpClient) {
//...
}
```

You most likely want to use the first one.

[ServerConfiguration](src/main/java/com/dynatrace/sdk/server/ServerConfiguration.java) is an interface which has a basic implementation: [BasicServerConfiguration](src/main/java/com/dynatrace/sdk/server/BasicServerConfiguration.java)

The most basic use-case would be to create a [DynatraceClient](src/main/java/com/dynatrace/sdk/server/DynatraceClient.java) the following way:

```java
DynatraceClient client = new DynatraceClient(new BasicServerConfiguration("username","password"));
``` 

You may want to take a look at [BasicServerConfiguration](src/main/java/com/dynatrace/sdk/server/BasicServerConfiguration.java) source-code for a list and documentation of other constructors.

### <a name="testautomation"></a>Test Automation

#### Creation

```java
import com.dynatrace.sdk.server.testautomation.TestAutomation;
//...
TestAutomation automation = new TestAutomation(DynatraceClient);
```

#### Wiki entries

- [Test Automation(REST)](https://community.dynatrace.com/community/pages/viewpage.action?pageId=193298719)

### <a name="sessions"></a>Sessions

#### Creation

```java
import com.dynatrace.sdk.server.sessions.Sessions
//...
Sessions sessions = new Sessions(DynatraceClient);
```

#### Wiki entries 

- [Live Sessions(REST)](https://community.dynatrace.com/community/pages/viewpage.action?pageId=175966050)


## Building
In order to build the library, one must execute `mvn clean install` or `mvn clean package`.

### <a name="tests"></a> Running tests
The SDK comes with some unit tests, to run them, execute the following command:
> `mvn clean test`

