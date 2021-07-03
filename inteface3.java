interface again{
    int a = 10;
    void thirdtry();
}
public class inteface3 {
  public static void main(String[] args){
      again obj = new again(){
          public void thirdtry(){
              System.out.println(a);
          }
        };
        obj.thirdtry();
    }  
}
