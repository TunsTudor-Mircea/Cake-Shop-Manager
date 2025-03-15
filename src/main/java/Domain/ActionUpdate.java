package Domain;

import Repository.Base.IRepository;

public class ActionUpdate<ID, T> implements IAction<ID, T> {
    private final IRepository<ID, T> repo;
    private final T oldElem;
    private final T newElem;
    private final ID id;

    public ActionUpdate(IRepository<ID, T> repo, T oldElem, T newElem, ID id) {
        this.repo = repo;
        this.oldElem = oldElem;
        this.newElem = newElem;
        this.id = id;
    }

    @Override
    public void executeUndo() {
        repo.modify(id, oldElem);
    }

    @Override
    public void executeRedo() {
        repo.modify(id, newElem);
    }
}