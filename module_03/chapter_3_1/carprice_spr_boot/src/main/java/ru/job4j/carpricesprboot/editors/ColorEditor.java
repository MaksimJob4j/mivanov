package ru.job4j.carpricesprboot.editors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;
import ru.job4j.carpricesprboot.items.description.Color;

import java.beans.PropertyEditorSupport;

public class ColorEditor extends PropertyEditorSupport {
    private final static Logger LOGGER = LogManager.getLogger(ColorEditor.class);

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        LOGGER.traceEntry();
        if (StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
            Color color = new Color();
            color.setId(Integer.parseInt(text));
            setValue(color);
        }
    }
}
