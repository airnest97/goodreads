package africa.semicolon.goodreads.controllers.requestsAndResponse;

import africa.semicolon.goodreads.models.enums.AgeRate;
import africa.semicolon.goodreads.models.enums.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
public class BookItemUploadRequest {
    @NotNull @NotBlank
    private String title;
    @NotNull @NotBlank
    private String author;
    @NotNull @NotBlank
    private String description;
    @NotNull @NotBlank
    private String coverImageFileName;
    @NotNull @NotBlank
    private String fileName;
    @NotNull
    private AgeRate ageRating;
    @NotNull @NotBlank
    private String uploadedBy;
    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateUploaded;
    @NotNull
    private Category category;
}
