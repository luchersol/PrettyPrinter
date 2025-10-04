package com.prettyprinter.printer;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.prettyprinter.ObjectUtils;

import de.vandermeer.asciitable.AsciiTable;

public class TablePrinter extends AbstractPrinter {

    private Table table;

    public TablePrinter(Table table) {
        this.table = table;
    }

    public static class Table {

        private List<Map<String, Object>> objects;

        public Table(Collection<Map<String, Object>> objects) {
            this.objects = new ArrayList<>(objects);
        }

        public static Table ofMaps(Collection<Map<String, Object>> objects) {
            return new Table(objects);
        }

        public static Table ofObjects(Collection<Object> objects, boolean withMethod) throws IllegalArgumentException, InvocationTargetException {
            List<Map<String, Object>> list = new ArrayList<>(objects.size());
            for (Object object : objects)
                list.add(ObjectUtils.objectToMap(object, withMethod));
            return new Table(list);
        }

        public static Table ofObjects(Collection<Object> objects) throws IllegalArgumentException, InvocationTargetException {
            return ofObjects(objects, false);
        }

    }

    @Override
    public void display() {
        if (table == null || table.objects.isEmpty()) {
            System.out.println("No data to display.");
            return;
        }

        Set<String> columns = new LinkedHashSet<>();
        for (Map<String, ?> obj : table.objects) {
            columns.addAll(obj.keySet());
        }

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow(columns.toArray());
        at.addRule();

        for (Map<String, ?> obj : table.objects) {
            List<String> row = new ArrayList<>();
            for (String col : columns) {
                Object value = obj.get(col);
                row.add(value == null ? "" : value.toString());
            }
            at.addRow(row);
            at.addRule();
        }

        String rend = at.render();
        println(rend);
    }

    public static void main(String[] args) {
        List<Map<String, Object>> data = new ArrayList<>();

        Map<String, Object> row1 = new LinkedHashMap<>();
        row1.put("ID", 1);
        row1.put("Name", "Laptop with a very long name");
        row1.put("Price", 1200.50);
        data.add(row1);

        Map<String, Object> row2 = new LinkedHashMap<>();
        row2.put("ID", 2);
        row2.put("Name", "Mouse");
        row2.put("Price", 25.99);
        data.add(row2);

        Map<String, Object> row3 = new LinkedHashMap<>();
        row3.put("ID", 3);
        row3.put("Name", "Keyboard");
        row3.put("Price", 45.0);
        data.add(row3);

        new TablePrinter(Table.ofMaps(data)).display();
    }
}
