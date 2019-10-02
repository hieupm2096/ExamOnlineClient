/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author oswal
 */
@Entity
@Table(catalog = "examonline", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamQuestionAnswer.findAll", query = "SELECT e FROM ExamQuestionAnswer e")
    , @NamedQuery(name = "ExamQuestionAnswer.findById", query = "SELECT e FROM ExamQuestionAnswer e WHERE e.id = :id")
    , @NamedQuery(name = "ExamQuestionAnswer.findByContent", query = "SELECT e FROM ExamQuestionAnswer e WHERE e.content = :content")
    , @NamedQuery(name = "ExamQuestionAnswer.findByIsCorrect", query = "SELECT e FROM ExamQuestionAnswer e WHERE e.isCorrect = :isCorrect")})
public class ExamQuestionAnswer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "_id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "_content")
    private String content;
    @Column(name = "_is_correct")
    private Boolean isCorrect;
    @JoinColumns({
        @JoinColumn(name = "_exam_id", referencedColumnName = "_exam_id")
        , @JoinColumn(name = "_question_id", referencedColumnName = "_question_id")})
    @ManyToOne(optional = false)
    private ExamQuestion examQuestion;
    @JoinColumn(name = "_answer_id", referencedColumnName = "_id")
    @ManyToOne(optional = false)
    private Answer answer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answer")
    private List<ExamStudentAnswer> examstudentanswerList;

    public ExamQuestionAnswer() {
    }

    public ExamQuestionAnswer(String id) {
        this.id = id;
    }

    public ExamQuestionAnswer(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public ExamQuestion getExamQuestion() {
        return examQuestion;
    }

    public void setExamQuestion(ExamQuestion examQuestion) {
        this.examQuestion = examQuestion;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @XmlTransient
    public List<ExamStudentAnswer> getExamstudentanswerList() {
        return examstudentanswerList;
    }

    public void setExamstudentanswerList(List<ExamStudentAnswer> examstudentanswerList) {
        this.examstudentanswerList = examstudentanswerList;
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
        if (!(object instanceof ExamQuestionAnswer)) {
            return false;
        }
        ExamQuestionAnswer other = (ExamQuestionAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ExamQuestionAnswer[ id=" + id + " ]";
    }
    
}
