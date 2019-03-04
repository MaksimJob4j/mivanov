package ru.job4j.ood.products;

import org.junit.Test;
import ru.job4j.ood.products.storage.Shop;
import ru.job4j.ood.products.storage.Trash;
import ru.job4j.ood.products.storage.Warehouse;
import ru.job4j.ood.products.food.Bread;
import ru.job4j.ood.products.food.Milk;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ControlQualityTest {

    @Test
    public void redistributeProductsTest() {
        Milk milk1 = new Milk("milk-warehouse", LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(100));
        Milk milk2 = new Milk("milk-shop", LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(10));
        Milk milk3 = new Milk("milk-shop", LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(10));
        Milk milk4 = new Milk("milk-shop-discount", LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(1));
        Milk milk5 = new Milk("milk-trash", LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1));
        Milk milk6 = new Milk("milk-trash", LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1));
        Bread bread1 = new Bread("bread-warehouse", LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(100));
        Bread bread2 = new Bread("bread-shop", LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(10));
        Bread bread3 = new Bread("bread-shop-discount", LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(1));
        Bread bread4 = new Bread("bread-shop-discount", LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(1));
        Bread bread5 = new Bread("bread-trash", LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1));
        Bread bread6 = new Bread("bread-trash", LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1));

        Warehouse warehouse = new Warehouse();
        Shop shop = new Shop();
        Trash trash = new Trash();

        ControlQuality controlQuality = new ControlQuality(warehouse, shop, trash);

        controlQuality.redistributeProducts(milk1, LocalDateTime.now(), 0.3);
        controlQuality.redistributeProducts(milk2, LocalDateTime.now(), 0.3);
        controlQuality.redistributeProducts(milk3, LocalDateTime.now(), 0.3);
        controlQuality.redistributeProducts(milk4, LocalDateTime.now(), 0.3);
        controlQuality.redistributeProducts(milk5, LocalDateTime.now(), 0.3);
        controlQuality.redistributeProducts(milk6, LocalDateTime.now(), 0.3);
        controlQuality.redistributeProducts(bread1, LocalDateTime.now(), 0.2);
        controlQuality.redistributeProducts(bread2, LocalDateTime.now(), 0.2);
        controlQuality.redistributeProducts(bread3, LocalDateTime.now(), 0.2);
        controlQuality.redistributeProducts(bread4, LocalDateTime.now(), 0.2);
        controlQuality.redistributeProducts(bread5, LocalDateTime.now(), 0.2);
        controlQuality.redistributeProducts(bread6, LocalDateTime.now(), 0.2);

        assertEquals(2, warehouse.getFoods().size());
        assertEquals(6, shop.getFoods().size());
        assertEquals(4, trash.getFoods().size());

        assertThat(milk1.getDiscount(), is(0.0));
        assertThat(milk2.getDiscount(), is(0.0));
        assertThat(milk3.getDiscount(), is(0.0));
        assertThat(milk4.getDiscount(), is(0.3));
        assertThat(milk5.getDiscount(), is(0.0));
        assertThat(milk6.getDiscount(), is(0.0));
        assertThat(bread1.getDiscount(), is(0.0));
        assertThat(bread2.getDiscount(), is(0.0));
        assertThat(bread3.getDiscount(), is(0.2));
        assertThat(bread4.getDiscount(), is(0.2));
        assertThat(bread5.getDiscount(), is(0.0));
        assertThat(bread6.getDiscount(), is(0.0));
    }
}