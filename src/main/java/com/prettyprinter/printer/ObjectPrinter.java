package com.prettyprinter.printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.prettyprinter.format.JsonFormatter;
import com.prettyprinter.format.MultiLineFormatter;
import com.prettyprinter.format.StringFormatter;

/**
 * Al usar clases inaccesibles (List.of() por ejemplo), puede causar errores
 */
public class ObjectPrinter extends AbstractPrinter {

    public Object object;
    public StringFormatter formatter;

    public ObjectPrinter(Object object) {
        this.object = object;
    }

    public ObjectPrinter(Object object, StringFormatter formatter) {
        this.object = object;
        this.formatter = formatter;
    }

    public ObjectPrinter style(StringFormatter formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public void display() {
        println(formatter.format(object));
    }

    private record Person(String name, Map<String, Person> p) {
        public boolean hasName() {
            return this.name != null;
        }
    }

    public static void main(String[] args) {
        // List<Person> list = new ArrayList<>(List.of(new Person("A"), new Person("C"), new Person("C")));
        // ObjectPrinter printer = new ObjectPrinter(
        //     new Person("A",
        //                 new HashMap<>(Map.of(
        //                     "P1", new Person("B", new HashMap<>()),
        //                     "P2", new Person("C", null)
        //                     )
        //                 ))
        //             );
        // printer.object = null;
        ObjectPrinter printer = new ObjectPrinter(
            new ArrayList<>(List.of(
                new Person("A", null),
                new Person("B", null)
                )
            ));
        printer.style(new MultiLineFormatter());
        printer.display();
    }
}
