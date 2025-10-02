import java.util.Scanner;
public class Number 
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int[] a= new int[5];
        System.out.println("Enter the numbers into the array");
        for(int i=0;i<a.length;i++)
        {
        System.out.println("Enter value at a["+i+"]:");
        a[i]=sc.nextInt();
        }
        System.out.println("Print the number");
        for(int i=0;i<a.length;i++)
        {
            System.out.println("a["+i+"]="+a[i]);
        }

    }
}
