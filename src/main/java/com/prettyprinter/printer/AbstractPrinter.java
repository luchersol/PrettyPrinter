package com.prettyprinter.printer;

import org.fusesource.jansi.Ansi;

import com.prettyprinter.Displayable;
public abstract class AbstractPrinter implements Displayable {

    public static void println(Object x) {
        System.out.println(x);
    }

    public static void println() {
        System.out.println();
    }

    public static void printf(String format, Object args) {
        System.out.printf(format, args);
    }

    public static void print(Object x) {
        System.out.print(x);
    }

    public static void line(String x, int repeat) {
        System.out.println(x.repeat(repeat));
    }

    public static void line(char x, int repeat) {
        System.out.println(String.valueOf(x).repeat(repeat));
    }

    public static void line(int repeat) {
        line("=", repeat);
    }

    public static void line() {
        line(70);
    }

    public static Ansi ansi() {
        return Ansi.ansi();
    }

}
