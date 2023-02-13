package ProjectDoge.StudentSoup.repository.file;

import ProjectDoge.StudentSoup.entity.file.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<ImageFile, Long>, FileRepositoryCustom {
}
