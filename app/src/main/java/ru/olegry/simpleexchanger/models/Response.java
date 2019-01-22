package ru.olegry.simpleexchanger.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ValCurs")
public class Response {

    @Attribute(name = "Date")
    private String date;

    @Attribute(name = "name")
    private String name;

    @ElementList(inline = true)
    private List<Currency> currencyList;

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }
}
