package ProjectDoge.StudentSoup.entity.school;

import ProjectDoge.StudentSoup.dto.school.SchoolFormDto;
import ProjectDoge.StudentSoup.entity.board.Board;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SCHOOL", uniqueConstraints = {@UniqueConstraint(
        name = "SCHOOL_UNIQUE_CONSTRAINT",
        columnNames = {"NAME", "COORDINATE"}
)})
@Getter
@Setter
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String schoolName;

    @Column(name = "COORDINATE")
    private String schoolCoordinate;

    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Department> departments = new ArrayList<>();

    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Restaurant> restaurants = new ArrayList<>();

    //== 생성 메서드 ==//
    public School createSchool(SchoolFormDto schoolFormDto){
        this.schoolName = schoolFormDto.getSchoolName();
        this.schoolCoordinate = schoolFormDto.getSchoolCoordinate();
    return this;
    }

}
