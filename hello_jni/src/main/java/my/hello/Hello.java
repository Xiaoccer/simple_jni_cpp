package my.hello;

import my.hello.JniLoader;

public class Hello {
    static {
        JniLoader.load(Hello.class, "Hello.so");
    }

    private int number = 1234;

    private native String say(String s);

    public int getNumber() {
        return number;
    }

    public static void main(String[] args) {
        Hello h = new Hello();
        int number = h.getNumber();
        String s = h.say("call jni");
        if (s.equals("ok")) {
            System.out.println("Change number from " + number + " to " + h.getNumber());
        } else {
            System.out.println("Wrong!");
        }
    }

}
