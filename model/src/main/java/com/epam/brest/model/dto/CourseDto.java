package com.epam.brest.model.dto;

import java.math.BigDecimal;

public class CourseDto {

    /**
     * Course Id.
     */
    private Integer courseId;

    /**
     * Course Name.
     */
    private String courseName;

    /**
     * Count student of the Course.
     */
    private BigDecimal countStudent;

    /**
     * Constructor without arguments.
     */
    public CourseDto() {
    }

    /**
     * Constructor with department name.
     *
     * @param courseName course name
     */
    public CourseDto(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Returns <code>Integer</code> representation of this courseId.
     *
     * @return courseId Course Id.
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * Sets the course's identifier.
     *
     * @param courseId Course Id.
     */
    public void setCourseId(final Integer courseId) {
        this.courseId = courseId;
    }

    /**
     * Returns <code>String</code> representation of this courseName.
     *
     * @return courseName Course Name.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the course's name.
     *
     * @param courseName Course Name.
     */
    public void setCourseName(final String courseName) {
        this.courseName = courseName;
    }

    /**
     * Returns <code>BigDecimal</code> representation of count students
     * for the Course.
     *
     * @return courseId.
     */
    public BigDecimal getCountStudent() {
        return countStudent;
    }

    /**
     * Sets the course's count student.
     *
     * @param countStudent Count student.
     */
    public void setCountStudent(final BigDecimal countStudent) {
        this.countStudent = countStudent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CourseDto{"
                + "courseId=" + courseId
                + ", courseName='" + courseName + '\''
                + ", countStudent=" + countStudent
                + '}';
    }
}
