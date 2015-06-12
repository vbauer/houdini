
# Houdini [![Build Status](https://travis-ci.org/vbauer/houdini.svg)](https://travis-ci.org/vbauer/houdini) [![Coverage Status](https://coveralls.io/repos/vbauer/houdini/badge.svg?branch=master)](https://coveralls.io/r/vbauer/houdini?branch=master) [![Maven](https://img.shields.io/github/tag/vbauer/houdini.svg?label=maven)](https://jitpack.io/#vbauer/houdini)

> No performer should attempt to bite off red-hot iron unless he has a good set of teeth.

<img align="right" style="margin-left: 15px" width="300" height="315" src="misc/houdini.png">

**Houdini** is a simple and humane type conversion system, which allows you to prevent a lot of unnecessary code.

For example, when you work with [Spring Type Conversion](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#core-convert) API,
you have to implement each converter as a separate class. It produces a lot of excessive code.
Houdini allows you to aggregate different converters in a single place and re-use common logic without additional classes.

*The project was named in honor of Harry Houdini (born Erik Weisz, later Ehrich Weiss or Harry Weiss) who was a Hungarian-American illusionist and stunt performer, noted for his sensational escape acts.*

See an *[Example](https://github.com/vbauer/houdini#example)* section for a quick start.


## Main features

* Ready-To-Use solution
* Compact and very simple API
* Completely re-usable components
* Direct usage of converters if necessary
* Preventing unnecessary code
* Compatible with:
  * Java 6+ (SE/EE)
  * Android platform
  * [Spring](https://spring.io) 2.x+


## Comparison with other conversion systems (like Spring's Converter)

Usually, each big project with some conversion system (ex. Spring) has the following problems:

**A huge number of converter classes**<br/>
Houdini allows to join several converters into a single bean (unlike Spring converters), so it will minimize the 
number of classes and prevent unnecessary code. For example, you can join converters by functionality or by modules.
It also simplifies source code navigation for developer.
An additional feature is that the smaller class number of classes also decreases compilation time a little bit.

**Duplicated code in converters**<br/>
Some converters could be very similar to other (except filling of some fields).
Using Spring it is most likely to see basic classes or shared components to resolve this situation.
Using Houdini it is unnecessary to create additional classes, you can just put this logic in a new method.

**Conditional converters**<br/>
It is a typical situation for REST services:
* sometimes it is necessary to send full information,
* sometimes short information,
* or need to combine it somehow using conditionals.

Using Spring converters you need to create new POJO and new converters to resolve it.
Houdini allows to use additional conditional parameters. (See an Example section).

**Out of IOC context**<br/>
This is a rare case for JEE developers, but sometimes it's necessary to use some converters without something like [ConversionService](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/core/convert/ConversionService.html).
It could be needed when our code is out of Spring context (ex: shared code for Spring and [GWT](http://www.gwtproject.org) apps).
Using Houdini we could put all needed converters into a single bean and use it like a simple single Java class.


## Setup

### Maven
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.vbauer</groupId>
    <artifactId>houdini</artifactId>
    <version>1.2.0</version>
</dependency>
```

### Gradle
```groovy
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.github.vbauer:houdini:1.2.0'
}
```

### Leiningen
```clojure
:repositories [["jitpack.io" "https://jitpack.io"]]

:dependencies [[com.github.vbauer/houdini "1.2.0"]]
```

## Configuration

**Houdini** doesn't depended on third-party dependencies, but have mechanism to make integration with Spring framework.
It can be used with pure Java or Android projects.

### Java / Android configuration

How to configure Houdini with pure Java:

```java
// Registry all necessary converters:
final ObjectConverterRegistry registry = new ObjectConverterRegistryImpl();

// After that you can specify all needed converters:
// registry.registerConverters(new UserConverter());
// registry.registerConverters(new RoleConverter());
// registry.registerConverters(new CompanyConverter());

// Create service to make conversions (or you can make simple singleton object):
final ObjectConverterService converterService = new ObjectConverterServiceImpl();
```

### Spring configuration

Choose the most appropriate way for you:

* Configure application context using Java code
* Use XML file to configure context

#### Java based configuration

You need to configure 2 beans:
* `ObjectConverterService` which you will use to convert objects
* and `ObjectConverterBeanPostProcessor` which is necessary to detect converters in the Spring context

```java
@Configuration
public class AppContext {
    @Bean
    public ObjectConverterService objectConverterService() {
        return new ObjectConverterServiceImpl();
    }
    @Bean
    public ObjectConverterBeanPostProcessor objectConverterBeanPostProcessor() {
        return new ObjectConverterBeanPostProcessor(objectConverterService());
    }
}
```

You also need to define converter beans in the `AppContext` or using `@ComponentScan` annotation.

#### XML Schema-based configuration

You still need to configure the same 2 beans:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Converter service bean -->
    <bean class="com.github.vbauer.houdini.service.ObjectConverterServiceImpl" />

    <!-- Bean post processor to register converters  -->
    <bean class="com.github.vbauer.houdini.processor.ObjectConverterBeanPostProcessor" />
</beans>
```


## Conversion API

To make new converter, you need to create a new class (or use an existing one) and mark this bean as converter using `@ObjectConverter` annotation.

It is possible to add this annotation on:
* **Bean class** - all public methods from this class will be registered as converters.
* **Bean's public method** - only this method will be registered as converter.

All necessary converters will be registered in bean `ObjectConverterService`.
This service also provides all necessary methods for data conversion:

* Register converter methods.
* Convert some data object (with parameters) to another data object.
* Convert `Set` or `List` of data to `Set`/`List` of data.
* Convert `Set`/`List` of data to one element or `Set`/`List` (it could be useful for some REST API services).

That's all!


## Example

This tiny example shows the power of Houdini: one class, two converters, re-usable logic, no one line of excessive code.

```java
@ObjectConverter
public class UserConverter {

    public UserDTO shortInfo(final User user) {
        return fullInfo(user, false);
    }

    public UserDTO fullInfo(final User user, Boolean full) {
        final UserDTO userDTO = new UserDTO()
            .setId(user.getId())
            .setLogin(user.getLogin());

        if (Boolean.TRUE.equals(full)) {
            userDTO.setPassword(user.getPassword());
        }

        return userDTO;
    
}
```

Converting User domain object to DTO:
```java
final User user = new User()
    .setId(ID)
    .setLogin(LOGIN)
    .setPassword(PASSWORD)

final UserDTO shortUserDTO1 = converterService.convert(UserDTO.class, user);
final UserDTO shortUserDTO2 = converterService.convert(UserDTO.class, user, false);
final UserDTO fullUserDTO = converterService.convert(UserDTO.class, user, true);
```


## Might also like

* [jackdaw](https://github.com/vbauer/jackdaw) - Java Annotation Processor which allows to simplify development.
* [herald](https://github.com/vbauer/herald) - Logging annotation for Spring framework.
* [caesar](https://github.com/vbauer/caesar) - Library that allows to create async beans from sync beans.
* [commons-vfs2-cifs](https://github.com/vbauer/commons-vfs2-cifs) - SMB/CIFS provider for Commons VFS.
* [avconv4java](https://github.com/vbauer/avconv4java) - Java interface to avconv tool.


## License

Copyright 2015 Vladislav Bauer

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
