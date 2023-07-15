package alticshaw.com.coszastore.payload.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CategoryRequest {
    private int id;
    @NotNull
    @NotEmpty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
