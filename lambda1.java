interface new2{
    void new1(int i);
}

//class second implements new{
//    public void new1(){
//        System.out.println("Hello");
//    }
//}

public class lambda1 {
    public static void main(String[] args){
        new2 obj;
        //for only one parameter we didnt need to write (i) instead we can write just i.
        obj = i -> System.out.println("Hello"+" "+ i);
        obj.new1(6);
    }
}
