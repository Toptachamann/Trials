
import parcs.*;

public class Runner implements AM {
    @Override
    public void run(AMInfo info) {

        long n = info.parent.readLong();
	System.out.printf("Received number = %d\n", n);
        long result;
        if (n == 1) {
            result = 1;
        } else {
            point p1 = info.createPoint();
            channel c1 = p1.createChannel();
            p1.execute("Runner");
            c1.write(n - 1);

            point p2 = info.createPoint();
            channel c2 = p2.createChannel();
            p2.execute("Runner");
            c2.write(n - 1);

            long r1 = c1.readLong();
            long r2 = c2.readLong();
            result = r1 + r2;
        }
        info.parent.write(result);
    }
}
