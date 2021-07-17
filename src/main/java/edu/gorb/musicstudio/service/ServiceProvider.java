package edu.gorb.musicstudio.service;

import edu.gorb.musicstudio.service.impl.UserServiceImpl;

public class ServiceProvider {
    private final UserService userService = new UserServiceImpl();
    private ServiceProvider() {
    }

    private static class ServiceProviderHolder {
        private static final ServiceProvider instance = new ServiceProvider();
    }

    public static ServiceProvider getInstance(){
        return ServiceProviderHolder.instance;
    }

    public UserService getUserService() {
        return userService;
    }
}
