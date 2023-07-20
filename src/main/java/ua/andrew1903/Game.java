package ua.andrew1903;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private static final int COLORS = 4;

    private final List<Bottle> bottles;

    public Game(int bottlesNumber, int bottlesCapacity, int emptyBottles) {
        this.bottles = generateBottles(bottlesNumber, bottlesCapacity);
        this.bottles.addAll(Collections.nCopies(emptyBottles, new Bottle(bottlesCapacity)));
    }

    private List<Bottle> generateBottles(int bottlesNumber, int bottlesCapacity) {
        return IntStream.range(0, bottlesNumber)
                .mapToObj(i -> new Bottle(bottlesCapacity, IntStream.range(0, COLORS)
                        .mapToObj(i1 -> getRandomColor(0, COLORS)).collect(Collectors.toCollection(Stack::new))))
                .toList();
    }

    private boolean transferLiquid(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= bottles.size() ||
                toIndex < 0 || toIndex >= bottles.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return transfer(fromIndex, toIndex);
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
        int fromColor = from.peek();
        int toColor = to.peek();
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
                    1: exit
                    2: transfer the color to another bottle
                    """);
            System.out.print("ENTER NUMBER: ");
            var input = scan.nextLine();
            if (input.equals("1")) {
                return;
            } else {
                System.out.print("Enter the index of the first bottle: ");
                var firstIndex = scan.nextInt();
                System.out.print("Enter the index of the second bottle: ");
                var secondIndex = scan.nextInt();
                var transferRes = transferLiquid(firstIndex, secondIndex)
                        ? "Transferred"
                        : "Something went wrong";
                System.out.println(transferRes);
            }
        }
    }
}
