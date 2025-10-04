package com.prettyprinter.format;

import java.util.Collection;
import java.util.Iterator;

public class MultiLineFormatter implements StringFormatter<Collection<?>> {

    @Override
    public String format(Collection<?> collection) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        if (!collection.isEmpty()) {
            sb.append("\n");
            Iterator<?> iterator = collection.iterator();
            while (iterator.hasNext()) {
                Object obj = iterator.next();
                sb.append("\t").append(obj);
                if (iterator.hasNext()) {
                    sb.append(",");
                }
                sb.append("\n");
            }
        }

        sb.append("]");
        return sb.toString();
    }

}
