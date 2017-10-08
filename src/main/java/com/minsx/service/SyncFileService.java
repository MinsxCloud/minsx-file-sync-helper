package com.minsx.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.minsx.util.FileUtil;

public class SyncFileService {
	/**
	 * The number of bytes in a kilobyte.
	 */
	public static final long ONE_KB = 1024;
	/**
	 * The number of bytes in a megabyte.
	 */
	public static final long ONE_MB = ONE_KB * ONE_KB;
	/**
	 * The file copy buffer size (30 MB)
	 */
	private static final long FILE_COPY_BUFFER_SIZE = ONE_MB * 30;

	public static void copyDirectory(File srcDir, File destDir) throws IOException {
		FileUtil.copyDirectory(srcDir, destDir);
	}

	/**
	 * 增量拷贝文件夹实现
	 */
	private static void doCopyDirectory(final File srcDir, final File destDir, final FileFilter filter,
			final boolean preserveFileDate, final List<String> exclusionList, final boolean increase)
			throws IOException {
		// recurse
		final File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
		if (srcFiles == null) { // null if abstract pathname does not denote a directory, or if an I/O error
								// occurs
			throw new IOException("Failed to list contents of " + srcDir);
		}
		if (destDir.exists()) {
			if (destDir.isDirectory() == false) {
				throw new IOException("Destination '" + destDir + "' exists but is not a directory");
			}
		} else {
			if (!destDir.mkdirs() && !destDir.isDirectory()) {
				throw new IOException("Destination '" + destDir + "' directory cannot be created");
			}
		}
		if (destDir.canWrite() == false) {
			throw new IOException("Destination '" + destDir + "' cannot be written to");
		}
		for (final File srcFile : srcFiles) {
			final File dstFile = new File(destDir, srcFile.getName());
			if (exclusionList == null || !exclusionList.contains(srcFile.getCanonicalPath())) {
				if (srcFile.isDirectory()) {
					doCopyDirectory(srcFile, dstFile, filter, preserveFileDate, exclusionList, increase);
				} else {
					if (increase) {
						if (dstFile.exists()) {
							if (srcFile.lastModified() > dstFile.lastModified()) {
								doCopyFile(srcFile, dstFile, preserveFileDate);
							}
						} else {
							doCopyFile(srcFile, dstFile, preserveFileDate);
						}
					} else {
						doCopyFile(srcFile, dstFile, preserveFileDate);
					}
				}
			}
		}

		// Do this last, as the above has probably affected directory metadata
		if (preserveFileDate) {
			destDir.setLastModified(srcDir.lastModified());
		}
	}

	/**
	 * 为拷贝文件做预检测
	 */
	private static void checkFileRequirements(File src, File dest) throws FileNotFoundException {
		if (src == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (dest == null) {
			throw new NullPointerException("Destination must not be null");
		}
		if (!src.exists()) {
			throw new FileNotFoundException("Source '" + src + "' does not exist");
		}
	}

	/**
	 * 复制文件实现
	 */
	private static void doCopyFile(final File srcFile, final File destFile, final boolean preserveFileDate)
			throws IOException {
		if (destFile.exists() && destFile.isDirectory()) {
			throw new IOException("Destination '" + destFile + "' exists but is a directory");
		}

		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel input = null;
		FileChannel output = null;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			input = fis.getChannel();
			output = fos.getChannel();
			final long size = input.size(); // TODO See IO-386
			long pos = 0;
			long count = 0;
			while (pos < size) {
				final long remain = size - pos;
				count = remain > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : remain;
				final long bytesCopied = output.transferFrom(input, pos, count);
				if (bytesCopied == 0) { // IO-385 - can happen if file is truncated after caching the size
					break; // ensure we don't loop forever
				}
				pos += bytesCopied;
			}
		} finally {
			IOUtils.closeQuietly(output, fos, input, fis);
		}

		final long srcLen = srcFile.length(); // TODO See IO-386
		final long dstLen = destFile.length(); // TODO See IO-386
		if (srcLen != dstLen) {
			throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile
					+ "' Expected length: " + srcLen + " Actual: " + dstLen);
		}
		if (preserveFileDate) {
			destFile.setLastModified(srcFile.lastModified());
		}
	}

	/**
	 * 复制文件夹
	 */
	public static void copyDirectoryByCompare(final File srcDir, final File destDir, final FileFilter filter,
			final boolean preserveFileDate, final boolean increase) throws IOException {
		checkFileRequirements(srcDir, destDir);
		if (!srcDir.isDirectory()) {
			throw new IOException("Source '" + srcDir + "' exists but is not a directory");
		}
		if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
			throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
		}

		// Cater for destination being directory within the source directory
		List<String> exclusionList = null;
		if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
			final File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
			if (srcFiles != null && srcFiles.length > 0) {
				exclusionList = new ArrayList<String>(srcFiles.length);
				for (final File srcFile : srcFiles) {
					final File copiedFile = new File(destDir, srcFile.getName());
					exclusionList.add(copiedFile.getCanonicalPath());
				}
			}
		}
		doCopyDirectory(srcDir, destDir, filter, preserveFileDate, exclusionList, increase);
	}

	/**
	 * 删除文件实现
	 */
	private static void doDeleteFile(final File destFile) throws IOException {
		if (destFile.isDirectory()) {
			File[] files = destFile.listFiles();
			for (File file : files) {
				doDeleteFile(file);
			}
			destFile.delete();
		} else {
			destFile.delete();
		}
	}

	/**
	 * 删除文件夹实现
	 */
	private static void doDeleteDirectory(final File srcDir, final File destDir, final FileFilter filter,
			final List<String> exclusionList) throws IOException {
		// destination
		final File[] destFiles = filter == null ? destDir.listFiles() : destDir.listFiles(filter);
		if (destFiles == null) { // null if abstract pathname does not denote a directory, or if an I/O error
									// occurs
			throw new IOException("Failed to list contents of " + destDir);
		}

		if (srcDir.exists()) {
			if (srcDir.isDirectory() == false) {
				throw new IOException("Destination '" + srcDir + "' exists but is not a directory");
			}
		}

		if (destDir.exists()) {
			if (destDir.isDirectory() == false) {
				throw new IOException("Destination '" + destDir + "' exists but is not a directory");
			}
		}

		if (destDir.canWrite() == false) {
			throw new IOException("Destination '" + destDir + "' cannot be written to");
		}

		for (final File destFile : destFiles) {
			final File srcFile = new File(srcDir, destFile.getName());
			if (exclusionList == null || !exclusionList.contains(srcFile.getCanonicalPath())) {
				if (destFile.isDirectory()) {
					if (srcFile.exists()) {
						doDeleteDirectory(srcFile, destFile, filter, exclusionList);
					} else {
						doDeleteFile(destFile);
					}
				} else {
					if (!srcFile.exists()) {
						doDeleteFile(destFile);
					}
				}
			}
		}
	}

	/**
	 * 删除文件夹
	 */
	public static void deleteDirectoryByCompare(final File srcDir, final File destDir, final FileFilter filter) throws IOException {
		checkFileRequirements(srcDir, destDir);
		if (!srcDir.isDirectory()) {
			throw new IOException("Source '" + srcDir + "' exists but is not a directory");
		}
		if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
			throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
		}

		// Cater for destination being directory within the source directory
		List<String> exclusionList = null;
		if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
			final File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
			if (srcFiles != null && srcFiles.length > 0) {
				exclusionList = new ArrayList<String>(srcFiles.length);
				for (final File srcFile : srcFiles) {
					final File copiedFile = new File(destDir, srcFile.getName());
					exclusionList.add(copiedFile.getCanonicalPath());
				}
			}
		}
		doDeleteDirectory(srcDir, destDir, filter, exclusionList);
	}
	
	/**
	 * 同步文件夹
	 */
	public static void syncDirectory(final File srcDir, final File destDir) throws IOException {
		deleteDirectoryByCompare(srcDir, destDir, null);
		copyDirectoryByCompare(srcDir, destDir, null, true, true);
	}
	

}
