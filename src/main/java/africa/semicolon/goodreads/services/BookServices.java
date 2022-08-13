package africa.semicolon.goodreads.services;

import africa.semicolon.goodreads.controllers.requestsAndResponse.BookItemUploadRequest;
import africa.semicolon.goodreads.dtos.BookDto;
import africa.semicolon.goodreads.exception.GoodReadsException;
import africa.semicolon.goodreads.models.Book;
import africa.semicolon.goodreads.models.Credentials;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface BookServices {
    CompletableFuture<Map<String, Credentials>> generateUploadURLs(String fileExtension, String imageExtension) throws ExecutionException, InterruptedException;

    Book save(BookItemUploadRequest bookItemUploadrequest) throws GoodReadsException;

    Book findBookByTitle(String title) throws GoodReadsException;
    Map<String, String> generateDownloadUrls(String fileName, String imageFileName) throws GoodReadsException;
    Map<String, Object> findAll(int pageNumber, int noOfItems);
    List<BookDto> getAllBooksForUser(String email);
}
