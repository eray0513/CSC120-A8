import java.util.ArrayList;

public class Fairy implements Contract{
    
    private String name; // Name of the fairy
    private int pixieDustCur; // Current pixie dust in use
    private int pixieDustCap; // Pixie dust capacity
    private Talent talent; // Enum Talent for the fairy's talent
    private double curX; // Current x position
    private double curY; // Current y position
    private ArrayList<String> inventory; // Array List of strings for the fairy's inventory
    private double curSize; // Current size of fairy

    /**
     * Constructs a fairy using name, age, and talent, all else is default for all fairies
     * @param name of the fairy
     * @param talent of the fairy
     */
    public Fairy (String name, Talent talent){
        this.name = name;
        this.talent = talent;
        this.pixieDustCap = 100; // default capacity
        this.pixieDustCur = this.pixieDustCap; // start with max capacity of dust
        this.curX = 0; // start at position (0, 0)
        this.curY = 0;
        this.curSize = 10.0; // default fairy size
        this.inventory = new ArrayList<String>(); // initialize inventory to empty
        this.inventory.add("Pixie Dust");
    }

    /**
     * Allows a fairy to pick up an object and stores it in the inventory
     * @param item that the fairy is grabbing
     */
    public void grab(String item){
        this.inventory.add(item);
    }

    /**
     * Allows a fairy to drop and object and then removes it from the inventory if it is in the inventory
     * @param item that the fairy is dropping
     */
    public String drop(String item){
        if(this.inventory.contains(item)){
            this.inventory.remove(item);
            return "I don't need my " + item + " anymore!";
        } else{
            throw new RuntimeException("I don't have that item in my possession. Try again later.");
        }
    }

    /**
     * Allows fairies to examine objects depending on their talents, and decide whether or not they want it
     * @param item that the fairy is examining
     */
    public void examine(String item){
        if(this.talent == Talent.TINKER){
            System.out.println("Oooo I like this " + item + " I could use this in my workshop!!");
            this.grab(item);
        } else if(this.talent == Talent.LIGHT){
            System.out.println("This " + item + " is looking a little sunless...let me brighten it up!");
        } else if(this.talent == Talent.ANIMAL){
            System.out.println("I just KNOW the chipmunks would love this " + item + ". I'll take you with me!");
            this.grab(item);
        } else if(this.talent == Talent.WATER){
            System.out.println("This " + item + " could use a little dew sparkle...Here is comes!");
        } else if(this.talent == Talent.DUST){
            System.out.println("Queen Clarian should see this " + item + ". What if it affects the pixie tree??");
        } else if(this.talent == Talent.FASTFLYING){
            System.out.println("Will this " + item + " make me fly faster?? I think not. Not worth my time.");
        } else if(this.talent == Talent.GARDEN){
            System.out.println("Oh my...oh my...that " + item + " is a bit dirty isn't it? No thank you.");
        }
    }

    /**
     * Allows a fairy to use an item if it exists in their inventory
     * @param item that the fairy is using
     */
    public void use(String item){
        if(this.inventory.contains(item)){
            if(item.contains("Pixie Dust")||item.contains("pixie dust")){
                int needed = this.pixieDustCap - this.pixieDustCur;
                this.pixieDustCur += needed;
            } else{
                System.out.println("Wow! I love my " + item + "! I'm glad I brought it with me!");
            }
        } else{
            throw new RuntimeException("I can't use an item I don't have.");
        }
    }

    /**
     * Allows a fairy to walk in a chosen direction as long as they don't go into the negatives and prints their coordinates
     * @param direction which direction the fairy is walking (North, East, South, West)
     * @return t/f if the fairy was able to walk in that direction
     */
    public boolean walk(String direction){
        if(direction.contains("West")){
            if(this.canMove(direction)){
                this.curX --;
                System.out.println(this.name + " is currently at position " + this.getCoordinates());
                return true;
            } else{
                System.out.println(this.name + " remained at position " + this.getCoordinates());
                return false;
            }
        } else if (direction.contains("East")){
            this.curX ++;
            System.out.println(this.name + " is currently at position " + this.getCoordinates());
            return true;
        } else if (direction.contains("South")){
            if(this.canMove(direction)){
                this.curY --;
                System.out.println(this.name + " is currently at position " + this.getCoordinates());
                return true;
            } else{
                System.out.println(this.name + " remained at position " + this.getCoordinates());
                return false;
            }
        } else if (direction.contains("North")){
            this.curY ++;
            System.out.println(this.name + " is currently at position " + this.getCoordinates());
            return true;
        } else{
            throw new RuntimeException("Direction not recognized. Try again.");
        }
    }

    /**
     * Allows a fairy to fly to a chosen, non-negative position 
     * @param x new x coordinate
     * @param y new y coordinate
     * @return t/f if the flight was comopleted
     */
    public boolean fly(double x, double y){
        double pixieNeeded = x+y;
        if(this.isRemaining(pixieNeeded)){
            if(x>=0 && y>=0){
                this.curX = x;
                this.curY = y;
                this.pixieDustCur -= pixieNeeded;
                System.out.println(this.name + " is currently at position " + this.getCoordinates());
                return true;
            } else{
                System.out.println(this.name + " remained at position " + this.getCoordinates());
                return false;
            }
        } else{
            throw new RuntimeException("Not enough pixie dust. Refill and try later.");
        }
    }

    /**
     * Allows a fairy to shrink in half
     * @return a Number that indicates the fairy's new size
     */
    public Number shrink(){
        this.curSize = this.curSize/2;
        return this.curSize;
    }

    /**
     * Allows a fairy to grow twice as large
     * @return a Number that indicate's the fairy's new size
     */
    public Number grow(){
        this.curSize = this.curSize*2;
        return this.curSize;
    }

    /**
     * Allows a fairy to gain energy and pixie dust back to full capacity
     */
    public void rest(){
        int pixieNeeded = this.pixieDustCap - this.pixieDustCur;
        this.pixieDustCap += pixieNeeded;
    }

    /**
     * Allows a fairy to attempt to undo their previous action
     */
    public void undo(){
        throw new RuntimeException("There are no undos in life. Not even for fairies. Live with the consequences of your choices.");
    }

    /**
     * Allows the fairy's current coordinates to be accessed
     * @return a string formatted (x, y)
     */
    public String getCoordinates(){
        return "(" + this.curX + ", " + this.curY + ")";
    }

    public boolean canMove(String direction){
        if(direction.contains("West")){
            if(this.curX-1 >=0){
                return true;
            } else{
                return false;
            }
        }else if(direction.contains("South")){
            if(this.curY - 1 >= 0){
                return true;
            } else{
                return false;
            }
        } else{
            return true;
        }
    }

    public boolean isRemaining(int pixieNeeded){
        boolean result = true;
        if(this.pixieDustCur < pixieNeeded){
            result = false;
        }
        return result;
    }
public static void main(String[] args) {
    Fairy tinkerbell = new Fairy("Tinkerbell", Talent.TINKER);
    Fairy rosetta = new Fairy("Rosetta", Talent.GARDEN);
    Fairy fawn = new Fairy("Fawn", Talent.ANIMAL);
    Fairy vidia = new Fairy("Vidia", Talent.FASTFLYING);
    Fairy clarence = new Fairy("Clarence", Talent.DUST);
    Fairy silvermist = new Fairy("Silvermist", Talent.WATER);
    Fairy iridessa = new Fairy("Iridessa", Talent.LIGHT);

    tinkerbell.grab("Hammer");
    System.out.println(tinkerbell.drop("Hammer"));
    try{
        System.out.println(tinkerbell.drop("Hammer"));
    }catch(Exception e){
        System.out.println(e);
    }

    tinkerbell.examine("Hammer");
    rosetta.examine("Hammer");
    fawn.examine("Hammer");
    vidia.examine("Hammer");
    clarence.examine("Hammer");
    silvermist.examine("Hammer");
    iridessa.examine("Hammer");
    
    tinkerbell.use("Hammer");
    try{
        tinkerbell.use("Shovel");
    }catch(Exception e){
        System.out.println(e);
    }
    
    tinkerbell.use("Pixie Dust");
    
    System.out.println(tinkerbell.walk("West"));
    System.out.println(tinkerbell.walk("East"));
    System.out.println(tinkerbell.walk("West"));
    System.out.println(tinkerbell.walk("South"));
    System.out.println(tinkerbell.walk("North"));
    System.out.println(tinkerbell.walk("South"));

    try{
        tinkerbell.walk("west");
    }catch(Exception e){
        System.out.println(e);
    }

    tinkerbell.fly(3, 5);
    tinkerbell.fly(-1, 2);
    tinkerbell.fly(2, -1);
    
    try{
    tinkerbell.fly(50, 50);
    }catch(Exception e){
        System.out.println(e);
    }

    System.out.println(rosetta.grow());
    System.out.println(fawn.shrink());
    System.out.println(fawn.shrink());
    System.out.println(rosetta.shrink());

    try{
        silvermist.undo();
    } catch(Exception e){
        System.out.println(e);
    }

}
}
