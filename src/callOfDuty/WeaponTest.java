package callOfDuty;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing Weapon and its subclasses,
 * RocketLauncher and Missile.
 * @author josephpoirier
 *
 */
class WeaponTest {

    Base base;
    Missile mis;
    RocketLauncher rl;

    @BeforeEach
    void setUp() throws Exception {

        base = new Base();
        

        mis = new Missile();
        rl = new RocketLauncher();
    }

    @Test
    void testWeapon() {
        assertEquals(3, mis.getShotLeft());

        // TODO: add more cases
        mis.shootAt(0, 0, base);
        assertEquals(2, mis.getShotLeft());
        mis.shootAt(0, 0, base);
        assertEquals(1, mis.getShotLeft());
        mis.shootAt(0, 0, base);
        mis.shootAt(0, 0, base);
        mis.shootAt(0, 0, base);
        assertEquals(0, mis.getShotLeft());
    }

    @Test
    void testGetWeaponType() {
        assertEquals("missile", mis.getWeaponType().toLowerCase());

        // TODO: add more cases
        assertEquals("rocket_launcher", rl.getWeaponType().toLowerCase());
    }

    
    @Test
    void testGetShotLeft() {
        mis.shootAt(0, 0, this.base);
        assertEquals(2, mis.getShotLeft());

        // TODO: add more cases
        rl.shootAt(0, 0, base);
        assertEquals(19, rl.getShotLeft());
        rl.shootAt(0, 0, base);
        assertEquals(18, rl.getShotLeft());
        rl.shootAt(0, 0, base);
        rl.shootAt(0, 0, base);
        rl.shootAt(0, 0, base);
        assertEquals(15, rl.getShotLeft());
    }

    @Test
    void testDecrementShotleft() {
        mis.decrementShotLeft();
        assertEquals(2, mis.getShotLeft());

        // TODO: add more cases
        mis.decrementShotLeft();
        assertEquals(1, mis.getShotLeft());
        mis.decrementShotLeft();
        assertEquals(0, mis.getShotLeft());
        mis.decrementShotLeft();
        assertEquals(-1, mis.getShotLeft());
        
    }

    @Test
    void testShootAt() {
        mis.shootAt(0, 0, this.base);
        assertTrue(base.getTargetsArray()[0][0].isHitAt(0, 0));
        assertEquals(1, base.getShotsCount());
        
        // TODO: add more cases
        rl.shootAt(5, 7, this.base);
        assertTrue(base.getTargetsArray()[5][7].isHitAt(5, 7));
        assertEquals(2, base.getShotsCount());
        
        rl.shootAt(5, 7, this.base);
        assertEquals(3, base.getShotsCount());
        
    }

}
