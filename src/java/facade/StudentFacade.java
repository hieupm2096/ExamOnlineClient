/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Student;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author oswal
 */
@Stateless
public class StudentFacade extends AbstractFacade<Student> {
    
    private final Logger LOGGER = LoggerFactory.getLogger(StudentFacade.class);

    @PersistenceContext(unitName = "ExamOnlineClientPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentFacade() {
        super(Student.class);
    }

    public Student findStudentByIdAndPassword(String id, String password) {
        Student student = null;
        if (id != null && password != null) {
            String findStudentByIdAndPassword = "SELECT s FROM Student s WHERE s.id = :id AND s.password = :password";
            try {
                student = (Student) em.createQuery(findStudentByIdAndPassword)
                        .setParameter("id", id)
                        .setParameter("password", password)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOGGER.error(e.getMessage());
            }
        }
        // else  email or/and password is empty
        return student;
    }
}
