package com.epam.brest.web_app.validators;

import com.epam.brest.model.Course;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.epam.brest.model.constants.CourseConstants.COURSE_NAME_SIZE;

@Component
public class CourseValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Course.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors,"courseName", "courseName.empty");
        Course course = (Course) target;

        if (StringUtils.hasLength(course.getCourseName())
                && course.getCourseName().length() > COURSE_NAME_SIZE) {
            errors.rejectValue("courseName", "courseName.maxSize");
        }
    }
}
