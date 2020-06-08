package View.ShoppingMenus.ProductsAndOffsMenus.SortsView;

import Interfaces.Sortable;
import View.Menu;
import exceptions.MenuException;
import model.Product;

import java.util.HashMap;

public class SortsMenu<E extends Sortable> extends Menu {
    E controller;

    public SortsMenu(Menu parentMenu, E controller) {
        super("Sorts Menu", parentMenu);
        this.controller = controller;
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getShowSorts());
        submenus.put(2, getSortByField());
        submenus.put(3, getShowCurrentSort());
        submenus.put(4, getDisableSort());
        setSubMenus(submenus);
    }

    public Menu getShowSorts(){
        return new Menu("Show available sorts", this) {
            @Override
            public void show() {
                for (String sort : controller.getAvailableSorts()) {
                    System.out.println(sort);
                }
                System.out.println("Enter anything to return.");
            }

            @Override
            public void execute() {
                scanner.nextLine();
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getSortByField(){
        return new Menu("Sort by field", this) {
            @Override
            public void show() {
                System.out.println("Enter a field to sort:");
            }

            @Override
            public void execute() {
                String field = scanner.nextLine();
                try {
                    for (Product product : controller.addSort(field)) {
                        System.out.println(product);
                        System.out.println("-----------------------------------");
                    }
                } catch (MenuException e) {
                    System.out.println(e.getMessage());
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getShowCurrentSort(){
        return new Menu("Show current sort", this) {
            @Override
            public void show() {
                System.out.println(controller.getCurrentSort());
                System.out.println("Enter anything to return.");
            }

            @Override
            public void execute() {
                scanner.nextLine();
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };    }

    public Menu getDisableSort() {
        return new Menu("Disable sort", this) {
            @Override
            public void show() {
                System.out.println("Enter anything to return.");
            }

            @Override
            public void execute() {
                controller.disableSort();
                scanner.nextLine();
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }
}