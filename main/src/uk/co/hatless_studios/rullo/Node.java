package uk.co.hatless_studios.rullo;

class Node {

    private int value;
    private boolean state;
    private boolean lock;

    Node(int value){

         this.value = value;
         state = true;
         lock = false;

    }

    int getValue(){
        return value;
    }

    boolean isOn(){
        return state;
    }

    boolean isOdd(){
        return (value % 2) == 1;
    }

    boolean isLocked(){
        return lock;
    }

    boolean getState() { return state; }

    void setState(){
        state = false;
    }

    void setLock(){
        lock = true;
    }

}
