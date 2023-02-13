package ProjectDoge.StudentSoup.dto.board;

import ProjectDoge.StudentSoup.entity.board.Board;
import ProjectDoge.StudentSoup.entity.board.BoardCategory;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class BoardDto {

    private Long id;
    private BoardCategory boardCategory;
    private String title;
    private String content;
    private String ip;
    private List<String> fileNames;
    private String nickname;
    private int view;
    private String writeDate;
    private String updateDate;
    private int reviewCount;
    private int likedCount;
    private String memberProfileImageName;
    private boolean like;

    public BoardDto(Board board, Boolean like) {
        this.id = board.getId();
        this.boardCategory = board.getBoardCategory();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.ip = board.getIp();
        this.fileNames = setBoardImageFileNames(board);
        this.nickname = board.getMember().getNickname();
        this.view = board.getView();
        this.writeDate = board.getWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.updateDate = board.getUpdateDate().toString();
        this.reviewCount = board.getBoardReplies().size();
        this.likedCount = board.getLikedCount();
        this.memberProfileImageName = setProfileImageFileName(board.getMember());
        this.like = like;
    }

    private List<String> setBoardImageFileNames(Board board) {
        if(!board.getImageFileList().isEmpty()) {
            List<String> imageFileList = new ArrayList<>();
            for (ImageFile imageFile : board.getImageFileList()) {
                imageFileList.add(imageFile.getFileName());
            }
            return imageFileList;
        }
        return Collections.emptyList();
    }

    private String setProfileImageFileName(Member member){
        if(member.getImageFile() != null){
            return member.getImageFile().getFileName();
        }
        return null;
    }
}
