package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Wall extends FieldAction {

    private Heading heading;

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        // TODO needs to be implemented
        //Player player = new Player();

        //Hvis players heading er den samme som eller modsat wall heading -> umuligt at skyde
        //Hvis player heading er modsat wall heading -> umuligt at flytte til wall space
        //I begge tilfælde undersøges forholdet mellem wall space og player space

        /*
        if player heading == wall heading || player heading == modsat wall heading
                if check space er something
                     skyd robot
                else
                    skyd umuligt
        else
             do nothing, continue


        if player heading == modsat wall heading
            if check space er something
                     flyt robot
                else
                    flytning umuligt
        else
             do nothing, continue

             Problem: hvordan skal space undersøges uden at benytte for mange if-sætninger?

             switch case
             pheading SOUTH & wheading SOUTH
              checkSpace

        */


        return false;
    }

}