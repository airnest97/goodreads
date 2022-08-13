package africa.semicolon.goodreads.services;

import africa.semicolon.goodreads.controllers.requestsAndResponse.BookItemUploadRequest;
import africa.semicolon.goodreads.dtos.BookDto;
import africa.semicolon.goodreads.exception.GoodReadsException;
import africa.semicolon.goodreads.models.Book;
import africa.semicolon.goodreads.models.Credentials;
import africa.semicolon.goodreads.repositories.BookRepository;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class BookServicesImpl implements BookServices{

    private final AmazonS3 amazonS3;
    private final String IMAGE_BUCKET;
    private final String FILE_BUCKET;

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;


    public BookServicesImpl(AmazonS3 amazonS3, BookRepository bookRepository, ModelMapper modelMapper) {
        this.amazonS3 = amazonS3;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.FILE_BUCKET = System.getenv("FILE_BUCKET");
        this.IMAGE_BUCKET = System.getenv("IMAGE_BUCKET");
    }

    private String generateUrl(String bucketName, String fileName, HttpMethod httpMethod){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10);
        return amazonS3.generatePresignedUrl(bucketName, fileName, calendar.getTime(), httpMethod).toString();
    }

    @Override
    @Async
    public CompletableFuture<Map<String, Credentials>> generateUploadURLs(String fileExtension, String imageExtension) throws ExecutionException, InterruptedException {
        if (!amazonS3.doesBucketExistV2(FILE_BUCKET)){
            amazonS3.createBucket(FILE_BUCKET);
        }
        if (!amazonS3.doesBucketExistV2(IMAGE_BUCKET)){
            amazonS3.createBucket(IMAGE_BUCKET);
        }
        String fileName = UUID.randomUUID()+fileExtension;
        String imageFileName = UUID.randomUUID()+imageExtension;

        String fileUploadUrl = generateUploadUrlForFile(fileName).get();
        String imageUploadUrl = generateUploadUrlForImage(imageFileName).get();

        Map<String, Credentials> map = new HashMap<>();
        map.put("fileCredentials", new Credentials(fileName, fileUploadUrl));
        map.put("imageCredentials", new Credentials(imageFileName, imageUploadUrl));
        return CompletableFuture.completedFuture(map);
    }

    @Override
    public Book save(BookItemUploadRequest bookItemUploadRequest) {
        Book book = modelMapper.map(bookItemUploadRequest, Book.class);
        return bookRepository.save(book);
    }

    @Override
    public Book findBookByTitle(String title) throws GoodReadsException {
        Book book = bookRepository.findBookByTitleIsIgnoreCase(title);
        if (book == null){
            throw new GoodReadsException("Book not found", 404);
        }
        return book;

    }

    @Override
    public Map<String, String> generateDownloadUrls(String fileName, String imageFileName) throws GoodReadsException {
        if (!amazonS3.doesObjectExist(FILE_BUCKET, fileName)){
            throw new GoodReadsException("File does not exist", 400);
        }
        if (!amazonS3.doesObjectExist(IMAGE_BUCKET, imageFileName)){
            throw new GoodReadsException("File does not exist", 400);
        }
        String downloadUrlForFile = generateUrl(FILE_BUCKET, fileName, HttpMethod.GET);
        String downloadUrlForCoverImage = generateUrl(IMAGE_BUCKET, imageFileName, HttpMethod.GET);

        Map<String, String> map = new HashMap<>();
        map.put("file download url", downloadUrlForFile);
        map.put("cover image download url", downloadUrlForCoverImage);
        return map;
    }

    @Override
    public  Map<String, Object> findAll(int numberOfPages, int numberOfItems) {
        Pageable pageable = PageRequest.of(numberOfPages, numberOfItems,Sort.by("title"));
        Page<Book> page = bookRepository.findAll(pageable);
        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("totalNumberOfPages", page.getTotalPages());
        pageResult.put("totalNumberOfElementsInDatabase", page.getTotalElements());
        if (page.hasNext()){
            pageResult.put("nextPage", page.nextPageable());
        }
        if (page.hasPrevious()){
            pageResult.put("previousPage", page.previousPageable());
        }
        pageResult.put("books", page.getContent());
        pageResult.put("NumberOfElementsInPage", page.getNumberOfElements());
        pageResult.put("pageNumber", page.getNumber());
        pageResult.put("size", page.getSize());
        return pageResult;
    }

    @Override
    public List<BookDto> getAllBooksForUser(String email) {
        List<Book> list = bookRepository.findBookByUploadedBy(email);
        List<BookDto> bookDtoList = new ArrayList<>();
        for (Book book: list){
            BookDto bookDto = modelMapper.map(book, BookDto.class);
            bookDtoList.add(bookDto);
        }
        return bookDtoList;
    }

    @Async
    public CompletableFuture<String> generateUploadUrlForImage(String imageFileName) {
        log.info("Generating upload url for image");
        return CompletableFuture.completedFuture(generateUrl(IMAGE_BUCKET, imageFileName, HttpMethod.PUT));
    }

    @Async
    public CompletableFuture<String> generateUploadUrlForFile(String fileName){
        log.info("Generating upload url for file");
        return CompletableFuture.completedFuture(generateUrl(FILE_BUCKET, fileName, HttpMethod.PUT));
    }
}
