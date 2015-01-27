# XSS Presentation #

A presentation and sample app on XSS defense and mitigation.

## Requirements ##
- Java 8
- Chrome
- Chrome Driver

## Setting up ##
- Make sure Java 8 is first on your classpath.
- Chrome Driver can be installed by brew with `brew install chromedriver`

## Running ##

### Running the Application ###
From the root directory `./gradlew xss-demo-app:run`

### Running the Presentation ###
From the root directory `./gradlew presentation:run`

### Running the Test Suites ###
From the root directory:
- `./gradlew xss-demo-app:test` runs the unit tests
- `./gradlew xss-demo-app:intTest` runs the integration tests
- `./gradlew xss-demo-app:uaTest` runs the user acceptance tests

