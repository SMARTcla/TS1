package shop;

import org.junit.jupiter.api.Test;
import storage.ItemStock;
import storage.NoItemInStorage;
import storage.Storage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EShopControllerTest {

    @Test
    public void shoppingCartTestBuyingOneItemTest() throws NoItemInStorage {
        EShopController.startEShop();
        StandardItem item = new StandardItem(1, "EPYC 9654", 2000, "GADGETS", 10);

        assertThrows(IllegalArgumentException.class, () -> EShopController.addItemToStorage(item, 0));
        EShopController.addItemToStorage(item, 1);
        ArrayList<ItemStock> itemsFromStorage = new ArrayList<>(EShopController.getItemsFromStorage());

        assertEquals(1, itemsFromStorage.get(0).getCount());
        assertEquals(1, itemsFromStorage.size());
        assertSame(item, itemsFromStorage.get(0).getItem());

        ShoppingCart cart = EShopController.newCart();
        assertEquals(1, EShopController.getCarts().size());
        assertTrue(EShopController.getCarts().contains(cart));
        assertEquals(0, cart.getItemsCount());

        cart.addItem(item);
        assertEquals(1, cart.getItemsCount());
        assertTrue(cart.getCartItems().contains(item));
        EShopController.purchaseShoppingCart(cart, "Kononenko Mikhailo", "Praha 2");

        itemsFromStorage = new ArrayList<>(EShopController.getItemsFromStorage()); // update
        assertEquals(1, itemsFromStorage.size());
        assertEquals(0, itemsFromStorage.get(0).getCount());
        assertEquals(1, EShopController.getArchive().getHowManyTimesHasBeenItemSold(item));
    }


    @Test
    public void shoppingCartTestBuyingWithNoProductInStorage() throws NoItemInStorage {
        EShopController.startEShop();

        // Adding items
        int[] itemCount = {1,1,1,1,1};

        Item[] storageItems = {
            new StandardItem(1, "EPYC 7H12", 12533, "GADGETS", 10),
            new StandardItem(2, "EPYC 9474F", 32412, "GADGETS", 10),
            new StandardItem(3, "Ryzen Threadripper PRO 5995WX", 8800, "TOOLS", 10),
            new StandardItem(4, "Xeon Platinum 8368", 215432, "GADGETS", 5),
            new StandardItem(5, "EPYC 7552", 12531, "TOOLS", 5),
        };

        for (int i = 0; i < storageItems.length; i++) {
            EShopController.addItemToStorage(storageItems[i], itemCount[i]);
        }

        Storage storage = EShopController.getStorage();

        for (Item storageItem : storageItems) {
            assertEquals(1, storage.getItemCount(storageItem));
        }

        ShoppingCart cart = EShopController.newCart();
        assertEquals(1, EShopController.getCarts().size());
        assertTrue(EShopController.getCarts().contains(cart));
        assertEquals(0, cart.getItemsCount());

        for (int j = 0; j < 3; j++) {
            cart.addItem(storageItems[j]);
        }
        assertEquals(3, cart.getItemsCount());

        assertTrue(cart.getCartItems().contains(storageItems[0]));
        assertTrue(cart.getCartItems().contains(storageItems[1]));
        assertTrue(cart.getCartItems().contains(storageItems[2]));

        storage.removeItems(storageItems[1], 1);
        assertEquals(0, storage.getItemCount(storageItems[1]));

        assertThrows(NoItemInStorage.class, () -> EShopController.purchaseShoppingCart(cart, "Kononenko Mikhailo", "Praha 2"));
    }
}
