

# Expert System Demo

![Maven](https://img.shields.io/badge/maven-%3E%3D3.8.1-br.svg)
![Java](https://img.shields.io/badge/java-%3E%3D13-brightgreen.svg)
![JUnit](https://img.shields.io/badge/junit5-5.5.2-brightgreen.svg)


## Overview

The code is based on the example of Expert System described by Virginie Mathivet in her book:

    __L'Intelligence Artificielle pour les développeurs__
    __Concepts et implémentations en Java__


## Build the project and launch it

To use Maven the __toolchain.xml__ file must be defined.

Check that if everythink is correct with maven
```
mvn verify
```

Build the jar with the following command:
```
mvn package
```

Launch the demo using the following command:
```
java -jar target/sysexpert-1.0-DEMO.jar -Drules ./src/main/resources/shapes.rules
```

Then some questions in french should be asking to make the program infers the
shape.
