package azarenka.repository.logic.proxy;

import azarenka.entity.Module;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProxyModuleRepository extends MongoRepository<Module, String> {

    Module getById(String id);

    List<Module> getByName(String name);
}