# Dynatrace Server SDK [![Build Status](https://travis-ci.org/Dynatrace/Dynatrace-Server-REST-Java-SDK.svg?branch=master)](https://travis-ci.org/Dynatrace/Dynatrace-Server-REST-Java-SDK)

This library provides an easy to use Java implementation of Dynatrace Server REST API.

### Table of Contents
- [Installation](#installation)
    - [Maven](#maven)
    - [Gradle](#gradle)
- [Services](#services)
    - [Test Automation](#testautomation)
    - [Sessions](#sessions)
    - [System Profiles](#systemprofiles)
    - [Server Management](#servermanagement)
- [Building](#building)
    - [Running tests](#tests)

## <a name="installation"></a>Installation
Library is available in maven central repository (starting with version 7.0.0; for previous versions see the [Releases page](https://github.com/Dynatrace/Dynatrace-Server-REST-Java-SDK/releases)).

### Maven
- Add the following code to the *&lt;dependencies&gt;* section:
```xml
<dependency>
    <groupId>com.dynatrace.sdk</groupId>
    <artifactId>server-rest-sdk</artifactId>
    <version>LATEST_VERSION</version>
</dependency>
```

### Gradle
- Put the following code in your *dependencies* block:
```groovy
compile 'com.dynatrace.sdk:server-rest-sdk:LATEST_VERSION'
```

## Services
The SDK is divided into small modules called `services`. Each `service` corresponds to the appropriate Rest API endpoint. Documentation of Rest API endpoints can be found on your local Dynatrace Server: [https://DTSERVER:8021/api-docs/current/](https://localhost:8021/api-docs/current/).
General documentation for Server Rest interfaces is available [here](https://www.dynatrace.com/support/doc/appmon/appmon-reference/rest-interfaces/server-rest-interfaces/).

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

### <a name="sessions"></a>Sessions

#### Creation

```java
import com.dynatrace.sdk.server.sessions.Sessions
//...
Sessions sessions = new Sessions(DynatraceClient);
```

### <a name="systemprofiles"></a>System Profiles

#### Creation

```java
import com.dynatrace.sdk.server.systemprofiles.SystemProfiles
//...
SystemProfiles systemProfiles = new SystemProfiles(DynatraceClient);
```

### <a name="servermanagement"></a>Server Management

#### Creation

```java
import com.dynatrace.sdk.server.servermanagement.ServerManagement
//...
ServerManagement serverManagement = new ServerManagement(DynatraceClient);
```

## Building
In order to build the library, one must execute `mvn clean install` or `mvn clean package`.

### <a name="tests"></a> Running tests
The SDK comes with some unit tests, to run them, execute the following command:
> `mvn clean test`


