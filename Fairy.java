import java.util.ArrayList;

import javax.management.RuntimeErrorException;
public class Fairy implements Contract{
    
    private String name;
    private int pixieDustCur;
    private int pixieDustCap;
    private Talent talent;
    private int curX;
    private int curY;
    private ArrayList<String> inventory;

    public Fairy (String name, int age, Talent talent){
        this.name = name;
        this.age = age;
        this.talent = talent;
        this.pixieDustCap = 100;
        this.pixieDustCur = this.pixieDustCap;
        this.curX = 0;
        this.curY = 0;
        this.inventory = new ArrayList<String>();
    }

    public void grab(String item){
        this.inventory.add(item);
    }

    public String drop(String item){
        if(this.inventory.contains(item)){
            this.inventory.remove(item);
            return "I don't need my " + item + "anymore!";
        } else{
            throw new RuntimeException("I don't have that item in my possession. Try again later.");
        }
    }

    public void examine(String item){
        if(this.talent == Talent.TINKER){
            System.out.println("Oooo I like this " + item + "I could use this in my workshop!!");
            this.grab(item);
        } else if(this.talent == Talent.LIGHT){
            System.out.println("This " + item + " is looking a little sunless...let me brighten it up!");
        } else if(this.talent == Talent.ANIMAL){
            System.out.println("I just KNOW the chipmunks would love this " + item + ". I'll take you with me!");
            this.grab(item);
        } else if(this.talent == Talent.WATER){
            System.out.println("This " + item + " could you a little dew sparkle...Here is comes!");
        } else if(this.talent == Talent.DUST){
            System.out.println("Queen Clarian should see this " + item + ". What if it affects the pixie tree??");
        } else if(this.talent == Talent.FASTFLYING){
            System.out.println("Does it make me fly faster?? I think not. Not worth my time.");
        } else if(this.talent == Talent.GARDEN){
            System.out.println("Oh my...oh my...that " + item + " is a bit dirty isn't it? No thank you.");
        }
    }

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

    public boolean walk(String direction){
        if(direction.contains("West")){
            if(this.canMove(direction)){
                this.curX --;
                return true;
            } else{
                return false;
            }
        } else if (direction.contains("East")){
            this.curX ++;
            return true;
        } else if (direction.contains("South")){
            if(this.canMove(direction)){
                this.curY --;
                return true;
            } else{
                return false;
            }
        } else if (direction.contains("North")){
            this.curY ++;
            return true;
        } else{
            throw new RuntimeException("Direction not recognized. Try again.");
        }
    }

    public boolean fly(int x, int y){
        int pixieNeeded = x+y;
        if(this.isRemaining(pixieNeeded)){
            if(x>=0 && y>=0){
                this.curX = x;
                this.curY = y;
                this.pixieDustCur -= pixieNeeded;
                return true;
            } else{
                return false;
            }
        } else{
            throw new RuntimeException("Not enough pixie dust. Refill and try later.");
        }
    }

    public Number shrink(){

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
}
