package ru.olegry.simpleexchanger.data.network;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

public class Parser {

    private Serializer serializer = new Persister(new Format("<?xml version=\"1.0\" encoding= \"UTF-8\" ?>"));

    <T> T parse(String xml, Class<T> clazz) throws Exception {
        return serializer.read(clazz, xml);
    }
}
