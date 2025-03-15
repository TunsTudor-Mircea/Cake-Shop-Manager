package Repository.FileRepositories.XMLFileRepositories;

import Domain.BirthdayCake;
import Repository.FileRepositories.FileRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CakeRepositoryXML extends FileRepository<String, BirthdayCake> {
    protected XmlMapper xmlMapper = new XmlMapper();

    public CakeRepositoryXML(String fileName) {
        super(fileName);
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
            List<BirthdayCake> cakes = xmlMapper.readValue(file, new TypeReference<>() {});

            for (BirthdayCake cake : cakes)
                elements.put(cake.getId(), cake);
        } catch (IOException e) {
            throw new RuntimeException("Error reading from XML file.\n" + e.getMessage());
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
