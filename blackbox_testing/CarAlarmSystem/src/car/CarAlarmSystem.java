package car;

public class CarAlarmSystem implements ICarAlarmSystem {
    private boolean closed = false;
    private boolean locked = false;
    private boolean flash = false;
    private boolean sound = false;
    private boolean armed = false;
    private int clock_armed = 0;
    private int clock_sound = 0;
    private int clock_flash = 0;

    @Override
    public boolean opened() {
        return !closed;
    }

    @Override
    public boolean closed() {
        return closed;
    }

    @Override
    public boolean locked() {
        return locked;
    }

    @Override
    public boolean unlocked() {
        return !locked;
    }

    @Override
    public boolean flash() {
        return flash;
    }

    @Override
    public boolean sound() {
        return sound;
    }

    @Override
    public boolean armed() {
        return armed;
    }

    @Override
    public void lock() {
        locked = true;
        clock_armed = 0;
    }

    @Override
    public void unlock() {
        locked = false;
        armed = false;
        flash = false;
        sound = false;
    }

    @Override
    public void close() {
        closed = true;
        clock_armed = 0;
    }

    @Override
    public void open() {
        closed = false;
        clock_sound = 0;
        clock_flash = 0;

        if (armed) {
            sound = true;
            flash = true;
        }
    }

    @Override
    public void tick() {
        tick(1);
    }

    public void tick(int ticks) {
        clock_armed += ticks;
        clock_sound += ticks;
        clock_flash += ticks;

        if (clock_armed == 2 && closed && locked) {
            armed = true;
        }
        if (sound && clock_sound == 3) {
            sound = false;
        }
        if (flash && clock_flash == 30) {
            flash = false;
        }
    }

    public void printStates() {
        System.out.println("Closed: " + closed);
        System.out.println("Locked: " + locked);
        System.out.println("Flash: " + flash);
        System.out.println("Sound: " + sound);
        System.out.println("Armed: " + armed);
        System.out.println("Clock - armed: " + clock_armed);
        System.out.println("Clock - sound: " + clock_sound);
        System.out.println("Clock - flash: " + clock_flash);
        System.out.println();
        System.out.println("----------------------");
        System.out.println();
    }
}
