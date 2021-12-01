package laba1;


public class Ship extends Thread {

    private  int number;
    private int capacity;
    private int loadingTime=200;
    private boolean isLoading;

    public Ship(String name, int number){
        super(name);
        this.number = number;
    }

    public int getNumber(){
        return number;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean loading) {
        isLoading = loading;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getLoadingTime(){
        return loadingTime;
    }
}
