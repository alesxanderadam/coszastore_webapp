package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.payload.response.ColorResponse;

import java.util.List;

public interface ColorServiceImp {
    void add(String name);
    List<ColorResponse> getAllColors();
    void edit(Integer id, String name);
    void delete(Integer id);
}
