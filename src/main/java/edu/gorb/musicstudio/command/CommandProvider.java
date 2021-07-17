package edu.gorb.musicstudio.command;

import edu.gorb.musicstudio.command.impl.*;

import java.util.EnumMap;

import static edu.gorb.musicstudio.command.CommandType.*;

public class CommandProvider {
    private EnumMap<CommandType, Command> commands;

    private CommandProvider() {
        commands = new EnumMap<>(CommandType.class);
        commands.put(LOGIN, new LoginCommand());
        commands.put(LOGOUT, new LogoutCommand());
        commands.put(PERSONAL_PAGE, new PersonalPageCommand());
        commands.put(DEFAULT, new DefaultCommand());
        commands.put(HOME_PAGE, new HomePageCommand());
        commands.put(GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
        commands.put(CHANGE_LANGUAGE, new ChangeLanguageCommand());
    }

    private static class CommandProviderHolder {
        private static final CommandProvider instance = new CommandProvider();
    }

    public static CommandProvider getInstance() {
        return CommandProviderHolder.instance;
    }

    public Command getCommand(CommandType commandType) {
        return commands.get(commandType);
    }

    public Command getCommand(String command) {
        CommandType commandType;
        if (command == null) {
            return commands.get(DEFAULT);
        }
        try {
            commandType = CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandType = DEFAULT;
        }
        return commands.get(commandType);
    }

}
