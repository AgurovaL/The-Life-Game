import com.agurova.BacteriaColony;
import com.agurova.LifeFrame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

public class LifeFrameTest {
    private BacteriaColony bacteriaColony;
    private int iterations;
    private int height;
    private int width;

    @Before
    public void setUp() {
        iterations = 5;
        bacteriaColony = new BacteriaColony(height, width);
    }

    @After
    public void tearDown() {
        bacteriaColony.setZeroMatrix();
    }

    @Test
    public void createUITest() {
        for (int frameHeight=5, frameWidth=5 ; frameHeight<25 && frameWidth<25; frameHeight++, frameWidth++){
                new LifeFrame().createLifeGUI(frameHeight, frameWidth, iterations);
        }
    }

    @Test
    public void doLifeCircleTest() {
        LifeFrame lifeFrame = new LifeFrame();
        lifeFrame.createLifeGUI(25,25,5);
    }
}
