/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Exam.findAll", query = "SELECT e FROM Exam e")
    , @NamedQuery(name = "Exam.findById", query = "SELECT e FROM Exam e WHERE e.id = :id")
    , @NamedQuery(name = "Exam.findByDescription", query = "SELECT e FROM Exam e WHERE e.description = :description")
    , @NamedQuery(name = "Exam.findByNumOfQuestion", query = "SELECT e FROM Exam e WHERE e.numOfQuestion = :numOfQuestion")
    , @NamedQuery(name = "Exam.findByDuration", query = "SELECT e FROM Exam e WHERE e.duration = :duration")
    , @NamedQuery(name = "Exam.findByStartTime", query = "SELECT e FROM Exam e WHERE e.startTime = :startTime")})
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "_id")
    private String id;
    
    @Size(max = 200)
    @Column(name = "_description")
    private String description;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "_num_of_question")
    private int numOfQuestion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "_duration")
    private int duration;
    
    @Column(name = "_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam")
    private List<ExamQuestion> examquestionList;
    
    @JoinColumn(name = "_user_id", referencedColumnName = "_id")
    @ManyToOne
    private User userId;
    
    @JoinColumn(name = "_course_id", referencedColumnName = "_id")
    @ManyToOne(optional = false)
    private Course courseId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam")
    private List<ExamStudent> examStudentList;

    public Exam() {
    }

    public Exam(String id) {
        this.id = id;
    }

    public Exam(String id, int numOfQuestion, int duration) {
        this.id = id;
        this.numOfQuestion = numOfQuestion;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumOfQuestion() {
        return numOfQuestion;
    }

    public void setNumOfQuestion(int numOfQuestion) {
        this.numOfQuestion = numOfQuestion;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @XmlTransient
    public List<ExamQuestion> getExamquestionList() {
        return examquestionList;
    }

    public void setExamquestionList(List<ExamQuestion> examquestionList) {
        this.examquestionList = examquestionList;
    }


    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

    @XmlTransient
    public List<ExamStudent> getExamStudentList() {
        return examStudentList;
    }

    public void setExamStudentList(List<ExamStudent> examStudentList) {
        this.examStudentList = examStudentList;
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
        if (!(object instanceof Exam)) {
            return false;
        }
        Exam other = (Exam) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Exam[ id=" + id + " ]";
    }
    
}
