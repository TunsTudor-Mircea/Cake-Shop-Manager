package UI;

import Domain.BirthdayCake;
import Domain.Order;
import Service.*;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class UserInterface {
    private final BirthdayCakeService cakeService;
    private final OrderService orderService;

    public UserInterface() {
        cakeService = new BirthdayCakeService();
        orderService = new OrderService();
    }

    public UserInterface(BirthdayCakeService cakeService, OrderService orderService) {
        this.cakeService = cakeService;
        this.orderService = orderService;
    }

        public void show() {
            Scanner scanner = new Scanner(System.in);
            boolean flag = true;

            while (flag) {
                System.out.println("Welcome to the Cake-Shop!");
                System.out.println("[1] Manage Cake Shop");
                System.out.println("[2] Manage Orders");
                System.out.println("[0] Exit");
                System.out.print("Please enter your choice: ");

                String choice = scanner.next();

                System.out.println();

            switch (choice) {
                case "1": {
                    // Cake service
                    boolean cakeFlag = true;

                    while (cakeFlag) {
                        System.out.println("Cake-Shop Management Menu");
                        System.out.println("[1] Get all Cakes");
                        System.out.println("[2] Modify Cake List");
                        System.out.println("[3] Filter Cakes");
                        System.out.println("[4] Get Cake By ID");
                        System.out.println("[5] Get Reports");
                        System.out.println("[0] Back");
                        System.out.print("Please enter your choice: ");

                        String cakeChoice = scanner.next();

                        System.out.println();

                        switch (cakeChoice) {
                            case "1": {
                                // Print all cakes
                                try {
                                    for (BirthdayCake cake : cakeService.getAllBirthdayCakes())
                                        System.out.println(cake);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "2": {
                                // Modify Repository
                                boolean modifyFlag = true;

                                while (modifyFlag) {
                                    System.out.println("Modify Cake List");
                                    System.out.println("[1] Add a New Cake");
                                    System.out.println("[2] Remove a Cake");
                                    System.out.println("[3] Update a Cake");
                                    System.out.println("[4] Increase Price of Cake");
                                    System.out.println("[5] Decrease Price of Cake");
                                    System.out.println("[0] Back");
                                    System.out.print("Please enter your choice: ");

                                    String modifyChoice = scanner.next();
                                    System.out.println();

                                    switch (modifyChoice) {
                                        case "1": {
                                            // Add cake
                                            try {
                                                System.out.print("Enter the Cake size: ");
                                                int size = scanner.nextInt();
                                                System.out.println();

                                                System.out.print("Enter the Cake flavour: ");
                                                String flavour = scanner.next();
                                                System.out.println();

                                                System.out.print("Enter the number of candles: ");
                                                int candles = scanner.nextInt();
                                                System.out.println();

                                                System.out.print("Enter the price: ");
                                                double price = scanner.nextDouble();
                                                System.out.println();

                                                cakeService.addBirthdayCake(size, flavour, candles, price);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "2": {
                                            // Remove cake
                                            try {
                                                System.out.print("Enter the Cake ID: ");
                                                int id = scanner.nextInt();
                                                System.out.println();

                                                cakeService.removeBirthdayCake(id);
                                                orderService.removeInvalidOrders(id);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "3": {
                                            // Update cake by id
                                            try {
                                                System.out.print("Enter the Cake ID: ");
                                                int id = scanner.nextInt();
                                                System.out.println();

                                                System.out.print("Enter the New Cake size: ");
                                                int size = scanner.nextInt();
                                                System.out.println();

                                                System.out.print("Enter the New Cake flavour: ");
                                                String flavour = scanner.next();
                                                System.out.println();

                                                System.out.print("Enter the New number of candles: ");
                                                int candles = scanner.nextInt();
                                                System.out.println();

                                                System.out.print("Enter the New price: ");
                                                double price = scanner.nextDouble();
                                                System.out.println();

                                                cakeService.updateBirthdayCake(id, size, flavour, candles, price);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "4": {
                                            // Increase price of cake
                                            try {
                                                System.out.print("Enter the Cake ID: ");
                                                int id = scanner.nextInt();
                                                System.out.println();

                                                System.out.println("Enter the percentage by which to increase the price: ");
                                                double percentage = scanner.nextDouble();
                                                System.out.println();

                                                cakeService.increasePriceByPercent(id, percentage);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "5": {
                                            // Decrease price of cake
                                            try {
                                                System.out.print("Enter the Cake ID: ");
                                                int id = scanner.nextInt();
                                                System.out.println();

                                                System.out.println("Enter the percentage by which to decrease the price: ");
                                                double percentage = scanner.nextDouble();
                                                System.out.println();

                                                cakeService.decreasePriceByPercent(id, percentage);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "0": {
                                            // Back
                                            modifyFlag = false;
                                            break;
                                        }

                                        default: {
                                            System.out.println("Invalid Choice.");
                                            break;
                                        }
                                    }
                                }
                                break;
                            }

                            case "3": {
                                // Filter Repository
                                boolean filterFlag = true;

                                while (filterFlag) {
                                    System.out.println("Cake List Filter Menu");
                                    System.out.println("[1] Filter by Flavour");
                                    System.out.println("[2] Filter by Price");
                                    System.out.println("[0] Back");
                                    System.out.print("Please enter your choice: ");

                                    String filterChoice = scanner.next();
                                    System.out.println();

                                    switch (filterChoice) {
                                        case "1": {
                                            // Filter cakes by flavour
                                            try {
                                                System.out.print("Enter the Flavour: ");
                                                String flavour = scanner.next();
                                                System.out.println();

                                                for (BirthdayCake cake : cakeService.filterBirthdayCakesByFlavour(flavour))
                                                    System.out.println(cake);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "2": {
                                            // Filter cakes by price
                                            try {
                                                System.out.print("Enter the Price: ");
                                                double price = scanner.nextDouble();
                                                System.out.println();

                                                for (BirthdayCake cake : cakeService.filterBirthdayCakesByPrice(price))
                                                    System.out.println(cake);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "0": {
                                            // Back
                                            filterFlag = false;
                                            break;
                                        }

                                        default: {
                                            System.out.println("Invalid Choice.");
                                            break;
                                        }
                                    }
                                }

                                break;
                            }

                            case "4": {
                                // Get cake by id
                                try {
                                    System.out.print("Enter the Cake ID: ");
                                    int id = scanner.nextInt();
                                    System.out.println();

                                    System.out.println(cakeService.getBirthdayCakeById(id));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "5": {
                                // Get Reports
                                boolean reportFlag = true;

                                while (reportFlag) {
                                    System.out.println("Cake-Shop Reports");
                                    System.out.println("[1] Number of Cakes By Flavour");
                                    System.out.println("[2] Average Number of Candles");
                                    System.out.println("[0] Back");
                                    System.out.print("Please enter your choice: ");

                                    String reportChoice = scanner.next();
                                    System.out.println();

                                    switch (reportChoice) {
                                        case "1": {
                                            // Number of Cakes by Flavour
                                            try {
                                                System.out.print("Enter the Flavour: ");
                                                String flavour = scanner.next();
                                                System.out.println();

                                                System.out.print("Number of " + flavour + " cakes: ");
                                                System.out.println(cakeService.countCakesByFlavour(flavour));
                                                System.out.println();
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "2": {
                                            // Average Number of Candles
                                            try {
                                                System.out.print("The average number of candles is: ");
                                                System.out.println(cakeService.getAverageNumberOfCandles());
                                                System.out.println();
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }

                                            break;
                                        }

                                        case "0": {
                                            // Back
                                            reportFlag = false;
                                            break;
                                        }

                                        default: {
                                            System.out.println("Invalid Choice.");
                                            break;
                                        }
                                    }
                                }

                                break;
                            }

                            case "0": {
                                // Exit
                                cakeFlag = false;
                                break;
                            }

                            default:
                                System.out.println("Invalid choice.");
                                break;
                        }
                    }
                    break;
                }

                case "2": {
                    // Order service
                    boolean orderFLag = true;

                    while (orderFLag) {
                        System.out.println("Order Management Menu");
                        System.out.println("[1] Get all Orders");
                        System.out.println("[2] Modify Order List");
                        System.out.println("[3] Filter Orders");
                        System.out.println("[4] Get Order by ID");
                        System.out.println("[5] Get Reports");
                        System.out.println("[0] Back");
                        System.out.print("Please enter your choice: ");

                        String orderChoice = scanner.next();
                        System.out.println();

                        switch (orderChoice) {
                            case "1": {
                                // Get all orders
                                try {
                                    for (Order order : orderService.getOrders())
                                        System.out.println(order);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "2": {
                                // Modify Orders
                                boolean modifyFlag = true;

                                while (modifyFlag) {
                                    System.out.println("Modify Order List");
                                    System.out.println("[1] Add a New Order");
                                    System.out.println("[2] Remove an Order");
                                    System.out.println("[3] Update an Order");
                                    System.out.println("[0] Back");
                                    System.out.print("Please enter your choice: ");

                                    String modifyChoice = scanner.next();
                                    System.out.println();

                                    switch (modifyChoice) {
                                        case "1": {
                                            // Add new order
                                            try {
                                                System.out.print("Enter the Customer's Name: ");
                                                String name = scanner.next();
                                                System.out.println();

                                                System.out.print("Enter the Customer Contact Information: ");
                                                String contactInfo = scanner.next();
                                                System.out.println();

                                                System.out.print("Enter the Date of the Order: ");
                                                String dateOfOrder = scanner.next();
                                                System.out.println();

                                                System.out.print("Enter the number of cakes on the order: ");
                                                int cakeCount = scanner.nextInt();
                                                System.out.println();

                                                List<Integer> cakeIdList = new ArrayList<>();

                                                for (int index = 0; index < cakeCount; index++) {
                                                    System.out.print("Enter the Cake Id: ");
                                                    int cakeId = scanner.nextInt();
                                                    System.out.println();

                                                    cakeIdList.add(cakeId);
                                                }

                                                orderService.addOrder(name, contactInfo, dateOfOrder, cakeIdList);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "2": {
                                            // Remove order
                                            try {
                                                System.out.print("Enter the Order ID: ");
                                                int orderId = scanner.nextInt();
                                                System.out.println();

                                                orderService.removeOrder(orderId);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "3": {
                                            // Update order
                                            try {
                                                System.out.print("Enter the Order ID: ");
                                                int orderId = scanner.nextInt();
                                                System.out.println();

                                                System.out.print("Enter the New Customer's Name: ");
                                                String name = scanner.next();
                                                System.out.println();

                                                System.out.print("Enter the New Customer Contact Information: ");
                                                String contactInfo = scanner.next();
                                                System.out.println();

                                                System.out.print("Enter the New Date of the Order: ");
                                                String dateOfOrder = scanner.next();
                                                System.out.println();

                                                System.out.print("Enter the New number of cakes on the order: ");
                                                int cakeCount = scanner.nextInt();
                                                System.out.println();

                                                List<Integer> cakeIdList = new ArrayList<>();

                                                for (int index = 0; index < cakeCount; index++) {
                                                    System.out.print("Enter the Cake ID: ");
                                                    int cakeId = scanner.nextInt();
                                                    System.out.println();

                                                    cakeIdList.add(cakeId);
                                                }

                                                orderService.updateOrder(orderId, name, contactInfo, dateOfOrder, cakeIdList);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "0": {
                                            // Back
                                            modifyFlag = false;
                                            break;
                                        }

                                        default: {
                                            System.out.println("Invalid Choice.");
                                            break;
                                        }
                                    }
                                }

                                break;
                            }


                            case "3": {
                                // Filter Orders
                                boolean filterFlag = true;

                                while (filterFlag) {
                                    System.out.println("Order List Filter Menu");
                                    System.out.println("[1] Filter by Date");
                                    System.out.println("[2] Filter by Customer Name");
                                    System.out.println("[0] Back");
                                    System.out.print("Please enter your choice: ");

                                    String filterChoice = scanner.next();
                                    System.out.println();

                                    switch (filterChoice) {
                                        case "1": {
                                            // Filter orders by date
                                            try {
                                                System.out.print("Enter the Date: ");
                                                String date = scanner.next();
                                                System.out.println();

                                                for (Order order : orderService.filterOrdersByDate(date))
                                                    System.out.println(order);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "2": {
                                            // Filter orders by customer
                                            try {
                                                System.out.print("Enter the Name of the Customer: ");
                                                String name = scanner.next();
                                                System.out.println();

                                                for (Order order : orderService.filterOrdersByCustomerName(name))
                                                    System.out.println(order);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "0": {
                                            filterFlag = false;
                                            break;
                                        }

                                        default: {
                                            System.out.println("Invalid Choice.");
                                            break;
                                        }
                                    }
                                }
                                break;
                            }

                            case "4": {
                                // Get order by id
                                try {
                                    System.out.print("Enter the Order Id: ");
                                    int orderId = scanner.nextInt();
                                    System.out.println();

                                    System.out.println(orderService.getOrderById(orderId));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "5": {
                                // Order Reports
                                boolean reportFlag = true;

                                while (reportFlag) {
                                    System.out.println("Order Reports");
                                    System.out.println("[1] Earnings from Date");
                                    System.out.println("[2] Number of Orders by Customer");
                                    System.out.println("[3] Average Order Price");
                                    System.out.println("[0] Back");
                                    System.out.print("Please enter your choice: ");

                                    String reportChoice = scanner.next();
                                    System.out.println();

                                    switch (reportChoice) {
                                        case "1": {
                                            // Get earnings from date
                                            try {
                                                System.out.print("Enter the Date: ");
                                                String date = scanner.next();
                                                System.out.println();

                                                System.out.print("Earnings from " + date + ": ");
                                                System.out.println(orderService.getEarningsFromDate(date));
                                                System.out.println();
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "2": {
                                            // Number of Orders by Customer
                                            try {
                                                System.out.print("Enter the Customer Name: ");
                                                String customer = scanner.next();
                                                System.out.println();

                                                System.out.print("The number of orders of " + customer + " is: ");
                                                System.out.println(orderService.getNumberOfOrdersByCustomer(customer));
                                                System.out.println();
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "3": {
                                            // Average Order Price
                                            try {
                                                System.out.print("The average order price is: ");
                                                System.out.println(orderService.getAverageOrderPrice());
                                                System.out.println();
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        }

                                        case "0": {
                                            reportFlag = false;
                                            break;
                                        }

                                        default: {
                                            System.out.println("Invalid Choice.");
                                            break;
                                        }
                                    }
                                }

                                break;
                            }

                            case "0": {
                                orderFLag = false;
                                break;
                            }

                            default: {
                                System.out.println("Invalid choice.");
                                break;
                            }
                        }
                    }
                    break;
                }

                case "0": {
                    flag = false;
                    break;
                }

                default: {
                    System.out.println("Invalid choice.");
                    break;
                }
            }
        }
    }
}
