public class Burger {
    private final static String DEFAULT_PATTY_TYPE = "Beef";
    private static final int DEFAULT_PATTY_COUNT = 1;
    private final MyStack<String> myStack;
    private int myPattyCount;
    private String myPattyType;

    private static MyStack<String> getPlainBurger() {
        MyStack<String> plain = new MyStack<>();
        plain.push("Bun");
        plain.push(DEFAULT_PATTY_TYPE);
        plain.push("Bun");
        return plain;
    }

    private static MyStack<String> getBaronBurger(int pattyCount, String pattyType) {
        if (pattyCount < 1 || pattyCount > 3) {
            throw new IllegalArgumentException("You can only have between 1 and 3 patties, silly!");
        }
        MyStack<String> baron = new MyStack<>();
        baron.push("Bun");
        baron.push("Ketchup");
        baron.push("Mustard");
        baron.push("Mushrooms");
        baron.push(pattyType);
        baron.push("Cheddar");
        baron.push("Mozzarella");
        baron.push("Pepperjack");
        if (pattyCount >= 2) baron.push(pattyType);
        if (pattyCount == 3) baron.push(pattyType);
        baron.push("Onions");
        baron.push("Tomato");
        baron.push("Lettuce");
        baron.push("Baron-Sauce");
        baron.push("Mayonnaise");
        baron.push("Bun");
        baron.push("Pickle");
        return baron;
    }

    public Burger(boolean theWorks) {
        myStack = theWorks ? getBaronBurger(DEFAULT_PATTY_COUNT, DEFAULT_PATTY_TYPE) : getPlainBurger();
        myPattyCount = 1;
        myPattyType = DEFAULT_PATTY_TYPE;
    }

    //go through ingredients until all patties are found and changed
    public void changePatties(String newPattyType) {
        //return if the patties aren't actually changing
        if (myPattyType.equals(newPattyType)) return;

        MyStack<String> holdingStack = new MyStack<>();
        int pattiesLeft = myPattyCount;

        // change patties
        while (pattiesLeft > 0) {
            String current = myStack.pop();
            if (myPattyType.equals(current)) {
                current = newPattyType;
                pattiesLeft--;
            }
            holdingStack.push(current);
        }

        //add back ingredients
        while (!holdingStack.isEmpty()) {
            myStack.push(holdingStack.pop());
        }

        //change myPattyType to new patty type
        myPattyType = newPattyType;
    }

    //add the new patty where you first find cheese or a patty
    public void addPatty() {
        if (3 == myPattyCount) {
            throw new IllegalArgumentException("You can't have more than 3 patties, silly!");
        }

        MyStack<String> holdingStack = new MyStack<>();

        //add the patty when you find a patty or cheese
        while (!myStack.isEmpty()) {
            String current = myStack.pop();
            if (myPattyType.equals(current) || isCheese(current)) {
                myStack.push(current);
                myStack.push(myPattyType);
                myPattyCount++;
                break;
            }
            holdingStack.push(current);
        }

        //put the ingredients back on
        while (!holdingStack.isEmpty()) {
            myStack.push(holdingStack.pop());
        }

    }

    public void removePatty() {
        if (1 == myPattyCount) {
            throw new IllegalArgumentException("You can't have no patties, silly!");
        }

        MyStack<String> holdingStack = new MyStack<>();

        //find the patty
        while (!myStack.isEmpty()) {
            String current = myStack.pop();
            if (myPattyType.equals(current)) {
                //current patty won't be put back on stack if we leave here
                myPattyCount--;
                break;
            }
            holdingStack.push(current);
        }

        //put the ingredients back on
        while (!holdingStack.isEmpty()) {
            myStack.push(holdingStack.pop());
        }
    }

    public void addCategory(String category) {
        if ("Cheese".equals(category)) {
            addIngredient("Cheddar");
            addIngredient("Mozzarella");
            addIngredient("Pepperjack");
        } else if ("Veggies".equals(category)) {
            addIngredient("Mushrooms");
            addIngredient("Onions");
            addIngredient("Tomato");
            addIngredient("Lettuce");
            addIngredient("Pickle");
        } else if ("Sauce".equals(category)) {
            addIngredient("Ketchup");
            addIngredient("Mustard");
            addIngredient("Baron-Sauce");
            addIngredient("Mayonnaise");
        }
    }

    public void removeCategory(String category) {
        if ("Cheese".equals(category)) {
            removeIngredient("Pepperjack");
            removeIngredient("Mozzarella");
            removeIngredient("Cheddar");
        } else if ("Veggies".equals(category)) {
            removeIngredient("Pickle");
            removeIngredient("Lettuce");
            removeIngredient("Tomato");
            removeIngredient("Onions");
            removeIngredient("Mushrooms");
        } else if ("Sauce".equals(category)) {
            removeIngredient("Mayonnaise");
            removeIngredient("Baron-Sauce");
            removeIngredient("Mustard");
            removeIngredient("Ketchup");
        }
    }

    public void addIngredient(String ingredient) {
        MyStack<String> holdingStack = new MyStack<>();
        MyStack<String> recipeStack = getBaronBurger(myPattyCount, myPattyType);

        //find the ingredient location
        searchLocation:
        {
            while (!myStack.isEmpty()) {
                String current = myStack.pop();
                String recipeIngredient = recipeStack.pop();

                // find the recipe ingredient
                while (!current.equals(recipeIngredient)) {
                    // if we find the ingredient we need to add in the recipe stack,
                    // put the previous ingredient back on and then the new one, then break
                    if (recipeIngredient.equals(ingredient)) {
                        myStack.push(current);
                        myStack.push(ingredient);
                        break searchLocation;
                    }
                    recipeIngredient = recipeStack.pop();
                }

                holdingStack.push(current);
            }
        }

        //put the ingredients back on
        while (!holdingStack.isEmpty()) {
            myStack.push(holdingStack.pop());
        }
    }

    public void removeIngredient(String ingredient) {
        MyStack<String> holdingStack = new MyStack<>();

        //find the ingredient
        while (!myStack.isEmpty()) {
            String current = myStack.pop();
            if (ingredient.equals(current)) {
                //current ingredient won't be put back on stack if we leave here
                break;
            }
            holdingStack.push(current);
        }

        //put the ingredients back on
        while (!holdingStack.isEmpty()) {
            myStack.push(holdingStack.pop());
        }
    }

    @Override
    public String toString() {
        return myStack.toString();
    }

    private boolean isCheese(String ingredient) {
        return "Cheddar".equals(ingredient) || "Mozzarella".equals(ingredient) || "Pepperjack".equals(ingredient);
    }

    private boolean isVeggie(String ingredient) {
        return "Lettuce".equals(ingredient) || "Tomato".equals(ingredient) || "Onions".equals(ingredient)
                || "Pickle".equals(ingredient) || "Mushrooms".equals(ingredient);
    }

    private boolean isSauce(String ingredient) {
        return "Ketchup".equals(ingredient) || "Mustard".equals(ingredient) || "Mayonnaise".equals(ingredient)
                || "Baron-Sauce".equals(ingredient);
    }

}
