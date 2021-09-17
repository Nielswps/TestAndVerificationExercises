package car;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        CarAlarmSystem cas = new CarAlarmSystem();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean stop = false;

        System.out.println("Enter one of the following commands: ");
        System.out.println("    - open (o)");
        System.out.println("    - close (c)");
        System.out.println("    - unlock (u)");
        System.out.println("    - lock (l)");
        System.out.println("    - break (b)");
        System.out.println("    - tick (t) [(optional) number of ticks]");
        System.out.println();
        System.out.println("----------------------");
        System.out.println();

        while (!stop) {
            String command = reader.readLine();

            switch (command) {
                case "o":
                case "open":
                    cas.open();
                    break;
                case "c":
                case "close":
                    cas.close();
                    break;
                case "u":
                case "unlock":
                    cas.unlock();
                    break;
                case "l":
                case "lock":
                    cas.lock();
                    break;
                case "b":
                case "break":
                    stop = true;
                default:
                    if (command.startsWith("t") || command.startsWith("tick")) {
                        String[] commands = command.split(" ");
                        if (commands.length == 1) {
                            cas.tick();
                        } else if (commands.length == 2) {
                            cas.tick(Integer.parseInt(commands[1]));
                        } else {
                            System.out.println("Number of arguments was not recognized, try \"tick [integer]\"");
                        }
                    } else {
                        cas.tick();
                    }
            }
            cas.printStates();
        }
    }
}
