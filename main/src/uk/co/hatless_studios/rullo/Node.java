package uk.co.hatless_studios.rullo;

public class Node {

    private int value;
    private boolean state;
    private boolean lock;

    Node(int value){

         this.value = value;
         state = true;
         lock = false;

    }

    public int getValue(){
        return value;
    }

    public boolean isOn(){
        return state;
    }

    boolean isOdd(){
        return (value % 2) == 1;
    }

    public boolean isLocked(){
        return lock;
    }

    public boolean getState() { return state; }

    void setState() {
        state = false;
        lock = true;
    }

    void setLock() {
        lock = true;
    }

}
