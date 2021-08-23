package edu.gorb.musicstudio.controller.command;

import edu.gorb.musicstudio.controller.command.impl.*;
import edu.gorb.musicstudio.controller.command.impl.admin.*;
import edu.gorb.musicstudio.controller.command.impl.student.*;
import edu.gorb.musicstudio.controller.command.impl.teacher.*;

import java.util.EnumMap;

import static edu.gorb.musicstudio.controller.command.CommandType.*;

/**
 * Provides commands by request parameter
 */
public class CommandProvider {
    /**
     * Map containing command types associated with command instances
     */
    private final EnumMap<CommandType, Command> commands;

    private CommandProvider() {
        commands = new EnumMap<>(CommandType.class);
        commands.put(LOGIN, new LoginCommand());
        commands.put(LOGOUT, new LogoutCommand());
        commands.put(DEFAULT, new DefaultCommand());
        commands.put(HOME_PAGE, new HomePageCommand());
        commands.put(GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
        commands.put(CHANGE_LANGUAGE, new ChangeLanguageCommand());
        commands.put(SIGN_UP, new SignUpCommand());
        commands.put(GO_TO_SIGN_UP_PAGE, new GoToSignUpPageCommand());
        commands.put(CONFIRM_EMAIL, new ConfirmEmailCommand());
        commands.put(GO_TO_SEND_EMAIL_AGAIN_PAGE, new GoToSendEmailAgainPageCommand());
        commands.put(SEND_EMAIL_AGAIN, new SendEmailAgainCommand());
        commands.put(COURSES, new CoursesCommand());
        commands.put(COURSE_PAGE, new CoursePageCommand());
        commands.put(POST_COMMENT, new PostCommentCommand());
        commands.put(TEACHERS, new TeachersCommand());
        commands.put(TEACHER_PAGE, new TeacherPageCommand());
        commands.put(TEACHER_INIT, new GoToTeacherInitPageCommand());
        commands.put(TEACHER_LESSON_SCHEDULE, new TeacherLessonSchedulePageCommand());
        commands.put(SEND_TEACHER_INIT_DESCRIPTION, new SendTeacherInitDescriptionCommand());
        commands.put(TEACHER_SCHEDULE, new TeacherSchedulePageCommand());
        commands.put(ALTER_TEACHER_SCHEDULE, new AlterTeacherScheduleCommand());
        commands.put(TEACHER_PERSONAL_INFO, new TeacherPersonalInfoCommand());
        commands.put(UPDATE_TEACHER_DESCRIPTION, new UpdateTeacherDescriptionCommand());
        commands.put(SUBSCRIPTION_PAGE, new CourseSubscriptionPageCommand());
        commands.put(SEND_SUBSCRIPTION_REQUEST, new SendSubscriptionRequestCommand());
        commands.put(PERSONAL_SUBSCRIPTIONS, new PersonalSubscriptionsCommand());
        commands.put(CHOOSE_LESSON_DATETIME_PAGE, new ChooseLessonDateTimePageCommand());
        commands.put(CHOOSE_LESSON_DATETIME, new ChooseLessonDateTimeCommand());
        commands.put(STUDENT_LESSON_SCHEDULE, new StudentLessonSchedulePageCommand());
        commands.put(STUDENT_CANCEL_LESSON, new StudentCancelLessonCommand());
        commands.put(MANAGE_USERS_PAGE, new ManageUsersPageCommand());
        commands.put(ACTIVATE_USER, new ActivateUserCommand());
        commands.put(DEACTIVATE_USER, new DeactivateUserCommand());
        commands.put(ALL_LESSONS_PAGE, new AllLessonsPageCommand());
        commands.put(ADMIN_CANCEL_LESSON, new AdminCancelLessonCommand());
        commands.put(ALL_SUBSCRIPTIONS_PAGE, new AllSubscriptionsPageCommand());
        commands.put(ADMIN_APPROVE_SUBSCRIPTION, new ApproveSubscriptionCommand());
        commands.put(ADMIN_CANCEL_SUBSCRIPTION, new CancelSubscriptionCommand());
        commands.put(ALL_COURSES_PAGE, new AllCoursesPageCommand());
        commands.put(ADD_COURSE_PAGE, new AddCoursePageCommand());
        commands.put(ADD_COURSE, new AddCourseCommand());
        commands.put(ACTIVATE_COURSE, new ActivateCourseCommand());
        commands.put(DEACTIVATE_COURSE, new DeactivateCourseCommand());
        commands.put(CHANGE_COURSE_PAGE, new ChangeCoursePageCommand());
        commands.put(CHANGE_COURSE, new ChangeCourseCommand());
        commands.put(MANAGE_TEACHERS_COURSE_PAGE, new ManageTeachersCoursePageCommand());
        commands.put(ADD_TEACHER_TO_COURSE, new AddTeacherToCourseCommand());
        commands.put(REMOVE_TEACHER_FROM_COURSE, new RemoveTeacherFromCourseCommand());
        commands.put(MANAGE_COMMENTS, new CommentsForCoursePageCommand());
        commands.put(REMOVE_COMMENT, new RemoveCommentCommand());
    }

    /**
     * Return instance of {@link CommandProvider}
     *
     * @return Instance of {@link CommandProvider}
     */
    public static CommandProvider getInstance() {
        return CommandProviderHolder.instance;
    }

    /**
     * Get command by request parameter
     *
     * @param commandParameter request command parameter
     * @return instance of {@link Command} interface associated with parameter
     */
    public Command getCommand(String commandParameter) {
        CommandType commandType = CommandType.convertRequestParameterToCommandType(commandParameter);
        return commands.get(commandType);
    }

    private static class CommandProviderHolder {
        private static final CommandProvider instance = new CommandProvider();
    }
}
