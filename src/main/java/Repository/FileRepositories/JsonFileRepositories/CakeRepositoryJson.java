package Repository.FileRepositories.JsonFileRepositories;

import Domain.BirthdayCake;
import Repository.FileRepositories.FileRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CakeRepositoryJson extends FileRepository<String, BirthdayCake> {
    private ObjectMapper objectMapper = new ObjectMapper();

    public CakeRepositoryJson(String filename) {
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
            Map<String, BirthdayCake> cakes = objectMapper.readValue(file, new TypeReference<HashMap<String, BirthdayCake>>() {});

            for (Map.Entry<String, BirthdayCake> entry : cakes.entrySet()) {
                BirthdayCake cake = entry.getValue();

                elements.put(cake.getId(), cake);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from JSON file.\n");
        }
    }

    @Override
    protected void writeToFile() {
        File file = new File(filename);
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            objectMapper.writeValue(file, elements);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to JSON file.\n", e);
        }
    }
}
