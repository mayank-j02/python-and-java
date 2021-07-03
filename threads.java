class first extends Thread{
    public void run(){
        System.out.print("Hello,"+" ");
    }
}
class second extends Thread{
    public void run(){
        System.out.print("My name is Mayank Jain.");
    }
}
public class threads {
    public static void main(String[] args){
        first t1 = new first();
        second t2 = new second();
        t1.start();
        t2.start();
    }
}
