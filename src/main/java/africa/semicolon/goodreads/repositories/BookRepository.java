package africa.semicolon.goodreads.repositories;

import africa.semicolon.goodreads.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    @Override
    Page<Book> findAll(Pageable pageable);

    Book findBookByTitleIsIgnoreCase(String title);
    List<Book> findBookByUploadedBy(String email);
}
