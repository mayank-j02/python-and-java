import java.util.Scanner;

interface sort1{
    void bubblesort(int n, int arr[]);
}
public class bubblesort {
    public static void main(String [] args){
        Scanner num = new Scanner(System.in);
        System.out.print("Enter the number of values: ");
        int v = num.nextInt();
        int array[] = new int[v];
        for( int i = 0; i<v; i++){
            System.out.print("Enter the numbers to be sorted: ");
            array[i] = num.nextInt();
        }
        sort1 obj = (int n ,int arr[])-> 
        {
            boolean sorted = true;
            
            for(int i = 0; i<n-1; i++){
                for(int j = 0; j<n-i-1; j++){
                    if(arr[j]>arr[j+1]){
                        int temp = arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = temp;
                        sorted = false;
                }
            }
            if(sorted) break;
        }
        for (int a = 0; a < n; a++) {
            System.out.print("\t" + arr[a]);
        }
    };
        obj.bubblesort(v, array);
    }
}
