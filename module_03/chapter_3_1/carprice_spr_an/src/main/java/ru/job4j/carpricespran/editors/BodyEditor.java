package ru.job4j.carpricespran.editors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;
import ru.job4j.carpricespran.items.description.Body;

import java.beans.PropertyEditorSupport;

public class BodyEditor extends PropertyEditorSupport {
    private final static Logger LOGGER = LogManager.getLogger(BodyEditor.class);

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        LOGGER.traceEntry();
        if (StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
            Body body = new Body();
            body.setId(Integer.parseInt(text));
            setValue(body);
        }
    }
}
