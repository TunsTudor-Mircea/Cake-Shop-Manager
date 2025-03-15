package Domain;

import Repository.Base.IRepository;

public class ActionRemove<ID, T> implements IAction<ID, T> {
    private final IRepository<ID, T> repo;
    private final T deletedElem;
    private final ID id;

    public ActionRemove(IRepository<ID, T> repo, T deletedElem, ID id) {
        this.repo = repo;
        this.deletedElem = deletedElem;
        this.id = id;
    }

    @Override
    public void executeUndo() {
        repo.add(id, deletedElem);
    }

    @Override
    public void executeRedo() {
        repo.delete(id);
    }
}