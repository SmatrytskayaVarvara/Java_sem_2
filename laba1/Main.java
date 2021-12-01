package laba1;
public class Main {

    public static void main(String[] args) {
        int totalRoundTime = 4000;

        Load load = new Load(3000);


        Ship ship1 = new Ship("ship1", 1);
        Input input_capacity1 = new Input(ship1);
        Ship ship2 = new Ship("ship2", 2);
        Input input_capacity2 = new Input(ship2);
        Ship ship3 = new Ship("ship3", 3);
        Input input_capacity3 = new Input(ship3);
        Ship ship4 = new Ship("ship4", 4);
        Input input_capacity4 = new Input(ship4);


        ThreadLoad mythread1 = new ThreadLoad(load, ship1);
        ThreadLoad mythread2 = new ThreadLoad(load, ship2);
        ThreadLoad mythread3 = new ThreadLoad(load, ship3);
        ThreadLoad mythread4 = new ThreadLoad(load, ship4);

        long startedTime=System.currentTimeMillis();

        mythread1.start();
        mythread2.start();
        mythread3.start();
        mythread4.start();

        try {
            mythread1.join();
            mythread2.join();
            mythread3.join();
            mythread4.join();
        }catch (InterruptedException exception){
            exception.printStackTrace();
        }

        long endedTime=System.currentTimeMillis();
        long totalTime=endedTime-startedTime;

        System.out.println("Time in which all containers will be taken: "+ totalTime+
                totalRoundTime*(mythread1.getRoundsNumber()+mythread2.getRoundsNumber()+
                        mythread3.getRoundsNumber()+mythread4.getRoundsNumber()));
        System.out.println("Total number of rounds made by ship1 is "+mythread1.getRoundsNumber()+
                "\ntotal number of rounds made by ship2 is "+mythread2.getRoundsNumber()+
                "\ntotal number of rounds made by ship3 is "+mythread3.getRoundsNumber()+
                "\ntotal number of rounds made by ship4 is "+mythread4.getRoundsNumber());

    }
}
