/*
 * Grant Toepfer - TCSS 342
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    private static String FILE_PATH = "C:\\Users\\Grant\\OneDrive\\School\\WINTER 2016\\TCSS 342\\Assignments\\1 - Burger Baron\\zip\\customer.txt";

    public static void main(String[] args) throws FileNotFoundException {
        testBurger();
        try (Scanner in = new Scanner(new FileInputStream(FILE_PATH))) {
            int count = 0;
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println("Processing Order " + count++ + ": " + line);
                parseLine(line);
                System.out.println();
            }
        }
    }

    private static void parseLine(String line) {
        StringTokenizer st = new StringTokenizer(line);
        String token = st.nextToken();

        //set pattyCount
        int pattyCount = 1;
        if ("Single".equals(token)) {
            token = st.nextToken();
        } else if ("Double".equals(token)) {
            pattyCount = 2;
            token = st.nextToken();
        } else if ("Triple".equals(token)) {
            pattyCount = 3;
            token = st.nextToken();
        }

        //set pattyType
        String pattyType = "Beef";
        if ("Beef".equals(token)) {
            token = st.nextToken();
        } else if ("Chicken".equals(token) || "Veggie".equals(token)) {
            pattyType = token;
            token = st.nextToken();
        }

        //create burger
        boolean isBaron = "Baron".equals(token);
        Burger burger;
        if (isBaron) {
            st.nextToken();
            burger = new Burger(true);
        } else {
            burger = new Burger(false);
        }

        //change pattyType
        burger.changePatties(pattyType);

        //add the correct amount of patties
        while (pattyCount > 1) {
            burger.addPatty();
            pattyCount--;
        }

        //check for additions/omissions
        if (!st.hasMoreTokens()) {
            System.out.println(burger.toString());
            return;
        }
        st.nextToken(); // remove "with"
        if ("no".equals(token)) st.nextToken(); //remove "no"

        // omissions/additions
        while (st.hasMoreTokens() && !(token = st.nextToken()).equals("but")) {
            if (isCategory(token)) {
                if (isBaron) burger.removeCategory(token);
                else burger.addCategory(token);
            } else {
                if (isBaron) burger.removeIngredient(token);
                else burger.addIngredient(token);
            }
        }

        // check for exceptions
        if (!st.hasMoreTokens()) {
            System.out.println(burger.toString());
            return;
        }
        if ("no".equals(token)) st.nextToken(); // remove "no"

        //exceptions
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if (isBaron) burger.addIngredient(token);
            else burger.removeIngredient(token);
        }

        System.out.println(burger.toString());
    }

    private static boolean isCategory(String word) {
        return "Cheese".equals(word) || "Sauce".equals(word) || "Veggies".equals(word);
    }

    private static void testMyStack() {
        MyStack<Integer> testStack = new MyStack<>();
        testStack.push(1);
        testStack.push(2);
        testStack.push(3);
        System.out.println(testStack);

    }

    private static void testBurger() {
        parseLine("Double Baron Burger");
        // [Pickle, Bun, Mayonnaise, Baron-Sauce, Lettuce, Tomato, Onions, Beef, Pepperjack, Mozzarella, Cheddar, Beef, Mushrooms, Mustard, Ketchup, Bun]
        parseLine("Triple Chicken Burger with Onions Cheese but Cheddar");
        // [Bun, Onions, Chicken, Chicken, Pepperjack, Mozzarella, Chicken, Bun]
        parseLine("Baron Burger with no Veggies Sauce but Baron-Sauce");
        // [Bun, Baron-Sauce, Pepperjack, Mozzarella, Cheddar, Beef, Bun]
        parseLine("Single Veggie Baron Burger");
        // [Pickle, Bun, Mayonnaise, Baron-Sauce, Lettuce, Tomato, Onions, Pepperjack, Mozzarella, Cheddar, Veggie, Mushrooms, Mustard, Ketchup, Bun]
        parseLine("Double Baron Burger with no Cheese but Mozzarella");
        // [Pickle, Bun, Mayonnaise, Baron-Sauce, Lettuce, Tomato, Onions, Beef, Mozzarella, Beef, Mushrooms, Mustard, Ketchup, Bun]
        parseLine("Single Burger with Veggies but no Lettuce");
        // [Pickle, Bun, Tomato, Onions, Beef, Mushrooms, Bun]
        parseLine("Double Chicken Burger with Ketchup Cheddar Onions Mushrooms");
        // [Bun, Onions, Chicken, Cheddar, Chicken, Mushrooms, Ketchup, Bun]

        Burger testBurger = new Burger(false);
        System.out.println(testBurger.toString());
        // [Bun, Beef, Bun]

        testBurger.addPatty();
        System.out.println(testBurger.toString());
        // [Bun, Beef, Beef, Bun]

        testBurger.addPatty();
        System.out.println(testBurger.toString());
        // [Bun, Beef, Beef, Beef, Bun]

        testBurger.removePatty();
        System.out.println(testBurger.toString());
        // [Bun, Beef, Beef, Bun]

        testBurger.removePatty();
        System.out.println(testBurger.toString());
        // [Bun, Beef, Bun]


        // [Pickle, Bun, Mayonnaise, Baron-Sauce, Lettuce, Tomato, Onions, Beef, Pepperjack, Mozzarella, Cheddar, Beef, Mushrooms, Mustard, Ketchup, Bun]
        // [Bun, Onions, Chicken, Chicken, Pepperjack, Mozzarella, Chicken, Bun]
        // [Bun, Baron-Sauce, Pepperjack, Mozzarella, Cheddar, Beef, Bun]
        // [Pickle, Bun, Mayonnaise, Baron-Sauce, Lettuce, Tomato, Onions, Pepperjack, Mozzarella, Cheddar, Veggie, Mushrooms, Mustard, Ketchup, Bun]
        // [Pickle, Bun, Mayonnaise, Baron-Sauce, Lettuce, Tomato, Onions, Beef, Mozzarella, Beef, Mushrooms, Mustard, Ketchup, Bun]
        // [Pickle, Bun, Tomato, Onions, Beef, Mushrooms, Bun]
        // [Bun, Onions, Chicken, Cheddar, Chicken, Mushrooms, Ketchup, Bun]
        // [Bun, Beef, Bun]
        // [Bun, Beef, Beef, Bun]
        // [Bun, Beef, Beef, Beef, Bun]
        // [Bun, Beef, Beef, Bun]
        // [Bun, Beef, Bun]

        System.out.println();
    }

}
