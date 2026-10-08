import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class VendingMachineTest {

    VendingMachine vendingMachine; 
    VendingMachineItem item1;
    VendingMachineItem item2;
    VendingMachineItem item3;

    @BeforeEach 
    void setUp() {
        vendingMachine = new VendingMachine();
        item1 = new VendingMachineItem("Banana", 1.50);
        item2 = new VendingMachineItem("Poptart", 2.50);
    }

    @AfterEach
    void tearDown() { 
        vendingMachine = null;
        item1 = null;
        item2 = null;
        item3 = null;
    } 

    @Test
    void testGetName() {
        assertEquals("Banana", item1.getName());
    }

    @Test
    void testGetPrice() {
        assertEquals(1.50, item1.getPrice(), 0.01);
    }

    @Test 
    void testVendingMachineItemLessZero(){
        assertThrows(VendingMachineException.class, () -> item3 = new VendingMachineItem("Krabby Patty", -100.00));
        
    }

    @ParameterizedTest 
    @CsvSource ({
        "A", "B", "C", "D"
    }) 
    void testSlotIndex(String Code) {
        vendingMachine.addItem(item1, Code);
        assertEquals(item1, vendingMachine.getItem(Code));
    }

    @Test
    void testSlotIndexNull() {
        vendingMachine.addItem(item1, "A");
        assertThrows(VendingMachineException.class, () -> vendingMachine.getItem(null));
    }

    @Test
    void testAddItem() {
        vendingMachine.addItem(item1, "A");
        assertThrows(VendingMachineException.class, () -> vendingMachine.addItem(item2, "A"));
    }

    @Test
    void testGetBalance() {
         assertEquals(0.0, vendingMachine.getBalance(), 0.01);
    }

    @Test
    void testGetItem() {
        vendingMachine.addItem(item1, "A");
        assertEquals(item1, vendingMachine.getItem("A"));
        assertThrows(VendingMachineException.class, () -> vendingMachine.getItem("E") );

    }

    @Test
    void testInsertMoney() {
        vendingMachine.insertMoney(0.00);
        assertEquals(0.00, vendingMachine.getBalance(), 0.001);

        vendingMachine.insertMoney(5.00); 
        assertEquals(5.00, vendingMachine.getBalance(), 0.01); 

        assertThrows(VendingMachineException.class, () -> vendingMachine.insertMoney(-1.00));
    }

    @Test
    void testMakePurchase() {
        vendingMachine.addItem(item1, "A");
        vendingMachine.addItem(item2, "B");
        vendingMachine.insertMoney(1.50); 

        assertTrue(vendingMachine.makePurchase("A"));

        assertFalse(vendingMachine.makePurchase("C")); 
        assertFalse(vendingMachine.makePurchase("B"));   
    }

    @Test
    void testRemoveItem() {
        vendingMachine.addItem(item1, "A"); 
        assertEquals(item1, vendingMachine.removeItem("A")); 
 
        assertThrows(VendingMachineException.class, () -> vendingMachine.removeItem("A")); 
    }

    @Test
    void testReturnChange() {
        vendingMachine.insertMoney(5.00); 
        assertEquals(5.00, vendingMachine.returnChange(), 0.01); 
    }
}
