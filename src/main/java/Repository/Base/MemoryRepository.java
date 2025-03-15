package Repository.Base;

import Domain.Identifiable;

import java.util.*;

public class MemoryRepository<ID, T extends Identifiable<ID>> implements IRepository<ID, T> {
    protected Map<ID, T> elements;
    private int lastId;

    public MemoryRepository() {
        elements = new HashMap<>();
        lastId = 0;
    }

    @Override
    public void add(ID id, T entity) {
        elements.put(id, entity);
        lastId++;
    }

    @Override
    public Optional<T> delete(ID id) {
        if (elements.containsKey(id)) {
            T entity = elements.remove(id);
            return Optional.of(entity);
        }

        else
            return Optional.empty();
    }

    @Override
    public void modify(ID id, T entity) {
        if (elements.containsKey(id))
            elements.put(id, entity);

        else
            throw new RuntimeException("Invalid ID.\n");
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.of(elements.get(id));
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(elements.values());
    }

    public String getNextId() {
        return Integer.toString(lastId + 1);
    }
}
