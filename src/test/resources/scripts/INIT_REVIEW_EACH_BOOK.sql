INSERT INTO _user (id, name, last_name, email, username, password, created_at) VALUES (1, 'Duke', 'Last', 'duke@spring.io', 'duke123', '12345678', '2020-08-11T07:45:00.500+0200');

INSERT INTO book (id, title, isbn, author, genre, thumbnail_url, description, publisher, pages) VALUES (1, 'Java 14', '1234567891234', 'duke', 'Software Engineering', 'http://localhost:8080/image.png', 'New features of Java 14', 'JavaPublisher', 42);
INSERT INTO book (id, title, isbn, author, genre, thumbnail_url, description, publisher, pages) VALUES (2, 'Spring Boot', '1234567891235', 'duke', 'Software Engineering', 'http://localhost:8080/imageTwo.png', 'Development with Spring Boot', 'SpringIO', 42);

INSERT INTO review (title, content, rating, created_at, book_id, user_id) VALUES ('Nice book!', 'Can recommend reading it', 5, '2020-08-11T07:45:00.500+0200', 1, 1);
INSERT INTO review (title, content, rating, created_at, book_id, user_id) VALUES ('Did not understand anything', 'To advanced for beginners', 1, '2020-08-11T07:45:00.500+0200', 2, 1);
INSERT INTO review (title, content, rating, created_at, book_id, user_id) VALUES ('Too easy', 'Nice examples, but think this book is rather for beginners', 5, '2020-08-11T07:45:00.500+0200', 2, 1);
