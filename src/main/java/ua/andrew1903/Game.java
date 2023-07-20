package ua.andrew1903;

import java.util.*;

public class Game {
    private static final int COLORS = 4;
    private final List<Bottle> bottles;

    public Game(int bottlesNumber, int bottlesCapacity, int emptyBottles) {
        this.bottles = generateBottles(bottlesNumber, bottlesCapacity);
        this.bottles.addAll(Collections.nCopies(emptyBottles, new Bottle(bottlesCapacity)));
    }

    private List<Bottle> generateBottles(int bottlesNumber, int bottlesCapacity) {
        var bottles = new ArrayList<Bottle>();
        for (int i = 0; i < bottlesNumber; i++) {
            var liquids = new Stack<Integer>();
            for (int j = 0; j < COLORS; j++) {
                liquids.push(getRandomColor(0, COLORS));
            }
            bottles.add(new Bottle(bottlesCapacity, liquids));
        }
        return bottles;
    }

    private boolean transferLiquid(int firstBottleIndex, int secondBottleIndex) {
        if (firstBottleIndex < 0 || firstBottleIndex >= bottles.size() ||
                secondBottleIndex < 0 || secondBottleIndex >= bottles.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return transfer(firstBottleIndex, secondBottleIndex);
    }

    private boolean transfer(int fromIndex, int toIndex) {
        var to = bottles.get(toIndex);
        var from = bottles.get(fromIndex);
        if (!checkConditions(from, to)) {
            return false;
        }
        var pushed = to.push(from.pop());
        return pushed != -1;
    }

    private boolean checkConditions(Bottle from, Bottle to) {
        if (from == null || from.isEmpty()) {
            return false;
        }
        if (to == null || to.isFull()) {
            return false;
        }
        if (to.isEmpty()) {
            return true;
        }

        var fromColor = from.peek();
        var toColor = to.peek();
        return fromColor == toColor;
    }

    private String draw() {
        var res = new StringBuilder();
        for (var bottle: bottles) {
            var iterator = bottle.getLiquids();
            res.append("____________________")
                    .append("\n");

            while (iterator.hasNext()) {
                    res.append("|")
                            .append(iterator.next())
                            .append("  ");
            }
            res.append("\n").append("--------------------").append("\n");
            res.append("\n");
        }
        return res.toString();
    }

    private int getRandomColor(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void start(Scanner scan) {
        while (true) {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println(draw());
            System.out.print("""
                    1: transfer the color to another bottle
                    2: exit
                    """);
            System.out.print("ENTER NUMBER: ");
            var input = scan.nextLine();
            switch (input) {
                case "1" -> {
                    System.out.print("Enter the index of the first bottle: ");
                    var firstIndex = scan.nextInt();
                    System.out.print("Enter the index of the second bottle: ");
                    var secondIndex = scan.nextInt();
                    var transferRes = transferLiquid(firstIndex, secondIndex)
                            ? "Transferred"
                            : "Something went wrong";
                    System.out.println(transferRes);
                }
                case "2" -> {
                    return;
                }
            }
        }
    }
}
