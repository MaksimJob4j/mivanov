package ru.job4j.ood.products;

import org.junit.Test;
import ru.job4j.ood.products.food.Vegetable;
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

    @Test
    public void redistributeNewConditionsProductsTest() {

        Trash trash = new Trash();
        RecyclingStorage recyclingStorage = new RecyclingStorage(trash);
        Shop shop = new Shop(recyclingStorage, 0.25, 0.75, 0.2);


        LowTemperatureStorage warehouse3 = new LowTemperatureStorage(
                new Warehouse(shop, 0.25), true);
        FoodStorageLimited warehouse2 = new FoodStorageLimited(
                new LowTemperatureStorage(new Warehouse(warehouse3, 0.25), false));
        FoodStorageLimited warehouse1 = new FoodStorageLimited(
                new LowTemperatureStorage(new Warehouse(warehouse2, 0.25), false));
        ControlQuality controlQuality = new ControlQuality(warehouse1);

        LocalDateTime now = LocalDateTime.now();

        Milk milk1 = new Milk("milk-warehouse", now.minusDays(10), now.plusDays(100));
        controlQuality.redistributeProducts(milk1, now);
        assertStorage(warehouse1, 1, 0, 0, 0, 0, 0);
        assertThat(milk1.getDiscount(), is(0.0));

        LowTemperatureFood milk2 = new LowTemperatureFood(
                new Milk("milk-lth", now.minusDays(10), now.plusDays(100)));
        controlQuality.redistributeProducts(milk2, now);
        assertStorage(warehouse1, 1, 0, 1, 0, 0, 0);
        assertThat(milk2.getDiscount(), is(0.0));

        Milk milk3 = new Milk("milk-shop", now.minusDays(10), now.plusDays(10));
        controlQuality.redistributeProducts(milk3, now);
        assertStorage(warehouse1, 1, 0, 1, 1, 0, 0);
        assertThat(milk3.getDiscount(), is(0.0));

        Milk milk4 = new Milk("milk-shop", now.minusDays(10), now.plusDays(10));
        controlQuality.redistributeProducts(milk4, now);
        assertStorage(warehouse1, 1, 0, 1, 2, 0, 0);
        assertThat(milk4.getDiscount(), is(0.0));

        Milk milk5 = new Milk("milk-shop-discount", now.minusDays(10), now.plusDays(1));
        controlQuality.redistributeProducts(milk5, now);
        assertStorage(warehouse1, 1, 0, 1, 3, 0, 0);
        assertThat(milk5.getDiscount(), is(0.2));

        Milk milk6 = new Milk("milk-trash", now.minusDays(10), now.minusDays(1));
        controlQuality.redistributeProducts(milk6, now);
        assertStorage(warehouse1, 1, 0, 1, 3, 0, 1);
        assertThat(milk6.getDiscount(), is(0.0));

        RecyclableFood milk7 = new RecyclableFood(
                new Milk("milk-rc", now.minusDays(10), now.minusDays(1)));
        controlQuality.redistributeProducts(milk7, now);
        assertStorage(warehouse1, 1, 0, 1, 3, 1, 1);
        assertThat(milk7.getDiscount(), is(0.0));

        LowTemperatureFood milk8 = new LowTemperatureFood(
                new Milk("milk-lt", now.minusDays(10), now.plusDays(100)));
        controlQuality.redistributeProducts(milk8, now);
        assertStorage(warehouse1, 1, 0, 2, 3, 1, 1);
        assertThat(milk8.getDiscount(), is(0.0));

        warehouse1.setFull(true);

        Bread bread1 = new Bread("bread-warehouse", now.minusDays(10), now.plusDays(100));
        controlQuality.redistributeProducts(bread1, now);
        assertStorage(warehouse1, 1, 1, 2, 3, 1, 1);
        assertThat(bread1.getDiscount(), is(0.0));

        Bread bread2 = new Bread("bread-shop", now.minusDays(10), now.plusDays(10));
        controlQuality.redistributeProducts(bread2, now);
        assertStorage(warehouse1, 1, 1, 2, 4, 1, 1);
        assertThat(bread2.getDiscount(), is(0.0));

        Bread bread3 = new Bread("bread-shop-discount", now.minusDays(10), now.plusDays(1));
        controlQuality.redistributeProducts(bread3, now);
        assertStorage(warehouse1, 1, 1, 2, 5, 1, 1);
        assertThat(bread3.getDiscount(), is(0.2));

        Bread bread4 = new Bread("bread-shop-discount", now.minusDays(10), now.plusDays(1));
        controlQuality.redistributeProducts(bread4, now);
        assertStorage(warehouse1, 1, 1, 2, 6, 1, 1);
        assertThat(bread4.getDiscount(), is(0.2));

        Bread bread5 = new Bread("bread-trash", now.minusDays(10), now.minusDays(1));
        controlQuality.redistributeProducts(bread5, now);
        assertStorage(warehouse1, 1, 1, 2, 6, 1, 2);
        assertThat(bread5.getDiscount(), is(0.0));

        RecyclableFood bread6 = new RecyclableFood(
                new Bread("bread-rc", now.minusDays(10), now.minusDays(1)));
        controlQuality.redistributeProducts(bread6, now);
        assertStorage(warehouse1, 1, 1, 2, 6, 2, 2);
        assertThat(bread6.getDiscount(), is(0.0));

        Vegetable vegetable = new Vegetable("vegetable", now.minusDays(10), now.plusDays(100));
        controlQuality.redistributeProducts(vegetable, now);
        assertStorage(warehouse1, 1, 1, 3, 6, 2, 2);
        assertThat(vegetable.getDiscount(), is(0.0));

    }

    private void assertStorage(FoodStorage foodStorage, int wh1, int wh2, int lth, int sh, int rc, int tr) {
        assertEquals(wh1, foodStorage.getFoods().size());
        foodStorage = foodStorage.getChainStorage();
        assertEquals(wh2, foodStorage.getFoods().size());
        foodStorage = foodStorage.getChainStorage();
        assertEquals(lth, foodStorage.getFoods().size());
        foodStorage = foodStorage.getChainStorage();
        assertEquals(sh, foodStorage.getFoods().size());
        foodStorage = foodStorage.getChainStorage();
        assertEquals(rc, foodStorage.getFoods().size());
        foodStorage = foodStorage.getChainStorage();
        assertEquals(tr, foodStorage.getFoods().size());
    }
}