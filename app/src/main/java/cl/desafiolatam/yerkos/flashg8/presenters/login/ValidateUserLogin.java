package cl.desafiolatam.yerkos.flashg8.presenters.login;

import cl.desafiolatam.yerkos.flashg8.data.CurrentUser;

public class ValidateUserLogin {

    private ValidateUserCallback validateUserCallback;

    public ValidateUserLogin(ValidateUserCallback validateUserCallback) {
        this.validateUserCallback = validateUserCallback;
    }

    public void validateLogin(){
        if (new CurrentUser().getCurrentUser() != null){
            validateUserCallback.logger();
        } else {
            validateUserCallback.signUp();
        }
    }
}
