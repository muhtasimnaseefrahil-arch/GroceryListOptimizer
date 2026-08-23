public class Dairy extends GroceryItem implements Perishable
{
    private String type = "Dairy";
    private int daysUntilSpoiled;
    private boolean inFridge;

    public Dairy(String Name, int Quantity, int DaysUntilSpoiled, boolean InFridge)
    {
        super(Name,Quantity);
        this.daysUntilSpoiled = DaysUntilSpoiled;
        this.inFridge = InFridge;
    }

    @Override
    public int getLocation()
    {
        return inFridge ? 1 : 0;
    }

    @Override
    public String getAisleCategory()
    {
        return type;
    }

    @Override
    public String getInfo()
    {
        if(inFridge)
        {
            return ( getName() + " (" + getQuantity() + ") - Spoils in " + getDaysUntilSpoiled() + " days" );
        }
        else {
            return (getName() + " (" +getQuantity() + ")");
        }
    }

    @Override
    public int getDaysUntilSpoiled()
    {
        return this.daysUntilSpoiled;
    }

}