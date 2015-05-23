# fancySorty [![Build Status](https://travis-ci.org/sivakumar-kailasam/fancySorty.svg?branch=master)](https://travis-ci.org/sivakumar-kailasam/fancySorty)

### How to run this app
* The *properties.gradle* file contains the parameters to run the app
* To save you/me the trouble of generating a huge file that'd contain lot of numbers there's a handy command `./gradlew createSampleInput` or `./gradlew cSI` which will generate a file of about *14GB* (based on the input file property in the `properties.gradle` file)
* Once the input file is in place run `./gradlew jT`  or `./gradlew judgementTime`. This will create the output file which the N highest nos in descending order.



### How do I import this project into my editor
* If you're like me and like intelliJ then run `./gradlew idea` to generate intelliJ project files
* I liked eclipse once, if you still like it then run `./gradlew eclipse` to generate eclipse project files



### Run tests and see coverage reports
This project uses JaCoCo for coverage. Run `./gradlew check jacocoTestReport` and the generated report can be accessed at **./build/reports/jacoco/test/html/com.sk.sortomatic/index.html**
