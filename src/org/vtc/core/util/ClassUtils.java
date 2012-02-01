package org.vtc.core.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

/**
 * Helper class for class operations.
 * 
 * @author Michael Sieber
 */
public final class ClassUtils {
	private static final Logger LOGGER = Logger
			.getLogger(ClassUtils.class);

	/**
	 * Avoid instances, this is a static utility class.
	 */
	private ClassUtils() {
		// nothing to do here
	}

	/**
	 * Get the class name without package declaration.
	 * 
	 * @param clazz Class from which the name is to create
	 * @return The class name without package declaration
	 */
	public static String getClassName(Class<?> clazz) {
		String fullClassName = clazz.getName();
		int firstChar;
		firstChar = fullClassName.lastIndexOf('.') + 1;
		if (firstChar > 0) {
			fullClassName = fullClassName.substring(firstChar);
		}
		return fullClassName;
	}

	/**
	 * Get all classes with a specific annotation from a folder.
	 * 
	 * @param folder The folder from which the classes should be loaded
	 * @param annotation The annotation for which the method should look for
	 * @return A list of all classes in the given package with the given
	 *         annotation
	 */
	public static List<Class<?>> getClassesWithAnnotation(String folder,
			Class<? extends Annotation> annotation) {
		List<Class<?>> ret = new ArrayList<Class<?>>();
		try {
			List<Class<?>> classes = getClassNamesFromFolder(folder);

			for (Class<?> clazz : classes) {
				if (clazz.isAnnotationPresent(annotation)) {
					ret.add(clazz);
				}
			}
		} catch (Exception e) {
			LOGGER.debug(
					"Error loading classes from folder: " + folder.toString(),
					e);
		}

		return ret;
	}

	/**
	 * Get a list of all classes from a file.
	 * 
	 * @param files A list of files
	 * @param rootFolder The root folder from which the files are loaded
	 * @return A list of all classes in the given file/directory
	 * @throws IOException Thrown if an error during file reading occurs
	 * @throws ClassNotFoundException Thrown if a class cannot be loaded
	 */
	public static List<Class<?>> getClassNamesFromFiles(File[] files,
			String rootFolder)
			throws IOException, ClassNotFoundException {
		List<Class<?>> out = new ArrayList<Class<?>>();

		if (files != null) {

			// loop through all files
			for (File file : files) {

				// if file is directory open it and process its files
				if (file.isDirectory()) {
					out.addAll(getClassNamesFromFiles(file.listFiles(),
							rootFolder));
				} else if (hasExtension(file.getName(), ".jar")) {
					out.addAll(getClassNamesFromJAR(file));
				} else {
					String entryName = file.toString();
					entryName =
							entryName.substring(0, entryName.lastIndexOf('.'));
					entryName =
							entryName.replace(rootFolder + "\\", "")
									.replace("\\", ".");
					out.add(Class.forName(entryName));
					LOGGER.debug("Class " + entryName + " added.");
				}
			}
		}

		return out;
	}

	/**
	 * Get a list of all classes within a jar file.
	 * 
	 * @param jar The jar file from which the classes should be loaded
	 * @return A list of all classes from the jar file
	 * @throws IOException Thrown if an error during jar file loading occurs
	 * @throws ClassNotFoundException Thrown if the class cannot be loaded
	 */
	public static List<Class<?>> getClassNamesFromJAR(File jar)
			throws IOException, ClassNotFoundException {
		List<Class<?>> out = new ArrayList<Class<?>>();
		JarFile file = new JarFile(jar);

		// loop through all jar file entries
		Enumeration<JarEntry> entries = file.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();

			// only process *.class files
			if (hasExtension(entry.toString(), ".class")) {
				String fullClassName = entry.toString().replace("/", ".");
				fullClassName = fullClassName.replace(".class", "");
				out.add(Class.forName(fullClassName));
				LOGGER.debug("Class " + fullClassName + " added.");
			}
		}

		return out;
	}

	/**
	 * Check if a file has a specific extension.
	 * 
	 * @param file The file to check
	 * @param extension The extension which is expected
	 * @return True if the file has the expected extension
	 */
	public static boolean hasExtension(String file, String extension) {
		if (file != null && !file.equals("") && extension != null
				&& !extension.equals("")) {
			String ex = getFileExtension(file);

			if (ex != null && ex.equalsIgnoreCase(extension)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Get the extension of a file.
	 * 
	 * @param file The file from which the extension should be loaded
	 * @return The file extension or an empty string if no extension can be
	 *         found
	 */
	public static String getFileExtension(String file) {
		String extension = "";

		if (file != null && !file.equals("")) {
			int dotPos = file.lastIndexOf(".");
			extension = file.substring(dotPos);
		}

		return extension;
	}

	/**
	 * Get a list of all classes in a given folder.
	 * 
	 * @param folder The folder from which the classes should be loaded
	 * @return A list of all classes in the given package
	 * @throws IOException Thrown if an error occures while reading the class or
	 *             opening the folders
	 * @throws ClassNotFoundException Thrown if a class cannot be found
	 */
	public static List<Class<?>> getClassNamesFromFolder(String folder)
			throws IOException, ClassNotFoundException {
		File dir = new File(folder);
		return getClassNamesFromFiles(dir.listFiles(), folder);
	}
}
