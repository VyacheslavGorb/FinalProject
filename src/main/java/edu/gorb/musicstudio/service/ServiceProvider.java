package edu.gorb.musicstudio.service;

public class ServiceProvider {
    private final UserService userService = new UserService();
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
