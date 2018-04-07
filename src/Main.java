import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        System.out.println("Rullo solver is running...");


    }

    private int getIntInput(String prompt){

        System.out.println(prompt);
        Scanner inputter = new Scanner(System.in);

        return inputter.nextInt();
    }
}
