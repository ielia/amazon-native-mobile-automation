# Amazon Native Mobile Automation... and more

## Running
Remember to add the following JVM option:
```shell
--add-opens=java.base/java.lang=ALL-UNNAMED
```
There is a bug preventing me from adding a `modules-info.java` file with this specification.
For more information, please visit https://stackoverflow.com/questions/74892632/compilation-error-with-maven-on-intellij-module-not-found-processed-jcommander
