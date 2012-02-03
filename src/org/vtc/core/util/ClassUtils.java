package org.vtc.core.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;
import org.vtc.core.condition.ICondition;
import org.vtc.core.condition.util.ConditionHelper;

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
	 *         annotation or an empty list if any error during loading occures
	 */
	public static List<Class<?>> getClassesWithAnnotation(String folder,
			final Class<? extends Annotation> annotation) {
		ICondition<Class<?>> condition = new ICondition<Class<?>>() {

			@Override
			public boolean check(Class<?> check) {
				return check.isAnnotationPresent(annotation);
			}
		};

		try {
			return getClassNamesFromFolder(folder, condition);
		} catch (Exception e) {
			LOGGER.info("Error loading classes from folder " + folder);
			return new ArrayList<Class<?>>();
		}
	}

	/**
	 * Get a list of all classes from a file.
	 * 
	 * @param files A list of files
	 * @param rootFolder The root folder from which the files are loaded * @param
	 *            condition The condition for loading classes. This is used to
	 *            determine which classes should be loaded. This parameter may
	 *            be null.
	 * @param condition The condition for loading classes. This is used to
	 *            determine which classes should be loaded. If this parameter is
	 *            null, a default condition which always returns true will be
	 *            used.
	 * @return A list of all classes in the given file/directory
	 * @throws IOException Thrown if an error during file reading occurs
	 * @throws ClassNotFoundException Thrown if a class cannot be loaded
	 */
	public static List<Class<?>> getClassNamesFromFiles(File[] files,
			String rootFolder, ICondition<Class<?>> condition)
			throws IOException, ClassNotFoundException {
		List<Class<?>> out = new ArrayList<Class<?>>();

		if (files != null) {

			// if the condition is null get a default condition
			if (condition == null) {
				condition = ConditionHelper.createDefault();
			}

			// loop through all files
			for (File file : files) {

				// if file is directory open it and process its files
				if (file.isDirectory()) {
					out.addAll(getClassNamesFromFiles(file.listFiles(),
							rootFolder, condition));
				} else if (hasExtension(file.getName(), ".jar")) {
					out.addAll(getClassNamesFromJAR(file, condition));
				} else {
					URL folder =
							new URL("file:///" + rootFolder + "/");
					URLClassLoader loader =
							new URLClassLoader(
									new URL[] { folder }, Thread
											.currentThread()
											.getContextClassLoader());

					// get the full class name
					String entryName = file.toString();
					entryName =
							entryName.substring(0, entryName.lastIndexOf('.'));
					entryName =
							entryName.replace(rootFolder + "\\", "")
									.replace("\\", ".");

					// load the class
					Class<?> clazz = loader.loadClass(entryName);

					if (condition.check(clazz)) {
						out.add(clazz);
						LOGGER.debug("Class " + entryName + " added.");
					}
				}
			}
		}

		return out;
	}

	/**
	 * Get a list of all classes within a jar file.
	 * 
	 * @param jar The jar file from which the classes should be loaded
	 * @param condition The condition for loading classes. This is used to
	 *            determine which classes should be loaded. This parameter may
	 *            be null.
	 * @return A list of all classes from the jar file
	 * @throws IOException Thrown if an error during jar file loading occurs
	 * @throws ClassNotFoundException Thrown if the class cannot be loaded
	 */
	public static List<Class<?>> getClassNamesFromJAR(File jar,
			ICondition<Class<?>> condition)
			throws IOException, ClassNotFoundException {
		List<Class<?>> out = new ArrayList<Class<?>>();

		// if the condition is null get a default condition
		if (condition == null) {
			condition = ConditionHelper.createDefault();
		}

		// load the jar
		JarFile file = new JarFile(jar);
		URLClassLoader loader =
				new URLClassLoader(new URL[] { jar.toURI().toURL() }, Thread
						.currentThread().getContextClassLoader());

		// loop through all jar file entries
		Enumeration<JarEntry> entries = file.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();

			// only process *.class files
			if (hasExtension(entry.toString(), ".class")) {
				String fullClassName = entry.toString().replace("/", ".");
				fullClassName = fullClassName.replace(".class", "");

				Class<?> clazz = loader.loadClass(fullClassName);
				if (condition.check(clazz)) {
					out.add(clazz);
					LOGGER.debug("Class " + fullClassName + " added.");
				}
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
	 * @param condition The condition for loading classes. This is used to
	 *            determine which classes should be loaded. This parameter may
	 *            be null.
	 * @return A list of all classes in the given package
	 * @throws IOException Thrown if an error occures while reading the class or
	 *             opening the folders
	 * @throws ClassNotFoundException Thrown if a class cannot be found
	 */
	public static List<Class<?>> getClassNamesFromFolder(String folder,
			ICondition<Class<?>> condition)
			throws IOException, ClassNotFoundException {
		File dir = new File(folder);
		return getClassNamesFromFiles(dir.listFiles(), folder, condition);
	}
}
