package ua.andrew1903;

import java.util.Scanner;

public class Main {
    private final static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        Game game = new Game(4, 3, 1);
        game.start(scan);
    }
}
