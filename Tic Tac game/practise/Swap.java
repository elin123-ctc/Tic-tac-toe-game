public class Swap{
    public static void main(String args[])
    {
        int a=2;
        int b=3;
        System.out.println("Before swapping a: "+a);
        System.out.println("Before swapping a: "+b);
        a=a+b;
        b=a-b;
        a=a-b;
        System.out.println("After swapping a:"+a);
        System.out.println("After swapping a:"+b);
    }
}