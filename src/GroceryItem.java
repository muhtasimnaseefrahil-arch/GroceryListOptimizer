public abstract class GroceryItem
{
    private String name;
    private int quantity;

    public GroceryItem(String Name, int Quantity)
    {
        this.name = Name;
        this.quantity = Quantity;
    }

    public String getName()
    {
        return this.name;
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    public abstract int getLocation();

    public abstract String getAisleCategory();

    public abstract String getInfo();

}