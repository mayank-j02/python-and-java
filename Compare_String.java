import java.util.Scanner;
interface check1{
    void check(String x, String y);
}
public class Compare_String {
    public static void main(String[] args){
        Scanner str = new Scanner(System.in);
        System.out.print("Enter the string: ");
        String s1 = str.nextLine();
        System.out.print("enter the second string: ");
        String s2 = str.nextLine();
        check1 obj = (String x, String y)->{
            String a = x.toLowerCase();
            String b = y.toLowerCase();
            if (x.equals(y)){
                System.out.println("Strings are equal with case sensitivity.");
            }
            else {
                System.out.println("Strings are not equal with case sinsitivity.");
            }
            if(a.equals(b)){
                System.out.println("Strings are equal without case sensitivity.");
            }
            else{
                System.out.println("Strings are not equal without case sensiitivity.");
            }
        };
        obj.check(s1,s2);
    }
}
