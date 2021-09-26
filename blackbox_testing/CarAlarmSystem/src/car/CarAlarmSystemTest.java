package car;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CarAlarmSystemTest {
    private CarAlarmSystem cas;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        cas = new CarAlarmSystem();
    }

    @Test
    void opened_called_after_call_to_close() {
        cas.close();
        assertFalse(cas.opened());
    }

    @Test
    void opened_called_after_call_to_open() {
        cas.open();
        assertTrue(cas.opened());
    }

    @Test
    void closed_called_after_call_to_close() {
        cas.close();
        assertTrue(cas.closed());
    }

    @Test
    void closed_called_after_call_to_open() {
        cas.open();
        assertFalse(cas.closed());
    }

    @Test
    void locked() {
        cas.lock();
        assertTrue(cas.locked());
    }

    @Test
    void lock_called_twice_locks_the_cas() {
        cas.lock();
        cas.lock();
        assertTrue(cas.locked());
    }

    @Test
    void lock_locks_cas() {
        cas.lock();
        assertTrue(cas.locked());
    }

    @Test
    void unlock_called_twice_unlocks_the_cas() {
        cas.unlock();
        cas.unlock();
        assertTrue(cas.unlocked());
    }

    @Test
    void tick_without_parameter_ticks_clocks_by_one() {
        cas.tick();

        assertArrayEquals(cas.getClocks(), new int[]{1, 1, 1});
    }

    @Test
    void tick_passing_0_will_not_change_clocks_without_previous_tick() {
        cas.tick(0);

        assertArrayEquals(cas.getClocks(), new int[]{0, 0, 0});
    }

    @Test
    void tick_passing_0_will_not_change_clocks_after_a_previous_tick() {
        cas.tick();

        cas.tick(0);

        assertArrayEquals(cas.getClocks(), new int[]{1, 1, 1});
    }

    @Test
    void tick_taking_one_hundred_steps_after_the_cas_is_closed_and_locked_will_result_in_armed() {
        cas.close();
        cas.lock();

        cas.tick(100);

        assertTrue(cas.armed());
    }

    @Test
    void tick_passing_char_as_input_to_tick_keyword_leaves_ticks_unchanged() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("t a\n".getBytes());

        try {
            System.setIn(in);
            Main.main(new String[]{});
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setIn(sysInBackup);

        assertArrayEquals(new int[]{0, 0, 0}, cas.getClocks());
    }

    @Test
    void tick_passing_zero_keeps_clocks_at_zero() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("t 0\n".getBytes());

        try {
            System.setIn(in);
            Main.main(new String[]{});
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setIn(sysInBackup);

        assertArrayEquals(new int[]{0, 0, 0}, cas.getClocks());
    }

    @Test
    void tick_passing_max_integer_counts_up_clocks_to_one_thousand() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream(("t " + Integer.MAX_VALUE + "\n").getBytes());

        try {
            System.setIn(in);
            Main.main(new String[]{});
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setIn(sysInBackup);

        System.out.println(Arrays.toString(cas.getClocks()));

        assertArrayEquals(new int[]{1000, 1000, 1000}, cas.getClocks());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }
}