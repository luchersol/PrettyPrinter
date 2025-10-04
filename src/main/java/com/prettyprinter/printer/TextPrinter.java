package com.prettyprinter.printer;


public class TextPrinter extends AbstractPrinter {

    protected StringBuilder text;

    public TextPrinter(String text) {
        this.text = new StringBuilder(text);
    }

    public TextPrinter(CharSequence seq) {
        this.text = new StringBuilder(seq);
    }

    public TextPrinter(StringBuilder stringBuilder) {
        this.text = stringBuilder;
    }

    public TextPrinter append(Object obj) {
        this.text.append(obj);
        return this;
    }

    public TextPrinter delete(int start, int end) {
        this.text.delete(start, end);
        return this;
    }

    @Override
    public void display() {
        println(text.toString());
    }
}
