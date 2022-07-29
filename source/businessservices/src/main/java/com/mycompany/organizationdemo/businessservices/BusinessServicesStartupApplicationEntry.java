package com.mycompany.organizationdemo.businessservices;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
//@ServletComponentScan
@Configuration

public class BusinessServicesStartupApplicationEntry {

    public static final String ERROR_MSG_APPLICATION_CONTEXT_XML_NOT_FOUND_THE_ENTRY_DEPENDENCY_INJECTION_FILE_MUST_BE_APPLICATION_CONTEXT_XML = "applicationContext.xml not found.  The entry dependency injection file must be applicationContext.xml";

    public static final String LOG_MSG_XML_RESOURCE_FILENAME = "SpringBootApplication Start.  (Xml.Resource.ShortFilename=\"{}\", Xml.Resource.FullPath=\"{}\")";

    public static final String LOG_MSG_JAVA_CLASS_PATH_FILE_PATH = "SpringBootApplication Start.  java.class.path.file.path=\"{}\"";

    public static final String LOG_MSG_GET_BEAN_DEFINITION_NAME = "SpringBootApplication Start.  getBeanDefinitionName=\"{}\"";

    public static final String APPLICATION_CONTEXT_XML_RELATIVE_FILE_NAME = "/applicationContext.xml";

    public static final String JAVA_CLASS_PATH = "java.class.path";

    public static final String RESOURCE_LOCATION_PATTERN = "classpath*:**/*.xml"; /* ? add *.yml ? */

    private static Logger logger = LoggerFactory.getLogger(BusinessServicesStartupApplicationEntry.class);

    public static void main(final String[] args) {
        try {

            URL resource = BusinessServicesStartupApplicationEntry.class.getResource(APPLICATION_CONTEXT_XML_RELATIVE_FILE_NAME);
            if (null == resource || StringUtils.isBlank(resource.getPath())) {
                throw new FileNotFoundException(ERROR_MSG_APPLICATION_CONTEXT_XML_NOT_FOUND_THE_ENTRY_DEPENDENCY_INJECTION_FILE_MUST_BE_APPLICATION_CONTEXT_XML);
            }

            //import org.springframework.boot.builder.SpringApplicationBuilder;
            //org.springframework.context.ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(ApplicationEntry.class).initializers(new CustomAppContextApplicationContextInitializer(args)).run(args);

            org.springframework.context.ConfigurableApplicationContext applicationContext = SpringApplication.run(BusinessServicesStartupApplicationEntry.class, args);

            if (logger.isInfoEnabled()) {
                Resource[] items = getXMLResources();
                for (Resource item : items) {
                    logger.debug(LOG_MSG_XML_RESOURCE_FILENAME, item.getFilename(), item.isFile() ? item.getFile().getPath() : "");
                }

                List<File> list = getFiles(System.getProperty(JAVA_CLASS_PATH));
                for (File file : list) {
                    logger.debug(LOG_MSG_JAVA_CLASS_PATH_FILE_PATH, file.getPath());
                }

                String[] beanNames = applicationContext.getBeanDefinitionNames();
                Arrays.sort(beanNames);
                for (String beanName : beanNames) {
                    logger.debug(LOG_MSG_GET_BEAN_DEFINITION_NAME, beanName);
                }
            }

        } catch (BeanCreationException | IOException ex) { // | FileNotFoundException ex) {
            Throwable realCause = unwrap(ex);
            logger.error(realCause.getMessage(), realCause);
        }
    }

    private static Resource[] getXMLResources() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);

        Resource[] returnItems = resolver.getResources(RESOURCE_LOCATION_PATTERN);

        Arrays.sort(returnItems, new Comparator<Resource>() {
            public int compare(final Resource a, final Resource b) {
                return Objects.requireNonNull(a.getFilename()).compareTo(Objects.requireNonNull(b.getFilename()));
            }
        });
        return returnItems;
    }

    public static List<File> getFiles(final String paths) {
        List<File> filesList = new ArrayList<File>();
        for (final String path : paths.split(File.pathSeparator)) {
            final File file = new File(path);
            if (file.isDirectory()) {
                recurse(filesList, file);
            } else {
                filesList.add(file);
            }
        }
        return filesList;
    }

    private static void recurse(final List<File> filesList, final File f) {
        File[] list = f.listFiles();
        for (File file : list) {
            if (file.isDirectory()) {
                recurse(filesList, file);
            } else {
                filesList.add(file);
            }
        }
    }

    public static Throwable unwrap(final Throwable ex) {
        if (ex != null && BeanCreationException.class.isAssignableFrom(ex.getClass())) {
            return unwrap(ex.getCause());
        } else {
            return ex;
        }
    }
}
