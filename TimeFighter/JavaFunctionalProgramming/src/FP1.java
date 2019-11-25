public class FP1
{
    public static void main(String[] args) {
        String s = "Reliance";

        s.chars().mapToObj(ch -> Character.valueOf((char)ch)).forEach(System.out::println);

    }



}
