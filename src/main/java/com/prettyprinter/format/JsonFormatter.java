package com.prettyprinter.format;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.prettyprinter.ObjectUtils;

public class JsonFormatter implements StringFormatter<Object> {

    private boolean withMethod;

    public JsonFormatter(){

    }

    public JsonFormatter(boolean withMethod){
        this.withMethod = withMethod;
    }

    @Override
    public String format(Object obj) {
        Map<String, Object> map = ObjectUtils.objectToMap(obj, withMethod);
        return mapToPrettyString(map, 0);
    }

    private String mapToPrettyString(Object obj, int indent) {
        StringBuilder sb = new StringBuilder();
        String indentation = "  ".repeat(indent);

        if (obj == null) {
            sb.append("null");
        } else if (obj instanceof Map<?, ?> m) {
            if (m.isEmpty()) {
                sb.append("{}");
            } else {
                sb.append("{\n");
                String joined = m.entrySet().stream()
                        .map(entry -> indentation + "  " + entry.getKey() + ": "
                                    + mapToPrettyString(entry.getValue(), indent + 1))
                        .collect(Collectors.joining(",\n"));
                sb.append(joined).append("\n");
                sb.append(indentation).append("}");
            }
        } else if (obj instanceof List<?> l) {
            if(l.isEmpty()) {
                sb.append("[]");
            } else {
                sb.append("[\n");
                String childIndent = indentation + "  ";
                String joined = l.stream()
                    .map(item -> childIndent + mapToPrettyString(item, indent + 1))
                    .collect(Collectors.joining(",\n"));
                sb.append(joined).append("\n");
                sb.append(indentation).append("]");
            }
        } else if (ObjectUtils.isWrapper(obj) || obj instanceof String) {
            sb.append("\"").append(obj).append("\"");
        } else {
            Map<String, Object> innerObj = ObjectUtils.objectToMap(obj, withMethod);
            sb.append(mapToPrettyString(innerObj, indent));
        }

        return sb.toString();
    }

}
