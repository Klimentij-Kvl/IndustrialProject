package org.example.UIProcessor.ConsoleUI.Menus;

import org.example.UIProcessor.ConsoleUI.Commands.Command;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public abstract class Menu {
    protected Map<Integer, Command> options = new HashMap<>();
    protected Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        System.out.println(getMenuHeader());
        options.forEach((key, value) -> System.out.println(key + ": " + getOptionName(key)));
        System.out.println("0: Exit");
    }

    public void handleInput() {
        while (true) {
            System.out.println();
            displayMenu();
            System.out.print("Select an option: ");
            try {
                int choice = scanner.nextInt();
                if (choice == 0) {
                    System.out.println("Exiting...");
                    break;
                }
                Command command = options.get(choice);
                if (command != null) {
                    command.execute();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter a number between 0 and " + (options.size() + 1));
                scanner.nextLine();
            }
        }
    }
    protected abstract String getMenuHeader();

    protected abstract String getOptionName(int optionKey);
}
