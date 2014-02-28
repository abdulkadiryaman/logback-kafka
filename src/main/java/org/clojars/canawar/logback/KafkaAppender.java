package org.clojars.canawar.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.clojars.canawar.logback.formatter.Formatter;
import org.clojars.canawar.logback.formatter.MessageFormatter;

import java.util.Properties;

public class KafkaAppender extends AppenderBase<ILoggingEvent> {

    private String topic;
    private String brokerList;
    private Producer<String, String> producer;
    private Formatter formatter;
    private String pattern;


    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBrokerList() {
        return brokerList;
    }

    public void setBrokerList(String brokerList) {
        this.brokerList = brokerList;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void start() {
        if (this.formatter == null) {
            this.formatter = new MessageFormatter();
        }

        super.start();

        Properties props = new Properties();
        props.put("metadata.broker.list", this.brokerList);
        props.put("producer.type", "async");
        props.put("request.required.acks", "1");
        props.put("compression.codec", "snappy");
        props.put("topic.metadata.refresh.interval.ms", "60000");
        props.put("serializer.class", "kafka.serializer.StringEncoder");

        ProducerConfig config = new ProducerConfig(props);

        this.producer = new Producer<>(config);
    }

    @Override
    public void stop() {
        super.stop();
        this.producer.close();
    }

    @Override
    protected void append(ILoggingEvent event) {
        String payload = this.formatter.format(event);
        System.out.println("Payload : " + payload);
        KeyedMessage<String, String> data = new KeyedMessage<>(this.topic, payload);
        this.producer.send(data);
    }

}
