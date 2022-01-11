package com.epam.brest.web_app;

import com.epam.brest.model.Course;
import com.epam.brest.service.CourseDtoService;
import com.epam.brest.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final CourseDtoService courseDtoService;

    private final CourseService courseService;

    public CourseController(CourseDtoService courseDtoService,
                            CourseService courseService) {
        this.courseDtoService = courseDtoService;
        this.courseService = courseService;
    }

    /**
     * Goto courses list page.
     *
     * @return view name
     */

    @GetMapping(value = "/courses")
    public String courses(Model model) {
        model.addAttribute("courses",courseDtoService.findAllWithCountStudent());
        return "courses";
    }

    /**
     * Goto edit course page.
     *
     * @return view name
     */
    @GetMapping(value = "/course/{id}")
    public final String gotoEditCoursePage(@PathVariable Integer id, Model model) {
        return "course";
    }

    /**
     * Goto new course page.
     *
     * @return view name
     */
    @GetMapping(value = "/course")
    public final String gotoAddCoursePage(Model model) {
        logger.debug("gotoAddCoursePage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("course", new Course());
        return "course";
    }

    /**
     * Persist new course into persistence storage.
     *
     * @param course new course with filled data.
     * @return view name
     */
    @PostMapping(value = "/course")
    public String addCourse(Course course) {

        logger.debug("addCourse({}, {})", course);
        this.courseService.create(course);
        return "redirect:/courses";
    }
}
