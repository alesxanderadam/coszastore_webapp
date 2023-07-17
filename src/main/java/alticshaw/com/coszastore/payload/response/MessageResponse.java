package alticshaw.com.coszastore.payload.response;

import org.springframework.stereotype.Component;

@Component
public class MessageResponse {
    public String success(){
        return "Successful handling";
    }
    public String error(){ return "Successful handling";}
    public String unthorization(String name){ return name + " is unthorization";}
    public String invalid(String name){ return name + " is invalid";}
}
