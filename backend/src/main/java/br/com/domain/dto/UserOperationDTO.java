package br.com.domain.dto;

import br.com.domain.model.User;

public class UserOperationDTO {

    private String operation; // "CREATE", "UPDATE", "DELETE"

    private User user;

    public UserOperationDTO(String operation, User user) {
        this.operation = operation;
        this.user = user;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}