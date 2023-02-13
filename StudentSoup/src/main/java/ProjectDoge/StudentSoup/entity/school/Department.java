package ProjectDoge.StudentSoup.entity.school;

import ProjectDoge.StudentSoup.dto.department.DepartmentFormDto;
import ProjectDoge.StudentSoup.entity.board.Board;
import ProjectDoge.StudentSoup.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DEPARTMENT")
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_ID")
    private School school;

    @Column(name = "NAME")
    private String departmentName;
    @OneToMany(mappedBy = "department",cascade = CascadeType.REMOVE)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "department")
    private List<Board> boards = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setSchool(School school){
        if(this.school != null){
            school.getDepartments().remove(this);
        }
        this.school = school;
        school.getDepartments().add(this);
    }

    public void setMember(Member member){
        if(!this.members.contains(member)){
            this.members.add(member);
        }
    }

    //== 생성 메서드 ==//
    public Department createDepartmentForm(DepartmentFormDto form, School school){
        this.setDepartmentName(form.getDepartmentName());
        this.setSchool(school);
        return this;
    }
}
