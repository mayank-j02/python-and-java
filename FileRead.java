import java.io.FileReader;
import java.io.File;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileRead{
    public static void main(String[] args) throws FileNotFoundException{

        File myObj = new File("D:\\Documents\\mayank.txt");
        Scanner myread = new Scanner(myObj);
        int array[][] = new int[30][30];
        while (myread.hasNextLine()){
            int j = 0;
            String a = myread.nextLine();
                System.out.println(a);
                //char[] ch = new char[a.length()];
                //for (int i = 0; i < a.length(); i++) {
                //    ch[i] = a.charAt(i);
                //}
                //for (char c : ch) {
                //    System.out.print(c+ "\t");
                //}
                //System.out.println();
                for(int i = 0;i<a.length();i++){
                    int ascii = (int) a.charAt(i);
                    array[j][i] = ascii;
                    System.out.print(ascii+ "\t");   
            }
            j++;
            System.out.println();
        }
        Scanner rep = new Scanner(System.in);
        System.out.print("Enter the ascii to be replaced: ");
        int row = rep.nextInt();
        System.out.print("Enter the ascii: ");
        int col = rep.nextInt();
        for(int k = 0;k<30;k++){
            for(int l = 0;l<30;l++){
                if(array[k][l] == 0)
                    continue;
                if(array[k][l] == row){
                array[k][l] = col;
                }
                System.out.print(array[k][l]+ "\t");
            }
        }
        File outPrnt = new File("D:\\Documents\\mayank.txt");
        PrintWriter out = new PrintWriter(outPrnt);
        for (int i =0;i<5;i++){
            for(int j = 0; j < 30;j++){ 
            if(array[i][j]== 0)
                continue;
            else{
                out.print((char) array[i][j]);
            }
        }
            out.println();
        }out.close();

    }
}
