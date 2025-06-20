package kata.springBootSecurity.adminPanel.exceptionsHandlers;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
