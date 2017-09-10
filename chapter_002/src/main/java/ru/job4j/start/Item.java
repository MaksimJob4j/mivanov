package ru.job4j.start;

import java.util.Random;

/**
 * Заявка.
 */
public class Item {
    /**
     * id.
     */
    private final String id;
    /**
     * name.
     */
    private String name;
    /**
     * desc.
     */
    private String desc;
    /**
     * created.
     */
    private long created;
    /**
     * comments.
     */
    private String[] comments;

    /**
     *
     * @param name name.
     * @param desc desc.
     * @param created created.
     */
    public Item(String name, String desc, long created) {
        Random random = new Random();
        this.id = Long.toString(System.currentTimeMillis() + random.nextInt());
        this.name = name;
        this.desc = desc;
        this.created = created;
    }

    /**
     *
     * @return id.
     */
    public String getId() {
        return id;
    }


    /**
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return desc.
     */
    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param desc desc.
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @return created.
     */
    public long getCreated() {
        return created;
    }

    /**
     *
     * @param created created.
     */
    public void setCreated(long created) {
        this.created = created;
    }

    /**
     *
     * @return comments.
     */
    public String[] getComments() {
        return comments;
    }

    /**
     *
     * @param comments comments.
     */
    public void setComments(String[] comments) {
        this.comments = comments;
    }
}

