package org.clojars.canawar.logback.formatter;

import ch.qos.logback.classic.spi.ILoggingEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Formatter implementation that simply returns the logback message.
 *
 * @author tgoetz
 */
public class MessageFormatter implements Formatter {
    private final String SPACE = " ";
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");

    public String format(ILoggingEvent event) {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(sdf.format(new Date(event.getTimeStamp())));
        sb.append("]");
        sb.append(SPACE);
        sb.append(event.getLevel());
        sb.append(SPACE);
        sb.append(event.getLoggerName());
        sb.append(SPACE);
        sb.append(event.getFormattedMessage());
        sb.append(SPACE);
        return sb.toString();
    }
}
