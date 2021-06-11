package dk.dtu.compute.se.pisd.roborally.model;

/**
 * Model for handling direction
 *
 * @author S164539
 * @author S154780
 * @author S205472
 * @author S194612
 */

public enum Direction {

    Right, Left;

    /**
     * Returns next direction of value
     *
     * @return next
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    public Direction next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    /**
     * Returns previous direction of value
     *
     * @return prev
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    public Direction prev() {
        return values()[(this.ordinal() + values().length - 1) % values().length];
    }
}
