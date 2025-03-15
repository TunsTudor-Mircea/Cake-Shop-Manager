package Domain;

import Repository.Base.IRepository;

public class ActionAdd<ID, T> implements IAction<ID, T> {
    private final IRepository<ID, T> repo;
    private final T addedElem;
    private final ID id;

    public ActionAdd(IRepository<ID, T> repo, T addedElem, ID id) {
        this.repo = repo;
        this.addedElem = addedElem;
        this.id = id;
    }

    @Override
    public void executeUndo() {
        repo.delete(id);
    }

    @Override
    public void executeRedo() {
        repo.add(id, addedElem);
    }
}