package archive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import shop.Item;
import shop.Order;
import shop.StandardItem;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PurchasesArchiveTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private StandardItem mockedItem;
    private Order mockedOrder;
    private ItemPurchaseArchiveEntry mockedItemPurchaseArchiveEntry;
    private HashMap<Integer, ItemPurchaseArchiveEntry> itemArchive;
    private ArrayList<Order> orderArchive;


    @BeforeEach
    public void initTest() {
        mockedItem = Mockito.mock(StandardItem.class);
        mockedItemPurchaseArchiveEntry = Mockito.mock(ItemPurchaseArchiveEntry.class);
        mockedOrder = Mockito.mock(Order.class);
        orderArchive = new ArrayList<>();
        itemArchive = new HashMap<>();
        ArrayList<Item> items = new ArrayList<>();
        items.add(mockedItem);

        Mockito.when(mockedItem.getID()).thenReturn(1);

        Mockito.when(mockedOrder.getItems()).thenReturn(items);

        Mockito.when(mockedItemPurchaseArchiveEntry.getCountHowManyTimesHasBeenSold()).thenReturn(1);
        itemArchive.put(1, mockedItemPurchaseArchiveEntry);

        orderArchive.add(mockedOrder);
    }

    @Test
    public void printItemPurchaseStatisticsEmptyTest() {

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        PurchasesArchive emptyPurchasesArchive = new PurchasesArchive();
        String expected = "ITEM PURCHASE STATISTICS:" + System.lineSeparator();

        emptyPurchasesArchive.printItemPurchaseStatistics();

        assertEquals(expected, outContent.toString());

        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void printItemPurchaseStatisticsOneItemTest() {

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        Mockito.when(mockedItemPurchaseArchiveEntry.toString()).thenReturn("Some string");
        itemArchive.put(1, mockedItemPurchaseArchiveEntry);
        PurchasesArchive purchasesArchive = new PurchasesArchive(itemArchive, null);
        String expected = "ITEM PURCHASE STATISTICS:" + System.lineSeparator() + "Some string" + System.lineSeparator();

        purchasesArchive.printItemPurchaseStatistics();

        assertEquals(expected, outContent.toString());

        System.setErr(originalErr);
    }

    @Test
    public void getHowManyTimesHasBeenItemSoldPurchaseEmptyArchiveTest() {

        Mockito.when(mockedItem.getID()).thenReturn(1);
        PurchasesArchive emptyPurchasesArchive = new PurchasesArchive();

        assertEquals(0, emptyPurchasesArchive.getHowManyTimesHasBeenItemSold(mockedItem));
    }

    @Test
    public void getHowManyTimesHasBeenItemSoldTest() {

        Mockito.when(mockedItem.getID()).thenReturn(10);
        Mockito.when(mockedItemPurchaseArchiveEntry.getCountHowManyTimesHasBeenSold()).thenReturn(30);
        itemArchive.put(10, mockedItemPurchaseArchiveEntry);
        PurchasesArchive purchasesArchive = new PurchasesArchive(itemArchive, null);

        assertEquals(30, purchasesArchive.getHowManyTimesHasBeenItemSold(mockedItem));
    }

    @Test
    public void putOrderToPurchasesArchiveExistingItemTest() {

        PurchasesArchive purchasesArchive = new PurchasesArchive(itemArchive, orderArchive);

        purchasesArchive.putOrderToPurchasesArchive(mockedOrder); // Act

        Mockito.verify(mockedItemPurchaseArchiveEntry).increaseCountHowManyTimesHasBeenSold(1);
    }

    @Test
    public void putOrderToPurchasesArchiveNonExistingItemTest() {

        StandardItem newMockedItem = Mockito.mock(StandardItem.class);

        ArrayList<Item> items = new ArrayList<>();
        items.add(newMockedItem);
        Order newMockedOrder = Mockito.mock(Order.class);

        Mockito.when(newMockedItem.getID()).thenReturn(2);
        Mockito.when(newMockedOrder.getItems()).thenReturn(items);

        PurchasesArchive purchasesArchive = new PurchasesArchive(itemArchive, orderArchive);

        purchasesArchive.putOrderToPurchasesArchive(newMockedOrder);

        assertTrue(itemArchive.containsKey(2));
        assertEquals(2, itemArchive.get(2).getRefItem().getID());
        assertEquals(1, itemArchive.get(2).getCountHowManyTimesHasBeenSold());
    }
}
