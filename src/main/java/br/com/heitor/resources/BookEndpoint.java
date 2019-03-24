package br.com.heitor.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.heitor.model.Book;
import br.com.heitor.repo.BookManager;

@RestController
@RequestMapping(value="/books")
public class BookEndpoint {

	@Autowired
	private BookManager bookManager;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Book> getBook(@PathVariable String id) {
		Book book = bookManager.get(id);
		return ResponseEntity.ok(book);
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Book>> getAllBooks() {
		return ResponseEntity.ok().body(bookManager.getAll());
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Book> delete(@PathVariable String id) {
		bookManager.delete(bookManager.get(id));
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Book> add(@RequestBody Book book) {
		String bookId = bookManager.add(book);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(bookId).toUri();
		return ResponseEntity.created(uri).build();
	}

}
