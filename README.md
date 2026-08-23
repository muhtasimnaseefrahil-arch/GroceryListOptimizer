# Grocery List Optimizer

Grocery List Optimizer is an intuitive desktop utility built to streamline shopping trips and simplify kitchen inventory management. It categorizes shopping lists by store aisle and tracks food items already stored in your refrigerator to reduce food waste.

---

## What It Does

* **Smart Aisle Categorization:** Automatically sorts items into dedicated supermarket categories (`Produce`, `Dairy`, `Pantry`, `Meat`, and `Seafood`) to save time while shopping.
* **Dual Inventory Management:** Easily switch between your active **To Buy** shopping list and your **In Fridge** food tracker.
* **Expiration Alerts:** Displays remaining shelf-life for refrigerated food and flags expiring items with an automatic warning symbol (`[⚠]`).
* **Free Edit Mode:** Switch to an interactive notepad-style mode to manually adjust entries, clear text, or leave custom shopping notes and reminders.
* **Persistent Local Storage:** Saves all changes automatically to disk so your lists remain up to date every time you launch the application.

---

## File Structure

The project files inside `GroceryListOptimizerFullFinal.zip` include:

```text
GroceryListOptimizerFullFinal/
├── GroceryListOptimizer.java   # Main application, GUI layout, and file controls
├── GroceryItem.java            # Abstract base class for grocery items
├── Perishable.java             # Interface for expiration management
├── Produce.java                # Produce item category
├── Dairy.java                  # Dairy item category
├── Pantry.java                 # Pantry item category
├── Meat.java                   # Meat item category
└── Seafood.java                # Seafood item category
