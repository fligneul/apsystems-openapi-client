# APsystems OpenAPI Java SDK

[![Release](https://img.shields.io/github/v/release/fligneul/apsystems-openapi-client)](https://github.com/fligneul/apsystems-openapi-client/releases)
[![JitPack](https://jitpack.io/v/fligneul/apsystems-openapi-client.svg)](https://jitpack.io/#fligneul/apsystems-openapi-client)
[![Build Status](https://github.com/fligneul/apsystems-openapi-client/actions/workflows/ci.yml/badge.svg)](https://github.com/fligneul/apsystems-openapi-client/actions)
[![License](https://img.shields.io/github/license/fligneul/apsystems-openapi-client)](LICENSE)
![Java Version](https://img.shields.io/badge/Java-11%2B-blue)

A lightweight, Retrofit-based Java library for interacting with the APsystems OpenAPI.
 This SDK provides easy access to solar system details, inverter data, energy summaries, and storage metrics.

## Features

- **Fluent Configuration**: Use `ApSystemsClientBuilder` to set up your credentials.
- **Automatic Authentication**: Handles HMAC-SHA256 signature calculation and header management for every request.
- **Flexible Execution**: Supports both Synchronous and Asynchronous (Callback) patterns via Retrofit `Call<T>`.

## Installation (via JitPack)

Add the JitPack repository to your build file:

### Gradle
```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.fligneul:apsystems-openapi-client:v1.0.0")
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.fligneul</groupId>
    <artifactId>apsystems-openapi-client</artifactId>
    <version>v1.0.0</version>
</dependency>
```

## Quick Start

### Synchronous Usage
```java
ApSystemsClient client = new ApSystemsClientBuilder()
        .appId("YOUR_APP_ID")
        .appSecret("YOUR_APP_SECRET")
        .build();

// Direct data return, throws ApSystemsException on error
SystemDetails details = client.getSystemDetails("YOUR_SID");
System.out.println("Capacity: " + details.getCapacityAsDouble() + " kW");
```

### Asynchronous Usage (CompletableFuture)
```java
ApSystemsAsyncClient asyncClient = new ApSystemsClientBuilder()
        .appId("YOUR_APP_ID")
        .appSecret("YOUR_APP_SECRET")
        .buildAsync();

asyncClient.getSystemDetails("YOUR_SID")
    .thenAccept(details -> System.out.println("Async Capacity: " + details.getCapacityAsDouble()))
    .exceptionally(ex -> {
        ex.printStackTrace();
        return null;
    });
```

## Documentation

For a full list of available endpoints and parameter details, see the [API Reference](API.md).

## Requirements
- Java 11 or higher
- APsystems OpenAPI App ID and App Secret

## License
Apache License 2.0
