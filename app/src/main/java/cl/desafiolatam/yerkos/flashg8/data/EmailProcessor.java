package cl.desafiolatam.yerkos.flashg8.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmailProcessor {

    public String sanitizedEmail(String email){
        return email.replace("@","AT").replace(".","DOT");
    }

    public String keyEmails(String otherEmail){
        String currentEmail = new CurrentUser().email();
        List<String> email = new ArrayList<>();
        email.add(sanitizedEmail(currentEmail));
        email.add(sanitizedEmail(otherEmail));
        Collections.sort(email);
        return email.get(0) + " - " + email.get(1);
    }

}
