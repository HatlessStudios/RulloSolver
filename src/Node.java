class Node {

    private int value;
    private boolean state;
    private boolean lock;

    Node(int value){

         this.value = value;
         state = true;
         lock = false;

    }

    Node(int value, boolean lockBool){

        this.value = value;
        state = true;
        lock = lockBool;

    }

    int getValue(){
        return value;
    }

    boolean isOn(){
        return state;
    }

    boolean isLocked(){
        return lock;
    }

    void setState(boolean newState){
        state = newState;
    }

    void setLock(boolean newState){
        lock = newState;
    }

}
