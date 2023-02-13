package ProjectDoge.StudentSoup.entity.file;

import ProjectDoge.StudentSoup.dto.file.UploadFileDto;
import ProjectDoge.StudentSoup.entity.board.Board;
import ProjectDoge.StudentSoup.entity.restaurant.Restaurant;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReview;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FILE_ID")
    private Long id;

    @Column(name = "NAME")
    String fileName;

    @Column(name = "ORIGINAL_NAME")
    String fileOriginalName;

    @Column(name ="URL")
    String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_REVIEW_ID")
    private RestaurantReview restaurantReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    //== 연관관계 메서드 ==//
    public void setBoard(Board board){
        this.board = board;
    }

    public void setRestaurantReview(RestaurantReview restaurantReview){
        this.restaurantReview = restaurantReview;
    }

    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }


    //== 생성 메서드 ==//
    public ImageFile createFile(UploadFileDto dto){
        this.setFileName(dto.getStoreFileName());
        this.setFileOriginalName(dto.getOriginalFileName());
        this.setFileUrl(dto.getFileUrl());

        return this;
    }
}
