package ProjectDoge.StudentSoup.dto.member;


import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.member.Member;
import lombok.Data;
import lombok.Getter;

@Data
public class MemberMyPageDto {

    private String nickName;
    private String imageName;
    private String schoolName;
    private String departmentName;
    private String registrationDate;

    public MemberMyPageDto(Member member){
        this.nickName = member.getNickname();
        this.imageName = setImageFileName(member.getImageFile());
        this.schoolName = member.getSchool().getSchoolName();
        this.departmentName = member.getDepartment().getDepartmentName();
        this.registrationDate = member.getRegistrationDate();
    }

    private String setImageFileName(ImageFile imageFile){
        if(imageFile != null){
            return imageFile.getFileName();
        }
        return null;
    }
}
