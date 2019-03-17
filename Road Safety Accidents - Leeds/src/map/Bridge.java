package map;


public class Bridge {

    static int i=1;
    @SuppressWarnings("unused")
    public void println(String msg) {
        System.out.println("msg from js: " + msg);
    }

    @SuppressWarnings("unused")
    public void clickMap() {
        System.out.println("map clicked " + i);

    }
}
