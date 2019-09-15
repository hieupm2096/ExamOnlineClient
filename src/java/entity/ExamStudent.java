/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author oswal
 */
@Entity
@Table(catalog = "examonline", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamStudent.findAll", query = "SELECT e FROM ExamStudent e")
    , @NamedQuery(name = "ExamStudent.findByExamId", query = "SELECT e FROM ExamStudent e WHERE e.examStudentPK.examId = :examId")
    , @NamedQuery(name = "ExamStudent.findByStudentId", query = "SELECT e FROM ExamStudent e WHERE e.examStudentPK.studentId = :studentId")
    , @NamedQuery(name = "ExamStudent.findByResult", query = "SELECT e FROM ExamStudent e WHERE e.result = :result")
    , @NamedQuery(name = "ExamStudent.findByStartTime", query = "SELECT e FROM ExamStudent e WHERE e.startTime = :startTime")
    , @NamedQuery(name = "ExamStudent.findByEndTime", query = "SELECT e FROM ExamStudent e WHERE e.endTime = :endTime")
    , @NamedQuery(name = "ExamStudent.findByPasscode", query = "SELECT e FROM ExamStudent e WHERE e.passcode = :passcode")})
public class ExamStudent implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ExamStudentPK examStudentPK;
    @Column(name = "_result")
    private Integer result;
    @Column(name = "_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Size(max = 7)
    @Column(name = "_passcode")
    private String passcode;
    @ManyToMany(mappedBy = "examstudentList")
    private List<ExamQuestionAnswer> examQuestionAnswerList;
    @JoinColumn(name = "_exam_id", referencedColumnName = "_id", insertable=false, updatable=false)
    @ManyToOne(optional = false)
    private Exam exam;
    @JoinColumn(name = "_student_id", referencedColumnName = "_id", insertable=false, updatable=false)
    @ManyToOne(optional = false)
    private Student student;

    public ExamStudent() {
    }

    public ExamStudent(ExamStudentPK examStudentPK) {
        this.examStudentPK = examStudentPK;
    }

    public ExamStudent(String examId, String studentId) {
        this.examStudentPK = new ExamStudentPK(examId, studentId);
    }

    public ExamStudentPK getExamStudentPK() {
        return examStudentPK;
    }

    public void setExamStudentPK(ExamStudentPK examStudentPK) {
        this.examStudentPK = examStudentPK;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public List<ExamQuestionAnswer> getExamQuestionAnswerList() {
        return examQuestionAnswerList;
    }

    public void setExamQuestionAnswerList(List<ExamQuestionAnswer> examQuestionAnswerList) {
        this.examQuestionAnswerList = examQuestionAnswerList;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (examStudentPK != null ? examStudentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamStudent)) {
            return false;
        }
        ExamStudent other = (ExamStudent) object;
        if ((this.examStudentPK == null && other.examStudentPK != null) || (this.examStudentPK != null && !this.examStudentPK.equals(other.examStudentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ExamStudent[ examStudentPK=" + examStudentPK + " ]";
    }
    
}
