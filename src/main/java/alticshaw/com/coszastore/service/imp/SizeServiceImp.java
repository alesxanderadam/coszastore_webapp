package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.payload.response.SizeResponse;

import java.util.List;

public interface SizeServiceImp {
    void add(String name);
    List<SizeResponse>getAllSizes();
    void edit(Integer id, String name);
    void delete(Integer id);
}
