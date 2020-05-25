package Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

public class MinesGame {
    private static int width = 0;
    private static int height = 0 ;
    private static int mines = 0 ;
    public static String[][] backArray;
    private static String[][] GUI;
    private static String[][] proverka;
    private static int x_parameter;
    private static int y_parameter;
    private static boolean first;
    public static void main(String[] args) throws IOException, InterruptedException {

        EnterInformation();
        StartInitialize();
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        StartGame();
    }

    private static void StartGame() {
        ShowMap();
        System.out.println("1. Check");
        System.out.println("2. Disable");
        System.out.println("3. Move");
        Scanner scan  = new Scanner(System.in);
        String oprion = scan.next();


        System.out.println("Please eneter coordinates of cell with ',' between numbers");
        String inputNUmbers = scan.next();
        if (inputNUmbers.indexOf(",") == -1)
        {
            StartGame();
        }
        else
        {
            String[] input_string_parameters = inputNUmbers.split(",");
            if (Integer.valueOf(input_string_parameters[0]) > width-1 ||
                    Integer.valueOf(input_string_parameters[0]) <0 ||
                    Integer.valueOf(input_string_parameters[1]) > height-1 ||
                    Integer.valueOf(input_string_parameters[1]) <0 )
            {
                StartGame();
                return;
            }
            else
            {
                if (backArray[Integer.valueOf(input_string_parameters[1])][Integer.valueOf(input_string_parameters[0])] == "F")
                {
                    System.out.println("You win");
                    return;
                }
                if (Integer.valueOf(oprion )== 1)
                {
                    SearchCells(Integer.valueOf(input_string_parameters[0]) ,Integer.valueOf(input_string_parameters[1]));
                    StartGame();
                    return;
                }
                else if (Integer.valueOf(oprion )== 2 )
                {

                    x_parameter = Integer.valueOf(input_string_parameters[0]);
                    y_parameter = Integer.valueOf(input_string_parameters[1]);
                    backArray[Integer.valueOf(input_string_parameters[1])][Integer.valueOf(input_string_parameters[0])] = "*";
                    StartGame();
                    return;
                }
                else
                {
                    x_parameter = Integer.valueOf(input_string_parameters[0]);
                    y_parameter = Integer.valueOf(input_string_parameters[1]);
                    if (backArray[Integer.valueOf(input_string_parameters[1])][Integer.valueOf(input_string_parameters[0])] == "B"){
                        return;
                    }
                    else {
                        backArray[Integer.valueOf(input_string_parameters[1])][Integer.valueOf(input_string_parameters[0])] = "*";
                        StartGame();
                        return;
                    }
                }
            }

        }

    }

    private static void ShowMap() {
        GUI = new String[width][height];
        for (int i = 0 ; i < height ; i++)
        {
            for (int j = 0 ; j < width; j++)
            {
                if (proverka[i][j] != "M")
                {
                    GUI[i][j] = proverka[i][j];
                }
                else {
                    if (backArray[i][j] == "B") {
                        GUI[i][j] = "X";
                    } else {
                        GUI[i][j] = backArray[i][j];
                    }
                }
            }
        }
        for (int i = 0 ; i < height ; i++)
        {
            for (int j = 0 ; j < width ; j++)
            {
                System.out.print(GUI[i][j] + "|");

            }
            System.out.print("\n");
        }
        // nulirane na tyrsenite pozicij
        for (int i = 0 ; i < height ; i++)
        {
            for (int j = 0 ; j < width; j++)
            {
                proverka[i][j] = "M";

            }
        }
    }

    private static void SearchCells(Integer x_from_console, Integer y_from_console) {
        // -1
        int x_parameter_relative = y_parameter - 1;
        int y_parameter_relative = x_parameter - 1;
        if (y_parameter_relative >= 0) {
            if (Check(x_parameter_relative, y_parameter_relative)) {
                proverka[x_parameter_relative][y_parameter_relative] =
                        backArray[x_parameter_relative][y_parameter_relative];
            }
            if (Check(x_parameter_relative, y_parameter_relative)) {
                x_parameter_relative = x_parameter;
                proverka[x_parameter_relative][y_parameter_relative] =
                        backArray[x_parameter_relative][y_parameter_relative];
            }
            if (Check(x_parameter_relative, y_parameter_relative)) {
                x_parameter_relative = x_parameter + 1;
                proverka[x_parameter_relative][y_parameter_relative] =
                        backArray[x_parameter_relative][y_parameter_relative];
            }
        }
        x_parameter_relative = x_parameter -1;
        y_parameter_relative = y_parameter ;
        if (Check(x_parameter_relative, y_parameter_relative)) {
            proverka[x_parameter_relative][y_parameter_relative] =
                    backArray[x_parameter_relative][y_parameter_relative];
        }
        x_parameter_relative = x_parameter;
        if (Check(x_parameter_relative, y_parameter_relative)) {
            proverka[x_parameter_relative][y_parameter_relative] =
                    backArray[x_parameter_relative][y_parameter_relative];
        }
        x_parameter_relative = x_parameter+ 1;
        if (Check(x_parameter_relative, y_parameter_relative)) {
            proverka[x_parameter_relative][y_parameter_relative] =
                    backArray[x_parameter_relative][y_parameter_relative];
        }
        // +1

        x_parameter_relative = x_parameter - 1;
        y_parameter_relative = y_parameter + 1;
        if (y_parameter_relative <= height) {
            if (Check(x_parameter_relative, y_parameter_relative)) {
                proverka[x_parameter_relative][y_parameter_relative] =
                        backArray[x_parameter_relative][y_parameter_relative];
            }
            x_parameter_relative = x_parameter;
            if (Check(x_parameter_relative, y_parameter_relative)) {
                proverka[x_parameter_relative][y_parameter_relative] =
                        backArray[x_parameter_relative][y_parameter_relative];
            }
            x_parameter_relative = x_parameter+ 1;
            if (Check(x_parameter_relative, y_parameter_relative)) {
                proverka[x_parameter_relative][y_parameter_relative] =
                        backArray[x_parameter_relative][y_parameter_relative];
            }
        }
    }

    private static boolean Check(int x_parameter_relative, int y_parameter_relative) {
        if ((x_parameter_relative >= 0 && x_parameter_relative <= width-1) && (y_parameter_relative >= 0
                && y_parameter_relative <= height-1))
        {
            return  true;
        }
        else
        {
            return  false;
        }
    }

    private static void StartInitialize() {
        backArray = new String[width][height];
        GUI = new String[width][height];
        proverka = new String[width][height];
        int counter = 1;
        for (int i = 0 ; i < height ; i++)
        {
            for (int j = 0 ; j < width ; j++)
            {
                backArray[i][j] = "X";
                GUI[i][j]  = "X";
                proverka[i][j] = "M";
                counter++;
            }
        }

        // setstratEnd
        Random rand = new Random();
        int F_parameter = 0 ;
        int F_second_parameter = 0 ;
        int S_parameter = 0 ;
        int S_second_parameter = 0 ;


        if (rand.nextInt(100) <= 50)
        {
            S_parameter = 0 ;
            F_parameter =width-1;
        }
        else
        {
            S_parameter = width-1;
            F_parameter= 0;
        }

        if (rand.nextInt(100) <= 50)
        {
            S_second_parameter = 0 ;
            F_second_parameter =height-1;
        }
        else
        {
            S_second_parameter = height-1;
            F_second_parameter =0;
        }

        GUI[S_parameter][S_second_parameter] = "S";
        GUI[F_parameter][F_second_parameter] = "F";
        backArray[S_parameter][S_second_parameter] = "S";
        backArray[F_parameter][F_second_parameter] = "F";
        x_parameter = S_parameter;
        y_parameter = S_second_parameter;
        int cunter = 0 ;

        for (int i = 0 ; i < mines ; i++) {
            PlaceBombs();
        }

    }

    private static void PlaceBombs() {
        Random rand = new Random();
        int x;
        int y;
        x = rand.nextInt(width) -1;
        y = rand.nextInt(height) -1;

        if (x <0 )
        {
            x = 0 ;
        }
        if (y <= 0)
        {
            y = 0;
        }

        if (backArray[x][y] == "F" ||  backArray[x][y] == "S")
        {
            PlaceBombs();
            return;
        }

        if (backArray[x][y] == "B")
        {
            PlaceBombs();
            return;
        }
        else
        {
            backArray[x][y] = "B";
            return;
        }
    }

    private static void EnterInformation() throws IOException {
        String fileName = "C:\\Users\\Lenovo\\Homework7\\src\\information.txt";
        File file = new File(fileName);

        ArrayList<String> information_from_file = new ArrayList<String>();
        Stream<String> linesStream = Files.lines(file.toPath());
        int count = 0;
        linesStream.forEach(information_from_file::add);

        for (String  s: information_from_file) {
            if (s.contains("width"))
            {
                width = Integer.valueOf(s.substring(s.indexOf("=")+1).trim());
            }
            if (s.contains("height"))
            {
                height = Integer.valueOf(s.substring(s.indexOf("=")+1).trim());
            }
            if (s.contains("mines"))
            {
                mines = Integer.valueOf(s.substring(s.indexOf("=")+1).trim());
            }
        }

    }

}

