package ProjectDoge.StudentSoup.entity.board;

import ProjectDoge.StudentSoup.dto.board.BoardFormDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.school.Department;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.entity.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOARD")
@DynamicInsert
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_ID")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;

    @Size(min = 2, max = 50)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_NICKNAME")
    private Member member;

    @Size(min = 5, max = 1000)
    private String content;

    private String ip;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<ImageFile> imageFileList = new ArrayList<>();

    private int view;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime writeDate;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updateDate;

    private int likedCount;

    private String isView;

    @ColumnDefault("'N'")
    private String authentication;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardReply> boardReplies = new ArrayList<>();

    @OneToMany(mappedBy = "board" , cascade = CascadeType.REMOVE)
    private List<BoardLike> boardLikes = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setSchool(School school){
        this.school = school;
        school.getBoards().add(this);
    }
    public void setDepartment(Department department){
        this.department = department;

        if(department != null && !department.getBoards().contains(this))
            department.getBoards().add(this);
    }

    //== 생성 메서드 ==//
    public Board createBoard(BoardFormDto form, Member member, School school, Department department) {
        this.setTitle(form.getTitle());
        this.setBoardCategory(form.getBoardCategory());
        this.setWriteDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
        this.setContent(form.getContent());
        this.setIp(form.getIp());
        this.setView(0);
        this.setLikedCount(0);
        this.setMember(member);
        this.setSchool(school);
        this.setDepartment(department);
        this.setIsView(setViewOption(form.getBoardCategory()));
        return this;
    }

    public Board createTestBoard(BoardFormDto form, Member member, School school, Department department) {
        this.setTitle(form.getTitle());
        this.setBoardCategory(form.getBoardCategory());
        this.setWriteDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
        this.setContent(form.getContent());
        this.setView(0);
        this.setLikedCount(10);
        this.setMember(member);
        this.setSchool(school);
        this.setDepartment(department);
        this.setIsView(setViewOption(form.getBoardCategory()));
        return this;
    }

    public Board createBoard(BoardFormDto form, Member member, School school) {
        this.setTitle(form.getTitle());
        this.setBoardCategory(form.getBoardCategory());
        this.setWriteDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
        this.setContent(form.getContent());
        this.setView(0);
        this.setLikedCount(0);
        this.setMember(member);
        this.setSchool(school);
        this.setIsView(setViewOption(form.getBoardCategory()));
        return this;
    }

    private String setViewOption(BoardCategory category){
        if(String.valueOf(category).equals("ANNOUNCEMENT"))
            return "N";
        return "Y";
    }

    public Board editBoard(BoardFormDto boardFormDto, Department department){
        this.setTitle(boardFormDto.getTitle());
        this.setContent(boardFormDto.getContent());
        updateDepartment(department);
        this.setBoardCategory(boardFormDto.getBoardCategory());
        this.setUpdateDate(LocalDateTime.now());
        return this;
    }

    private String dateFormat(LocalDateTime time){
        String formatTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return formatTime;
    }

    public Board createTestBoard(){
        this.setTitle("제목");
        this.setContent("내용");
        this.setIp("ip");
        this.setView(0);
        this.setWriteDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
        this.setLikedCount(0);
        return this;
    }

    //== 비즈니스 로직 ==//
    public void addViewCount(){
        this.view += 1;
    }

    public void addLikeCount() {
        this.likedCount += 1;
    }

    public void minusLikeCount() {
        if(this.likedCount > 0) {
            this.likedCount -= 1;
        }
    }
    public void addImageFile(ImageFile imageFile){
        this.getImageFileList().add(imageFile);

        if(imageFile.getBoard() != this)
             imageFile.setBoard(this);
    }

    private void updateDepartment(Department department){
        if(this.department == null && department != null){
            this.department = department;
            this.department.getBoards().add(this);
        } else if(this.department != null && department != null){
            this.department.getBoards().remove(this);
            this.department = department;
            this.department.getBoards().add(this);
        } else if(this.department != null && department == null){
            this.department.getBoards().remove(this);
            this.department = department;
        }
    }
}
