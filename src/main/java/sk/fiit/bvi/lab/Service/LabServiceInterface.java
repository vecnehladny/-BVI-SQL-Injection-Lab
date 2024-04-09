package sk.fiit.bvi.lab.Service;

import sk.fiit.bvi.lab.Wrapper.LoginWrapper;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface LabServiceInterface<T> {

    LoginWrapper getUsers(String profileId, String password) throws NoSuchAlgorithmException;

    List<T> getUser(String profileId);
}
