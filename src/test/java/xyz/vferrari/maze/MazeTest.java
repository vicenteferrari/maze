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

        String output = "1 3\n" + "2 4\n" + "5 6\n" + "7 8 9 10 11 12 13";

        assertEquals(output, actual);
    }

//    @Test
//    public void test() {
//        System.out.println(new Maze("13\n" +
//                "2 2 4\n" +
//                "3 1 3 5\n" +
//                "2 2 4\n" +
//                "3 1 3 6\n" +
//                "2 2 6\n" +
//                "2 4 5\n" +
//                "2 8 9\n" +
//                "2 7 9\n" +
//                "2 7 8\n" +
//                "2 11 13\n" +
//                "2 10 12\n" +
//                "2 11 13\n" +
//                "2 10 12").toString());
//    }
}
