package callOfDuty;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for JUnit testing Target and its subclasses
 * @author josephpoirier
 *
 */
class TargetTest {

    Base base;
    Armory armory;
    Barrack barrack;
    SentryTower st;
    Tank tank;
    OilDrum od;
    OilDrum od1;
    OilDrum od2;
    OilDrum od3;

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
        
        od1 = new OilDrum(base);
        base.placeTargetAt(od1, 9, 1, true);
        
        od2 = new OilDrum(base);
        base.placeTargetAt(od2, 9, 3, true);
        
        od3 = new OilDrum(base);
        base.placeTargetAt(od3, 9, 5, true);
        
    }

    @Test
    void testTarget() {

        // Armory
        assertEquals(2, armory.getHit().length);
        assertEquals(3, armory.getHit()[0].length);

        // Barrack
        assertEquals(1, barrack.getHit().length);
        assertEquals(3, barrack.getHit()[0].length);

        // TODO: add more cases
        
        // Tank
        assertEquals(1, tank.getHit().length);
        assertEquals(1, tank.getHit()[0].length);
        
        // Oil Drum
        assertEquals(1, od.getHit().length);
        assertEquals(1, od.getHit()[0].length);
    }

    @Test
    void testToString() {
        assertEquals("O", st.toString());
        assertEquals("T", tank.toString());

        // TODO: add more cases
        assertEquals("O", armory.toString());
        assertEquals("O", barrack.toString());
        assertEquals("O", od.toString());
    }

    @Test
    void testGetTargetName() {
        assertEquals("tank", tank.getTargetName().toLowerCase());
        assertEquals("sentrytower", st.getTargetName().toLowerCase());

        // TODO: add more cases
        assertEquals("oildrum", od.getTargetName().toLowerCase());
        assertEquals("armory", armory.getTargetName().toLowerCase());
        assertEquals("barrack", barrack.getTargetName().toLowerCase());
        
    }

    @Test
    void testExplode() {
        assertFalse(armory.isDestroyed());
        od.explode();
        assertTrue(armory.isDestroyed());

        // TODO: add more cases
        od1.explode();
        assertTrue(od1.isDestroyed());
        assertTrue(od2.isDestroyed());
        assertTrue(od3.isDestroyed());
    }


    @Test
    void testGetShot() {
        Target am = new Armory(this.base);
        assertTrue(this.base.okToPlaceTargetAt(am, 5, 5, false));
        this.base.placeTargetAt(am, 5, 5, false);
        am.getShot(5, 6);
        assertEquals(1, am.getHit()[0][1]);
        
        // TODO: add more cases
        am.getShot(5, 6);
        assertEquals(2, am.getHit()[0][1]);
        am.getShot(5, 5);
        assertEquals(1, am.getHit()[0][0]);
        am.getShot(5, 5);
        assertEquals(2, am.getHit()[0][0]);
    }

    @Test
    void testIsDestroyed() {
        assertFalse(od.isDestroyed());
        od.getShot(2, 1);
        assertTrue(od.isDestroyed());
        assertTrue(tank.isDestroyed());

        // TODO: add more cases
        assertTrue(armory.isDestroyed());
        assertFalse(barrack.isDestroyed());
        assertFalse(od3.isDestroyed());

    }

    @Test
    void testIsHitAt() {
        Target am = new Armory(this.base);
        assertTrue(this.base.okToPlaceTargetAt(am, 5, 5, false));
        this.base.placeTargetAt(am, 5, 5, true);
        assertFalse(am.isHitAt(5, 5));
        am.getShot(5, 5);
        assertTrue(am.isHitAt(5, 5));

        // TODO: add more cases
        assertFalse(am.isHitAt(5, 6));
        am.getShot(5, 6);
        assertTrue(am.isHitAt(5, 6));
        assertFalse(am.isHitAt(6, 6));
        
    }

}
