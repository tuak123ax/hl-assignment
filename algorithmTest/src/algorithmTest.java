import java.util.ArrayList;
import java.util.Scanner;

public class algorithmTest {
    public static void main(String[] args) {
        ArrayList<Long> arr = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input 5 numbers:");
        arr.add(scanner.nextLong());
        arr.add(scanner.nextLong());
        arr.add(scanner.nextLong());
        arr.add(scanner.nextLong());
        arr.add(scanner.nextLong());
        miniMaxSum(arr);
    }

    static void miniMaxSum(ArrayList<Long> arr){
        ArrayList<Long> listForMaxSum = new ArrayList<>(arr);
        ArrayList<Long> listForMinSum = new ArrayList<>(arr);

        //Find min value in array.
        Long min = listForMaxSum.get(0);
        for(Long item: listForMaxSum){
            if(item < min){
                min = item;
            }
        }

        //Remove min value so that there are only 4 max values in array.
        listForMaxSum.remove(min);

        //Calculate maxSum.
        Long maxSum = 0L;
        for(Long item: listForMaxSum){
            maxSum += item;
        }

        //Find max value in array.
        Long max = listForMinSum.get(0);
        for(Long item: listForMinSum){
            if(item > max){
                max = item;
            }
        }

        //Remove max value so that there are only 4 min values in array.
        listForMinSum.remove(max);

        //Calculate maxSum.
        Long minSum = 0L;
        for(Long item: listForMinSum){
            minSum += item;
        }

        //Show result
        System.out.print(minSum + " " + maxSum);

        //Bonus
        System.out.println("");
        System.out.println("Bonus:");
        //Count total of array.
        Long sumArray = 0L;
        for(Long item: arr){
            sumArray += item;
        }
        System.out.println("Count total of array: "+ sumArray);
        //Find min in array
        System.out.println("Find min in array: "+ min);
        //Find max in array
        System.out.println("Find max in array: "+ max);
        //Find even and odd elements in array.
        ArrayList<Long> evenArray = new ArrayList<>();
        ArrayList<Long> oddArray = new ArrayList<>();
        for(Long item : arr) {
            if(item % 2 == 0) {
                evenArray.add(item);
            } else {
                oddArray.add(item);
            }
        }
        System.out.print("Find even elements in array: ");
        for(Long item : evenArray)
        {
            System.out.print(item + " ");
        }
        System.out.println("");
        System.out.print("Find odd elements in array: ");
        for(Long item : oddArray)
        {
            System.out.print(item + " ");
        }
    }
}
