import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** JUnit 5 tests for VendingMachineItem, VendingMachineException, and VendingMachine. */
public class VendingMachineTest1 {

    @Test
    void itemConstructorStoresNameAndPrice() {
        VendingMachineItem item = new VendingMachineItem("Coke", 1.50);
        assertEquals("Coke", item.getName());
        assertEquals(1.50, item.getPrice(), 0.000001);
    }

    @Test
    void itemAllowsZeroPrice() {
        VendingMachineItem item = new VendingMachineItem("Free Item", 0.0);
        assertEquals("Free Item", item.getName());
        assertEquals(0.0, item.getPrice(), 0.000001);
    }

    @Test
    void itemRejectsNegativePrice() {
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> new VendingMachineItem("Coke", -1.00));
        assertEquals("Price cannot be less than zero", exception.getMessage());
    }

    @Test
    void exceptionDefaultConstructorWorks() {
        VendingMachineException exception = new VendingMachineException();
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void machineStartsEmptyWithZeroBalance() {
        VendingMachine machine = new VendingMachine();
        assertEquals(0.0, machine.getBalance(), 0.000001);
        assertNull(machine.getItem("A"));
        assertNull(machine.getItem("B"));
        assertNull(machine.getItem("C"));
        assertNull(machine.getItem("D"));
    }

    @Test
    void addItemPutsItemInCorrectSlot() {
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Coke", 1.50);
        machine.addItem(item, "B");
        assertSame(item, machine.getItem("B"));
        assertNull(machine.getItem("A"));
    }

    @Test
    void addItemRejectsOccupiedSlot() {
        VendingMachine machine = new VendingMachine();
        machine.addItem(new VendingMachineItem("Coke", 1.50), "A");

        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> machine.addItem(new VendingMachineItem("Pepsi", 1.50), "A"));
        assertEquals("Slot A already occupied", exception.getMessage());
    }

    @Test
    void addItemRejectsInvalidCode() {
        VendingMachine machine = new VendingMachine();
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> machine.addItem(new VendingMachineItem("Coke", 1.50), "E"));
        assertEquals("Invalid code for vending machine item", exception.getMessage());
    }

    @Test
    void removeItemReturnsItemAndEmptiesSlot() {
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Coke", 1.50);
        machine.addItem(item, "C");

        assertSame(item, machine.removeItem("C"));
        assertNull(machine.getItem("C"));
    }

    @Test
    void removeItemRejectsEmptySlot() {
        VendingMachine machine = new VendingMachine();
        VendingMachineException exception = assertThrows(
                VendingMachineException.class, () -> machine.removeItem("A"));
        assertEquals("Slot A is empty -- cannot remove item", exception.getMessage());
    }

    @Test
    void removeItemRejectsInvalidCode() {
        VendingMachine machine = new VendingMachine();
        VendingMachineException exception = assertThrows(
                VendingMachineException.class, () -> machine.removeItem("E"));
        assertEquals("Invalid code for vending machine item", exception.getMessage());
    }

    @Test
    void insertMoneyIncreasesBalance() {
        VendingMachine machine = new VendingMachine();
        machine.insertMoney(1.00);
        machine.insertMoney(2.50);
        assertEquals(3.50, machine.getBalance(), 0.000001);
    }

    @Test
    void insertZeroMoneyLeavesBalanceUnchanged() {
        VendingMachine machine = new VendingMachine();
        machine.insertMoney(0.0);
        assertEquals(0.0, machine.getBalance(), 0.000001);
    }

    @Test
    void insertMoneyRejectsNegativeAmount() {
        VendingMachine machine = new VendingMachine();
        VendingMachineException exception = assertThrows(
                VendingMachineException.class, () -> machine.insertMoney(-1.00));
        assertEquals("Invalid amount.  Amount must be >= 0", exception.getMessage());
        assertEquals(0.0, machine.getBalance(), 0.000001);
    }

    @Test
    void makePurchaseSucceedsWhenEnoughMoneyIsInserted() {
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Coke", 1.50);
        machine.addItem(item, "A");
        machine.insertMoney(2.00);

        assertTrue(machine.makePurchase("A"));
        assertEquals(0.50, machine.getBalance(), 0.000001);
        assertNull(machine.getItem("A"));
    }

    @Test
    void makePurchaseSucceedsWithExactAmount() {
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Coke", 1.50);
        machine.addItem(item, "D");
        machine.insertMoney(1.50);

        assertTrue(machine.makePurchase("D"));
        assertEquals(0.0, machine.getBalance(), 0.000001);
        assertNull(machine.getItem("D"));
    }

    @Test
    void makePurchaseFailsWhenNotEnoughMoneyIsInserted() {
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Coke", 1.50);
        machine.addItem(item, "A");
        machine.insertMoney(1.00);

        assertFalse(machine.makePurchase("A"));
        assertEquals(1.00, machine.getBalance(), 0.000001);
        assertSame(item, machine.getItem("A"));
    }

    @Test
    void makePurchaseFailsWhenSlotIsEmpty() {
        VendingMachine machine = new VendingMachine();
        machine.insertMoney(5.00);

        assertFalse(machine.makePurchase("A"));
        assertEquals(5.00, machine.getBalance(), 0.000001);
    }

    @Test
    void makePurchaseRejectsInvalidCode() {
        VendingMachine machine = new VendingMachine();
        assertThrows(VendingMachineException.class, () -> machine.makePurchase("E"));
    }

    @Test
    void returnChangeReturnsBalanceAndResetsItToZero() {
        VendingMachine machine = new VendingMachine();
        machine.insertMoney(3.75);

        assertEquals(3.75, machine.returnChange(), 0.000001);
        assertEquals(0.0, machine.getBalance(), 0.000001);
    }

    @Test
    void returnChangeOnEmptyBalanceReturnsZero() {
        VendingMachine machine = new VendingMachine();
        assertEquals(0.0, machine.returnChange(), 0.000001);
        assertEquals(0.0, machine.getBalance(), 0.000001);
    }
}
