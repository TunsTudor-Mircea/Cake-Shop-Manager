package Repository.FileRepositories.XMLFileRepositories;

import Domain.Order;
import Repository.FileRepositories.FileRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class OrderRepositoryXML extends FileRepository<String, Order> {
    protected XmlMapper xmlMapper = new XmlMapper();

    public OrderRepositoryXML(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        File file = new File(filename);

        if (xmlMapper == null) {
            xmlMapper = new XmlMapper();
        }

        if (file.length() == 0) {
            elements = new HashMap<>();
            return;
        }

        try {
            List<Order> orders = xmlMapper.readValue(file, new TypeReference<>() {});

            for (Order order : orders)
                elements.put(order.getId(), order);
        } catch (IOException e) {
            throw new RuntimeException("Error reading from XML file.\n");
        }
    }

    @Override
    protected void writeToFile() {
        File file = new File(filename);

        if (xmlMapper == null) {
            xmlMapper = new XmlMapper();
        }

        try {
            xmlMapper.writeValue(file, elements.values());
        } catch (IOException e) {
            throw new RuntimeException("Error writing from XML file.\n");
        }
    }
}
