package dk.dtu.compute.se.pisd.roborally.model;
public enum Direction {

    Right, Left;

    public Direction next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public Direction prev() {
        return values()[(this.ordinal() + values().length - 1) % values().length];
    }
}
