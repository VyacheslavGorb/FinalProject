package edu.gorb.musicstudio.conroller.command;

import edu.gorb.musicstudio.conroller.command.impl.*;
import edu.gorb.musicstudio.conroller.command.impl.go.*;

import java.util.EnumMap;

import static edu.gorb.musicstudio.conroller.command.CommandType.*;

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
        commands.put(SIGN_UP, new SignUpCommand());
        commands.put(GO_TO_SIGN_UP_PAGE, new GoToSignUpPageCommand());
        commands.put(CONFIRM_EMAIL, new ConfirmEmailCommand());
        commands.put(GO_TO_SEND_EMAIL_AGAIN_PAGE, new GoToSendEmailAgainPageCommand());
        commands.put(SEND_EMAIL_AGAIN, new SendEmailAgainCommand());
        commands.put(GO_TO_EMAIL_SENT_PAGE, new GoToEmailSentPageCommand());
        commands.put(GO_TO_EMAIL_CONFIRMED_PAGE, new GoToEmailConfirmedPageCommand());
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

    private static class CommandProviderHolder {
        private static final CommandProvider instance = new CommandProvider();
    }

}
