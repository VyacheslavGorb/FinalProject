package edu.gorb.musicstudio.entity.dto;

public class CommentDto {
    private long commentId;
    private String studentName;
    private String studentSurname;
    private String content;
    private String dateTime;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentDto that = (CommentDto) o;

        if (commentId != that.commentId) return false;
        if (studentName != null ? !studentName.equals(that.studentName) : that.studentName != null) return false;
        if (studentSurname != null ? !studentSurname.equals(that.studentSurname) : that.studentSurname != null)
            return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return dateTime != null ? dateTime.equals(that.dateTime) : that.dateTime == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (commentId ^ (commentId >>> 32));
        result = 31 * result + (studentName != null ? studentName.hashCode() : 0);
        result = 31 * result + (studentSurname != null ? studentSurname.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommentDto{");
        sb.append("commentId=").append(commentId);
        sb.append(", studentName='").append(studentName).append('\'');
        sb.append(", studentSurname='").append(studentSurname).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", dateTime='").append(dateTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
