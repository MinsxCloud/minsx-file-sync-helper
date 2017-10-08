package com.minsx.test;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.minsx.service.SyncFileService;

public class FileTest {

	@Test
	public void testA() throws IOException {
		SyncFileService.copyDirectoryByCompare(new File("E:\\document\\AAA"), new File("E:\\document\\BBB"), null, true,true);
	}

	@Test
	public void testB() throws IOException {
		SyncFileService.deleteDirectoryByCompare(new File("E:\\document\\AAA"), new File("E:\\document\\BBB"), null);
	}

	@Test
	public void testC() throws IOException {
		SyncFileService.syncDirectory(new File("E:\\document\\AAA"), new File("E:\\document\\AAA\\BBB"));
	}

}
