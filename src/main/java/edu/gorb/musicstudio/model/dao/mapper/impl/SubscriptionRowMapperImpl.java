package edu.gorb.musicstudio.model.dao.mapper.impl;

import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.model.dao.ColumnName;
import edu.gorb.musicstudio.model.dao.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SubscriptionRowMapperImpl implements RowMapper<Subscription> {
    @Override
    public Subscription mapRow(ResultSet resultSet) throws SQLException {
        long subscriptionId = resultSet.getLong(ColumnName.SUBSCRIPTION_ID);
        long studentId = resultSet.getLong(ColumnName.LESSON_STUDENT_ID);
        long courseId = resultSet.getLong(ColumnName.COURSE_COURSE_ID);
        LocalDate startDate = resultSet.getDate(ColumnName.SUBSCRIPTION_DATE_START).toLocalDate();
        LocalDate endDate = resultSet.getDate(ColumnName.SUBSCRIPTION_DATE_END).toLocalDate();
        Subscription.SubscriptionStatus status =
                Subscription.SubscriptionStatus.valueOf(resultSet.getString(ColumnName.SUBSCRIPTION_STATUS));
        int lessonCount = resultSet.getInt(ColumnName.SUBSCRIPTION_LESSON_AMOUNT);
        return new Subscription(subscriptionId, studentId, courseId, startDate, endDate, status, lessonCount);
    }
}
