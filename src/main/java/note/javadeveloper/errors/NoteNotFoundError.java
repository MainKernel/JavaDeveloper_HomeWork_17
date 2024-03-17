package note.javadeveloper.errors;

public class NoteNotFoundError extends RuntimeException{
    public NoteNotFoundError(String message) {
        super(message);
    }
}
