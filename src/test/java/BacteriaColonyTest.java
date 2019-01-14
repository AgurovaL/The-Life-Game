import com.agurova.BacteriaColony;
import org.junit.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class BacteriaColonyTest {

    private BacteriaColony bacteriaColony;
    private int iterations;
    private int height;
    private int width;

    @Before
    public void setUp() {
        height = 5;
        width = 5;
       iterations = 5;
       bacteriaColony = new BacteriaColony(height, width);
    }

    @After
    public void tearDown() {
        bacteriaColony.setZeroMatrix();
    }

    @Test
    public void serRandomMatrixTest() {
        bacteriaColony.setRandomMatrix();
        assertNotNull(bacteriaColony.matrix);
    }

    @Test
    public void setZeroMatrixTest(){
        bacteriaColony.setZeroMatrix();
        for (int i = 0; i < bacteriaColony.getHeight(); i++) {
            for (int j = 0; j < bacteriaColony.getWidth(); j++) {
                assertEquals(bacteriaColony.matrix[i][j] ,0);
            }
        }
    }

    @Test
    public void countNeighboursTest0() {
        bacteriaColony.readColonyMatrix();
        assertEquals(2, bacteriaColony.countNeighbours(0,0) );
    }

    @Test
    public void countNeighboursTest1() {
        bacteriaColony.readColonyMatrix();
        assertEquals(4, bacteriaColony.countNeighbours(3,2) );
    }

    @Test
    public void countNeighboursTest2() {
        bacteriaColony.readColonyMatrix();
        assertEquals(3, bacteriaColony.countNeighbours(1,1) );
    }

    @Test
    public void countNeighboursTest3() {
        bacteriaColony.readColonyMatrix();
        assertEquals(2, bacteriaColony.countNeighbours(4,4) );
    }

    @Test
    public void updateTryTest() {
        bacteriaColony.readColonyMatrix();
        bacteriaColony.updateColonyMatrix();
    }

    @Test
    public void updateWithIterarionsTest() {
        bacteriaColony.setZeroMatrix();
        for (int i=0;i<iterations; i++){
            bacteriaColony.updateColonyMatrix();
        }
    }

}

