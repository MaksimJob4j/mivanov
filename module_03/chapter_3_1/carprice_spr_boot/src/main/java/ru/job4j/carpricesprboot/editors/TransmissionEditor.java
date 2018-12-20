package ru.job4j.carpricesprboot.editors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;
import ru.job4j.carpricesprboot.items.description.Transmission;

import java.beans.PropertyEditorSupport;

public class TransmissionEditor extends PropertyEditorSupport {
    private final static Logger LOGGER = LogManager.getLogger(TransmissionEditor.class);

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        LOGGER.traceEntry();
        if (StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
            Transmission transmission = new Transmission();
            transmission.setId(Integer.parseInt(text));
            setValue(transmission);
        }
    }
}
