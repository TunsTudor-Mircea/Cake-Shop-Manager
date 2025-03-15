package gui;

import Domain.BirthdayCake;
import Domain.Order;
import Repository.Base.MemoryRepository;
import Repository.Base.RepositoryFactory;
import Repository.EntityRepositories.BirthdayCakeRepository;
import Service.BirthdayCakeService;
import Service.OrderService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    // Cake Management Components
    @FXML
    private ListView<BirthdayCake> cakeListView;
    @FXML
    private TextField idTextField, sizeTextField, flavourTextField, candlesTextField, priceTextField;
    @FXML
    private TextField filterFlavourTextField, filterPriceTextField;

    // Order Management Components
    @FXML
    private ListView<Order> orderListView;
    @FXML
    private TextField orderIdTextField, customerNameTextField, customerContactTextField, orderDateTextField;
    @FXML
    private TextArea cakeIdsTextArea;
    @FXML
    private TextField filterDateTextField, filterCustomerTextField;

    private BirthdayCakeService cakeService;
    private OrderService orderService;

    private ObservableList<BirthdayCake> cakeList;
    private ObservableList<Order> orderList;

    @FXML
    private void initialize() throws IOException {
        RepositoryFactory.Repositories repositories = RepositoryFactory.createRepositories();

        BirthdayCakeRepository cakeRepository = (BirthdayCakeRepository) repositories.cakeRepository;
        MemoryRepository<String, Order> orderRepository = repositories.orderRepository;

        // Initialize services
        cakeService = new BirthdayCakeService(cakeRepository, orderRepository);
        orderService = new OrderService(orderRepository, cakeService);

        // Initialize lists
        cakeList = FXCollections.observableArrayList(cakeService.getAllBirthdayCakes());
        orderList = FXCollections.observableArrayList(orderService.getOrders());
        threadsList = FXCollections.observableArrayList(cakeService.filterCakesLargerThan8());

        // Bind lists to ListViews
        cakeListView.setItems(cakeList);
        orderListView.setItems(orderList);
        threadsListView.setItems(threadsList);

        // Set event listeners
        cakeListView.setOnMouseClicked(_ -> selectCake());
        orderListView.setOnMouseClicked(_ -> selectOrder());

        threadTypeChoiceBox.getItems().addAll("Traditional Threads", "Executor Service");
    }

    // Cake Management Actions
    private void selectCake() {
        BirthdayCake selectedCake = cakeListView.getSelectionModel().getSelectedItem();
        if (selectedCake != null) {
            idTextField.setText(selectedCake.getId());
            sizeTextField.setText(String.valueOf(selectedCake.getSize()));
            flavourTextField.setText(selectedCake.getFlavour());
            candlesTextField.setText(String.valueOf(selectedCake.getCandles()));
            priceTextField.setText(String.valueOf(selectedCake.getPrice()));
        }
    }

    @FXML
    private void handleAddCake() {
        try {
            int size = Integer.parseInt(sizeTextField.getText());
            String flavour = flavourTextField.getText();
            int candles = Integer.parseInt(candlesTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());

            cakeService.addBirthdayCake(size, flavour, candles, price);
            refreshCakes();
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    @FXML
    private void handleRemoveCake() {
        try {
            int id = Integer.parseInt(idTextField.getText());
            cakeService.removeBirthdayCake(id);
            orderService.removeInvalidOrders(id);
            refreshCakes();
            refreshOrders();
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    @FXML
    private void handleUpdateCake() {
        try {
            int id = Integer.parseInt(idTextField.getText());
            int size = Integer.parseInt(sizeTextField.getText());
            String flavour = flavourTextField.getText();
            int candles = Integer.parseInt(candlesTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());

            cakeService.updateBirthdayCake(id, size, flavour, candles, price);
            refreshCakes();
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    @FXML
    private void filterCakesByFlavour() {
        String flavour = filterFlavourTextField.getText();
        cakeListView.setItems(FXCollections.observableArrayList(cakeService.filterBirthdayCakesByFlavour(flavour)));
    }

    @FXML
    private void filterCakesByPrice() {
        double price = Double.parseDouble(filterPriceTextField.getText());
        cakeListView.setItems(FXCollections.observableArrayList(cakeService.filterBirthdayCakesByPrice(price)));
    }

    @FXML
    private void viewCakeReports() {
        String report = generateCakeReport();
        showReportDialog("Birthday Cake Reports", report);
    }

    private String generateCakeReport() {
        return "Average Cake Price: $" + cakeService.getAverageCakePrice() + "\n" +
                "Average Number of Candles: " + cakeService.getAverageNumberOfCandles() + "\n";
    }

    @FXML
    private void refreshCakes() {
        cakeList.setAll(cakeService.getAllBirthdayCakes());
    }

    @FXML
    private void refreshCakeListView() {
        cakeListView.setItems(cakeList);
    }

    // Order Management Actions
    private void selectOrder() {
        Order selectedOrder = orderListView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            orderIdTextField.setText(selectedOrder.getOrderId());
            customerNameTextField.setText(selectedOrder.getCustomerName());
            customerContactTextField.setText(selectedOrder.getCustomerContact());
            orderDateTextField.setText(selectedOrder.getOrderDate());
            cakeIdsTextArea.setText(selectedOrder.getCakeIds().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")));
        }
    }

    @FXML
    private void handleAddOrder() {
        try {
            String customerName = customerNameTextField.getText();
            String customerContact = customerContactTextField.getText();
            String orderDate = orderDateTextField.getText();

            List<Integer> cakeIds = parseCakeIds(cakeIdsTextArea.getText());

            orderService.addOrder(customerName, customerContact, orderDate, cakeIds);

            refreshOrders();
            clearOrderFields();
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    @FXML
    private void handleRemoveOrder() {
        try {
            String orderId = orderIdTextField.getText();
            orderService.removeOrder(Integer.parseInt(orderId));
            refreshOrders();
            clearOrderFields();
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    @FXML
    private void handleUpdateOrder() {
        try {
            String orderId = orderIdTextField.getText();
            String customerName = customerNameTextField.getText();
            String customerContact = customerContactTextField.getText();
            String orderDate = orderDateTextField.getText();

            List<Integer> cakeIds = parseCakeIds(cakeIdsTextArea.getText());

            orderService.updateOrder(Integer.parseInt(orderId), customerName, customerContact, orderDate, cakeIds);

            refreshOrders();
            clearOrderFields();
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    private List<Integer> parseCakeIds(String cakeIdsText) {
        if (cakeIdsText.isEmpty()) return List.of();
        return Arrays.stream(cakeIdsText.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @FXML
    private void refreshOrders() {
        orderList.setAll(orderService.getOrders());
    }

    @FXML
    private void refreshOrderListView() {
        orderListView.setItems(orderList);
    }

    private void clearOrderFields() {
        orderIdTextField.clear();
        customerNameTextField.clear();
        customerContactTextField.clear();
        orderDateTextField.clear();
        cakeIdsTextArea.clear();
    }

    @FXML
    private void filterOrdersByDate() {
        String date = filterDateTextField.getText();
        orderListView.setItems(FXCollections.observableArrayList(orderService.filterOrdersByDate(date)));
    }

    @FXML
    private void filterOrdersByCustomer() {
        String customer = filterCustomerTextField.getText();
        orderListView.setItems(FXCollections.observableArrayList(orderService.filterOrdersByCustomerName(customer)));
    }

    @FXML
    private void viewOrderReports() {
        String report = generateOrderReport();
        showReportDialog("Order Reports", report);
    }

    private String generateOrderReport() {
        return "Total Revenue: $" + orderService.getTotalEarnings() + "\n" +
                "Average Order Price: $" + orderService.getAverageOrderPrice() + "\n";
    }

    private void showReportDialog(String title, String content) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);

        // Add a TextArea to display the report content
        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(400, 300);

        // Set the dialog content
        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(textArea);
        dialogPane.getButtonTypes().add(ButtonType.OK);
        dialog.setDialogPane(dialogPane);

        dialog.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private ChoiceBox<String> threadTypeChoiceBox;
    @FXML
    private TextField updateFlavorTextFIeld, nrOfThreadsTextField;
    @FXML
    private ListView<BirthdayCake> threadsListView;
    @FXML
    ObservableList<BirthdayCake> threadsList;

    @FXML
    void handleRefreshThreads() {
        threadsList.setAll(cakeService.filterCakesLargerThan8());
    }

    @FXML
    void handleBulkUpdate() {
        String threadType = threadTypeChoiceBox.getValue();
        int nrOfThreads = Integer.parseInt(nrOfThreadsTextField.getText());
        String newFlavor = updateFlavorTextFIeld.getText();

        if (threadType.equals("Java Threads")) {
            cakeService.bulkUpdateCakesTraditional(nrOfThreads, newFlavor);
        } else {
            cakeService.bulkUpdateCakesExecutor(nrOfThreads, newFlavor);
        }

        refreshCakes();
    }

    @FXML
    void handleMultiThreadingSort() {
        threadsList.setAll(cakeService.sortCakesByIdMultiThreaded());
    }

    @FXML
    void handleUndo() {
        try {
            cakeService.undo();
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
        refreshCakes();
        refreshOrders();
    }

    @FXML
    void handleRedo() {
        try{
            cakeService.redo();
        } catch (Exception e){
            showAlert(e.getMessage());
        }
        refreshCakes();
        refreshOrders();
    }
}
