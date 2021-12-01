package laba1;

public class ThreadLoad extends Thread{
    private int roundsNumber=0;
    Load load;
    Ship ship;

    public ThreadLoad(Load load, Ship ship){
        this.load = load;
        this.ship = ship;
    }

    public int getRoundsNumber() {
        return roundsNumber;
    }

    @Override
    public String toString(){
        return "ThreadLoad {"+"ship="+ship.getNumber()+'}';
    }

    @Override
    public void run(){
        while(load.getContainersNumber()>0){
            System.out.println("Ship "+ship.getNumber()+" takes "+ship.getCapacity()+" containers");
            load.setContainersNumber(load.getContainersNumber()-ship.getCapacity());
            synchronized (ship){
                try{
                    ship.setIsLoading(true);
                    this.sleep(ship.getLoadingTime()* ship.getCapacity());
                    ship.setIsLoading(false);
                    roundsNumber++;
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
