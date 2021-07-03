interface second{
    String name = "Mayank";
    void again();
}
class secondmethod implements second{
    public void again(){
        System.out.println("Tried again.");
    }
}

public class interface2 {
    public static void main(String[] args){
        secondmethod obj = new secondmethod();
        obj.again();
    }
}
