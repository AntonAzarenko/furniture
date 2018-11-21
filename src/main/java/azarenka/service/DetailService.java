package azarenka.service;

import azarenka.entity.Detail;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DetailService {
    List<Detail> getAll();

    Detail getById(Long id);

    void save(Detail detail);

    void delete(Long id);
}