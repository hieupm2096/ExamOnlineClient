/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author oswal
 */
@Entity
@Table(name = "examstudentanswer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Examstudentanswer.findAll", query = "SELECT e FROM ExamStudentAnswer e")
    , @NamedQuery(name = "Examstudentanswer.findById", query = "SELECT e FROM ExamStudentAnswer e WHERE e.id = :id")})
public class ExamStudentAnswer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_id")
    private Integer id;
    @JoinColumn(name = "_answer_id", referencedColumnName = "_id")
    @ManyToOne(optional = false)
    private ExamQuestionAnswer answer;
    @JoinColumns({
        @JoinColumn(name = "_exam_id", referencedColumnName = "_exam_id")
        , @JoinColumn(name = "_student_id", referencedColumnName = "_student_id")})
    @ManyToOne(optional = false)
    private ExamStudent examStudent;

    public ExamStudentAnswer() {
    }

    public ExamStudentAnswer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ExamQuestionAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(ExamQuestionAnswer answerId) {
        this.answer = answerId;
    }

    public ExamStudent getExamStudent() {
        return examStudent;
    }

    public void setExamStudent(ExamStudent examstudent) {
        this.examStudent = examstudent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamStudentAnswer)) {
            return false;
        }
        ExamStudentAnswer other = (ExamStudentAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Examstudentanswer[ id=" + id + " ]";
    }
    
}
