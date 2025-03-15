package Repository.FileRepositories.BinaryFileRepositories;

import Domain.Order;
import Repository.FileRepositories.FileRepository;

import java.io.*;
import java.util.HashMap;

public class OrderRepositoryBinaryFile extends FileRepository<String, Order> {
    public OrderRepositoryBinaryFile(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            elements = (HashMap<String, Order>) ois.readObject();
        } catch(EOFException e) {
            this.elements = new HashMap<>();
            writeToFile();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(elements);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
