import java.util.ArrayList;
import java.util.List;
public class generics1 {
    public static void main(String[] args){
        List <Integer> values = new ArrayList<Integer>();
        values.add(7);
        //if we now add a string or char value to it, it will show error while writing the code itself or else we will
        //run time error.
        //values.add('Mayank');
        System.out.println(values);
    }
}
