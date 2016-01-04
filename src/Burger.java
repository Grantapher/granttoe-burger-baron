public class Burger {
    private final static String DEFAULT_PATTY = "Beef";
    private final MyStack<String> myStack;
    private int myPattyCount;
    private String myPattyType;

    private static MyStack<String> getPlainBurger() {
        MyStack<String> plain = new MyStack<>();
        plain.push("Bun");
        plain.push(DEFAULT_PATTY);
        plain.push("Bun");
        return plain;
    }

    private static MyStack<String> getBaronBurger() {
        MyStack<String> baron = new MyStack<>();
        baron.push("Bun");
        baron.push("Ketchup");
        baron.push("Mustard");
        baron.push("Mushrooms");
        baron.push(DEFAULT_PATTY);
        baron.push("Cheddar");
        baron.push("Mozzarella");
        baron.push("Pepperjack");
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
        myStack = theWorks ? getBaronBurger() : getPlainBurger();
        myPattyCount = 1;
        myPattyType = DEFAULT_PATTY;
    }

    //go through ingredients until all patties are found and changed
    public void changePatties(String pattyType) {
        MyStack<String> holdingStack = new MyStack<>();
        int pattiesLeft = myPattyCount;

        // change patties
        while (pattiesLeft > 0) {
            String current = myStack.pop();
            if (myPattyType.equals(current)) {
                current = pattyType;
                pattiesLeft--;
            }
            holdingStack.push(current);
        }

        //add back ingredients
        while (!holdingStack.isEmpty()) {
            myStack.push(holdingStack.pop());
        }

        //change myPattyType to new patty
        myPattyType = pattyType;
    }

    //add the new patty where you first find cheese or a patty
    public void addPatty() {
        if (3 == myPattyCount) return;
        MyStack<String> holdingStack = new MyStack<>();

        //add the patty when you find a patty or cheese
        while (!myStack.isEmpty()) {
            String current = myStack.pop();
            if (myPattyType.equals(current) || "Cheddar".equals(current)
                    || "Mozzarella".equals(current) || "Pepperjack".equals(current)) {
                myStack.push(current);
                myStack.push(myPattyType);
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
        if (1 == myPattyCount) return;
        MyStack<String> holdingStack = new MyStack<>();

        //find the patty
        while (!myStack.isEmpty()) {
            String current = myStack.pop();
            if (myPattyType.equals(current)) {
                //current patty won't be put back on stack if we leave here
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

    }

    public void removeCategory(String category) {

    }

    public void addIngredient(String ingredient) {

    }

    public void removeIngredient(String ingredient) {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

}
