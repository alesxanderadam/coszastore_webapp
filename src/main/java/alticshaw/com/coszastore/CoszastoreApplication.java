package alticshaw.com.coszastore;

import alticshaw.com.coszastore.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoszastoreApplication implements CommandLineRunner {
	private final FileStorageService fileStorageService;

	@Autowired
	public CoszastoreApplication(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	public static void main(String[] args) {
		SpringApplication.run(CoszastoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileStorageService.init();
	}
}
