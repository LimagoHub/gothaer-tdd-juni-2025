package org.example;


import lombok.RequiredArgsConstructor;
import org.example.io.Writer;

import java.util.List;

@RequiredArgsConstructor
public class ComputerPlayer  {

    private static final int TURNS [] = {3,1,1,2};

    private final Writer writer;
    /*
        wenn anzahl der Steine durch 4 einen Rest von 0 hat nimm 3 Steine
        wenn anzahl der Steine durch 4 einen Rest von 1 hat nimm 1 Steine
        wenn anzahl der Steine durch 4 einen Rest von 2 hat nimm 1 Steine
        wenn anzahl der Steine durch 4 einen Rest von 3 hat nimm 2 Steine
     */

    public Integer doTurn(Integer board) {
        int turn = TURNS[board % 4];
        writer.write(String.format("Computer nimmt %s Steine.", turn));
        return turn;
    }

}
