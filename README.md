

# Simple Expert System

![Maven](https://img.shields.io/badge/maven-%3E%3D3.8.1-br.svg)
![Java](https://img.shields.io/badge/java-%3E%3D11-brightgreen.svg)
![JUnit](https://img.shields.io/badge/junit5-5.5.2-brightgreen.svg)

## Overview

The code is based on the example provided by Virginie Mathivet from it's book:

    __L'Intelligence Artificielle pour les développeurs__
    __Concepts et implémentations en Java__


## Build the project and launch it

Check that everythink is ok with maven

```
mvn verify
```


Build the jar with the following command:

```
mvn package
java -jar target/sysexpert-1.0-DEMO.jar -Drules ./src/main/resources/shapes.rules
```

So you will have some questions (in french) for which the program should
guess a shape 
