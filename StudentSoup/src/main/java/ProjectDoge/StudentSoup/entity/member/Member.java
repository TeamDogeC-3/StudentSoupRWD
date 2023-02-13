package ProjectDoge.StudentSoup.entity.member;

import ProjectDoge.StudentSoup.dto.member.MemberFormBDto;
import ProjectDoge.StudentSoup.entity.board.BoardReply;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReviewLike;
import ProjectDoge.StudentSoup.entity.school.Department;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.entity.board.Board;
import ProjectDoge.StudentSoup.entity.board.BoardLike;
import ProjectDoge.StudentSoup.entity.board.BoardReplyLike;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantLike;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReview;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "MEMBER", uniqueConstraints = {@UniqueConstraint(
        name = "MEMBER_UNIQUE_CONSTRAINT",
        columnNames = {"NICKNAME", "ID", "EMAIL"}
)})
@DynamicInsert
@Setter
@Getter
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Size(min = 5, max = 20)
    @NotEmpty
    private String id;

    @NotEmpty
    private String pwd;

    @Size(min = 2, max = 12)
    @NotEmpty
    private String nickname;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IMAGE_FILE_ID")
    private ImageFile imageFile;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Email
    private String email;

    private String registrationDate;

    @Column(name = "MEMBER_CLASSIFICATION")
    @ColumnDefault("'STUDENT'")
    @Enumerated(EnumType.STRING)
    private MemberClassification memberClassification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_ID", nullable = false)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<RestaurantLike> restaurantLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<RestaurantReview> restaurantReviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<RestaurantReviewLike> restaurantReviewLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardLike> boardLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardReplyLike> boardReplyLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<BoardReply> boardReplies = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setSchool(School school){
        if(this.school != null){
            this.school.getMembers().remove(this);
        }
        this.school = school;
        school.getMembers().add(this);
    }
    public void setDepartment(School school, Department department){
        List<Department> departmentList = school.getDepartments();
        for(Department findDepartment : departmentList){
            if(department.getDepartmentName().equals(findDepartment.getDepartmentName())){
                this.department = findDepartment;
                department.getMembers().add(this);
            }
        }
    }

    //== 생성 메서드 ==//
    public Member createMember(MemberFormBDto dto, School school, Department department){
        this.setId(dto.getId());
        this.setPwd(dto.getPwd());
        this.setNickname(dto.getNickname());
        this.setEmail(dto.getEmail());
        this.setImageFile(null);
        this.setGender(dto.getGender());
        this.setRegistrationDate(dateFormat(LocalDateTime.now()));
        this.setSchool(school);
        this.setDepartment(department);
        return this;
    }

    private String dateFormat(LocalDateTime time){
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
