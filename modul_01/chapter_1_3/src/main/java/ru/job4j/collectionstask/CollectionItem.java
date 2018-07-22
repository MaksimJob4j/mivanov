package ru.job4j.collectionstask;

import java.util.Collection;
import java.util.Comparator;

/**
 * .
 */
public class CollectionItem {
    /**
     * .
     */
    private Collection<String> collection;
    /**
     * .
     */
    private Long timeAdd;

    /**
     * .
     * @return collection.
     */
    public Collection<String> getCollection() {
        return collection;
    }

    /**
     * .
     * @param collection collection.
     */
    public void setCollection(Collection<String> collection) {
        this.collection = collection;
    }

    /**
     * .
     * @return time.
     */
    public Long getTimeAdd() {
        return timeAdd;
    }

    /**
     * .
     * @param timeAdd timeAdd.
     */
    public void setTimeAdd(Long timeAdd) {
        this.timeAdd = timeAdd;
    }

    /**
     * .
     * @return time.
     */
    public Long getTimeDel() {
        return timeDel;
    }

    /**
     * .
     * @param timeDel timeDel.
     */
    public void setTimeDel(Long timeDel) {
        this.timeDel = timeDel;
    }

    /**
     * .
     * @return comparator.
     */
    public static Comparator<CollectionItem> getAddComparator() {
        return addComparator;
    }

    /**
     * .
     * @param addComparator addComparator.
     */
    public static void setAddComparator(Comparator<CollectionItem> addComparator) {
        CollectionItem.addComparator = addComparator;
    }

    /**
     * .
     * @return comparator.
     */
    public static Comparator<CollectionItem> getDelComparator() {
        return delComparator;
    }

    /**
     * .
     * @param delComparator delComparator.
     */
    public static void setDelComparator(Comparator<CollectionItem> delComparator) {
        CollectionItem.delComparator = delComparator;
    }

    /**
     * .
     */
    private Long timeDel;

    /**
     * .
     * @param collection collection.
     */
    CollectionItem(Collection<String> collection) {
        this.collection = collection;
    }

    /**
     * .
     */
    private static Comparator<CollectionItem> addComparator = new Comparator<CollectionItem>() {
        @Override
        public int compare(CollectionItem o1, CollectionItem o2) {
            return (int) (o1.timeAdd - o2.timeAdd);
        }
    };

    /**
     * .
     */
    private static Comparator<CollectionItem> delComparator = new Comparator<CollectionItem>() {
        @Override
        public int compare(CollectionItem o1, CollectionItem o2) {
            return (int) (o1.timeDel - o2.timeDel);
        }
    };


}
