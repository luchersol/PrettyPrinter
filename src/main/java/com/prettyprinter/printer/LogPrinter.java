package com.prettyprinter.printer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.fusesource.jansi.Ansi.Color;

public class LogPrinter extends TextPrinter {

    private boolean showTime;
    private boolean showDate;
    private boolean showDateTime;

    private String label;
    private int color;
    private String formatDate = "yyyy-MM-dd";
    private String formatTime = "HH:mm:ss.SSS";
    private String formatDateTime = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public enum Level {
        TRACE("TRACE", Color.BLUE),
        DEBUG("DEBUG", Color.YELLOW),
        INFO("INFO", Color.CYAN),
        SUCCESS("SUCCESS", Color.GREEN),
        ERROR("ERROR", Color.RED),
        FATAL("FATAL", Color.MAGENTA);

        private final String label;
        private final Color color;

        Level(String label, Color color) {
            this.label = label;
            this.color = color;
        }

        public String getLabel() {
            return label;
        }

        public Color getColor() {
            return color;
        }
    }


    public LogPrinter(String text) {
        super(text);
    }

    private LogPrinter log(Level level) {
        this.label = level.getLabel();
        this.color = level.getColor().value();
        return this;
    }

    public LogPrinter label(String label) {
        return this;
    }

    public LogPrinter color(Color color) {
        this.color = color.value();
        return this;
    }

    public LogPrinter formatDate(String format) {
        this.formatDate = format;
        return this;
    }

    public LogPrinter formatTime(String format) {
        this.formatTime = format;
        return this;
    }

    public LogPrinter formatDateTime(String format) {
        this.formatDateTime = format;
        return this;
    }

    public LogPrinter deactivateFormatTemporal() {
        this.showDate = false;
        this.showTime = false;
        this.showDateTime = false;
        return this;
    }

    public LogPrinter activateFormatDate() {
        this.showDate = true;
        this.showTime = false;
        this.showDateTime = false;
        return this;
    }

    public LogPrinter activateFormatTime() {
        this.showDate = false;
        this.showTime = true;
        this.showDateTime = false;
        return this;
    }

    public LogPrinter activateFormatDateTime() {
        this.showDate = false;
        this.showTime = false;
        this.showDateTime = true;
        return this;
    }

    public LogPrinter trace()     { return log(Level.TRACE); }
    public LogPrinter debug()     { return log(Level.DEBUG); }
    public LogPrinter info()      { return log(Level.INFO); }
    public LogPrinter success()   { return log(Level.SUCCESS); }
    public LogPrinter error()     { return log(Level.ERROR); }
    public LogPrinter fatal()     { return log(Level.FATAL); }

    @Override
    public void display(){
        StringBuilder first = new StringBuilder("[").append(label);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            showDateTime ? formatDateTime:
            showDate ? formatDate :
            showTime ? formatTime : "");
        if (!formatter.toString().isBlank()) first.append(' ');
        first.append(formatter.format(now));
        first.append("]: ");

        String res = ansi()
                .bold().fg(color)
                .a(first.toString())
                .reset().fg(color)
                .a(this.text)
                .reset()
                .toString();
        println(res);
    }
    public static void main(String[] args) {
        LogPrinter logger = new LogPrinter("mensaje de prueba");

        logger.trace().activateFormatDate().display();
        logger.debug().activateFormatTime().display();
        logger.info().activateFormatDateTime().display();
        logger.success().deactivateFormatTemporal().display();
        logger.error().display();
        logger.fatal().display();
    }
}
