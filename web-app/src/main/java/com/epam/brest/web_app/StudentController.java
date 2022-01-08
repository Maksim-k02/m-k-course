package com.epam.brest.web_app;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StudentController {

    /**
     * Goto student list page.
     *
     * @return view name
     */
    @GetMapping(value = "/students")
    public final String students(Model model) {
        return "students";
    }

    /**
     * Goto edit students page.
     *
     * @return view name
     */
    @GetMapping(value = "/student/{id}")
    public final String gotoEditStudentPage(@PathVariable Integer id, Model model) {
        return "student";
    }

    /**
     * Goto new student page.
     *
     * @return view name
     */
    @GetMapping(value = "/student/add")
    public final String gotoAddStudentPage(Model model) {
        return "student";
    }
}
