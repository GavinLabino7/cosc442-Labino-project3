| **Method / Behavior** | **Valid Case(s)** | **Exception / Invalid Case(s)** | **Boundary Case(s)** | **Oracle / Expected Result** | **Related JUnit Test(s)** |
| --- | --- | --- | --- | --- | --- |
| getSlotIndex(String) | Valid slot codes A, B, C, and D | Null slot code | First slot A and last slot D | Valid codes allow the item to be stored and retrieved from the requested slot; null currently causes a NullPointerException instead of the expected VendingMachineException | testSlotIndex, testSlotIndexNull |
| addItem(VendingMachineItem, String) | Add an item to an empty slot | Add to an occupied slot | Slots A, B, C, and D | Item is stored in the requested slot; adding to an occupied slot throws VendingMachineException | testAddItem |
| getItem(String) | Retrieve an item from an occupied slot | Invalid slot code | Slot A | Correct item is returned from the requested slot; invalid code throws VendingMachineException | testGetItem |
| removeItem(String) | Remove an existing item | Remove from an empty slot | Remove from slot A; remove the same slot twice | Existing item is returned and removed; removing from an empty slot throws VendingMachineException | testRemoveItem |
| getName() | Retrieve an item's name | N/A | N/A | Returns exactly the name supplied to the constructor | testGetName |
| getPrice() | Retrieve an item's price | N/A | Positive price of 1.50 | Returns exactly the price supplied to the constructor | testGetPrice |
| insertMoney(double) | Insert 5.00 | Negative amount | 0.00 | Positive money increases the balance; negative money throws VendingMachineException; the test expects 0.00 to be accepted according to the documented precondition | testInsertMoney |
| getBalance() | Get the current balance | N/A | Initial balance of 0.00 | Returns the current balance | testGetBalance |
| makePurchase(String) — successful purchase | Enough money and item exists | N/A | Exact price of item1 (1.50) | Returns true when there is enough money and the item exists | testMakePurchase |
| makePurchase(String) — insufficient funds | Item exists but balance is insufficient | Insufficient balance for item2 | Balance of 0.00 for a 5.00 item | Returns false when the balance is insufficient | testMakePurchase |
| makePurchase(String) — empty slot | Attempt purchase from an empty slot | Empty slot | Newly constructed machine has empty slot C | Returns false when the selected slot is empty | testMakePurchase |
| returnChange() | Return a positive balance | N/A | Balance of 5.00 | Returns the current balance | testReturnChange |