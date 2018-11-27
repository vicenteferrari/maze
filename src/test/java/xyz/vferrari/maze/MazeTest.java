package xyz.vferrari.maze;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MazeTest {
    @Test
    public void mazeTest1() {
        String input = "13\n" +
                "2 2 4\n" +
                "3 1 3 5\n" +
                "2 2 4\n" +
                "3 1 3 6\n" +
                "2 2 6\n" +
                "2 4 5\n" +
                "2 8 9\n" +
                "2 7 9\n" +
                "2 7 8\n" +
                "2 11 13\n" +
                "2 10 12\n" +
                "2 11 13\n" +
                "2 10 12";

        Maze maze = new Maze(input);
        String actual = maze.toString();
        System.out.println("Solucion: " + actual);
        String output = "1 3\n" + "2 4\n" + "5 6\n" + "7 8 9 10 11 12 13";

        assertEquals(output, actual);
    }

    @Test
    public void mazeTest2() {
        String input = "6\n" +
                "3 3 4 5\n" +
                "0\n" +
                "1 1\n" +
                "1 1\n" +
                "2 1 6\n" +
                "1 5";

        Maze maze = new Maze(input);
        String actual = maze.toString();
        System.out.println("Solucion: " + actual);
        String output = "none";

        assertEquals(output, actual);
    }
}
