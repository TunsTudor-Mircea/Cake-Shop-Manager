package Domain;

public interface IAction<ID, T> {
    void executeUndo();
    void executeRedo();
}
