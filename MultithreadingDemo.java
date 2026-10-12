import java.util.ArrayList;
import java.util.List;

class TicketBooking {
    private final String passengerName;

    TicketBooking(String passengerName) {
        if (passengerName == null || passengerName.isBlank()) {
            throw new IllegalArgumentException("Passenger name cannot be empty.");
        }
        this.passengerName = passengerName;
    }

    void bookTicket() {
        try {
            Thread.sleep(100); // Simulate booking work
            System.out.printf(
                "Passenger: %s | Status: Booked | Thread: %s | ID: %d%n",
                passengerName,
                Thread.currentThread().getName(),
                Thread.currentThread().getId()
            );
        } catch (InterruptedException e) {
            System.out.printf(
                "Passenger: %s | Status: Booking interrupted | Thread: %s%n",
                passengerName,
                Thread.currentThread().getName()
            );
            Thread.currentThread().interrupt();
        }
    }
}

class BookingThread extends Thread {
    private final TicketBooking booking;

    BookingThread(TicketBooking booking, String threadName) {
        super(threadName);
        this.booking = booking;
    }

    @Override
    public void run() {
        booking.bookTicket();
    }
}

class BookingRunnable implements Runnable {
    private final TicketBooking booking;

    BookingRunnable(TicketBooking booking) {
        this.booking = booking;
    }

    @Override
    public void run() {
        booking.bookTicket();
    }
}

public class MultithreadingDemo {
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();

        // Thread created by extending Thread
        threads.add(new BookingThread(
            new TicketBooking("Rahul"), "Thread-Rahul"));

        // Threads created by implementing Runnable
        threads.add(new Thread(
            new BookingRunnable(new TicketBooking("Priya")), "Thread-Priya"));
        threads.add(new Thread(
            new BookingRunnable(new TicketBooking("Aman")), "Thread-Aman"));
        threads.add(new Thread(
            new BookingRunnable(new TicketBooking("Neha")), "Thread-Neha"));

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Main thread was interrupted.");
                return;
            }
        }

        System.out.println("All passenger booking requests have been processed.");
    }
}