import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Sodoku
{
    // global variables and stuff
    Random rand = new Random();
    int[][] board = new int[9][9];
    int[][] copy = new int[9][9];
    ArrayList[][] possibleValues;

    // constructor for no input
    public Sodoku() {
        // null board (technically it's all 0)
    }

    // constructor that accounts for input.
    public Sodoku(File f) throws IOException {
        Scanner scan = new Scanner(f);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board[r][c] = scan.nextInt();
            }
        }
    }

    // returns the solved sodoku board
    // returns a 9x9 array
    // brute force algorithm
    // random number generating for each blank square
    // just curious to see how long would take to compile
    // super inefficient
    public int[][] solveRandomBruteForce() {
        int[][] copy = new int[9][9];
        int iterations = 0;

        // while board isnt solved
        while (!allBoxesValid(copy) && !allRowsAndColsValid(copy)) {
            // make copy of board
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    copy[r][c] = board[r][c];
                }
            }

            // replace 0's with random numbers from 1-9
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (copy[r][c] == 0) {
                        copy[r][c] = rand.nextInt(9) + 1;
                    }
                }
            }
            iterations++;
            // System.out.println("iterations" + iterations);
        }

        // if copy is valid solution, return it.
        System.out.println("total iterations: " + iterations);
        return copy;
    }

    // need a more efficient algorithm to solve more complex puzzles
    // TODO: write this algorithm that only lets the program guess valid numbers for blank squares.
    public int[][] solve()
    {
        // for each blank square, randomly guess possible values.
        // get list of possible numbers from row, col, and box
        // guess from that list, using random to choose the index of the list

        // create copy of board to make changes to
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                copy[r][c] = board[r][c];
            }
        }

        int iterations = 0;

        // get possible values for default board
        possibleValues = getPossibleValuesArray(board);


        while (!allBoxesValid(copy) && !allRowsAndColsValid(copy)) {
            // TODO:
            // get a 9x9 array that holds possible values for each square.
            // loop through all of the possibilities instead of using randomness to find the answer.
            // basically:
            // get possible values for each square.
            // if a square has only one possible value, use that value and put it into the square.
            // refresh the possible values for all squares.
            // repeat until answer is found.

            boolean flag = false;
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (copy[r][c] == 0) { // only do this if the square is empty
                        if (possibleValues[r][c].size() == 1) {
                            //System.out.println(r + " " + c + ": " + possibleValues[r][c]);
                            // insert single value into the square
                            copy[r][c] = (int) possibleValues[r][c].get(0);
                            flag = true;
                            //System.out.println(copy[r][c]);
                        }
                    }
                }
            }
            // refresh the possible values for all squares
            possibleValues = getPossibleValuesArray(copy);

            // if the puzzle doesn't let you eliminate one square at one point, you have
            // to choose one value and test if it works.
            // TODO: write this later
            if (flag) {
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        if (copy[r][c] == 0) {
                            if (possibleValues[r][c] [...])
                        }
                    }
                }
            }





            iterations++;
            System.out.println("iteration #" + iterations);

            // Steps: uncomment below
            //System.out.println(toString(copy));
        }

        System.out.println("total iterations: " + iterations);
        return copy;
    }

    public ArrayList[][] getPossibleValuesArray(int[][] b) {
        ArrayList[][] returnVal = new ArrayList[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (b[r][c] == 0) {
                    returnVal[r][c] = returnPossibleValues(returnNumbersFromRow(r, b),
                                                           returnNumbersFromCol(c, b),
                                                           returnNumbersFromBox(r, c, b));
                }
                else {
                    returnVal[r][c] = null;
                }
            }
        }

        return returnVal;
    }

    // method that returns a list of numbers in the row
    public ArrayList<Integer> returnNumbersFromRow(int rowIndex, int[][] b) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int c = 0; c < 9; c++) {
            nums.add(b[rowIndex][c]);
        }
        return nums;
    }

    // method that returns a list of numbers in the col
    public ArrayList<Integer> returnNumbersFromCol(int colIndex, int[][] b) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int r = 0; r < 9; r++) {
            nums.add(b[r][colIndex]);
        }
        return nums;
    }

    // method that returns a list of numbers in the 3x3 box
    public ArrayList<Integer> returnNumbersFromBox(int r, int c, int[][] b) {
        int rowIndex = -1, colIndex = -1;
        // first we see what box the square belongs to
        if (r <= 2 && c <= 2) { // 0,0
            rowIndex = 0;
            colIndex = 0;
        }
        else if (r <= 2 && c <= 5) { // 0,3
            rowIndex = 0;
            colIndex = 3;
        }
        else if (r <= 2 && c >= 6) { // 0,6
            rowIndex = 0;
            colIndex = 6;
        }
        else if (r <= 5 && c <= 2) { // 3,0
            rowIndex = 3;
            colIndex = 0;
        }
        else if (r <= 5 && c <= 5) { // 3,3
            rowIndex = 3;
            colIndex = 3;
        }
        else if (r <= 5 && c >= 6) { // 3,6
            rowIndex = 3;
            colIndex = 6;
        }
        else if (r >= 6 && c <= 2) { // 6,0
            rowIndex = 6;
            colIndex = 0;
        }
        else if (r >= 6 && c <= 5) { // 6,3
            rowIndex = 6;
            colIndex = 3;
        }
        else if (r >= 6 && c >= 6) { // 6,6
            rowIndex = 6;
            colIndex = 6;
        }

        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                nums.add(b[rowIndex + x][colIndex + y]);
            }
        }

        return nums;
    }
    // these three methods can be used to optimize the solving algorithm

    // takes the numbers used in the row, col, and boxes and finds what numbers are available for the tile.
    public ArrayList<Integer> returnPossibleValues(ArrayList<Integer> r,
                                                   ArrayList<Integer> c,
                                                   ArrayList<Integer> b) {
        ArrayList<Integer> returnVal = new ArrayList<Integer>();
        for (int i = 1; i < 10; i++) {
            if (!r.contains(i) && !c.contains(i) && !b.contains(i)) {
                returnVal.add(i);
            }
        }
        return returnVal;
    }

    // answer checking methods:
    // method for checking each 3x3 box's validity
    // for indexes, use top left box.
    // 0,0 0,3 0,6
    // 3,0 3,3 3,6
    // 6,0 6,3 6,6
    public boolean boxIsValid(int rowIndex, int colIndex, int[][] b) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                nums.add(b[rowIndex + x][colIndex + y]);
            }
        }
        return (nums.contains(1) &&
                nums.contains(2) &&
                nums.contains(3) &&
                nums.contains(4) &&
                nums.contains(5) &&
                nums.contains(6) &&
                nums.contains(7) &&
                nums.contains(8) &&
                nums.contains(9));
    }

    // returns true is all boxes are valid
    public boolean allBoxesValid(int[][] b) {
        for (int x = 0; x < 7; x = x + 3) {
            for (int y = 0; y < 7; y = y + 3) {
                if (!boxIsValid(x, y, b)) {
                    return false;
                }
            }
        }
        return true;
    }

    // method for checking an individual row's validity
    public boolean rowIsValid(int rowIndex, int[][] b) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int c = 0; c < 9; c++) {
            nums.add(b[rowIndex][c]);
        }
        return (nums.contains(1) &&
                nums.contains(2) &&
                nums.contains(3) &&
                nums.contains(4) &&
                nums.contains(5) &&
                nums.contains(6) &&
                nums.contains(7) &&
                nums.contains(8) &&
                nums.contains(9));
    }

    // method for checking an individual column's validity
    public boolean colIsValid(int colIndex, int[][] b) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int r = 0; r < 9; r++) {
            nums.add(b[r][colIndex]);
        }
        return (nums.contains(1) &&
                nums.contains(2) &&
                nums.contains(3) &&
                nums.contains(4) &&
                nums.contains(5) &&
                nums.contains(6) &&
                nums.contains(7) &&
                nums.contains(8) &&
                nums.contains(9));
    }

    // checks all rows and columns to see if they are valid
    public boolean allRowsAndColsValid(int[][] b) {
        for (int r = 0; r < 9; r++) {
            if (!rowIsValid(r, b)) {
                return false;
            }
        }
        for (int c = 0; c < 9; c++) {
            if (!colIsValid(c, b)) {
                return false;
            }
        }
        return true;
    }

    // Prints board provided
    public String toString(int[][] b) {
        String returnVal = "";

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (((c + 1) % 3) == 0 && c != 8) { // have border
                    if (b[r][c] == 0) {
                        returnVal += "  | ";
                    }
                    else {
                        returnVal += b[r][c] + " | ";
                    }
                }
                else { // don't add border
                    if (b[r][c] == 0) {
                        returnVal += "  ";
                    }
                    else {
                        returnVal += b[r][c] + " ";
                    }
                }
            }

            if ((r + 1) % 3 == 0 && r != 8) {
                returnVal += "\n------+-------+------\n";
            }
            else {
                returnVal += "\n";
            }
        }

        return returnVal;
    }
}