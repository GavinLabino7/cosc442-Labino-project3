Inital Coverage Results:

VendingMachine.java:
- Statement 100%
- Branch 100%

VendingMachineItem.java:
- Statement 87.5%
- Branch 50%

<br>

Final Coverage Results:

VendingMachine.java:
- Statement 100%
- Branch 100%

VendingMachineItem.java:
- Statement 100%
- Branch 100%

<br>

I added a test to check if creating an item with a negative price gave the correct exception

<br>

Compound boolean in makePurchase(): 
- The first condition is item != null. In testMakePurchase() this condition is evaluated to true when a purchase is made to a slot with am item in it. It goes to false when a purchase is made to a slot with not item.
- The second condition is this.balance >= item.getPrice(). In testMakePurchase(), it is evaluated to true when there is enough money to purchase an item, and false when there isn't enough money.

<br>

The test that fails was is testInsertMoney() saying zero in an invalid amount, when it shouldn't be.