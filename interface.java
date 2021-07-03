import java.util.Scanner;

interface first{

    String color = "Blue";

    int a = 10;
    void fillUp();
}

public class Interface implements first {
    public static void main(String[] args){
        //Scanner st = new Scanner(System.in);
        System.out.println(color);
        Interface test = new Interface();
        test.fillUp();
    }
    public void fillUp() {
        System.out.println(a);
    }
}
