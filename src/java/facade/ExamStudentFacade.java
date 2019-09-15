/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.ExamStudent;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author oswal
 */
@Stateless
public class ExamStudentFacade extends AbstractFacade<ExamStudent> {

    @PersistenceContext(unitName = "ExamOnlineClientPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExamStudentFacade() {
        super(ExamStudent.class);
    }

    public ExamStudent findAvailableExamStudentByExamIdAndStudentId(String examId, String studentId)
            throws NoResultException, NonUniqueResultException {
        String findAvailableExamStudentByExamIdAndStudentId = "SELECT e FROM ExamStudent e "
                + "WHERE e.examStudentPK.examId = :examId "
                + "AND e.examStudentPK.studentId = :studentId";
        em.getEntityManagerFactory().getCache().evictAll();
        ExamStudent es = null;
        try {
            es = (ExamStudent) em.createQuery(findAvailableExamStudentByExamIdAndStudentId)
                    .setParameter("examId", examId)
                    .setParameter("studentId", studentId)
                    .getSingleResult();
        } catch (NoResultException e) {

        }
        return es;
    }
}
