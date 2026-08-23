import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class GroceryListOptimizer
{

    /* ---------------------------GROCERY LIST OPTIMIZER-------------------------------- */
    //                               -DEVELOPER INFO-
    //Name                    ID           Contribution
    //Muhtasim Naseef Rahil | 2611781642 | [GroceryListOptimizer Class, Swing GUI]
    //S M Shahriar Islam    | 2531662642 | [GroceryItem, Perishable and Item Type Classes]
    //Github:      https://github.com/Hailstorm789/Grocerylistoptimiser
    /* --------------------------------------------------------------------------------- */


    //Constant Variables
    public static final int MAX_NAME_LENGTH = 24;
    public static final int MAX_QUANTITY = 1000;
    public static final int MAX_SPOIL_DAYS = 1461;
    public static final int WARNING_DAYS = 7;
    public static final String[] Category = {"Produce","Dairy","Pantry","Meat","Seafood"};
    public static final String[] Section = {"To Buy","In Fridge"};


    //Input Field Objects
    private static JTextField nameInputField = new JTextField(10);
    private static JSpinner quantitySpinner = new JSpinner( new SpinnerNumberModel(1,1,MAX_QUANTITY,1) );
    private static JSpinner spoilSpinner = new JSpinner( new SpinnerNumberModel(0,0,MAX_SPOIL_DAYS,1) );
    private static JComboBox<String> categoryBox = new JComboBox<>(Category);
    private static JComboBox<String> sectionBox = new JComboBox<>(Section);
    private static boolean isEditable = false;
    private static JButton editButton = new JButton("Edit");


    //Output Display
    private static JTextArea[] toBuyCategories = new JTextArea[5];
    private static JTextArea[] inFridgeCategories = new JTextArea[5];


    //Main Method
    public static void main(String args[])
    {
        //Main Frame
        JFrame F = new JFrame("Grocery List Optimizer v1.0");
        F.setSize(1340,720);
        F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        F.setLayout( new BorderLayout() );
        F.setLocationRelativeTo(null);


        /*Top Panel: Upper Panel and Input Field*/
        JPanel topPanel = new JPanel(new BorderLayout());


        /* Upper Panel: Title, Edit and Clear Buttons */
        JPanel upperPanel = new JPanel(new BorderLayout());

        //Title Text
        JLabel title = new JLabel(" Grocery List Optimizer");
        title.setFont(new Font("Verdana",Font.BOLD,16));
        upperPanel.add(title, BorderLayout.WEST);


        //Format Buttons Panel
        JPanel formatButtons = new JPanel(new BorderLayout(5,0));

        //Clear Buy List Button
        JButton clearBuy = new JButton("Clear Buy List");
        clearBuy.addActionListener(click -> clearBuyList());
        formatButtons.add(clearBuy, BorderLayout.CENTER);

        //Clear Fridge Button
        JButton clearFridge = new JButton("Clear Fridge");
        clearFridge.addActionListener(click -> clearFridgeList());
        formatButtons.add(clearFridge, BorderLayout.EAST);

        //Edit Toggle Button
        editButton.addActionListener(click -> toggleEditable());
        formatButtons.add(editButton, BorderLayout.WEST);


        //Add Format Buttons to Upper Panel
        upperPanel.add(formatButtons, BorderLayout.EAST);


        //Add Upper Panel to Top Panel
        topPanel.add(upperPanel, BorderLayout.NORTH);


        /* Input Panel: Text Field, Spinner and ComboBox */
        JPanel inputPanel = new JPanel();

        //Name Input
        inputPanel.add( new JLabel("Name:") );
        inputPanel.add(nameInputField);

        //Category ComboBox
        inputPanel.add( new JLabel("Category:") );
        inputPanel.add(categoryBox);

        //Quantity Spinner
        inputPanel.add( new JLabel("Quantity:") );
        inputPanel.add(quantitySpinner);

        //Category Spinner
        inputPanel.add( new JLabel("Spoils in (Days):") );
        inputPanel.add(spoilSpinner);

        //Section ComboBox
        inputPanel.add( new JLabel("Section:") );
        inputPanel.add(sectionBox);

        //Add Item Button
        JButton addItem = new JButton("Add Item");
        addItem.addActionListener(click -> addItem());
        inputPanel.add(addItem);

        //Add input Panel to Top Panel
        topPanel.add(inputPanel, BorderLayout.SOUTH);


        /* Add Top Panel to Main Frame */
        F.add(topPanel, BorderLayout.NORTH);


        /* ToBuy and InFridge Section Tabs */
        JTabbedPane sectionTabs = new JTabbedPane();

        //ToBuy Section
        JPanel toBuyPanel = new JPanel( new GridLayout(1,5) );
        for(int i=0; i<toBuyCategories.length; i++)
        {
            toBuyCategories[i] = new JTextArea();
            toBuyCategories[i].setEditable(false);

            JPanel toBuyCatPanel = new JPanel(new BorderLayout());
            toBuyCatPanel.setBorder( BorderFactory.createTitledBorder(Category[i]) );
            toBuyCatPanel.add( new JScrollPane(toBuyCategories[i]) );
            toBuyPanel.add(toBuyCatPanel);

        }
        sectionTabs.add(Section[0], toBuyPanel);

        //InFride Section
        JPanel inFridgePanel = new JPanel( new GridLayout(1,5) );
        for(int i=0; i<inFridgeCategories.length; i++)
        {
            inFridgeCategories[i] = new JTextArea();
            inFridgeCategories[i].setEditable(false);

            JPanel inFridgeCatPanel = new JPanel(new BorderLayout());
            inFridgeCatPanel.setBorder( BorderFactory.createTitledBorder(Category[i]) );
            inFridgeCatPanel.add( new JScrollPane(inFridgeCategories[i]) );
            inFridgePanel.add(inFridgeCatPanel);

        }
        sectionTabs.add(Section[1], inFridgePanel);

        //Add Section Tabs to Main Frame
        F.add(sectionTabs,BorderLayout.CENTER);


        /* Load Data */
        loadData();


        /* Main Frame Visibility */
        F.setVisible(true);

    }


    //Object Creation
    public static void addItem()
    {
        //Take name input
        String itemName = nameInputField.getText().trim();

        //Reject Empty Name
        if (itemName.isEmpty()) {
            return;
        }

        //Ensure Name Length Limit
        if (itemName.length() > MAX_NAME_LENGTH) {
            itemName = itemName.substring(0, MAX_NAME_LENGTH);
        }

        //Take Other Inputs
        int quantity = (Integer) quantitySpinner.getValue();
        int spoilInDays = (Integer) spoilSpinner.getValue();
        String category = (String) categoryBox.getSelectedItem();
        String section = (String) sectionBox.getSelectedItem();
        boolean isInFridge = section.equals(Section[1]);

        //Polymorphic Object Creation
        GroceryItem item;

        if (Category[0].equals(category)) {
            item = new Produce(itemName, quantity, spoilInDays, isInFridge);
        } else if (Category[1].equals(category)) {
            item = new Dairy(itemName, quantity, spoilInDays, isInFridge);
        } else if (Category[2].equals(category)) {
            item = new Pantry(itemName, quantity, spoilInDays, isInFridge);
        } else if (Category[3].equals(category)) {
            item = new Meat(itemName, quantity, spoilInDays, isInFridge);
        } else if (Category[4].equals(category)) {
            item = new Seafood(itemName, quantity, spoilInDays, isInFridge);
        } else {
            return;
        }

        //Save Object Info Into File
        saveItem(item);

        //Reset Inputs
        resetInput();

        //Load New Data
        loadData();

    }


    //Save Info To File
    public static void saveItem(GroceryItem item)
    {
        //Get Item Info
        String itemInfo = item.getInfo();

        //Warning Verification
        if (item instanceof Perishable && item.getLocation() == 1)
        {
            if (((Perishable) item).getDaysUntilSpoiled() <= WARNING_DAYS)
            {
                itemInfo = ("[⚠] " + itemInfo);
            }
        }


        //Add Info to Dedicated File
        String fileName = (Section[item.getLocation()] + "_" + item.getAisleCategory() + ".txt");

        File itemFile = new File(fileName);
        try
        {
            FileWriter fw = new FileWriter(itemFile,true);
            fw.write((itemInfo + "\n"));
            fw.close();
        }
        catch(Exception E)
        {
            System.out.println("Error writing file: " + E.getMessage());
        }

    }


    //Reset Inputs back to Initial Values
    public static void resetInput()
    {
        nameInputField.setText("");
        quantitySpinner.setValue(1);
        spoilSpinner.setValue(0);
    }


    //Load File Information into Text Area
    public static void loadData()
    {
        for(int i=0; i < Category.length; i++)
        {
            //Reset Text Area before loading new Data
            toBuyCategories[i].setText("");
            inFridgeCategories[i].setText("");

            //Reconstruct File Names
            String toBuyFileName = (Section[0] + "_" + Category[i] + ".txt");
            String inFridgeFilesName = (Section[1] + "_" + Category[i] + ".txt");

            //Read Files to Dedicated Text Area
            readFile(toBuyFileName, toBuyCategories[i]);
            readFile(inFridgeFilesName, inFridgeCategories[i]);

        }

    }


    //Read Files
    public static void readFile(String fileName, JTextArea textArea)
    {
        File itemFile = new File(fileName);
        if(itemFile.exists())
        {
            try
            {
                Scanner scan = new Scanner(itemFile);
                while(scan.hasNextLine())
                {
                    textArea.append(scan.nextLine() + "\n");
                }
                scan.close();
            }
            catch(Exception E)
            {
                System.out.println("Error reading file: " + E.getMessage());
            }

        }

    }


    //Toggle Text Area Edit
    public static void toggleEditable()
    {
        if(isEditable)
        {
            saveCurrentText();
            isEditable = false;
            editButton.setText("Edit");
        }
        else
        {
            isEditable = true;
            editButton.setText("Stop");
        }

        //Toggle Editable for Text Areas
        for(int i=0; i < Category.length; i++)
        {
            toBuyCategories[i].setEditable(isEditable);
            inFridgeCategories[i].setEditable(isEditable);
        }
    }


    //Save Current Texts to Files After Edit
    public static void saveCurrentText()
    {
        for(int i=0; i<Category.length; i++)
        {
            writeTextToFile(Section[0] + "_" + Category[i] + ".txt", toBuyCategories[i]);
            writeTextToFile(Section[1] + "_" + Category[i] + ".txt", inFridgeCategories[i]);
        }

    }


    //Write Texts inside Text Areas to Dedicated Files After Edit
    public static void writeTextToFile(String fileName, JTextArea textArea)
    {
        File itemFile = new File(fileName);

        try
        {
            FileWriter fw = new FileWriter(itemFile, false);
            fw.write(textArea.getText());
            fw.close();
        }
        catch(Exception E)
        {
            System.out.println("Error Writing File: " + E.getMessage());
        }

    }


    //Delete To Buy Files
    public static void clearBuyList()
    {
        for(int i=0; i<Category.length; i++)
        {
            //Reconstruct File Objects
            File toBuyFile = new File(Section[0] + "_" + Category[i] + ".txt");

            //Delete ToBuy Files
            if(toBuyFile.exists())
            {
                toBuyFile.delete();
            }

        }

        //Load Updated Data
        loadData();

    }


    //Delete In Fridge File
    public static void clearFridgeList()
    {
        for(int i=0; i<Category.length; i++)
        {
            //Reconstruct File Objects
            File inFridgeFile = new File(Section[1] + "_" + Category[i] + ".txt");

            //Delete InFridge File
            if(inFridgeFile.exists())
            {
                inFridgeFile.delete();
            }

        }

        //Load Updated Data
        loadData();

    }

}