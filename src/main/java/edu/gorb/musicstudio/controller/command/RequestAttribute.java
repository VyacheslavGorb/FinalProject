package edu.gorb.musicstudio.controller.command;

/**
 * Class provides string constants that represent request attributes
 */
public final class RequestAttribute {
    public static final String PAGE_COUNT = "page_count";
    public static final String COURSES = "courses";
    public static final String SEARCH_LINE = "search_line";
    public static final String NOTHING_FOUND = "nothing_found";
    public static final String COURSE = "course";
    public static final String COMMENTS = "comments";
    public static final String TEACHERS = "teachers";
    public static final String ON_COURSE_TEACHERS = "on_course_teachers";
    public static final String NOT_ON_COURSE_TEACHERS = "not_on_course_teachers";
    public static final String TEACHER = "teacher";
    public static final String COMMAND = "command";
    public static final String LESSON_SCHEDULE_MAP = "lesson_schedule_map";
    public static final String LESSON_SCHEDULE_DATES = "lesson_schedule_dates";
    public static final String TEACHER_SCHEDULE = "teacher_schedules";
    public static final String MAX_AVAILABLE_LESSON_COUNT = "max_available_lesson_count";
    public static final String SUBSCRIPTIONS = "subscriptions";
    public static final String TEACHERS_SCHEDULES = "teachers_schedules";
    public static final String AVAILABLE_DATES = "available_dates";
    public static final String SUBSCRIPTION = "subscription";
    public static final String ACTIVE_SUBSCRIPTIONS = "active_subscriptions";
    public static final String DATE = "date";
    public static final String USER_MAP = "user_map";
    public static final String SUBSCRIPTIONS_FOR_SURE_LESSON_COUNT = "for_sure_lesson_count";
    public static final String SUBSCRIPTIONS_LESSON_COUNT = "total_lesson_count";
    public static final String SUBSCRIPTION_SCHEDULE_MAP = "subscriptions_schedule_map";
    public static final String WAITING_FOR_APPROVE_SUBSCRIPTIONS = "waiting_for_approve_subscriptions";

    private RequestAttribute() {
    }
}
