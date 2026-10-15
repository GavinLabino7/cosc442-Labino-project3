package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.awt.Color;

public class InventoryTest {

    private Inventory inventory;
    private Item item1;
    private Item item2;
    private Item item3;

    @BeforeEach
    void setUp() {
        inventory = new Inventory(3);
        item1 = new Item('^', Color.RED, "Sword", "Iron Sword");
        item2 = new Item('!', Color.GREEN, "Potion", "Healing Potion");
        item3 = new Item('$', Color.YELLOW, "Gold Coin", "Gold Coin");
    }

    @AfterEach
    void tearDown() {
        inventory = null;
        item1 = null;
        item2 = null;
        item3 = null;
    }

    @Test
    void testAdd() {
        inventory.add(item1);
        assertEquals(item1, inventory.get(0));
    }

    @Test
    void testAddMultipleItems() {
        inventory.add(item1);
        inventory.add(item2);

        assertEquals(item1, inventory.get(0));
        assertEquals(item2, inventory.get(1));
    }

    @Test
    void testAddWhenFull() {
        inventory.add(item1);
        inventory.add(item2);
        inventory.add(item2);

        assertEquals(item1, inventory.get(0));
        assertEquals(item2, inventory.get(1));
        assertEquals(item2, inventory.get(2));
        assertTrue(inventory.isFull());

        inventory.add(item3);
        assertFalse(inventory.contains(item3));
        
    }

    @Test
    void testContains() {
        inventory.add(item1);

        assertTrue(inventory.contains(item1));
        assertFalse(inventory.contains(item2));
    }

    @Test
    void testContainsNull() {
        assertTrue(inventory.contains(null));
    }

    @Test
    void testGet() {
        inventory.add(item1);

        assertEquals(item1, inventory.get(0));
    }

    @Test
    void testGetEmptySlot() {
        assertNull(inventory.get(0));
    }

    @Test
    void testGetItems() {
        inventory.add(item1);
        inventory.add(item2);

        Item[] items = inventory.getItems();

        assertEquals(3, items.length);
        assertEquals(item1, items[0]);
        assertEquals(item2, items[1]);
        assertNull(items[2]);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testIsFull(int numberOfItems) {
        for (int i = 0; i < numberOfItems; i++) {
            inventory.add(item1);
        }

        assertEquals(numberOfItems == 3, inventory.isFull());
    }

    @Test
    void testIsFullWhenEmpty() {
        assertFalse(inventory.isFull());
    }

    @Test
    void testRemove() {
        inventory.add(item1);

        inventory.remove(item1);

        assertNull(inventory.get(0));
        assertFalse(inventory.contains(item1));
    }

    @Test
    void testRemoveItemNotInInventory() {
        inventory.add(item1);

        inventory.remove(item2);

        assertTrue(inventory.contains(item1));
    }

    @Test
    void testRemoveOnlyFirstMatchingReference() {
        inventory.add(item1);
        inventory.add(item1);

        inventory.remove(item1);

        assertNull(inventory.get(0));
        assertEquals(item1, inventory.get(1));
    }

    @Test
    void testAddAfterRemove() {
        inventory.add(item1);
        inventory.remove(item1);
        inventory.add(item2);

        assertEquals(item2, inventory.get(0));
    }
}
