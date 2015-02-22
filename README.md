
# Houdini [![Build Status](https://travis-ci.org/vbauer/houdini.svg)](https://travis-ci.org/vbauer/houdini)

> No performer should attempt to bite off red-hot iron unless he has a good set of teeth.

<img align="right" style="margin-left: 15px" width="300" height="315" src="misc/houdini.png">

**Houdini** is a simple and humane type conversion system for Spring which allows you to prevent a lot of unnecessary code.

When you have a deal with Spring Conversion API, you have to implement each converter as the separate class. It will produce a lot of excess code.
Houdini allows you to aggregate different converters in the single place and re-use common logic without additional classes.

*Project was named in honor of Harry Houdini (born Erik Weisz, later Ehrich Weiss or Harry Weiss) who was a Hungarian-American illusionist and stunt performer, noted for his sensational escape acts.*


## Main features

* Ready-To-Use solution
* Compact and clean converters
* Completely re-usable components
* Compact and very simple API
* Direct usage of converters if necessary
* Preventing unnecessary code


## Setup

Maven:
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.vbauer</groupId>
    <artifactId>houdini</artifactId>
    <version>1.0.0</version>
</dependency>
```

Gradle:
```groovy
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.github.vbauer:houdini:1.0.0'
}
```

## Example

This tiny examples show the power of Houdini: one class, two converters, re-usable logic, no one line of excess code.

```java
@Component
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

}
```

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
