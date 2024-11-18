import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ReaderWriter {
    private int readCount = 0;
    private int sharedData = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition writeCondition = lock.newCondition();

    public void read(int readerId) throws InterruptedException {
        lock.lock();
        try {
            readCount++;
            if (readCount == 1) {
                writeCondition.await();
            }
        } finally {
            lock.unlock();
        }

        System.out.println("Reader " + readerId + ": Reading the shared data: " + sharedData);
        Thread.sleep(1000);

        lock.lock();
        try {
            readCount--;
            if (readCount == 0) {
                writeCondition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void write(int writerId) throws InterruptedException {
        lock.lock();
        try {
            while (readCount > 0) {
                writeCondition.await();
            }

            sharedData++;
            System.out.println("Writer " + writerId + ": Writing to the shared data. New value: " + sharedData);
            Thread.sleep(1000);
            writeCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

class Reader implements Runnable {
    private final ReaderWriter readerWriter;
    private final int id;

    public Reader(ReaderWriter readerWriter, int id) {
        this.readerWriter = readerWriter;
        this.id = id;
    }

    public void run() {
        try {
            readerWriter.read(id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Writer implements Runnable {
    private final ReaderWriter readerWriter;
    private final int id;

    public Writer(ReaderWriter readerWriter, int id) {
        this.readerWriter = readerWriter;
        this.id = id;
    }

    public void run() {
        try {
            readerWriter.write(id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ReaderWriter readerWriter = new ReaderWriter();
        Thread[] readers = new Thread[5];
        Thread[] writers = new Thread[3];

        for (int i = 0; i < 5; i++) {
            readers[i] = new Thread(new Reader(readerWriter, i + 1));
            readers[i].start();
        }

        for (int i = 0; i < 3; i++) {
            writers[i] = new Thread(new Writer(readerWriter, i + 1));
            writers[i].start();
        }

        for (Thread reader : readers) {
            try {
                reader.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        for (Thread writer : writers) {
            try {
                writer.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}