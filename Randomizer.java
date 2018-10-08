import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Randomizer {
  public static void main(String[] args) {
    int[] sample = new int[]{0,1,2,3,4,5,6,7,8};
    Random rand = new Random();
    Scanner kbd = new Scanner(System.in);

    int count;

    System.out.print("Enter the number of sets: ");
    count = kbd.nextInt();

    for(int i = 0; i < count; i++){
      for(int j = 0; j < sample.length; j++){
        int position = rand.nextInt(sample.length);
         int temp = sample[j];
         sample[j] = sample[position];
         sample[position] = temp;
      }

      try(BufferedWriter writer = new BufferedWriter(new FileWriter("datasets.txt", true))){
        System.out.println(Arrays.toString(sample));
        writer.append(Arrays.toString(sample));
        writer.newLine();
        writer.close();
      }catch(IOException e){
        e.printStackTrace();
      }
    }//end of for loop
    System.out.println("Data sets saved!");
  }
}
