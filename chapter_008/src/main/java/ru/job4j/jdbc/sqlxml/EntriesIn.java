package ru.job4j.jdbc.sqlxml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "entries")
class EntriesIn {
    private List<EntryIn> entry;

    List<EntryIn> getEntry() {
        return entry;
    }

    @XmlElement
    void setEntry(List<EntryIn> entry) {
        this.entry = entry;
    }

}

class EntryIn {
    private int field;

    int getField() {
        return field;
    }

    @XmlElement
    void setField(int field) {
        this.field = field;
    }

}

