package callOfDuty;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for JUnit testing the Base Class
 * @author josephpoirier
 *
 */
class BaseTest {

    Base base;
    Armory armory;
    Barrack barrack;
    SentryTower st;
    Tank tank;
    OilDrum od;

    @BeforeEach
    void setUp() throws Exception {

        base = new Base();

        armory = new Armory(base);
        base.placeTargetAt(armory, 0, 0, true);

        barrack = new Barrack(base);
        base.placeTargetAt(barrack, 0, 4, true);

        st = new SentryTower(base);
        base.placeTargetAt(st, 2, 4, true);

        tank = new Tank(base);
        base.placeTargetAt(tank, 1, 3, true);

        od = new OilDrum(base);
        base.placeTargetAt(od, 2, 1, true);
    }

    @Test
    void testBase() {
        assertEquals(10, base.getTargetsArray().length);

        // TODO: add more cases
        assertEquals(10, base.getTargetsArray()[0].length);
        assertEquals("ARMORY", base.getTargetsArray()[0][0].getTargetName());
        assertEquals(0, base.getTargetsArray()[0][0].getCoordinate()[0]);
        
    }

    @Test
    void testPlaceAllTargetRandomly() {
        this.base = new Base();
        base.placeAllTargetRandomly();
        List<Target> list = new ArrayList<Target>();
        int headQuarterCount = 0;
        int armoryCount = 0;
        int barracksCount = 0;
        int sentryCount = 0;
        int tanksCount = 0;
        int odCount = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (base.getTargetsArray()[i][j].getTargetName() != "GROUND") {
                    if (!list.contains(base.getTargetsArray()[i][j])) {
                        list.add(base.getTargetsArray()[i][j]);
                        switch (base.getTargetsArray()[i][j].getTargetName().toLowerCase()) {
                        case "armory": {
                            armoryCount++;
                            break;
                        }
                        case "headquarter": {
                            headQuarterCount++;
                            break;
                        }
                        case "barrack": {
                            barracksCount++;
                            break;
                        }
                        case "sentrytower": {
                            sentryCount++;
                            break;
                        }
                        case "tank": {
                            tanksCount++;
                            break;
                        }
                        case "oildrum": {
                            odCount++;
                            break;
                        }
                        }
                    }
                }
            }
        }
        assertEquals(list.size(), 18);

        assertEquals(1, headQuarterCount);
        assertEquals(2, armoryCount);
        assertEquals(3, barracksCount);
        assertEquals(4, sentryCount);
        assertEquals(4, tanksCount);
        assertEquals(4, odCount);
    }

    @Test
    void testOkToPlaceTargetAt() {
        assertFalse(this.base.okToPlaceTargetAt(new Armory(this.base), 1, 7, false));
        assertFalse(this.base.okToPlaceTargetAt(new Armory(this.base), 1, 8, true));
        assertTrue(this.base.okToPlaceTargetAt(new Armory(this.base), 1, 8, false));

        // TODO: add more cases
        assertTrue(this.base.okToPlaceTargetAt(new OilDrum(this.base), 3, 8, false));
        assertFalse(this.base.okToPlaceTargetAt(new Barrack(this.base), 1, 2, false));
        assertTrue(this.base.okToPlaceTargetAt(new Tank(this.base), 7, 6, false));
        
    }
    
    

    @Test
    void testPlaceTargetAt() {
        Target armory = new Armory(base);
        this.base.placeTargetAt(armory, 5, 5, false);
        assertEquals(5, armory.getCoordinate()[0]);
        assertEquals(5, armory.getCoordinate()[1]);
        assertEquals(3, armory.getHit().length);
        assertEquals(2, armory.getHit()[0].length);
        
        Target oilDrum = new OilDrum(base);
        this.base.placeTargetAt(oilDrum, 8, 2, true);
        // TODO: add more cases
        assertEquals(8, oilDrum.getCoordinate()[0]);
        assertEquals(2, oilDrum.getCoordinate()[1]);
        assertEquals(1, oilDrum.getHit().length);
        assertEquals(1, oilDrum.getHit()[0].length);
        
    }
    
    
    @Test
    void testIsOccupied() {

        Armory arm = new Armory(base);
        this.base.placeTargetAt(arm, 0, 0, true);
        assertTrue(base.isOccupied(0, 0));

        // TODO: add more cases
        assertTrue(base.isOccupied(0, 1));
        assertTrue(base.isOccupied(1, 1));
        assertTrue(base.isOccupied(2, 1));
        
    }

    @Test
    void testShootAt() {

        Armory arm = new Armory(base);
        this.base.placeTargetAt(arm, 5, 5, true);

        base.shootAt(5, 5);
        assertTrue(arm.isHitAt(5, 5));

        // TODO: add more cases
        base.shootAt(6, 5);
        assertTrue(arm.isHitAt(6, 5));
        base.shootAt(6, 6);
        assertTrue(arm.isHitAt(6, 6));
        base.shootAt(5, 6);
        assertTrue(arm.isHitAt(5, 6));

    }

    @Test
    void testIsGameOver() {

        assertFalse(base.isGameOver(new RocketLauncher(), new Missile()));

        // TODO: add more cases
        Weapon rocketLauncher = new RocketLauncher();
        Weapon missileLauncher = new Missile();
        
        rocketLauncher.setShotLeft(0);
        assertFalse(base.isGameOver(rocketLauncher,  missileLauncher));
        
    }

    @Test
    void testWin() {
        assertFalse(this.base.win());

        // TODO: add more cases
    }

    @Test
    void testIncrementAndSetShotsCount() {

        assertEquals(0, base.getShotsCount());
        base.incrementShotsCount();
        assertEquals(1, base.getShotsCount());

        // TODO: add more cases
        base.incrementShotsCount();
        assertEquals(2, base.getShotsCount());
        base.incrementShotsCount();
        assertEquals(3, base.getShotsCount());
        base.incrementShotsCount();
        assertEquals(4, base.getShotsCount());
    }

    @Test
    void testSetAndGetDestroyedTargetCount() {
        base.setDestroyedTargetCount(10);
        assertEquals(10, base.getDestroyedTargetCount());

    }

  

    @Test
    void testGetTargetsArray() {
        assertEquals(10, base.getTargetsArray().length);

    }


}
