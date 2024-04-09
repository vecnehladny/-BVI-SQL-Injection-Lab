package sk.fiit.bvi.lab.Entity.mapper;

public interface UserMapperInterface {
    void setId(Long id);
    void setEmail(String email);
    void setName(String name);
    void setProfileIdFromObject(Object o);  // Use Object if profileId types are inconsistent across classes
    void setUsername(String username);
}
