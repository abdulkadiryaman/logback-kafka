# logback-kafka

Logback appenders for logging data to Apache Kafka 0.8.0, based on the project at https://github.com/ptgoetz/logback-kafka (which is for Kafka 0.7).


## Maven Dependency
To use logback-kafka in your project add to following to your pom.xml:

```xml
<repository>
  <id>clojars.org</id>
  <url>http://clojars.org/repo</url>
</repository>
...
<dependency>
  <groupId>org.clojars.canawar</groupId>
  <artifactId>logback-kafka</artifactId>
  <version>0.2.11</version>
</dependency>
```

## Configuration

To configure your application to log to kafka, add an appender entry in your logback configuration file, and specify 
a zookeeper host string, and kafka topic name to log to.


```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <appender name="KAFKA"
        class="org.clojars.canawar.logback.KafkaAppender">
        <topic>mytopic</topic>
        <zookeeperHost>localhost:2181</zookeeperHost>
    </appender>
    <root level="debug">
        <appender-ref ref="KAFKA" />
    </root>
</configuration>
```

## Overriding Default Behavior
By default, the Kafka appender will simply write the received log message to the kafka queue. You can override this 
behavior by specifying a custom formatter class:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <appender name="KAFKA"
        class="org.clojars.canawar.logback.KafkaAppender">
        <topic>foo</topic>
        <zookeeperHost>localhost:2181</zookeeperHost>
        <brokerList>localhost:9092</brokerList>
        <!-- specify a custom formatter, JsonFormatter turns regular logs into JSON objects -->
        <formatter class="org.clojars.canawar.logback.formatter.JsonFormatter"></formatter>
    </appender>
    <root level="debug">
        <appender-ref ref="KAFKA" />
    </root>
</configuration>
```



Formatters simply need to implement the `org.clojars.canawar.logback.formatter.Formatter` interface:

```java
package org.clojars.canawar.logback.formatter;

import ch.qos.logback.classic.spi.ILoggingEvent;

public interface Formatter {
    String format(ILoggingEvent event);
}
```

You can find the `ch.qos.logback.classic.spi.ILoggingEvent` javadoc [here](http://logback.qos.ch/apidocs/ch/qos/logback/classic/spi/ILoggingEvent.html).



