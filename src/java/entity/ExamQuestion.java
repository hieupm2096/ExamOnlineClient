/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author oswal
 */
@Entity
@Table(name = "examquestion", catalog = "examonline", schema = "")
@NamedQueries({
    @NamedQuery(name = "ExamQuestion.findAll", query = "SELECT e FROM ExamQuestion e")
    , @NamedQuery(name = "ExamQuestion.findByExamId", query = "SELECT e FROM ExamQuestion e WHERE e.examQuestionPK.examId = :examId")
    , @NamedQuery(name = "ExamQuestion.findByQuestionId", query = "SELECT e FROM ExamQuestion e WHERE e.examQuestionPK.questionId = :questionId")
    , @NamedQuery(name = "ExamQuestion.findByQuestionTypeId", query = "SELECT e FROM ExamQuestion e WHERE e.questionTypeId = :questionTypeId")
    , @NamedQuery(name = "ExamQuestion.findByContent", query = "SELECT e FROM ExamQuestion e WHERE e.content = :content")
    , @NamedQuery(name = "ExamQuestion.findByCourseId", query = "SELECT e FROM ExamQuestion e WHERE e.courseId = :courseId")
    , @NamedQuery(name = "ExamQuestion.findByStatus", query = "SELECT e FROM ExamQuestion e WHERE e.status = :status")})
public class ExamQuestion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected ExamQuestionPK examQuestionPK;
    
    @JoinColumn(name = "_question_type_id", referencedColumnName = "_id")
    @ManyToOne(optional = false)
    private QuestionType questionTypeId;
    
    @Size(max = 200)
    @Column(name = "_content")
    private String content;
    
    @JoinColumn(name = "_course_id", referencedColumnName = "_id")
    @ManyToOne
    private Course courseId;
    
    @Column(name = "_status")
    private Boolean status;
    
    @JoinColumn(name = "_exam_id", referencedColumnName = "_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Exam exam;
    
    @JoinColumn(name = "_question_id", referencedColumnName = "_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Question question;
    
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn({ @JoinColumn(name = "_exam_id", referencedColumnName = "_exam_id"), @JoinColumn(name = "_question_id", referencedColumnName = "_questions_id") })
//    private List<ExamQuestionAnswer> examQuestionAnswerList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "examQuestion")
    private List<ExamQuestionAnswer> examQuestionAnswerList;


    public ExamQuestion() {
    }

    public ExamQuestion(ExamQuestionPK examQuestionPK) {
        this.examQuestionPK = examQuestionPK;
    }

    public ExamQuestion(String examId, String questionId) {
        this.examQuestionPK = new ExamQuestionPK(examId, questionId);
    }

    public ExamQuestionPK getExamQuestionPK() {
        return examQuestionPK;
    }

    public void setExamQuestionPK(ExamQuestionPK examQuestionPK) {
        this.examQuestionPK = examQuestionPK;
    }

    public QuestionType getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(QuestionType questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (examQuestionPK != null ? examQuestionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamQuestion)) {
            return false;
        }
        ExamQuestion other = (ExamQuestion) object;
        if ((this.examQuestionPK == null && other.examQuestionPK != null) || (this.examQuestionPK != null && !this.examQuestionPK.equals(other.examQuestionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ExamQuestion[ examQuestionPK=" + examQuestionPK + " ]";
    }

    @XmlTransient
    public List<ExamQuestionAnswer> getExamQuestionAnswerList() {
        return examQuestionAnswerList;
    }

    public void setExamQuestionAnswerList(List<ExamQuestionAnswer> examQuestionAnswerList) {
        this.examQuestionAnswerList = examQuestionAnswerList;
    }
}
