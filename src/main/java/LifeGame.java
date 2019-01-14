import com.agurova.LifeFrame;

public class LifeGame {

    public static void main(String[] args) {
        final int height = 25;//Integer.parseInt(args[0]);
        final int width = 25;//Integer.parseInt(args[1]);
        final int iterationsNumber = 5;//Integer.parseInt(args[2]);

       new LifeFrame().createLifeGUI(height, width, iterationsNumber);
    }
}
