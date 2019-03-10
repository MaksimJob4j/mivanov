package ru.job4j.ood.products;

import org.junit.Test;
import ru.job4j.ood.products.storage.*;
import ru.job4j.ood.products.food.Bread;
import ru.job4j.ood.products.food.Milk;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ControlQualityTest {

    @Test
    public void redistributeProductsTest() {
        LocalDateTime now = LocalDateTime.now();
        Milk milk1 = new Milk("milk-warehouse", now.minusDays(10), now.plusDays(100));
        Milk milk2 = new Milk("milk-shop", now.minusDays(10), now.plusDays(10));
        Milk milk3 = new Milk("milk-shop", now.minusDays(10), now.plusDays(10));
        Milk milk4 = new Milk("milk-shop-discount", now.minusDays(10), now.plusDays(1));
        Milk milk5 = new Milk("milk-trash", now.minusDays(10), now.minusDays(1));
        Milk milk6 = new Milk("milk-trash", now.minusDays(10), now.minusDays(1));
        Bread bread1 = new Bread("bread-warehouse", now.minusDays(10), now.plusDays(100));
        Bread bread2 = new Bread("bread-shop", now.minusDays(10), now.plusDays(10));
        Bread bread3 = new Bread("bread-shop-discount", now.minusDays(10), now.plusDays(1));
        Bread bread4 = new Bread("bread-shop-discount", now.minusDays(10), now.plusDays(1));
        Bread bread5 = new Bread("bread-trash", now.minusDays(10), now.minusDays(1));
        Bread bread6 = new Bread("bread-trash", now.minusDays(10), now.minusDays(1));

        Trash trash = new Trash();
        Shop shop = new Shop(trash, 0.25, 0.75, 0.2);
        Warehouse warehouse = new Warehouse(shop, 0.25);

        ControlQuality controlQuality = new ControlQuality(warehouse);

        controlQuality.redistributeProducts(milk1, now);
        controlQuality.redistributeProducts(milk2, now);
        controlQuality.redistributeProducts(milk3, now);
        controlQuality.redistributeProducts(milk4, now);
        controlQuality.redistributeProducts(milk5, now);
        controlQuality.redistributeProducts(milk6, now);
        controlQuality.redistributeProducts(bread1, now);
        controlQuality.redistributeProducts(bread2, now);
        controlQuality.redistributeProducts(bread3, now);
        controlQuality.redistributeProducts(bread4, now);
        controlQuality.redistributeProducts(bread5, now);
        controlQuality.redistributeProducts(bread6, now);

        assertEquals(2, warehouse.getFoods().size());
        assertEquals(6, shop.getFoods().size());
        assertEquals(4, trash.getFoods().size());

        assertThat(milk1.getDiscount(), is(0.0));
        assertThat(milk2.getDiscount(), is(0.0));
        assertThat(milk3.getDiscount(), is(0.0));
        assertThat(milk4.getDiscount(), is(0.2));
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