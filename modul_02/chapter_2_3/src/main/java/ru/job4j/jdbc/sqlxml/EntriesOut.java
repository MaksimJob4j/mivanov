package ru.job4j.jdbc.sqlxml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "entries")
class EntriesOut {

    private List<EntryOut> entry;

    public List<EntryOut> getEntry() {
        return entry;
    }

    @XmlElement
    public void setEntry(List<EntryOut> entry) {
        this.entry = entry;
    }

    long sumAttributes() {
        long sum = 0L;
        for (EntryOut ent : entry) {
            sum += ent.field;
        }
        return sum;
    }
}

class EntryOut {
    int field;

    public int getField() {
        return field;
    }

    @XmlAttribute
    public void setField(int field) {
        this.field = field;
    }
}
