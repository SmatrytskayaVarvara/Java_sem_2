package laba1;
import java.util.Scanner;

public class Input{
    int capacity;
    Ship ship;

    public Input(Ship ship){
        this.ship = ship;
        System.out.println("Input ship "+ship.getNumber()+ " capacity: ");
        Scanner in = new Scanner(System.in);
        capacity=in.nextInt();
        this.capacity = capacity;
        ship.setCapacity(capacity);
    }


}
