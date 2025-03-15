package Repository.FileRepositories.JsonFileRepositories;

import Domain.Order;
import Repository.FileRepositories.FileRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OrderRepositoryJson extends FileRepository<String, Order> {
    private ObjectMapper objectMapper = new ObjectMapper();

    public OrderRepositoryJson(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
            File file = new File(filename);
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
            }

            if (file.length() == 0) {
                elements = new HashMap<>();
                return;
            }

        try {
            Map<String, Order> orders = objectMapper.readValue(file, new TypeReference<HashMap<String, Order>>() {});

            for (Map.Entry<String, Order> entry : orders.entrySet()) {
                Order order = entry.getValue();

                elements.put(order.getId(), order);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from JSON file.\n");
        }
    }

    @Override
    protected void writeToFile() {
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
            }

        try {
            objectMapper.writeValue(new File(filename), elements);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to JSON file.\n", e);
        }
    }
}
