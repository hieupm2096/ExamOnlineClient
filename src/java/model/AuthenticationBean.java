/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Student;
import facade.StudentFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author oswal
 */
@Named(value = "authenticationBean")
@SessionScoped
public class AuthenticationBean implements Serializable {

    @EJB
    private StudentFacade studentFacade;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationBean.class);

    public static final String EXAM_ENTRANCE_PAGE_REDIRECT = "/app/exam-entrance?faces-redirect=true";
    public static final String LOGIN_PAGE_REDIRECT = "/login?faces-redirect=true";

    private String id;
    private String password;

    private Student loginStudent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Student getLoginStudent() {
        return loginStudent;
    }

    public String login() {
        loginStudent = studentFacade.findStudentByIdAndPassword(id, password);
        if (loginStudent != null) {
            LOGGER.info("Login successful for {}", loginStudent.getName());
            return EXAM_ENTRANCE_PAGE_REDIRECT;
        } else {
            LOGGER.info("login failed");
            return null;
        }
    }

    public String logout() {
        String identifier = loginStudent.getName();

        // invalidate session
        LOGGER.debug("invalidating session for ", identifier);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        LOGGER.info("logout successfully for ", identifier);
        return LOGIN_PAGE_REDIRECT;
    }

    public boolean isLoggedIn() {
        return loginStudent != null;
    }

    public String isLoggedInForwardHome() {
        if (isLoggedIn()) {
            return EXAM_ENTRANCE_PAGE_REDIRECT;
        }
        return null;
    }
}
