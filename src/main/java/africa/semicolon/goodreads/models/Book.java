package africa.semicolon.goodreads.models;

import africa.semicolon.goodreads.models.enums.AgeRate;
import africa.semicolon.goodreads.models.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Document("books")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String description;
    private String coverImageUrl;
    private AgeRate ageRating;
    private String uploadedBy;
    private LocalDateTime dateUploaded;
    private Category category;
}
